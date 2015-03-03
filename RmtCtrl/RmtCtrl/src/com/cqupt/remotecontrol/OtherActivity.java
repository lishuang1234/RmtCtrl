package com.cqupt.remotecontrol;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * ���ܣ������豸�б����
 * @author LS
 *
 */
public class OtherActivity extends Activity {
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(discovery);
	}
	public List<BluetoothDevice> deviceList;
	public boolean DISCOVERY_OVER = false;
	public ProgressDialog pd;
	public ArrayAdapter<String> adapter;
	public ListView list1;
	public List<String> infor;
	public static final String DEVICE_LIST = "com.example.action.device_list";
	public static final String CONNECT = "com.example.action.connect";
	public static final String SERVICE = "com.example.server.searchstart";// ����Service
	public BluetoothDevice device;
	public BluetoothSocket socket;
	public BluetoothAdapter btAdapter;
	public Discovery discovery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_activity);
		btAdapter = BluetoothAdapter.getDefaultAdapter();// ��ȡĬ��������
		deviceList = new ArrayList<BluetoothDevice>();
		pd = new ProgressDialog(OtherActivity.this);
		list1 = (ListView) findViewById(R.id.list1);
		infor = new ArrayList<String>();// ������Ϣ
		discovery = new Discovery();
		IntentFilter filter = new IntentFilter();
		filter = new IntentFilter(DEVICE_LIST);
		registerReceiver(discovery, filter);
		Intent intent2 = new Intent();
		intent2.setAction(SERVICE);
		startService(intent2);// ����Service
		new Work().execute(DISCOVERY_OVER);
	}
	/**
	 * ������������������Ϣ
	 * 
	 * @author Administrator
	 * 
	 */
	class Discovery extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (DEVICE_LIST.equals(action))// ���յ����������{��
			{
				infor = intent.getStringArrayListExtra("device");
				DISCOVERY_OVER = true;
			}
		}
	}
	/**
	 * ����б����������豸
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// ����б������������
		list1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(CONNECT);
				intent.putExtra("position", position);// Service���ܽ������ӹ㲥
				sendBroadcast(intent);// ���͹Ⲩ��Service��������
				intent.setClass(OtherActivity.this, BtActivity.class);// ���ܽ���
				startActivity(intent);
			}
		});
	}
	/**
	 * �������ؽ���������List
	 * 
	 * @author Administrator
	 * 
	 */
	protected class Work extends AsyncTask<Boolean, String, String> {
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			adapter = new ArrayAdapter<String>(OtherActivity.this,
					R.layout.information, infor);
			list1.setAdapter(adapter);
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.setMessage("�������������Ժ󡭡�");
			pd.setIndeterminate(false);// �����ֵ��Сֵ���ƶ�
			pd.setCancelable(false);// ����ȡ��
			pd.show();
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
				if (DISCOVERY_OVER) {
					pd.dismiss();
					break;
				}
			}
			return null;
		}
	}
}
