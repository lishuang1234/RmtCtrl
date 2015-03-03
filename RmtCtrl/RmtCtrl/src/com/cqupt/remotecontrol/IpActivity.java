package com.cqupt.remotecontrol;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 功能：ＷＩＦＩ控制主界面Ａｃｔｉｖｉｔｙ
 * @author LS
 *
 */
public class IpActivity extends Activity {
	public static String IP = "192.168.1.1";
	public static final int PORT = 8888;
	public static final byte LIST = (byte) 0xA1;
	public static final byte CTRL = (byte) 0xA2;
	public static final byte END = (byte) 0xA3;
	public static final byte RSP = (byte) 0xA4;
	public static final byte[] KEYS = { (byte) 0xAA, (byte) 0xBB, (byte) 0xCC,
			(byte) 0xDD };
	public Button b1;
	public EditText ipEdit;
	public Button bo1;
	public Button bo2;
	public Button bo3;
	public Button bo4;
	public Button bc1;
	public Button bc2;
	public Button bc3;
	public Button bc4;
	public TextView t1;
	public ImageView[] iv = new ImageView[4];
	public DatagramSocket ds = null;
	public Receive rec;
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			byte[] b = (byte[]) msg.obj;
			System.out.println("接收到的Wifi--->" + new String(b, 0, 4)
					+ "字节数组长度：--》" + b.length);
			for (int i = 0; i < 4; i++) {
				if (new String(b, i, 1).equals("1")) {
					iv[i].setImageResource(R.drawable.openlight);
					System.out.println("light open-->" + i);
				} else
					iv[i].setImageResource(R.drawable.closeip);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.ipmain);
		b1 = (Button) findViewById(R.id.button1);
		t1 = (TextView) findViewById(R.id.text1);
		bo1 = (Button) findViewById(R.id.buttono1);
		bo2 = (Button) findViewById(R.id.buttono2);
		bo3 = (Button) findViewById(R.id.buttono3);
		bo4 = (Button) findViewById(R.id.buttono4);
		bc1 = (Button) findViewById(R.id.buttonc1);
		bc2 = (Button) findViewById(R.id.buttonc2);
		bc3 = (Button) findViewById(R.id.buttonc3);
		bc4 = (Button) findViewById(R.id.buttonc4);
		iv[0] = (ImageView) findViewById(R.id.image1);
		iv[1] = (ImageView) findViewById(R.id.image2);
		iv[2] = (ImageView) findViewById(R.id.image3);
		iv[3] = (ImageView) findViewById(R.id.image4);
		ipEdit = (EditText) findViewById(R.id.ipEdit);
		try {
			if (ds == null) {
				ds = new DatagramSocket(PORT);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		if (ds == null) {
			Toast.makeText(this, "请重启应用！", Toast.LENGTH_SHORT).show();
			finish();
		}
		rec = new Receive();
		rec.start();
		b1.setOnClickListener(new MyButtonListener());
		bo1.setOnClickListener(new MyButtonListener());
		bo2.setOnClickListener(new MyButtonListener());
		bo3.setOnClickListener(new MyButtonListener());
		bo4.setOnClickListener(new MyButtonListener());
		bc1.setOnClickListener(new MyButtonListener());
		bc2.setOnClickListener(new MyButtonListener());
		bc3.setOnClickListener(new MyButtonListener());
		bc4.setOnClickListener(new MyButtonListener());
		Intent intent = getIntent();
		Bundle infoBundle = intent.getExtras();
		ipEdit.setText(infoBundle.getString("ip"));
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			if (ds == null) {
				ds = new DatagramSocket(PORT);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		if (rec == null) {
			rec = new Receive();
			rec.start();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			if (ds != null) {
				ds.close();
				ds = null;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		if (rec != null) {
			rec.destroy();
			rec = null;
		}
	}
	public void changeIP(View v) {
		IP = ipEdit.getText().toString();
		ipEdit.setEnabled(false);
	}
	class MyButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			byte[] data = null;
			String[] sendData = new String[] { "m1", "m2", "m3", "m4", "m5",
					"m6", "m7", "m8", "m9" };
			switch (v.getId()) {
			case R.id.button1:// 刷新
				data = sendData[8].getBytes();
				break;
			case R.id.buttono1:// //1 open
				data = sendData[0].getBytes();
				iv[0].setImageResource(R.drawable.openlight);
				break;
			case R.id.buttonc1:// 1 close
				data = sendData[4].getBytes();
				iv[0].setImageResource(R.drawable.closeip);
				break;
			case R.id.buttono2:// 2 open
				data = sendData[1].getBytes();
				iv[1].setImageResource(R.drawable.openlight);
				break;
			case R.id.buttonc2:
				data = sendData[5].getBytes();
				iv[1].setImageResource(R.drawable.closeip);
				break;
			case R.id.buttono3:
				data = sendData[2].getBytes();
				iv[2].setImageResource(R.drawable.openlight);
				break;
			case R.id.buttonc3:
				data = sendData[6].getBytes();
				iv[2].setImageResource(R.drawable.closeip);
				break;
			case R.id.buttono4:
				data = sendData[3].getBytes();
				iv[3].setImageResource(R.drawable.openlight);
				break;
			case R.id.buttonc4:
				data = sendData[7].getBytes();
				iv[3].setImageResource(R.drawable.closeip);
				break;
			default:
				return;
			}
			try {
				InetAddress addr = InetAddress.getByName(IP);
				DatagramPacket dp = new DatagramPacket(data, data.length, addr,
						PORT);
				ds.send(dp);
			} catch (Exception e) {
				System.out.println("fail");
			}
		}

	}
	public byte[] encrypt(byte[] b) {
		int i;
		byte[] temp = new byte[b.length];
		for (i = 0; i < 3; i++) {
			temp[i] = (byte) (b[i] ^ KEYS[i]);
		}
		temp[i] = b[i];
		return temp;
		// return b;
	}
	class Receive extends Thread {
		@Override
		public void run() {
			byte b[] = new byte[1024];
			DatagramPacket dp = new DatagramPacket(b, b.length);
			while (true) {
				try {
					ds.receive(dp);
					Message msg = new Message();
					msg.obj = dp.getData();
					handler.sendMessage(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
