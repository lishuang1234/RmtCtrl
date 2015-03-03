package com.cqupt.model;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
/**
 * ¹¦ÄÜ£ºListViewÊÊÅäÆ÷£¬¼Ì³Ð×ÔArrayAdapter
 * @author LS
 *
 */
public class RoomlistAdapter extends ArrayAdapter<Room>{
	public RoomlistAdapter(Context context, List<Room> roomlist) {
		super(context, 0, roomlist);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Activity activity = (Activity) getContext();
		View rowView = convertView;
		RoomView roomView;
		if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(com.cqupt.remotecontrol.R.layout.roomlist, null);
            roomView = new RoomView(rowView);
            rowView.setTag(roomView);
        } else {
        	roomView = (RoomView)rowView.getTag();
        }
		final Room room = getItem(position);
		roomView.setRoomView(room);
		return rowView;
	}
	
}
