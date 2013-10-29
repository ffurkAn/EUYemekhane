package com.euyemekhane;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class Tutorial extends SherlockFragmentActivity {

	private ActionBar mActionBar;
	private ViewPager mPager;
	private TutorialPagerAdapter mPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);
		
		mActionBar = getSupportActionBar();
		mActionBar.hide();

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, TutorialFragment1.class.getName()));
        fragments.add(Fragment.instantiate(this, TutorialFragment2.class.getName()));
        fragments.add(Fragment.instantiate(this, TutorialFragment3.class.getName()));
        fragments.add(Fragment.instantiate(this, TutorialFragment4.class.getName()));
		
		mPagerAdapter  = new TutorialPagerAdapter(super.getSupportFragmentManager(), fragments);
		mPager = (ViewPager) findViewById(R.id.tutorialPager);
		mPager.setAdapter(mPagerAdapter);
	}

}
