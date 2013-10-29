package com.euyemekhane;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TutorialPagerAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> fragments;

	public TutorialPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return this.fragments.get(position);
	}
}
