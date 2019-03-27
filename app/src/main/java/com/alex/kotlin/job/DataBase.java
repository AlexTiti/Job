package com.alex.kotlin.job;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Alex
 * @date 2019/1/23 下午3:01
 * @email 18238818283@sina.cn
 * @desc ...
 */
public class DataBase extends SQLiteOpenHelper {


	public DataBase(Context context) {
		super(context, "job.db", null, 1);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE  job (_id integer primary key autoincrement, name varchar )";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
