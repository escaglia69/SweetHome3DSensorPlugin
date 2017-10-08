package com.eteks.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;

public class SensorFeedActionListener implements ActionListener {

  private static URL jsonUrl = null;
  private Home home;

  public SensorFeedActionListener() {
  }

  public SensorFeedActionListener(Home home, String url) {
	  try {
		if ((url !=null) && (!url.equals(""))) {
			jsonUrl = new URL(url);
		} else {
			jsonUrl = new URL("http://lele.duckdns.org:8080/");
		}
	    this.home = home;
	  } catch (IOException ioe) {
		System.err.println(ioe.getMessage());
	  }
  }

  public void actionPerformed(ActionEvent ev) {
    SensorFeed feed = null;
    try {
      JsonParser parser = new JsonParser(jsonUrl);
      feed =  parser.parseResponse();
      if (feed.getSensors().isEmpty()) {
        System.out.println("No sensors found.");
      } else {
        System.out.println(feed.getSensors().size() + " sensors found");
        int id;
        for (HomePieceOfFurniture furniture : home.getFurniture()) {
          if (furniture.isDoorOrWindow()) {
            if (furniture.getName().matches("(D|W)\\d+")) {
              id = Integer.parseInt(furniture.getName().substring(1));
              //System.out.println("ID: "+id);
              if (feed.getSensors().get(id).getIntDoor()==1) {
                furniture.setVisible(true);
              } else {
                furniture.setVisible(false);
              }
            }
            /*if (furniture.getName().matches("(S)\\d+(A)")) {
              id = Integer.parseInt(furniture.getName().substring(1,furniture.getName().length()-1));
              //System.out.println("ID: "+id);
              if (null == furniture.getDescription()) {
                furniture.setDescription("open");
              }
              if (feed.getSensors().get(id).getExtDoor()==0) {
                if (furniture.getDescription().equals("closed")) {
                  furniture.setX(furniture.getX()-furniture.getWidth());
                  furniture.setDescription("open");
                }
              } else {
                if (!furniture.getDescription().equals("closed")) {
                  furniture.setX(furniture.getX()+furniture.getWidth());
                  furniture.setDescription("closed");                            
                }
              }
            }
            if (furniture.getName().matches("(S)\\d+(B)")) {
              id = Integer.parseInt(furniture.getName().substring(1,furniture.getName().length()-1));
              //System.out.println("ID: "+id);
              if (null == furniture.getDescription()) {
                furniture.setDescription("open");
              }
              if (feed.getSensors().get(id).getExtDoor()==0) {
                if (furniture.getDescription().equals("closed")) {
                  furniture.setX(furniture.getX()+furniture.getWidth());
                  furniture.setDescription("open");
                }
              } else {
                if (!furniture.getDescription().equals("closed")) {
                  furniture.setX(furniture.getX()-furniture.getWidth());
                  furniture.setDescription("closed");                            
                }
              }
            }*/
          }
        }
      }
    } catch (IOException ioe) {
      //System.out.println("Passo 1");
	  System.err.println("Socket exception: "+ioe.getMessage());
    } catch (JsonParseException jsone) {
    	System.err.println("Error in JSON response: "+jsone.getMessage());
    } catch (Throwable t) {
      //System.out.println("Passo 2");
      t.printStackTrace();
    }
  }

}
