package com.cqupt.model;
/**
 * 功能：房间信息类
 * @author LS
 *
 */
public class Room {
	private String roomName;
	private String roomIp;
	private String roomPort;
	private String analog_1 = "analog_1";
	private String analog_2 = "analog_2";
	private String analog_3 = "analog_3";
	private String analog_4 = "analog_4";
	private String digital_1 = "digital_1";
	private String digital_2 = "digital_2";
	private String digital_3 = "digital_3";
	private String digital_4 = "digital_4";
	public Room(String roomName, String roomIp, String roomPort) {
		this.roomName = roomName;
		this.roomIp = roomIp;
		this.roomPort = roomPort;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomIp() {
		return roomIp;
	}
	public void setRoomIp(String roomIp) {
		this.roomIp = roomIp;
	}
	public String getRoomPort() {
		return roomPort;
	}
	public void setRoomPort(String roomPort) {
		this.roomPort = roomPort;
	}
	public String getAnalog_1() {
		return analog_1;
	}
	public void setAnalog_1(String analog_1) {
		this.analog_1 = analog_1;
	}
	public String getAnalog_2() {
		return analog_2;
	}
	public void setAnalog_2(String analog_2) {
		this.analog_2 = analog_2;
	}
	public String getAnalog_3() {
		return analog_3;
	}
	public void setAnalog_3(String analog_3) {
		this.analog_3 = analog_3;
	}
	public String getAnalog_4() {
		return analog_4;
	}
	public void setAnalog_4(String analog_4) {
		this.analog_4 = analog_4;
	}
	public String getDigital_1() {
		return digital_1;
	}
	public void setDigital_1(String digital_1) {
		this.digital_1 = digital_1;
	}
	public String getDigital_2() {
		return digital_2;
	}
	public void setDigital_2(String digital_2) {
		this.digital_2 = digital_2;
	}
	public String getDigital_3() {
		return digital_3;
	}
	public void setDigital_3(String digital_3) {
		this.digital_3 = digital_3;
	}
	public String getDigital_4() {
		return digital_4;
	}
	public void setDigital_4(String digital_4) {
		this.digital_4 = digital_4;
	}
}
