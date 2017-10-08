package com.eteks.test;

public class Sensor {
	
	private int id;
	private float temp;
	private int intDoor;
	private int extDoor;
	private float batteryValue;
	private String readTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public int getIntDoor() {
		return intDoor;
	}
	public void setIntDoor(int intDoor) {
		this.intDoor = intDoor;
	}
	public int getExtDoor() {
		return extDoor;
	}
	public void setExtDoor(int extDoor) {
		this.extDoor = extDoor;
	}
	public float getBatteryValue() {
		return batteryValue;
	}
	public void setBatteryValue(float batteryValue) {
		this.batteryValue = batteryValue;
	}
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

}
