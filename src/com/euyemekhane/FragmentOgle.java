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

public class FragmentOgle extends SherlockFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getArguments().getInt("GosterimTipi", -1) == 1) {
			int size;
			int dpValue = 8; //margin in dips
			float d = getActivity().getResources().getDisplayMetrics().density;
			int margin = (int)(dpValue * d); //margin in pixels
			MenuDAL dalMenu = new MenuDAL(getActivity());
			String[] gunlukMenu;
			Menu menu;
			Calendar c = Calendar.getInstance();
			Pattern splitter = Pattern.compile("[\\-]");
			LinearLayout ll = (LinearLayout) getView().findViewById(R.id.ogleLinear);
			LayoutParams params1 = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params1.setMargins(margin, margin, 0, 0);
			LayoutParams params2 = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			dpValue = 16;
			margin = (int)(dpValue * d);
			params2.setMargins(margin, 0, 0, 0);
			TextView txtViewTarih = new TextView(getActivity());
			TextView txtView = new TextView(getActivity());
			txtViewTarih.setLayoutParams(params1);
			txtView.setLayoutParams(params2);
			ll.addView(txtViewTarih);
			ll.addView(txtView);
			txtView.setTextSize(20);
			txtViewTarih.setTextSize(23);
			txtViewTarih.setTextColor(Color.rgb(0, 153, 204));

			menu = dalMenu.GunlukOgleYemekGetir(c.get(Calendar.DAY_OF_MONTH), (c.get(Calendar.MONTH) + 1));
			if (menu == null) {
				Toast.makeText(getActivity(), "Yemek bulunamadý - Öðle", Toast.LENGTH_SHORT).show();
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
					/*Kýrmýzý iþaretleme iþi burada yapýlacak*/
				}
				txtView.append("\n\n\tAfiyet olsun... :)");
			}

		} else if (getArguments().getInt("GosterimTipi", -1) == 2) {
			buildListView();
		}

	}

	public void buildListView()
	{
		MenuDAL dalMenu = new MenuDAL(getActivity());
		ArrayList<Menu> menuListe = dalMenu.TumOgleGetir();
		if (menuListe.isEmpty()) {
			Toast.makeText(getActivity(), "Liste yok - Öðle", Toast.LENGTH_SHORT).show();
		} else {
			ListView listView = (ListView) getView().findViewById(R.id.ogleListView);
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
