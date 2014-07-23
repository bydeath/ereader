package cn.dengzhiguo.eread.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataHelper extends OrmLiteSqliteOpenHelper {

	public DataHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, "eread.db", factory, 1);
		// TODO Auto-generated constructor stub
	}
	public DataHelper(Context context) {
		super(context, "eread.db", null, 1);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(connectionSource, Book.class);
			TableUtils.createTable(connectionSource, Newword.class);
			
		} catch (Exception e) {
			Log.e(DataHelper.class.getName(), "创建数据库失败", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldversion,
			int newversion) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		
	}

	

}
