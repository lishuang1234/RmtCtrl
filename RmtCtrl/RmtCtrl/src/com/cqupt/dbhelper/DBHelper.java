package com.cqupt.dbhelper;
import java.util.LinkedList;
import java.util.List;
import com.cqupt.model.Room;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * ���ܣ�ʵ��Room��Ϣ���ݿ���ɾ�Ĳ�
 * @author LS
 *
 */
public class DBHelper{
	private static DBOpenHelper dbOpenHelper;
	public DBHelper(Context context){
		dbOpenHelper = new DBOpenHelper(context, "room_data_db"); 
	}
	public Long insert(String name, String ip, String port){
		ContentValues values = new ContentValues();  
        // ��ö����в����ֵ�ԣ����м���������ֵ��ϣ�����뵽��һ�е�ֵ��ֵ��������ݿ⵱�е���������һ��  
        values.put("name", name);  
        values.put("ip", ip);  
        values.put("port", port);  
        SQLiteDatabase sqliteDatabase = dbOpenHelper.getWritableDatabase();  
        // ����insert�������Ϳ��Խ����ݲ��뵽���ݿ⵱��  
        // ��һ������:������  
        // �ڶ���������SQl������һ�����У����ContentValues�ǿյģ���ô��һ�б���ȷ��ָ��ΪNULLֵ  
        // ������������ContentValues����  
        Long backinfo = sqliteDatabase.insert("room", null, values);
        sqliteDatabase.close();
        return backinfo;
	}
	public void delete(String name){
		SQLiteDatabase sqliteDatabase = dbOpenHelper.getWritableDatabase(); 
		sqliteDatabase.delete("room", "name=?", new String[]{name});
		sqliteDatabase.close();
	}
	public void delete(String name, String ip, String port){
		SQLiteDatabase sqliteDatabase = dbOpenHelper.getWritableDatabase(); 
		sqliteDatabase.delete("room", "name=? and ip=? and port=?", new String[]{name,ip,port});
		sqliteDatabase.close();
	}
	//�����ݿ��в�ѯ����
	public List<Room> queryData(){
		List<Room> list = new LinkedList<Room>();
		//������ݿ����
		SQLiteDatabase sqliteDatabase = dbOpenHelper.getReadableDatabase();
		//��ѯ���е�����
		Cursor cursor = sqliteDatabase.query("room", null, null, null, null, null, null);
		//��ȡname�е�����
		int nameIndex = cursor.getColumnIndex("name");
		//��ȡlevel�е�����
		int ipIndex = cursor.getColumnIndex("ip");
		//��ȡlevel�е�����
		int portIndex = cursor.getColumnIndex("port");
		for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
			String name = cursor.getString(nameIndex);
			String ip = cursor.getString(ipIndex);
			String port = cursor.getString(portIndex);
			list.add(new Room(name, ip, port));
		}
		cursor.close();//�رս����
		sqliteDatabase.close();//�ر����ݿ����
		return list;
	}
	/**
	 * �ر����ݿ���Դ
	 */
	public void close(){
		dbOpenHelper.close();
		dbOpenHelper = null;
	}
}
