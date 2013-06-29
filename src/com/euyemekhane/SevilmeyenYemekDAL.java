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
			
			db.close();

			return yemekler;

		}catch(Exception ex) {
			return null;
		}
	}
	
	public ArrayList<SevilmeyenYemek> SeciliYemekleriGetir()
	{
		ArrayList<SevilmeyenYemek> yemekler = new ArrayList<SevilmeyenYemek>();
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select * from SevilmeyenYemek where Selected = 1", null);

			yemekler = ConvertToEntity(c);

			c.close();
			
			db.close();

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
				
				if (getCursorInt(c, "Selected") == 1)
					entYemek.setSelected(true);
				else
					entYemek.setSelected(false);

				lstYemek.add(entYemek);

			}while (c.moveToNext());
		}

		return lstYemek;
	}

	public void YemekKaydet(SevilmeyenYemek entYemek)
	{
		try {
			SQLiteDatabase db = getDatabase();
			ContentValues values = new ContentValues();

			values.put("YemekAdi", entYemek.getYemekAdi());

			if(entYemek.isSelected() == false)
				values.put("Selected", 0);
			else
				values.put("Selected", 1);
			
			db.insert("SevilmeyenYemek", null, values);
			
			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SevilmeyenYemekKayit", ex.getMessage());
		}
	}
	
	public void YemekSil(SevilmeyenYemek entYemek)
	{
		try {
			SQLiteDatabase db = getDatabase();

			db.execSQL("delete from SevilmeyenYemek where YemekAdi = '" + entYemek.getYemekAdi() + "'");

			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SevilmeyenYemekSil", ex.getMessage());
		}
	}
	
	public void SecilenGuncelle(SevilmeyenYemek entYemek, int x)
	{
		try {
			SQLiteDatabase db = getDatabase();
			ContentValues args = new ContentValues();
			
			args.put("Selected", x);

			db.update("SevilmeyenYemek", args, "YemekAdi = '" + entYemek.getYemekAdi() + "'", null);

			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SecilenGuncelle", ex.getMessage());
		}
	}

	public void SevilmeyenGuncelle(SevilmeyenYemek entYemek, int x)
	{
		try {
			SQLiteDatabase db = getDatabase();
			ContentValues args = new ContentValues();

			args.put("Sevilmeyen", x);

			db.update("EUYemekhane", args, "YemekMenusu like '%" + entYemek.getYemekAdi() + "%'", null);

			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SevilmeyenGuncelle", ex.getMessage());
		}
	}

}
