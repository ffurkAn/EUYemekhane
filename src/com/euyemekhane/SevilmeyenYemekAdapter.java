package com.euyemekhane;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SevilmeyenYemekAdapter extends ArrayAdapter<SevilmeyenYemek> {
	private ArrayList<SevilmeyenYemek> entries;
	private Activity activity;
	private SparseBooleanArray mSelectedItemsIds;
	private SevilmeyenYemekDAL dalSevilmeyen;

	public SevilmeyenYemekAdapter(Activity a, int textViewResourceId, ArrayList<SevilmeyenYemek> entries) {
		super(a, textViewResourceId, entries);
		this.entries = entries;
		this.activity = a;
		mSelectedItemsIds = new SparseBooleanArray();
		dalSevilmeyen = new SevilmeyenYemekDAL(a);
	}

	public static class ViewHolder {
		protected TextView text;
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

			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)v.getTag();
		}

		final SevilmeyenYemek yemek = entries.get(position);
		if (yemek != null) {
			viewHolder.text.setText(yemek.getYemekAdi());
			v.setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4 : Color.TRANSPARENT);
		}
		
		return v;
	}
	
	@Override
    public void remove(SevilmeyenYemek x) {
		dalSevilmeyen.YemekSil(x);
		dalSevilmeyen.SevilmeyenGuncelle(x, 0);
		entries.remove(x);
        notifyDataSetChanged();
    }
	
	public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }
 
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
 
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
 
        notifyDataSetChanged();
    }
 
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }
 
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
