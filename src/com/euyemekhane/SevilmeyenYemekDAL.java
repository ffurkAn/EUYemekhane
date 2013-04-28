package com.euyemekhane;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

	public ArrayList<SevilmeyenYemek> TumYemekleriGetir()
	{
		ArrayList<SevilmeyenYemek> yemekler = new ArrayList<SevilmeyenYemek>();
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select * from SevilmeyenYemek", null);

			yemekler = ConvertToEntity(c);

			c.close();

			return yemekler;

		}catch(Exception ex) {
			return null;
		}

	}

	private ArrayList<SevilmeyenYemek> ConvertToEntity(Cursor c)
	{
		ArrayList<SevilmeyenYemek> lstYemek = new ArrayList<SevilmeyenYemek>();

		if (c.moveToFirst()) {
			do {
				SevilmeyenYemek entYemek = new SevilmeyenYemek();

				entYemek.setYemekAdi(getCursorStr(c, "YemekAdi"));

				lstYemek.add(entYemek);

			}while (c.moveToNext());
		}

		return lstYemek;

	}

	public void YemekKaydet(SevilmeyenYemek entYemek)
	{
		try {
			ContentValues values = new ContentValues();

			values.put("YemekAdi", entYemek.getYemekAdi());

			SQLiteDatabase db = getDatabase();
			db.insert("SevilmeyenYemek", null,values);
			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SevilmeyenYemekKayit", ex.getMessage());
		}

	}
	
	public void SevilmeyenGüncelle(SevilmeyenYemek entYemek)
	{
		try {
			
			  ContentValues args = new ContentValues();
			   args.put("Sevilmeyen", 1);
			    
			SQLiteDatabase db = getDatabase();
			
			
				db.update("EUYemekhane", args, "YemekMenusu like '%"+entYemek.getYemekAdi()+"%'", null);
				//db.execSQL(
					//	"update EUYemekhane set Sevilmeyen=0 where YemekMenusu like '%"+entYemek.getYemekAdi().toString()+"%'",null);
						
						
			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SevilmeyenYemekKayit", ex.getMessage());
		}
			
		
	}
}
