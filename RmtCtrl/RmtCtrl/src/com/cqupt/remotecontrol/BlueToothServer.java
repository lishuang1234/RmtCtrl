package com.cqupt.remotecontrol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
/**
 * 功能：蓝牙连接后台服务类
 * 1.实现蓝牙设备搜索
 * 2.蓝牙设备配对
 * 3.蓝牙间消息发送与接受
 * @author LS
 *
 */
public class BlueToothServer extends Service {
	public List<BluetoothDevice> deviceList;
	public boolean DISCOVERY_OVER = false;
	public ArrayList<String> infor;
	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	public static final String CONNECT = "com.example.action.connect";// 连接蓝牙消息
	public static final String DEVICE_LIST = "com.example.action.device_list";// 反馈搜到的蓝牙设备
	public static final String LIGHT_ONE_OPEN = "com.example.action.light_one_open";// 第一盏灯亮
	public static final String LIGHT_ONE_CLOSE = "com.example.action.light_one_close";// 第一盏灯亮
	public static final String LIGHT_TWO_OPEN = "com.example.action.light_tow_open";// 第一盏灯亮
	public static final String LIGHT_TWO_CLOSE = "com.example.action.light_two_close";// 第一盏灯亮
	public static final String LIGHT_THREE_OPEN = "com.example.action.light_three_open";// 第一盏灯亮
	public static final String LIGHT_THREE_CLOSE = "com.example.action.light_three_close";// 第一盏灯亮
	public static final String LIGHT_FOUR_OPEN = "com.example.action.light_four_open";// 第一盏灯亮
	public static final String LIGHT_FOUR_CLOSE = "com.example.action.light_four_close";// 第一盏灯亮
	public static final String WORK_STATE = "com.example.action.work_state";
	public static final String REFRESH = "com.example.action.refresh";// 刷新
	public static final String CUT_CONNECT_BT = "com.example.action.cut_connect_bt";// 断开蓝牙连接
	public BluetoothDevice device;
	public BluetoothSocket socket;
	public BluetoothAdapter btAdapter;
	public Intent sendToOtherAc;
	public Intent sendToBtMain;
	public int position;
	public OutputStream outStream;
	public byte[] myByte;
	public InputStream inPutStream;
	public BtDiscovery discovery;
	public StringBuffer buffer;
	public ControlReceiver sr;
	public String sendInfor;
	byte b[] = new byte[4];
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		btAdapter.startDiscovery();
		deviceList = new ArrayList<BluetoothDevice>();
		infor = new ArrayList<String>();
		myByte = new byte[1024];
		sendToBtMain = new Intent();
		sendToOtherAc = new Intent();
		sendToOtherAc.setAction(DEVICE_LIST);
		position = -1;
		SetupBtDiscoverReceiver();
		setUpControlReceiver();
	}
/**
 * 注册蓝牙设备信息接受广播
 */
	private void SetupBtDiscoverReceiver() {
		// TODO Auto-generated method stub
		// 注册系统广播接受蓝牙设备
		discovery = new BtDiscovery();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(discovery, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(discovery, filter);
	}
/**
 * 注册接受指令广播
 */
	private void setUpControlReceiver() {
		// TODO Auto-generated method stub
		// /注册接受指令广播
		sr = new ControlReceiver();
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction(LIGHT_ONE_OPEN);
		filter2.addAction(LIGHT_ONE_CLOSE);
		filter2.addAction(LIGHT_TWO_OPEN);
		filter2.addAction(LIGHT_TWO_CLOSE);
		filter2.addAction(LIGHT_THREE_OPEN);
		filter2.addAction(LIGHT_THREE_CLOSE);
		filter2.addAction(LIGHT_FOUR_OPEN);
		filter2.addAction(LIGHT_FOUR_CLOSE);
		filter2.addAction(REFRESH);// 刷新操作
		filter2.addAction(CONNECT);
		filter2.addAction(CUT_CONNECT_BT);
		registerReceiver(sr, filter2);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		cutConnect();
		unregisterReceiver(discovery);
		unregisterReceiver(sr);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("BTService開始搜索");
		new Work().execute(DISCOVERY_OVER);// 開始搜索
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 异步任务发送所得蓝牙设备信息
	 * 
	 * @author Administrator
	 * 
	 */
	protected class Work extends AsyncTask<Boolean, String, String> {
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		@Override
		protected String doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
			while (true) {
				if (DISCOVERY_OVER) {// 搜索結束
					System.out.println("搜索結束");
					sendToOtherAc.putStringArrayListExtra("device",
							turnString(deviceList));// 获得发送列表条目
					sendBroadcast(sendToOtherAc);
					break;
				}
			}
			return null;
		}
	}

	/**
	 * 接受蓝牙设备信息
	 * 
	 * @author Administrator
	 * 
	 */
	class BtDiscovery extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				deviceList.add(device);// 加入驱动表中
				System.out.println("Discovery__ing" + device.getName() + "/n"
						+ device.getAddress());
				// 发送Device名称
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				DISCOVERY_OVER = true;

			}
		}
	}
	/**
	 * 将驱动列表包装
	 * 
	 * @param deviceList2
	 * @return
	 */
	public ArrayList<String> turnString(List<BluetoothDevice> deviceList2) {
		// TODO Auto-generated method stub
		String[] infor1 = new String[deviceList2.size()];
		for (int i = 0; i < deviceList2.size(); i++) {
			if (deviceList2.get(i).getName() == null) {// 设备名为空处理
				System.out.println("deiceList  name is null");
				infor1[i] = "null";
			} else
				infor1[i] = deviceList2.get(i).getName();
			if (infor.indexOf(infor1[i]) == -1) {
				infor.add(infor1[i]);
			}
		}
		return infor;
	}
	/**
	 * 获取Device连接
	 * 
	 * @param position
	 */
	protected void connectBlue(int position) {
		// TODO Auto-generated method stub
		// device = deviceList.get(position);
		device = btAdapter.getRemoteDevice(deviceList.get(position)
				.getAddress());
		System.out.println("BeforeContect~~~"
				+ deviceList.get(position).getAddress());
		new Connect().start();// 连接线程
	}
	/**
	 * 连接蓝牙操作线程
	 * 
	 * @author Administrator
	 * 
	 */
	class Connect extends Thread {
		// private BluetoothSocket tmp;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("BeforeContect~~~");
			btAdapter.cancelDiscovery();
			try {
				socket = device.createRfcommSocketToServiceRecord(MY_UUID);// 获取Socket
				socket.connect();
				outStream = socket.getOutputStream();
				inPutStream = socket.getInputStream();
				new JieShou().start();
				System.out.println("Contect~~~");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("ContectError~~~");
				try {
					socket.close();
					outStream.flush();
					outStream.close();
					inPutStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			super.run();
		}
	}
/**
 * 信息接收线程
 * @author LS
 *
 */
	class JieShou extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			while (true) {
				int j;
				try {
					j = inPutStream.read(b);
					if (j > 0) {
						sendWorkStateToBt(b);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 连接蓝牙后读取操作 一写一读
	 * 
	 * @author Administrator
	 * 
	 */
	class Oper extends Thread {
		String infor = "";
		public Oper(String infor) {
			this.infor = infor;
			System.out.println(infor);
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String line = "";
			String backInfor = "";
			try {
				if (infor != "")
					outStream.write(infor.getBytes());// 发送
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}
	}
	/**
	 * 响应用户操作
	 * 
	 * @author Administrator
	 * 
	 */
	class ControlReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(CONNECT)) {// 连接蓝牙设备
				position = intent.getIntExtra("position", -1);
				if (position > -1) {
					connectBlue(position);// 连接
				}
			} else if (action.equals(CUT_CONNECT_BT)) {// 断开连接
				System.out.println(CUT_CONNECT_BT + "asdasd");
				cutConnect();
			} else if (action.equals(LIGHT_ONE_OPEN)) {
				sendInfor = "m1";
				System.out.println("打开灯1" + sendInfor);
				new Oper(sendInfor).start();
			} else if (action.equals(LIGHT_ONE_CLOSE)) {
				sendInfor = "m5";
				new Oper(sendInfor).start();
			} else if (action.equals(LIGHT_TWO_OPEN)) {
				sendInfor = "m2";
				new Oper(sendInfor).start();
			} else if (action.equals(LIGHT_TWO_CLOSE)) {
				sendInfor = "m6";
				new Oper(sendInfor).start();
			} else if (action.equals(LIGHT_THREE_OPEN)) {
				sendInfor = "m3";
				new Oper(sendInfor).start();
			} else if (action.equals(LIGHT_THREE_CLOSE)) {
				sendInfor = "m7";
				new Oper(sendInfor).start();
			} else if (action.equals(LIGHT_FOUR_OPEN)) {
				sendInfor = "m4";
				new Oper(sendInfor).start();
			} else if (action.equals(LIGHT_FOUR_CLOSE)) {
				sendInfor = "m8";
				new Oper(sendInfor).start();
			} else if (action.equals(REFRESH)) {
				sendInfor = "m9";
				new Oper(sendInfor).start();
			}
		}
	}

	/**
	 * 发送反馈显示状态
	 * 
	 * @param string
	 */
	public void sendWorkStateToBt(byte[] b) {
		// TODO Auto-generated method stub
		sendToBtMain.setAction(WORK_STATE);
		sendToBtMain.putExtra("lights_is_open", b);
		sendBroadcast(sendToBtMain);
	}
/**
 * 结束蓝牙连接，释放相关资源
 */
	public void cutConnect() {
		// TODO Auto-generated method stub
		if (socket != null && outStream != null && inPutStream != null) {
			try {
				socket.close();
				outStream.flush();
				inPutStream.close();
				outStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
