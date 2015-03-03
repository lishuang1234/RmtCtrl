package com.cqupt.remotecontrol;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
/**
 * 功能：蓝牙控制主界面Activity
 * @author LS
 *
 */
public class BtActivity extends Activity implements OnClickListener {
	public static final String LIGHT_ONE_OPEN = "com.example.action.light_one_open";// 第一盏灯亮
	public static final String LIGHT_ONE_CLOSE = "com.example.action.light_one_close";// 第一盏灯亮
	public static final String LIGHT_TWO_OPEN = "com.example.action.light_tow_open";// 第一盏灯亮
	public static final String LIGHT_TWO_CLOSE = "com.example.action.light_two_close";// 第一盏灯亮
	public static final String LIGHT_THREE_OPEN = "com.example.action.light_three_open";// 第一盏灯亮
	public static final String LIGHT_THREE_CLOSE = "com.example.action.light_three_close";// 第一盏灯亮
	public static final String LIGHT_FOUR_OPEN = "com.example.action.light_four_open";// 第一盏灯亮
	public static final String LIGHT_FOUR_CLOSE = "com.example.action.light_four_close";// 第一盏灯亮
	public static final String WORK_STATE = "com.example.action.work_state";
	public static final String REFRESH = "com.example.action.refresh";
	public static final String CUT_CONNECT_BT = "com.example.action.cut_connect_bt";// 断开蓝牙连接
	WorkStatReceiver receiver;
	private ImageView lightOne;
	private ImageView lightTwo;
	private ImageView lightThree;
	private ImageView lightFour;
	private ImageView[] vi = new ImageView[4];
	private Intent sendToServiceContr0l;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.btmain);
		setUpReceiver();
		setUpViews();
	}
	private void setUpViews() {
		// TODO Auto-generated method stub
		((Button) findViewById(R.id.btAc_btn1_open)).setOnClickListener(this);
		((Button) findViewById(R.id.btAc_btn1_close)).setOnClickListener(this);
		((Button) findViewById(R.id.btAc_btn2_open)).setOnClickListener(this);
		((Button) findViewById(R.id.btAc_btn2_close)).setOnClickListener(this);
		((Button) findViewById(R.id.btAc_btn3_open)).setOnClickListener(this);
		((Button) findViewById(R.id.btAc_btn3_close)).setOnClickListener(this);
		((Button) findViewById(R.id.btAc_btn4_open)).setOnClickListener(this);
		((Button) findViewById(R.id.btAc_btn4_close)).setOnClickListener(this);
		((Button) findViewById(R.id.btAc_btn_refersh)).setOnClickListener(this);
		lightOne = (ImageView) findViewById(R.id.btAc_iv_1);
		lightTwo = (ImageView) findViewById(R.id.btAc_iv_2);
		lightThree = (ImageView) findViewById(R.id.btAc_iv_3);
		lightFour = (ImageView) findViewById(R.id.btAc_iv_4);
		vi[0] = lightOne;
		vi[1] = lightTwo;
		vi[2] = lightThree;
		vi[3] = lightFour;
	}
	private void setUpReceiver() {
		// TODO Auto-generated method stub
		sendToServiceContr0l = new Intent();
		receiver = new WorkStatReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WORK_STATE);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		super.onDestroy();
	}
	/**
	 * 按键监听发送指令广播
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btAc_btn1_open:// / 1 open
			sendToServiceContr0l.setAction(LIGHT_ONE_OPEN);
			lightOne.setBackgroundResource(R.drawable.openlight);
			sendBroadcast(sendToServiceContr0l);
			break;
		case R.id.btAc_btn1_close:
			sendToServiceContr0l.setAction(LIGHT_ONE_CLOSE);// 1 close
			lightOne.setBackgroundResource(R.drawable.closelight);
			sendBroadcast(sendToServiceContr0l);
			break;
		case R.id.btAc_btn2_open:
			sendToServiceContr0l.setAction(LIGHT_TWO_OPEN);
			lightTwo.setBackgroundResource(R.drawable.openlight);
			// lightThree.setBackgroundResource(R.drawable.closelight);
			sendBroadcast(sendToServiceContr0l);
			break;
		case R.id.btAc_btn2_close:
			sendToServiceContr0l.setAction(LIGHT_TWO_CLOSE);
			lightTwo.setBackgroundResource(R.drawable.closelight);
			sendBroadcast(sendToServiceContr0l);
			break;
		case R.id.btAc_btn3_open:
			sendToServiceContr0l.setAction(LIGHT_THREE_OPEN);
			lightThree.setBackgroundResource(R.drawable.openlight);
			// lightTwo.setBackgroundResource(R.drawable.closelight);
			sendBroadcast(sendToServiceContr0l);
			break;
		case R.id.btAc_btn3_close:
			sendToServiceContr0l.setAction(LIGHT_THREE_CLOSE);
			lightThree.setBackgroundResource(R.drawable.closelight);
			sendBroadcast(sendToServiceContr0l);
			break;
		case R.id.btAc_btn4_open:
			sendToServiceContr0l.setAction(LIGHT_FOUR_OPEN);
			lightFour.setBackgroundResource(R.drawable.openlight);
			sendBroadcast(sendToServiceContr0l);
			break;
		case R.id.btAc_btn4_close:
			sendToServiceContr0l.setAction(LIGHT_FOUR_CLOSE);
			lightFour.setBackgroundResource(R.drawable.closelight);
			sendBroadcast(sendToServiceContr0l);
			break;
		case R.id.btAc_btn_refersh:
			sendToServiceContr0l.setAction(REFRESH);
			sendBroadcast(sendToServiceContr0l);
			break;
		}
	}
	class WorkStatReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			byte[] b;
			if (action.equals(WORK_STATE)) {
				b = intent.getByteArrayExtra("lights_is_open");
				System.out.println("接收到的Wifi--->" + new String(b, 0, 9)
						+ "字节数组长度：--》" + b.length);
				for (int i = 0; i < 4; i++) {
					if (new String(b, i, 1).equals("1")) {
						vi[i].setBackgroundResource(R.drawable.openlight);
						System.out.println("light open-->" + i);
					} else
						vi[i].setBackgroundResource(R.drawable.closeip);
				}
			}
		}
	}
}
