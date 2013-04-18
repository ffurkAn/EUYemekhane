package com.euyemekhane;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SevilmeyenYemekDAL extends DAL {

private Context context;

	public SevilmeyenYemekDAL(Context context)
	{
		this.context=context;
	}

	private SQLiteDatabase getDatabase()
	{
		DBAccessFile newDB = new DBAccessFile(context);
		return newDB.getDB();
	}

	//methods
}
