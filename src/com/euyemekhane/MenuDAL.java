package com.euyemekhane;

import java.util.ArrayList;
import java.util.Calendar;

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

	public Menu GunlukOgleYemekGetir(int gun, int ay)
	{
		try {
			SQLiteDatabase db = getDatabase();
			Cursor c;

			if (gun < 10)
				c = db.rawQuery("select * from EUYemekhane where YemekTuru = 'ogle' and MenuTarihi like '%0" + gun + "%" + ay + "%'", null);
			else
				c = db.rawQuery("select * from EUYemekhane where YemekTuru = 'ogle' and MenuTarihi like '%" + gun + "%" + ay + "%'", null);
				
			c.moveToFirst();

			Menu entMenu = new Menu();

			entMenu.setAy(getCursorStr(c, "MenuAyi"));
			entMenu.setTur(getCursorStr(c, "YemekTuru"));
			entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
			entMenu.setGun(getCursorInt(c, "Gun"));
			entMenu.setHafta(getCursorInt(c, "Hafta"));
			entMenu.setMenu(getCursorStr(c, "YemekMenusu"));
			entMenu.setSevilmeyen(getCursorInt(c, "Sevilmeyen"));

			c.close();
			
			db.close();

			return entMenu;

		} catch (Exception e) {
			return null;
		}
	}

	public Menu GunlukAksamYemekGetir(int gun, int ay)
	{
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c;
			
			if (gun < 10)
				c = db.rawQuery("select * from EUYemekhane where YemekTuru = 'aksam' and MenuTarihi like '%0" + gun + "%" + ay + "%'", null);
			else
				c = db.rawQuery("select * from EUYemekhane where YemekTuru = 'aksam' and MenuTarihi like '%" + gun + "%" + ay + "%'", null);
		
			c.moveToFirst();

			Menu entMenu = new Menu();

			entMenu.setAy(getCursorStr(c, "MenuAyi"));
			entMenu.setTur(getCursorStr(c, "YemekTuru"));
			entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
			entMenu.setGun(getCursorInt(c, "Gun"));
			entMenu.setHafta(getCursorInt(c, "Hafta"));
			entMenu.setMenu(getCursorStr(c, "YemekMenusu"));
			entMenu.setSevilmeyen(getCursorInt(c, "Sevilmeyen"));

			c.close();
			
			db.close();

			return entMenu;

		} catch (Exception e) {
			return null;
		}
	}

	public Menu IlkOgleYemekGetir()
	{
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select Hafta from EUYemekhane where YemekTuru = 'ogle'", null);
			c.moveToFirst();

			Menu entMenu = new Menu();

			//entMenu.setAy(getCursorStr(c, "MenuAyi"));
			//entMenu.setTur(getCursorStr(c, "YemekTuru"));
			//entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
			//entMenu.setGun(getCursorInt(c, "Gun"));
			entMenu.setHafta(getCursorInt(c, "Hafta"));
			//entMenu.setMenu(getCursorStr(c, "YemekMenusu"));
			//entMenu.setSevilmeyen(getCursorInt(c, "Sevilmeyen"));

			c.close();
			
			db.close();

			return entMenu;

		} catch (Exception e) {
			Log.d("#ERROR IlkOgleYemekGetir", e.getMessage());
			return null;
		}
	}
	
	public Menu SonOgleYemekGetir()
	{
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select MenuAyi, MenuTarihi from EUYemekhane where YemekTuru = 'ogle'", null);
			c.moveToLast();

			Menu entMenu = new Menu();

			entMenu.setAy(getCursorStr(c, "MenuAyi"));
			//entMenu.setTur(getCursorStr(c, "YemekTuru"));
			entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
			//entMenu.setGun(getCursorInt(c, "Gun"));
			//entMenu.setHafta(getCursorInt(c, "Hafta"));
			//entMenu.setMenu(getCursorStr(c, "YemekMenusu"));
			//entMenu.setSevilmeyen(getCursorInt(c, "Sevilmeyen"));

			c.close();
			
			db.close();

			return entMenu;

		} catch (Exception e) {
			return null;
		}
	}

	public Menu IlkAksamYemekGetir()
	{
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select Hafta from EUYemekhane where YemekTuru = 'aksam'", null);
			c.moveToFirst();

			Menu entMenu = new Menu();

			//entMenu.setAy(getCursorStr(c, "MenuAyi"));
			//entMenu.setTur(getCursorStr(c, "YemekTuru"));
			//entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
			//entMenu.setGun(getCursorInt(c, "Gun"));
			entMenu.setHafta(getCursorInt(c, "Hafta"));
			//entMenu.setMenu(getCursorStr(c, "YemekMenusu"));
			//entMenu.setSevilmeyen(getCursorInt(c, "Sevilmeyen"));

			c.close();
			
			db.close();

			return entMenu;

		} catch (Exception e) {
			Log.d("#ERROR IlkAksamYemekGetir", e.getMessage());
			return null;
		}
	}
	
	public Menu SonAksamYemekGetir()
	{
		try {
			SQLiteDatabase db = getDatabase();

			Cursor c = db.rawQuery("select MenuTarihi from EUYemekhane where YemekTuru = 'aksam'", null);
			c.moveToLast();

			Menu entMenu = new Menu();

			//entMenu.setAy(getCursorStr(c, "MenuAyi"));
			//entMenu.setTur(getCursorStr(c, "YemekTuru"));
			entMenu.setTarih(getCursorStr(c, "MenuTarihi"));
			//entMenu.setGun(getCursorInt(c, "Gun"));
			//entMenu.setHafta(getCursorInt(c, "Hafta"));
			//entMenu.setMenu(getCursorStr(c, "YemekMenusu"));
			//entMenu.setSevilmeyen(getCursorInt(c, "Sevilmeyen"));

			c.close();
			
			db.close();

			return entMenu;

		} catch (Exception e) {
			Log.d("#ERROR SonAksamYemekGetir", e.getMessage());
			return null;
		}
	}

	public void SecilenGuncelle(Menu entMenu, int x)
	{
		try {
			SQLiteDatabase db = getDatabase();
			ContentValues args = new ContentValues();

			args.put("Selected", x);

			db.update("EUYemekhane", args, "YemekTuru = '" + entMenu.getTur() + "' and MenuTarihi = '" + entMenu.getTarih() + "'", null);

			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SecilenGuncelle", ex.getMessage());
		}
	}

	public void SecilenleriTemizle(int s)
	{
		try {
			SQLiteDatabase db = getDatabase();
			ContentValues args = new ContentValues();

			args.put("Selected", 0);

			switch (s) {
				case 0:
					db.update("EUYemekhane", args, "YemekTuru = 'ogle'", null);
					break;
					
				case 1:
					db.update("EUYemekhane", args, "YemekTuru = 'aksam'", null);
					break;
	
				default:
					break;
			}

			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SecilenleriTemizle", ex.getMessage());
		}
	}
	
	public void SevilenleriSec(int s)
	{
		try {
			SQLiteDatabase db = getDatabase();
			ContentValues args = new ContentValues();

			args.put("Selected", 1);

			switch (s) {
				case 0:
					db.update("EUYemekhane", args, "YemekTuru = 'ogle' and Sevilmeyen = 0", null);
					break;
					
				case 1:
					db.update("EUYemekhane", args, "YemekTuru = 'aksam' and Sevilmeyen = 0", null);
					break;
	
				default:
					break;
			}

			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR SevilenleriSec", ex.getMessage());
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

			db.close();

			return menuler;

		}catch(Exception ex) {
			return null;
		}
	}

	public ArrayList<Menu> SeciliKayitlariGetir(int s)
	{
		ArrayList<Menu> menuler = new ArrayList<Menu>();
		try {
			SQLiteDatabase db = getDatabase();
			Cursor c;

			switch (s) {
			case 1:
				c = db.rawQuery("select * from EUYemekhane where YemekTuru = 'ogle' and Selected = 1", null);
				break;

			case 2:
				c = db.rawQuery("select * from EUYemekhane where YemekTuru = 'aksam' and Selected = 1", null);
				break;

			default:
				c = db.rawQuery("select * from EUYemekhane where Selected = 1", null);
				break;
			}

			menuler = ConvertToEntity(c);

			c.close();

			db.close();

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

			Cursor c = db.rawQuery("select * from EUYemekhane where YemekTuru = 'ogle'", null);

			menuler = ConvertToEntity(c);

			c.close();

			db.close();

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

			Cursor c = db.rawQuery("select * from EUYemekhane where YemekTuru = 'aksam'", null);

			menuler = ConvertToEntity(c);

			c.close();

			db.close();

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
				entMenu.setGun(getCursorInt(c, "Gun"));
				entMenu.setHafta(getCursorInt(c, "Hafta"));
				entMenu.setMenu(getCursorStr(c, "YemekMenusu"));
				entMenu.setSevilmeyen(getCursorInt(c, "Sevilmeyen"));

				if (getCursorInt(c, "Selected") == 1)
					entMenu.setSelected(true);
				else
					entMenu.setSelected(false);

				lstMenu.add(entMenu);

			}while (c.moveToNext());
		}

		c.close();
		
		return lstMenu;
	}

	public void MenuKaydet(Menu entMenu)
	{
		try {
			SQLiteDatabase db = getDatabase();
			ContentValues values = new ContentValues();

			values.put("MenuAyi", entMenu.getAy());
			values.put("YemekTuru", entMenu.getTur());
			values.put("MenuTarihi", entMenu.getTarih());
			values.put("Gun", entMenu.getGun());
			values.put("Hafta", entMenu.getHafta());
			values.put("YemekMenusu", entMenu.getMenu());
			values.put("Sevilmeyen", entMenu.getSevilmeyen());

			if(entMenu.isSelected() == false)
				values.put("Selected", 0);
			else
				values.put("Selected", 1);

			db.insert("EUYemekhane", null, values);

			db.close();

		}catch (Exception ex) {
			Log.d("#ERROR MenuKayit", ex.getMessage());
		}
	}

	public void TumKayitlariSil()
	{
		SQLiteDatabase db = getDatabase();

		db.execSQL("delete from EUYemekhane");

		db.close();
	}

	public void GunlukEskiKayitlariSil()
	{
		final Calendar c = Calendar.getInstance();
		int gun = c.get(Calendar.DAY_OF_MONTH); 
		int ay = (c.get(Calendar.MONTH)+1); 
		int id;

		SQLiteDatabase db = getDatabase();

		Cursor crsrOgle;
		
		if (gun < 10)
			crsrOgle = db.rawQuery("select * from EUYemekhane where YemekTuru = 'ogle' and MenuTarihi like '%0" + gun + "%" + ay + "%'", null);
		else
			crsrOgle = db.rawQuery("select * from EUYemekhane where YemekTuru = 'ogle' and MenuTarihi like '%" + gun + "%" + ay + "%'", null);
		
		if (crsrOgle.getCount() > 0)
		{
			crsrOgle.moveToFirst();
			id = crsrOgle.getInt(0); 
			db.execSQL("delete from EUYemekhane where EUYemekhane.YemekTuru = 'ogle' and EUYemekhane.id <" + id);
		}
		crsrOgle.close();

		Cursor crsrAksam;
		
		if (gun < 10)
			crsrAksam = db.rawQuery("select * from EUYemekhane where YemekTuru = 'aksam' and MenuTarihi like '%0" + gun + "%" + ay + "%'", null);
		else
			crsrAksam = db.rawQuery("select * from EUYemekhane where YemekTuru = 'aksam' and MenuTarihi like '%" + gun + "%" + ay + "%'", null);

		if (crsrAksam.getCount() > 0)
		{
			crsrAksam.moveToFirst();
			id = getCursorInt(crsrAksam, "id");	
			db.execSQL("delete from EUYemekhane where EUYemekhane.YemekTuru = 'aksam' and EUYemekhane.id <" + id);
		}
		crsrAksam.close();

		db.close();
	}
	
	public void HaftalikEskiKayitlariSil()
	{
		final Calendar c = Calendar.getInstance();
		int hafta = c.get(Calendar.WEEK_OF_MONTH);

		SQLiteDatabase db = getDatabase();

		db.execSQL("delete from EUYemekhane where Hafta < " + hafta);

		db.close();
	}

}
