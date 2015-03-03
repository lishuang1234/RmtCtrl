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
 * ���ܣ�����������
 * @author LS
 *
 */
public class GridActivity extends Activity {
	public BluetoothAdapter adapter;
	private GridView gridview;
	public Context cxt;
	private boolean isOpenBtPermission = false;
	public static final int REQUEST_DISCOVERABLE = 1;
	public static final String CUT_CONNECT_BT = "com.example.action.cut_connect_bt";// �Ͽ���������
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.grid);
		cxt = this;
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = BluetoothAdapter.getDefaultAdapter();
		// ���ɶ�̬���飬����ת������
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.p1);
		map1.put("ItemText", "IPģʽ���");
		lstImageItem.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemImage", R.drawable.p2);
		map2.put("ItemText", "IPģʽ����");
		lstImageItem.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("ItemImage", R.drawable.p3);
		map3.put("ItemText", "���IP����");
		lstImageItem.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("ItemImage", R.drawable.p4);
		map4.put("ItemText", "����ģʽ���");
		lstImageItem.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("ItemImage", R.drawable.p5);
		map5.put("ItemText", "����ģʽ��");
		lstImageItem.add(map5);
		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("ItemImage", R.drawable.p6);
		map6.put("ItemText", "����ģʽ�ر�");
		lstImageItem.add(map6);
		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("ItemImage", R.drawable.p7);
		map7.put("ItemText", "����ģʽ���");
		lstImageItem.add(map7);
		HashMap<String, Object> map8 = new HashMap<String, Object>();
		map8.put("ItemImage", R.drawable.p8);
		map8.put("ItemText", "����ģʽ����");
		lstImageItem.add(map8);
		HashMap<String, Object> map9 = new HashMap<String, Object>();
		map9.put("ItemImage", R.drawable.p9);
		map9.put("ItemText", "�ر�");
		lstImageItem.add(map9);
		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		SimpleAdapter saImageItems = new SimpleAdapter(this, // ûʲô����
				lstImageItem,// ������Դ
				R.layout.item,// night_item��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemImage", "ItemText" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// ��Ӳ�����ʾ
		gridview.setAdapter(saImageItems);
		// �����Ϣ����
		gridview.setOnItemClickListener(new ItemClickListener());
	}
	public void exitProgram() {
		AlertDialog.Builder builder = new Builder(cxt);
		builder.setTitle("��ʾ");
		builder.setMessage("��ȷ��Ҫ�˳���");
		builder.setPositiveButton("�ǵ�",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
	// ��AdapterView������(���������߼���)���򷵻ص�Item�����¼�
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// �ڱ�����arg2=arg3
			@SuppressWarnings("unchecked")
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			// ��ʾ��ѡItem��ItemText
			setTitle((String) item.get("ItemText"));
			if (item.get("ItemText").equals("IPģʽ���")) {
				Message s = new Message();
				s.obj = "RoomList";
				handler.sendMessage(s);
			} else if (item.get("ItemText").equals("IPģʽ����")) {
				Message s = new Message();
				s.obj = "RoomList";
				handler.sendMessage(s);
			} else if (item.get("ItemText").equals("���IP����")) {
				Message s = new Message();
				s.obj = "AddEquipment";
				handler.sendMessage(s);
			} else if (item.get("ItemText").equals("����ģʽ���")) {
				// /��ת�����б����
				if (isOpenBtPermission) {
					Intent intent3 = new Intent();
					intent3.setClass(GridActivity.this, OtherActivity.class);
					startActivity(intent3);
				} else
					Toast.makeText(GridActivity.this, "δ����������δ��ȡȨ�ޣ�", 0)
							.show();
			} else if (item.get("ItemText").equals("����ģʽ��")) {
				//���������豸
				Intent enabler = new Intent(
						BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				startActivityForResult(enabler, REQUEST_DISCOVERABLE);
				isOpenBtPermission = true;
			} else if (item.get("ItemText").equals("����ģʽ�ر�")) {
				//�ر�����
				Intent sendToService = new Intent();
				sendToService.setAction(CUT_CONNECT_BT);
				sendBroadcast(sendToService);
				adapter.disable();
			} else if (item.get("ItemText").equals("����ģʽ���")) {
				Message s = new Message();
				s.obj = "MessageActivity";
				handler.sendMessage(s);
			} else if (item.get("ItemText").equals("����ģʽ����")) {
				Toast.makeText(GridActivity.this, "��ʱû��ͨ�ù���", 0).show();
			} else if (item.get("ItemText").equals("�ر�")) {
				exitProgram();
			}
		}
	}
}
