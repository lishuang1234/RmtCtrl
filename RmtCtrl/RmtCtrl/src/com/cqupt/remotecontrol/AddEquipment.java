package com.cqupt.remotecontrol;
import com.cqupt.dbhelper.DBHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * ���ܣ���ӷ�����ϢActivity
 * @author LS
 *
 */
public class AddEquipment extends Activity {
	private EditText nameEditText;
	private EditText ipEditText;
	private EditText portEditText;
	private Button addButton;
	private Handler handler_show;
	private String roomname;
	private String roomip;
	private String roomport;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		setContentView(R.layout.add_equipment);
		Intent intent = getIntent();
		Bundle infoBundle = intent.getExtras();
		if(infoBundle != null){
			handler_show = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					Toast.makeText(AddEquipment.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
				}
			};
			nameEditText = (EditText)findViewById(R.id.add_name);
			ipEditText = (EditText)findViewById(R.id.add_ip);
			portEditText = (EditText)findViewById(R.id.add_port);
			addButton = (Button)findViewById(R.id.add_button);
			roomname = infoBundle.getString("name");
			roomip = infoBundle.getString("ip");
			roomport = infoBundle.getString("port");
			nameEditText.setText(roomname);
			ipEditText.setText(roomip);
			portEditText.setText(roomport);
			addButton.setText("�޸�");
			addButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String name = nameEditText.getText().toString().trim();
					String ip = ipEditText.getText().toString().trim();
					String port = portEditText.getText().toString().trim();
					if(name.equals("")){
						show("�豸������Ϊ��");
						nameEditText.requestFocus();
						return ;
					}
					if(ip.equals("")){
						show("IP��ַ����Ϊ��");
						ipEditText.requestFocus();
						return ;
					}
					if(port.equals("")){
						show("�˿ںŲ���Ϊ��");
						portEditText.requestFocus();
						return ;
					}
					if(name.equals(roomname) && ip.equals(roomip) && port.equals(roomport)){
						show("û���޸�");
						return ;
					}
					DBHelper dbHelper = new DBHelper(AddEquipment.this);
					Long backinfo = dbHelper.insert(name, ip, port);
					if(backinfo == -1){
						show("�޸�ʧ��");
						dbHelper.close();
						return ;
					}
					dbHelper.delete(roomname,roomip,roomport);
					dbHelper.close();
					show("�޸ĳɹ�");
					AddEquipment.this.finish();
				}
			});
		}else{
			init();
		}
	}
	public void init(){
		handler_show = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Toast.makeText(AddEquipment.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
			}
		};
		nameEditText = (EditText)findViewById(R.id.add_name);
		ipEditText = (EditText)findViewById(R.id.add_ip);
		portEditText = (EditText)findViewById(R.id.add_port);
		addButton = (Button)findViewById(R.id.add_button);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = nameEditText.getText().toString().trim();
				String ip = ipEditText.getText().toString().trim();
				String port = portEditText.getText().toString().trim();
				if(name.equals("")){
					show("�豸������Ϊ��");
					nameEditText.requestFocus();
					return ;
				}
				if(ip.equals("")){
					show("IP��ַ����Ϊ��");
					ipEditText.requestFocus();
					return ;
				}
				if(port.equals("")){
					show("�˿ںŲ���Ϊ��");
					portEditText.requestFocus();
					return ;
				}
				DBHelper dbHelper = new DBHelper(AddEquipment.this);
				Long backinfo = dbHelper.insert(name, ip, port);
				if(backinfo == -1){
					show("���ʧ��");
					return ;
				}
				dbHelper.close();
				show("��ӳɹ�");
				AddEquipment.this.finish();
			}
		});
	}
	public void show(String string){
		Message message = new Message();
		message.obj = string;
		handler_show.sendMessage(message);
	}
}
