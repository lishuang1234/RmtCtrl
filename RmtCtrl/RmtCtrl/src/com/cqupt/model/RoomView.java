package com.cqupt.model;
import android.view.View;
import android.widget.TextView;
/**
 * 功能：显示房间具体信息
 * @author LS
 *
 */
public class RoomView {
	private View baseView;
	private TextView room;
    private TextView ip;
    public RoomView(View baseView) {
        this.baseView = baseView;
    }
    public void setRoomView(Room room){
    	getRoomName().setText(room.getRoomName());
    	getRoomIP().setText(room.getRoomIp());
    }
	public TextView getRoomName() {
		if(room == null){
			room = (TextView) baseView.findViewById(com.cqupt.remotecontrol.R.id.roomName);
		}
		return room;
	}
	public TextView getRoomIP() {
		if(ip == null){
			ip = (TextView) baseView.findViewById(com.cqupt.remotecontrol.R.id.roomIP);
		}
		return ip;
	}
}
