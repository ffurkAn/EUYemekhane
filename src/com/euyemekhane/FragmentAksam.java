package com.euyemekhane;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FragmentAksam extends SherlockFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getArguments().getInt("GosterimTipi", -1) == 1) {
			int size;
			MenuDAL dalMenu = new MenuDAL(getActivity());
			String[] gunlukMenu;
			Menu menu;
			Calendar c = Calendar.getInstance();
			Pattern splitter = Pattern.compile("[\\/=]");
			LinearLayout ll = (LinearLayout) getView().findViewById(R.id.aksamLinear);
			LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(8, 0, 0, 0);
			TextView txtViewTarih = new TextView(getActivity());
			TextView txtView = new TextView(getActivity());
			txtViewTarih.setLayoutParams(params);
			txtView.setLayoutParams(params);
			ll.addView(txtViewTarih);
			ll.addView(txtView);
			txtView.setTextSize(20);
			txtViewTarih.setTextSize(23);
			txtViewTarih.setTextColor(Color.rgb(0, 153, 204));

			menu = dalMenu.GunlukAksamYemekGetir(c.get(Calendar.DAY_OF_MONTH), (c.get(Calendar.MONTH) + 1));
			if (menu == null) {
				Toast.makeText(getActivity(), "Yemek bulunamadý", Toast.LENGTH_SHORT).show();
			} else {

				if (menu.getSevilmeyen() == 1) {
					txtView.setTextColor(Color.RED);
				} else {
					txtView.setTextColor(Color.BLACK);
				}

				txtViewTarih.setText("" + menu.getTarih() + "\n");
				gunlukMenu = splitter.split(menu.getMenu());
				size = gunlukMenu.length;
				for (String s : gunlukMenu) {
					if (--size == 0) {
						txtView.append("\nToplam cal = ");
					}
					txtView.append("" + s.trim() + "\n");
				}
			}

		} else if (getArguments().getInt("GosterimTipi", -1) == 2) {
			buildListView();
		}

	}

	public void buildListView()
	{
		MenuDAL dalMenu = new MenuDAL(getActivity());
		ArrayList<Menu> menuListe = dalMenu.TumAksamGetir();
		if (menuListe.isEmpty()) {
			Toast.makeText(getActivity(), "Liste yok", Toast.LENGTH_SHORT).show();
		} else {
			ListView listView = (ListView) getView().findViewById(R.id.aksamListView);
			CustomAdapter adapter = new CustomAdapter(getActivity(), R.id.aksamListView, menuListe);
			listView.setAdapter(adapter);
		}
	}

	@Override
	public SherlockFragmentActivity getSherlockActivity() {
		return super.getSherlockActivity();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_aksam, container, false);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
}
