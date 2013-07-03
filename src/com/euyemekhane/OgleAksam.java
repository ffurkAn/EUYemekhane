package com.euyemekhane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.format.Time;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class OgleAksam extends SherlockFragmentActivity {

	private ActionBar mActionBar;
	private ViewPager mPager;
	private Tab tab;
	private MenuDAL dalMenu = new MenuDAL(this);
	private Intent preIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ogle_aksam);

		preIntent = getIntent();
		
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
		tab.setTag("ogle");
		mActionBar.addTab(tab);

		tab = mActionBar.newTab().setText("Akþam").setTabListener(tabListener);
		tab.setTag("aksam");
		mActionBar.addTab(tab);
		
		Time now = new Time();
		now.setToNow();

		if ((preIntent.getIntExtra("GosterimTipi", -1) == 1) && (now.hour > 14 && now.hour < 19)) {
			mPager.setCurrentItem(1);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if (preIntent.getIntExtra("GosterimTipi", -1) == 2) {
			com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
			inflater.inflate(R.menu.actionbar_menu, menu);
			return super.onCreateOptionsMenu(menu);
		}
 
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int currentItem = mPager.getCurrentItem();
		
		switch (item.getItemId()) {
	        case R.id.sevilenleriSec:
	            dalMenu.SevilenleriSec(currentItem);

	            if (currentItem == 0) {
	            	FragmentOgle fragOgle = (FragmentOgle) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
		            fragOgle.buildListView();
	            } else if (currentItem == 1) {
	            	FragmentAksam fragAksam = (FragmentAksam) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":1");
		            fragAksam.buildListView();
	            }

	            return true;
	        
	        case R.id.secilenleriTemizle:
	        	dalMenu.SecilenleriTemizle(currentItem);

	        	if (currentItem == 0) {
	            	FragmentOgle fragOgle = (FragmentOgle) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
		            fragOgle.buildListView();
	            } else if (currentItem == 1) {
	            	FragmentAksam fragAksam = (FragmentAksam) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":1");
		            fragAksam.buildListView();
	            }

	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
