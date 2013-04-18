package com.euyemekhane;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class MenuDAL extends DAL {

	private Context context;

	public MenuDAL(Context context)
	{
		this.context = context;
	}

	private SQLiteDatabase getDatabase()
	{
		DBAccessFile newDB = new DBAccessFile(context);
		return newDB.getDB();
	}
	
	public Menu SonOgleYemekGetir()
	{
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select * from EUYemekhane where EUYemekhane.YemekTuru = 'ogle'", null);
			c.moveToLast();
			
			Menu entMenu = new Menu();

			entMenu.setAy(getCursorStr(c, "MenuAyi"));
			entMenu.setTur(getCursorStr(c, "YemekTuru"));
			entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
			entMenu.setMenu(getCursorStr(c, "YemekMenusu"));

			return entMenu;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public Menu SonAksamYemekGetir()
	{
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select * from EUYemekhane where EUYemekhane.YemekTuru = 'aksam'", null);
			c.moveToLast();
			
			Menu entMenu = new Menu();

			entMenu.setAy(getCursorStr(c, "MenuAyi"));
			entMenu.setTur(getCursorStr(c, "YemekTuru"));
			entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
			entMenu.setMenu(getCursorStr(c, "YemekMenusu"));

			return entMenu;
			
		} catch (Exception e) {
			Log.d("#ERROR SonAksamYemekGetir", e.getMessage());
			return null;
		}
	}
	
	public ArrayList<Menu> TumKayitlariGetir()
	{
		ArrayList<Menu> menuler = new ArrayList<Menu>();
		try {
			SQLiteDatabase db = getDatabase();
			SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
			
			sqb.setTables("EUYemekhane");

			Cursor c = sqb.query(db, null, null, null, null, null, null);

			menuler = ConvertToEntity(c);

			c.close();

			return menuler;

		}catch(Exception ex) {
			return null;
		}

	}
	
	public ArrayList<Menu> TumOgleGetir()
	{
		ArrayList<Menu> menuler = new ArrayList<Menu>();
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select * from EUYemekhane where EUYemekhane.YemekTuru = 'ogle'", null);

			menuler = ConvertToEntity(c);

			c.close();

			return menuler;

		}catch(Exception ex) {
			return null;
		}

	}
	
	public ArrayList<Menu> TumAksamGetir()
	{
		ArrayList<Menu> menuler = new ArrayList<Menu>();
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select * from EUYemekhane where EUYemekhane.YemekTuru = 'aksam'", null);

			menuler = ConvertToEntity(c);

			c.close();

			return menuler;

		}catch(Exception ex) {
			return null;
		}

	}

	private ArrayList<Menu> ConvertToEntity(Cursor c)
	{
		ArrayList<Menu> lstMenu = new ArrayList<Menu>();

		if (c.moveToFirst()) {
			do {
				Menu entMenu = new Menu();

				entMenu.setAy(getCursorStr(c, "MenuAyi"));
				entMenu.setTur(getCursorStr(c, "YemekTuru"));
				entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
				entMenu.setMenu(getCursorStr(c, "YemekMenusu"));

				lstMenu.add(entMenu);

			}while (c.moveToNext());
		}

		return lstMenu;

	}

	public void MenuKaydet(Menu entMenu)
	{
		try {
			ContentValues values = new ContentValues();

			values.put("MenuAyi", entMenu.getAy());
			values.put("YemekTuru", entMenu.getTur());
			values.put("MenuTarihi", entMenu.getTarih());
			values.put("YemekMenusu", entMenu.getMenu());

			SQLiteDatabase db = getDatabase();
			db.insert("EUYemekhane", null, values);
			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR MenuKayit", ex.getMessage());
		}

	}

}