package com.euyemekhane;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAccessFile extends SQLiteOpenHelper {

	private final static int DATABASE_VERSION = 6;
	private final static String DATABASE_NAME = "YEMEKHANE";
	private static String TABLE_NAME = "";

	public DBAccessFile(Context context, String tabloAdi, int version) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		TABLE_NAME = tabloAdi;
	}

	public DBAccessFile(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE EUYemekhane(id INTEGER PRIMARY KEY autoincrement, MenuAyi TEXT, YemekTuru TEXT, MenuTarihi TEXT, Gun INTEGER, YemekMenusu TEXT, Sevilmeyen INTEGER, Selected INTEGER)");
		db.execSQL("CREATE TABLE SevilmeyenYemek(YemekAdi TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(newVersion > oldVersion) {
			db.execSQL("DROP TABLE IF EXISTS EUYemekhane");
			db.execSQL("DROP TABLE IF EXISTS SevilmeyenYemek");
			onCreate(db);
		}
	}

	public SQLiteDatabase getDB()
	{
		return this.getWritableDatabase();
	}

}
