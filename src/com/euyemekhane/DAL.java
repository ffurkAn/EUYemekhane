package com.euyemekhane;

import android.database.Cursor;

public class DAL {
	protected String getCursorStr(Cursor crs, String name) {
		return crs.getString(crs.getColumnIndex(name));
	}

	protected double getCursorDouble(Cursor crs, String name) {
		return crs.getDouble(crs.getColumnIndex(name));
	}

	protected int getCursorBool(Cursor crs, String name) {
		return crs.getString(crs.getColumnIndex(name)).equals("true") ? 1 : 0;
	}

	protected int getCursorInt(Cursor crs, String name) {
		try {
			return crs.getInt(crs.getColumnIndex(name));
		}catch (Exception e) {
			return -999;
		}
	}

	protected String CheckString(Object value) {
		if (value == null)
			return "";
		else
			return value.toString();
	}

	protected Boolean CheckBool(Object value) {
		return Boolean.parseBoolean(value.toString());
	}

	protected int CheckInt(Object value) {
		if (value == null)
			return 0;
		else
			return Integer.parseInt(value.toString());
	}

	protected double CheckDouble(Object value) {
		if (value == null)
			return 0;
		else
			return Double.parseDouble(value.toString());
	}
}
