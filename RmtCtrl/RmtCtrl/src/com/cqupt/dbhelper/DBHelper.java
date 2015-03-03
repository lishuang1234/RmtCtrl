package com.cqupt.dbhelper;
import java.util.LinkedList;
import java.util.List;
import com.cqupt.model.Room;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 功能：实现Room信息数据库增删改查
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
        // 向该对象中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致  
        values.put("name", name);  
        values.put("ip", ip);  
        values.put("port", port);  
        SQLiteDatabase sqliteDatabase = dbOpenHelper.getWritableDatabase();  
        // 调用insert方法，就可以将数据插入到数据库当中  
        // 第一个参数:表名称  
        // 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值  
        // 第三个参数：ContentValues对象  
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
	//从数据库中查询数据
	public List<Room> queryData(){
		List<Room> list = new LinkedList<Room>();
		//获得数据库对象
		SQLiteDatabase sqliteDatabase = dbOpenHelper.getReadableDatabase();
		//查询表中的数据
		Cursor cursor = sqliteDatabase.query("room", null, null, null, null, null, null);
		//获取name列的索引
		int nameIndex = cursor.getColumnIndex("name");
		//获取level列的索引
		int ipIndex = cursor.getColumnIndex("ip");
		//获取level列的索引
		int portIndex = cursor.getColumnIndex("port");
		for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
			String name = cursor.getString(nameIndex);
			String ip = cursor.getString(ipIndex);
			String port = cursor.getString(portIndex);
			list.add(new Room(name, ip, port));
		}
		cursor.close();//关闭结果集
		sqliteDatabase.close();//关闭数据库对象
		return list;
	}
	/**
	 * 关闭数据库资源
	 */
	public void close(){
		dbOpenHelper.close();
		dbOpenHelper = null;
	}
}
