package com.euyemekhane;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class OgleAksam extends SherlockFragmentActivity {

	private ActionBar mActionBar;
	private ViewPager mPager;
	private Tab tab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ogle_aksam);

		Intent preIntent = getIntent();

		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		if (preIntent.getIntExtra("GosterimTipi", -1) == 1) {
			mActionBar.setTitle("Günlük Menü");
		}

		mPager = (ViewPager) findViewById(R.id.pager);

		FragmentManager fm = getSupportFragmentManager();

		ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				mActionBar.setSelectedNavigationItem(position);
			}
		};
		
		mPager.setOnPageChangeListener(ViewPagerListener);
		ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fm);
		viewPagerAdapter.setGosterimTipi(preIntent.getIntExtra("GosterimTipi", -1));
		mPager.setAdapter(viewPagerAdapter);

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// Pass the position on tab click to ViewPager
				mPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}
		};

		tab = mActionBar.newTab().setText("Öðle").setTabListener(tabListener);
		mActionBar.addTab(tab);

		tab = mActionBar.newTab().setText("Akþam").setTabListener(tabListener);
		mActionBar.addTab(tab);
		
		Time now = new Time();
		now.setToNow();

		if ((preIntent.getIntExtra("GosterimTipi", -1) == 1) && (now.hour > 14 && now.hour < 19)) {
			mPager.setCurrentItem(1);
		}
	}
}
