package com.euyemekhane;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Menu> {
	private ArrayList<Menu> entries;
	private Activity activity;

	public CustomAdapter(Activity a, int textViewResourceId, ArrayList<Menu> entries) {
		super(a, textViewResourceId, entries);
		this.entries = entries;
		this.activity = a;
	}

	public static class ViewHolder {
		protected TextView text1;
		protected TextView text2;
		protected CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final ViewHolder viewHolder;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.text1 = (TextView) v.findViewById(R.id.big);
			viewHolder.text2 = (TextView) v.findViewById(R.id.small);
			viewHolder.checkBox = (CheckBox) v.findViewById(R.id.checkBox);
			viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					Menu element = (Menu) viewHolder.checkBox.getTag();
					element.setSelected(buttonView.isChecked());
					MenuDAL dalMenu = new MenuDAL(getContext());
					SQLiteDatabase db = dalMenu.getDatabase();
					
					////////////
					ContentValues args = new ContentValues();
					if(isChecked)
						{
						args.put("Selected", 1);						
						}
					else
						{
						args.put("Selected", 0);
						}
					int gun = element.getGun();
					
					db.update("EUYemekhane", args, "YemekMenusu like '%"+element.getAy().toString()+"%"+element.getGun()+"%'", null);
					
					///////////
				}
			});

			v.setTag(viewHolder);
			viewHolder.checkBox.setTag(entries.get(position));
		} else {
			viewHolder = (ViewHolder)v.getTag();
			((ViewHolder) v.getTag()).checkBox.setTag(entries.get(position));
		}

		final Menu menu = entries.get(position);
		if (menu != null) {
			viewHolder.text1.setText(menu.getTarih());
			viewHolder.text2.setText(menu.getMenu());
			viewHolder.checkBox.setChecked(entries.get(position).isSelected());
		}
		return v;
	}
}
