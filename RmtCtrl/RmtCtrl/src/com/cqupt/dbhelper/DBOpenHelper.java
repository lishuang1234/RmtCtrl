package com.cqupt.dbhelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**.
 * 功能：继承SQLiteOpenHelper，创建初始化数据库
 * @author LS
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper  {
	private static final int VERSION = 1;
	private static final String CREATE_SQL = 
			"create table room" +
			"(" +
				"id int PRIMARY KEY," +
				"name varchar(20) NOT NULL, " +
				"ip varchar(20) NOT NULL, " +
				"port varchar(6) NOT NULL" +
			")";  
	public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DBOpenHelper(Context context, String name, int version){  
        this(context,name,null,version);  
    }  
    public DBOpenHelper(Context context, String name){  
        this(context,name,VERSION);  
    }  
    /**
     * 创建数据库
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create a database");
		db.execSQL(CREATE_SQL); 
	}
	/**
	 * 数据库更新方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("upgrade a database");
	}
}
