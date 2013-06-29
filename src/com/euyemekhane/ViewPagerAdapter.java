package com.euyemekhane;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 2;
	private int gosterimTipi = 0;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
			case 0:
				Bundle args = new Bundle();
				args.putInt("GosterimTipi", gosterimTipi);
				FragmentOgle fragmentTab1 = new FragmentOgle();
				fragmentTab1.setArguments(args);
				return fragmentTab1;
	
			case 1:
				args = new Bundle();
				args.putInt("GosterimTipi", gosterimTipi);
				FragmentAksam fragmentTab2 = new FragmentAksam();
				fragmentTab2.setArguments(args);
				return fragmentTab2;
		}

		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGE_COUNT;
	}

	public int getGosterimTipi() {
		return gosterimTipi;
	}

	public void setGosterimTipi(int gosterimTipi) {
		this.gosterimTipi = gosterimTipi;
	}
}
