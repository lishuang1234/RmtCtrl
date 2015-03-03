package com.cqupt.remotecontrol;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
/**
 * 功能：程序主界面
 * @author LS
 *
 */
public class GridActivity extends Activity {
	public BluetoothAdapter adapter;
	private GridView gridview;
	public Context cxt;
	private boolean isOpenBtPermission = false;
	public static final int REQUEST_DISCOVERABLE = 1;
	public static final String CUT_CONNECT_BT = "com.example.action.cut_connect_bt";// 断开蓝牙连接
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String string = (String) msg.obj;
			if (string.equals("RoomInfo")) {
				Intent intent = new Intent();
				intent.setClass(cxt, RoomInfo.class);
				startActivity(intent);
			} else if (string.equals("AddEquipment")) {
				Intent intent = new Intent();
				intent.setClass(cxt, AddEquipment.class);
				startActivity(intent);
			} else if (string.equals("RoomList")) {
				Intent intent = new Intent();
				intent.setClass(cxt, RoomList.class);
				startActivity(intent);
			} else if (string.equals("MessageActivity")) {
				Intent intent = new Intent();
				intent.setClass(cxt, MessageActivity.class);
				startActivity(intent);
			}
		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.grid);
		cxt = this;
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = BluetoothAdapter.getDefaultAdapter();
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.p1);
		map1.put("ItemText", "IP模式监控");
		lstImageItem.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemImage", R.drawable.p2);
		map2.put("ItemText", "IP模式设置");
		lstImageItem.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("ItemImage", R.drawable.p3);
		map3.put("ItemText", "添加IP房间");
		lstImageItem.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("ItemImage", R.drawable.p4);
		map4.put("ItemText", "蓝牙模式监控");
		lstImageItem.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("ItemImage", R.drawable.p5);
		map5.put("ItemText", "蓝牙模式打开");
		lstImageItem.add(map5);
		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("ItemImage", R.drawable.p6);
		map6.put("ItemText", "蓝牙模式关闭");
		lstImageItem.add(map6);
		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("ItemImage", R.drawable.p7);
		map7.put("ItemText", "短信模式监控");
		lstImageItem.add(map7);
		HashMap<String, Object> map8 = new HashMap<String, Object>();
		map8.put("ItemImage", R.drawable.p8);
		map8.put("ItemText", "短信模式设置");
		lstImageItem.add(map8);
		HashMap<String, Object> map9 = new HashMap<String, Object>();
		map9.put("ItemImage", R.drawable.p9);
		map9.put("ItemText", "关闭");
		lstImageItem.add(map9);
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
				lstImageItem,// 数据来源
				R.layout.item,// night_item的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// 添加并且显示
		gridview.setAdapter(saImageItems);
		// 添加消息处理
		gridview.setOnItemClickListener(new ItemClickListener());
	}
	public void exitProgram() {
		AlertDialog.Builder builder = new Builder(cxt);
		builder.setTitle("提示");
		builder.setMessage("你确定要退出吗？");
		builder.setPositiveButton("是的",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// 在本例中arg2=arg3
			@SuppressWarnings("unchecked")
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			// 显示所选Item的ItemText
			setTitle((String) item.get("ItemText"));
			if (item.get("ItemText").equals("IP模式监控")) {
				Message s = new Message();
				s.obj = "RoomList";
				handler.sendMessage(s);
			} else if (item.get("ItemText").equals("IP模式设置")) {
				Message s = new Message();
				s.obj = "RoomList";
				handler.sendMessage(s);
			} else if (item.get("ItemText").equals("添加IP房间")) {
				Message s = new Message();
				s.obj = "AddEquipment";
				handler.sendMessage(s);
			} else if (item.get("ItemText").equals("蓝牙模式监控")) {
				// /跳转蓝牙列表界面
				if (isOpenBtPermission) {
					Intent intent3 = new Intent();
					intent3.setClass(GridActivity.this, OtherActivity.class);
					startActivity(intent3);
				} else
					Toast.makeText(GridActivity.this, "未打开蓝牙或者未获取权限！", 0)
							.show();
			} else if (item.get("ItemText").equals("蓝牙模式打开")) {
				//开启蓝牙设备
				Intent enabler = new Intent(
						BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				startActivityForResult(enabler, REQUEST_DISCOVERABLE);
				isOpenBtPermission = true;
			} else if (item.get("ItemText").equals("蓝牙模式关闭")) {
				//关闭蓝牙
				Intent sendToService = new Intent();
				sendToService.setAction(CUT_CONNECT_BT);
				sendBroadcast(sendToService);
				adapter.disable();
			} else if (item.get("ItemText").equals("短信模式监控")) {
				Message s = new Message();
				s.obj = "MessageActivity";
				handler.sendMessage(s);
			} else if (item.get("ItemText").equals("短信模式设置")) {
				Toast.makeText(GridActivity.this, "暂时没开通该功能", 0).show();
			} else if (item.get("ItemText").equals("关闭")) {
				exitProgram();
			}
		}
	}
}
