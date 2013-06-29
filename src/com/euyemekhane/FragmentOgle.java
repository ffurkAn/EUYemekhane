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

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FragmentOgle extends SherlockFragment {

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
			LinearLayout ll = (LinearLayout) getView().findViewById(R.id.ogleLinear);
			LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			TextView txtView = new TextView(getActivity());
			txtView.setLayoutParams(params);
			ll.addView(txtView);
			txtView.setTextSize(25);
			menu = dalMenu.GunlukOgleYemekGetir(c.get(Calendar.DAY_OF_MONTH), (c.get(Calendar.MONTH) + 1));
			if (menu == null) {
				txtView.setText("Yemek bulunamadý");
			} else {

				if (menu.getSevilmeyen() == 1) {
					txtView.setTextColor(Color.RED);
				} else {
					txtView.setTextColor(Color.BLACK);
				}

				gunlukMenu = splitter.split(menu.getMenu());
				txtView.append("" + menu.getTarih() + "\n");
				size = gunlukMenu.length;
				for (String s : gunlukMenu) {
					if (--size == 0) {
						txtView.append("\nToplam cal = ");
					}
					txtView.append("" + s.trim() + "\n");
				}
			}

		} else if (getArguments().getInt("GosterimTipi", -1) == 2) {
			MenuDAL dalMenu = new MenuDAL(getActivity());
			ListView listView = (ListView) getView().findViewById(R.id.ogleListView);
			ArrayList<Menu> menuListe = dalMenu.TumOgleGetir();
			CustomAdapter adapter = new CustomAdapter(getActivity(), R.id.ogleListView, menuListe);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ogle, container, false);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
}
