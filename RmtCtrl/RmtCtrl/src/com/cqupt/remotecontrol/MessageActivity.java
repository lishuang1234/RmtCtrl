package com.cqupt.remotecontrol;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 功能：短信控制主界面Ａｃｔｉｖｉｔｙ
 * @author LS
 *
 */
public class MessageActivity extends Activity {
	public static String PHONENUMBER = "1123";
	public static final String COMMAND_1_close = "close1"; 
	public static final String COMMAND_1_open = "open1"; 
	public static final String COMMAND_2_close = "close2"; 
	public static final String COMMAND_2_open = "open2"; 
	public static final String COMMAND_query = "query3"; 
	public static final String COMMAND_warning = "warning"; 
	public static TextView phoneTextView;
	public static ImageButton button1;
	private static boolean button1State;
	public static ImageButton button2;
	private static boolean button2State;
	public static Button button3;
	public static Button button4;
	public static ImageView light1;
	public static ImageView light2;
	public static ImageView light3;
	public static ImageView light4;
	public static SoundPool sp;
	public static HashMap<Integer, Integer> spMap;
	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.message);
		sp = new SoundPool(2,AudioManager.STREAM_MUSIC,0);
        spMap = new HashMap<Integer,Integer>();
        spMap.put(1, sp.load(this, R.raw.beep, 1));
		startListen();
		light1 = (ImageView)findViewById(R.id.light1);
		light2 = (ImageView)findViewById(R.id.light2);
		light3 = (ImageView)findViewById(R.id.light3);
		light4 = (ImageView)findViewById(R.id.light4);
		phoneTextView = (TextView)findViewById(R.id.phoneNumber);
		button1 = (ImageButton) this.findViewById(R.id.button1);
		button1State = false;
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//sendSMS(PHONENUMBER, "123");
				setButton1();
			}
		});
		button2 = (ImageButton) this.findViewById(R.id.button2);
		button2State = false;
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//sendSMS(PHONENUMBER, "123");
				setButton2();
			}
		});
		button3 = (Button) this.findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//sendSMS(PHONENUMBER, "123");
				sendCommand(COMMAND_query); //查询第三个灯状态
			}
		});
		button4 = (Button) this.findViewById(R.id.button4);
		button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//sendSMS(PHONENUMBER, "123");
				//sendCommand(COMMAND_4_query); //查询第四个灯状态
				AlertDialog.Builder builder = new Builder(MessageActivity.this); 
		        builder.setTitle("帮助"); 
		        builder.setPositiveButton("确定", null); 
		        builder.setIcon(android.R.drawable.ic_dialog_info); 
		        builder.setMessage("第一行两个灯是由下面两个按钮控制开关，查询按钮可以查询四个灯的状态"); 
		        builder.show(); 
			}
		});
		     
	}
	public void playSounds(int sound, int number){
	    //实例化AudioManager对象，控制声音
	    AudioManager am = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
	    //最大音量
	    float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    //当前音量
	    float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float volumnRatio = audioCurrentVolumn/audioMaxVolumn;
	    //播放
	    sp.play(spMap.get(sound),     //声音资源
	        volumnRatio,         //左声道
	        volumnRatio,         //右声道
	        1,             //优先级，0最低
	        number,         //循环次数，0是不循环，-1是永远循环
	        1);            //回放速度，0.5-2.0之间。1为正常速度
	}
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
	        StringBuilder info = new StringBuilder();// 短信内容   
	        StringBuilder number = new StringBuilder();// 短信发件人   
	        Bundle bundle = intent.getExtras();  
	        if (bundle != null) {  
	            Object[] _pdus = (Object[]) bundle.get("pdus");  
	            SmsMessage[] message = new SmsMessage[_pdus.length];  
	            for (int i = 0; i < _pdus.length; i++) {  
	                message[i] = SmsMessage.createFromPdu((byte[]) _pdus[i]);  
	            }  
	            for (SmsMessage currentMessage : message) {  
	                info.append(currentMessage.getDisplayMessageBody());  
	                number.append(currentMessage.getDisplayOriginatingAddress());  
	            }  
	            String smsBody = info.toString();  
	            String smsNumber = number.toString();  
	            if (smsNumber.contains("+86")) {  
	                smsNumber = smsNumber.substring(3);  
	            }
	            //到这里接收完成
	            if(smsNumber.equals(PHONENUMBER)){
	            	if(smsBody.equals(COMMAND_warning)){
	            		Toast.makeText(context, "Warning!!!", Toast.LENGTH_LONG).show();
	            		playSounds(1, 1);
	            		this.abortBroadcast();
	            		return ;
	            	}
	            	String[] string = smsBody.split("\r\n");
	            	if(string[0].charAt(0) == '1'){
	            		button1.setImageDrawable(getResources().getDrawable(R.drawable.open));
	        			light1.setImageDrawable(getResources().getDrawable(R.drawable.lightopen));
	            	}
	            	if(string[0].charAt(1) == '1'){
	            		button2.setImageDrawable(getResources().getDrawable(R.drawable.open));
	        			light2.setImageDrawable(getResources().getDrawable(R.drawable.lightopen));
	            	}
	            	if(string[0].charAt(2) == '1'){
	            		setLight3On();
	            	}
	            	if(string[0].charAt(3) == '1'){
	            		setLight4On();
	            	}
	            	if(string[0].charAt(0) == '0'){
	            		button1.setImageDrawable(getResources().getDrawable(R.drawable.close));
	        			light1.setImageDrawable(getResources().getDrawable(R.drawable.lightclose));
	            	}
	            	if(string[0].charAt(1) == '0'){
	            		button2.setImageDrawable(getResources().getDrawable(R.drawable.close));
	        			light2.setImageDrawable(getResources().getDrawable(R.drawable.lightclose));
	            	}
	            	if(string[0].charAt(2) == '0'){
	            		setLight3Off();
	            	}
	            	if(string[0].charAt(3) == '0'){
	            		setLight4Off();
	            	}
	            	this.abortBroadcast();
	            }
	            else {
					return ;
				}
	        }
		}
	};
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message, menu);
        return true;
    }
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		final EditText tempEditText = new EditText(this);
		new AlertDialog.Builder(this)
		.setTitle("请输入手机号码")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setView(tempEditText)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
				public void onClick(DialogInterface dialog, int which) { 
					// TODO Auto-generated method stub  
					PHONENUMBER = tempEditText.getText().toString();
					phoneTextView.setText(PHONENUMBER);
				} 
			})
		.setNegativeButton("取消", null)
		.show();
		return super.onMenuItemSelected(featureId, item);
	}
	public void sendCommand(String com){
		sendSMS(PHONENUMBER, com);
	}
	public void sendSMS(String phonenumber,String msg){//发送短信的类
        //PendingIntent pi=PendingIntent.getActivity(this, 0, new Intent(this,MainActivity.class), 0);
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(phonenumber, null, msg, null, null);//发送信息到指定号码
    }
	public boolean getButton1State(){
		return button1State;
	}
	public boolean getButton2State(){
		return button2State;
	}
	public void setButton1(){
		button1State = !button1State;
		if(getButton1State()){
			sendCommand(COMMAND_1_open); //打开第一个灯
			button1.setImageDrawable(getResources().getDrawable(R.drawable.open));
			light1.setImageDrawable(getResources().getDrawable(R.drawable.lightopen));
		}else{
			sendCommand(COMMAND_1_close); //关闭第一个灯
			button1.setImageDrawable(getResources().getDrawable(R.drawable.close));
			light1.setImageDrawable(getResources().getDrawable(R.drawable.lightclose));
		}
	}
	public void setButton2(){
		button2State = !button2State;
		if(getButton2State()){
			sendCommand(COMMAND_2_open); //打开第二个灯
			button2.setImageDrawable(getResources().getDrawable(R.drawable.open));
			light2.setImageDrawable(getResources().getDrawable(R.drawable.lightopen));
		}else{
			sendCommand(COMMAND_2_close); //关闭第二个灯
			button2.setImageDrawable(getResources().getDrawable(R.drawable.close));
			light2.setImageDrawable(getResources().getDrawable(R.drawable.lightclose));
		}
	}
	public void setLight3On(){
		light3.setImageDrawable(getResources().getDrawable(R.drawable.lightopen));
	}
	public void setLight3Off(){
		light3.setImageDrawable(getResources().getDrawable(R.drawable.lightclose));
	}
	public void setLight4On(){
		light4.setImageDrawable(getResources().getDrawable(R.drawable.lightopen));
	}
	public void setLight4Off(){
		light4.setImageDrawable(getResources().getDrawable(R.drawable.lightclose));
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		endListen();
		super.onDestroy();
	}
	public void startListen(){
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(myReceiver, filter);
	}
	public void endListen(){
		unregisterReceiver(myReceiver);
	}
}
