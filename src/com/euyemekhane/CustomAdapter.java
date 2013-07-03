package com.euyemekhane;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
					Menu element = (Menu) buttonView.getTag();
					element.setSelected(isChecked);
					MenuDAL dalMenu = new MenuDAL(activity);
					int x;

					if (element.isSelected())
						x = 1;
					else
						x = 0;

					dalMenu.SecilenGuncelle(element, x);
				}
			});
			viewHolder.checkBox.setTag(entries.get(position));
			v.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder)v.getTag();
			((ViewHolder) v.getTag()).checkBox.setTag(entries.get(position));
		}

		final Menu menu = entries.get(position);
		if (menu != null) {
			if (menu.getSevilmeyen() == 1)
				viewHolder.text2.setTextColor(Color.RED);
			else
				viewHolder.text2.setTextColor(Color.BLACK);

			viewHolder.text1.setText(menu.getTarih());
			viewHolder.text2.setText("");
			Pattern splitter = Pattern.compile("[\\/=]");
			String[] gunlukMenu = splitter.split(menu.getMenu());
			int size = gunlukMenu.length;
			for (String s : gunlukMenu) {
				if (--size == 0) {
					viewHolder.text2.append("\nToplam cal = ");
				}
				viewHolder.text2.append("" + s.trim() + "\n");
			}
			viewHolder.checkBox.setChecked(entries.get(position).isSelected());
		}

		return v;
	}
}
