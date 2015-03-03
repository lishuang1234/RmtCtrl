package com.cqupt.remotecontrol;
import com.cqupt.internet.UDPHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
/**
 * 功能：房间信息展示
 */
public class RoomInfo extends Activity{
	private static TextView textView;	
	private String roomName;
	public String ip;
	public int port;	
	public UDPHelper udpHelper;
	public UDPReceiveThread udpThread;
	public ImageView[] digital = new ImageView[4];
	public SeekBar seekBar_1;
	public SeekBar seekBar_2;
	public CheckBox checkBox_1;
	public CheckBox checkBox_2;
	public Button refreshButton;
	public Handler udpSendHandler;
	public Handler UIRefreshHandler;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.roominfo);
		digital[0] = (ImageView)findViewById(R.id.light1);
		digital[1] = (ImageView)findViewById(R.id.light2);
		digital[2] = (ImageView)findViewById(R.id.light3);
		digital[3] = (ImageView)findViewById(R.id.light4);
		seekBar_1 = (SeekBar)findViewById(R.id.analog_1_seekBar);
		seekBar_2 = (SeekBar)findViewById(R.id.analog_2_seekBar);
		checkBox_1 = (CheckBox)findViewById(R.id.analog_1_check);
		checkBox_2 = (CheckBox)findViewById(R.id.analog_2_check);
		textView = (TextView)findViewById(R.id.roomInfo_name);
		refreshButton = (Button)findViewById(R.id.refresh_button);
		Intent intent = getIntent();
		Bundle infoBundle = intent.getExtras();
		if(infoBundle == null){
			finish();
		}
		roomName = infoBundle.getString("name");
		ip = infoBundle.getString("ip");
		port = Integer.parseInt(infoBundle.getString("port"));
		textView.setText(roomName);
		UIRefreshHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				String string = msg.obj.toString();
				refresh(string);
			}
		};
		seekBar_1.setOnSeekBarChangeListener(new SeekBarListener(checkBox_1, "a1"));
		seekBar_2.setOnSeekBarChangeListener(new SeekBarListener(checkBox_2, "a2"));
	}
	public void refresh(String string){
		if(string.length() != 14) return ;
		for(int i = 0; i < 4; i++){
			if(string.charAt(i) == '0'){
				digital[i].setImageDrawable(getResources().getDrawable(R.drawable.lightclose));
			}else{
				digital[i].setImageDrawable(getResources().getDrawable(R.drawable.lightopen));
			}
		}
	}
	@Override
	protected void onPause() {
		udpThread.isRun = false;
		udpHelper.close();
		udpSendHandler.getLooper().quit();
		super.onPause();
	}
	@Override
	protected void onResume() {
		udpHelper = new UDPHelper();
		udpThread = new UDPReceiveThread();
		udpThread.start();
		new UDPSendThread().start();
		super.onResume();
	}
	public void UIChanged(String string){
		Message msg = UIRefreshHandler.obtainMessage();
		msg.obj = string;
		UIRefreshHandler.sendMessage(msg);
	}
	public void send(String string){
		System.out.println(string);
		Message childMsg = udpSendHandler.obtainMessage();
        childMsg.obj = string;
        udpSendHandler.sendMessage(childMsg);
	}
	public void refreshButtonClick(View v){
		send("query");
	}
	public void analogButtonClick(View v){
		Button button = (Button)v;
		int num = 0;
		switch (button.getId()) {
		case R.id.analog_1_button:
			num = (int)(seekBar_1.getProgress() * 1.0f / seekBar_1.getMax() * 255);
			send("a1" + num/100 + num/10%10 + num%10);
			break;
		case R.id.analog_2_button:
			num = (int)(seekBar_2.getProgress() * 1.0f / seekBar_2.getMax() * 255);
			send("a2" + num/100 + num/10%10 + num%10);
			break;
		default:
			break;
		}
	}
	public void digitalButtonClick(View v){
		Button button = (Button)v;
		switch (button.getId()) {
		case R.id.digital_1_button_close:
			send("d10");	//d代表数字信号，1代表第一路，0代表低电平
			break;
		case R.id.digital_1_button_open:
			send("d11");	//d代表数字信号，1代表第一路，1代表高电平
			break;
		case R.id.digital_2_button_close:
			send("d20");	//d代表数字信号，2代表第二路
			break;
		case R.id.digital_2_button_open:
			send("d21");	//d代表数字信号，2代表第二路
			break;
		case R.id.digital_3_button_close:
			send("d30");	//d代表数字信号，3代表第三路
			break;
		case R.id.digital_3_button_open:
			send("d31");	//d代表数字信号，3代表第三路
			break;
		case R.id.digital_4_button_close:
			send("d40");	//d代表数字信号，4代表第四路
			break;
		case R.id.digital_4_button_open:
			send("d41");	//d代表数字信号，4代表第四路
			break;
		default:
			break;
		}
	}
	class UDPReceiveThread extends Thread{
		public boolean isRun = true;
		public UDPReceiveThread(){
		}
		@Override
		public void run() {
			String string;
			while(isRun){
				string = udpHelper.receive();
				if(string == null) continue;
				UIChanged(string);
			}
		}
	}
	class UDPSendThread extends Thread{
		public UDPSendThread(){
		}
		@Override
		public void run() {
			Looper.prepare();
			udpSendHandler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					String info = msg.obj.toString();
					if(udpHelper != null){
						udpHelper.sendData(ip, port, info);
					}
				}
			};
			Looper.loop();
		}
	}
	class SeekBarListener implements SeekBar.OnSeekBarChangeListener{
		public static final int MAX = 100;
		public static final int DEVIATION = 5;
		public int lastSend = 0;
		public CheckBox checkBox;
		public String ID = "";
		public SeekBarListener(CheckBox _checkBox, String _ID){
			this.checkBox = _checkBox;
			this.ID = _ID;
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if(!checkBox.isChecked()){
				return ;
			}
			if((lastSend - progress > DEVIATION) || (progress - lastSend > DEVIATION)){
				int num = (int)(progress * 1.0f / MAX * 255);
				send(ID + num/100 + num/10%10 + num%10);
				lastSend = progress;
			}
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}
	
}
