package com.euyemekhane;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class SevilmeyenYemekAdapter extends ArrayAdapter<SevilmeyenYemek> {
	private ArrayList<SevilmeyenYemek> entries;
	private Activity activity;

	public SevilmeyenYemekAdapter(Activity a, int textViewResourceId, ArrayList<SevilmeyenYemek> entries) {
		super(a, textViewResourceId, entries);
		this.entries = entries;
		this.activity = a;
	}

	public static class ViewHolder {
		protected TextView text;
		protected CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final ViewHolder viewHolder;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.sevilmeyen_listview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.text = (TextView) v.findViewById(R.id.sevilmeyenListViewYemekAdi);
			viewHolder.checkBox = (CheckBox) v.findViewById(R.id.checkBoxSevilmeyen);
			viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					SevilmeyenYemek element = (SevilmeyenYemek) buttonView.getTag();
					element.setSelected(isChecked);
					SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(activity);
					int x;

					if (element.isSelected())
						x = 1;
					else
						x = 0;
					
					dalSevilmeyen.SecilenGuncelle(element, x);
				}
			});
			viewHolder.checkBox.setTag(entries.get(position));
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)v.getTag();
			((ViewHolder) v.getTag()).checkBox.setTag(entries.get(position));
		}

		final SevilmeyenYemek yemek = entries.get(position);
		if (yemek != null) {
			viewHolder.text.setText(yemek.getYemekAdi());
			viewHolder.checkBox.setChecked(entries.get(position).isSelected());
		}
		
		return v;
	}
}
