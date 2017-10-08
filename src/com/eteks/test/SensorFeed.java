package com.eteks.test;

import java.util.List;

public class SensorFeed {

	private List<Sensor> sensors;
	private String startTime;
	
	public List<Sensor> getSensors() {
	  return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	public String getStartTime() {
	  return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
}
