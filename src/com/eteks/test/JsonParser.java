package com.eteks.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
  private URL url = null;
  
  
  public JsonParser(URL url) {
	this.url = url;
  }
  public SensorFeed parseResponse() throws IOException, JsonParseException {
	  SensorFeed sensorFeed = null;
	  int iid,itemp,iintdoor,iextdoor,ibat,itime;
	  try {
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5000);
	  	//InputStream is = url.openStream();
	  	BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	  	String line = br.readLine();
  		if ((line!=null) && line.startsWith("{\"Sensor\":")) {
  			sensorFeed = new SensorFeed();
  			List<Sensor> sensors = new ArrayList<Sensor>();
  		  	while ((line=br.readLine())!=null) {
  		  		if (line.startsWith("	{\"Id\":")) {
  		  			iid = line.indexOf("Id\":\"");
  		  			itemp = line.indexOf("Temp\":\"");
  		  			iintdoor = line.indexOf("Int door\":\"");
  		  			iextdoor = line.indexOf("Ext door\":\"");
  		  			ibat = line.indexOf("Battery V\":\"");
  		  			itime = line.indexOf("Read time\":\"");
  		  			if (iid>0 && itemp>0 && iintdoor>0 && iextdoor>0 && ibat>0 && itime>0) {
  		  				Sensor sensor = new Sensor();
  		  				sensor.setId(Integer.parseInt(line.substring(iid+5,iid+7)));
  		  				sensor.setTemp(Float.parseFloat(line.substring(itemp+7,itemp+12)));
  		  				sensor.setIntDoor(Integer.parseInt(line.substring(iintdoor+11,iintdoor+12)));
  		  				sensor.setExtDoor(Integer.parseInt(line.substring(iextdoor+11,iextdoor+12)));
  		  				sensor.setBatteryValue(Float.parseFloat(line.substring(ibat+12,ibat+16)));
  		  				sensor.setReadTime(line.substring(itime+12,itime+31));
  		  				sensors.add(sensor);		  				
  		  			} else {
  		  				System.err.println("Error in reading sensor item!");
  		  				throw new JsonParseException("Error parsing line for id: "+sensors.size()+": "+line);
  		  			}
  		  		} else if (line.startsWith("],")) {
  		  			sensorFeed.setSensors(sensors);
  		  		} else if (line.startsWith("\"Started at\":")) {
  		  			sensorFeed.setStartTime(line.substring(14,33));
  		  		}
  		  	}
  		} else {
  			System.err.println("Error in reading sensorfeed!");
  			throw new JsonParseException("First line not found");
  		}
	  } catch (IOException ioe) {
		  System.err.println(ioe.getMessage());
		  throw new IOException(ioe.getMessage());
		  
	  }
	  return sensorFeed;
  }
}

