package com.suiding.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.suiding.activity.framework.FragmentActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.fragment.framework.FragmentBase;
import com.suiding.util.ExtraUtil;
import com.suiding.util.XmlCacheUtil;

public class WelcomeTrendsActivity extends FragmentActivityBase implements
		OnPageChangeListener, OnClickListener {

	public static final String HAVERUN_VERSION = "RUN_VERSION";
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private PagerAdapter mPagerAdapter;

	private static WelcomeTrendsActivity mWelcomeTrends = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_welcome_trends);
		mWelcomeTrends = this;
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		FragmentManager manager = getSupportFragmentManager();
		mPagerAdapter = new PagerAdapter(manager);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mViewPager.getCurrentItem() < WelcomeFragment.FRAGMENT_COUNT - 1) {
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
		} else {
			XmlCacheUtil.putInt(HAVERUN_VERSION, SuidingApp.getVersionCode());
            SuidingApp.getApp().startForeground(this);
			finish();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int currPage) {
		// TODO Auto-generated method stub
		// mRadioButtons[currPage].setChecked(true);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class PagerAdapter extends FragmentPagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			ExtraUtil.putExtra(WelcomeFragment.TEXRA_SECTION_NUMBER, position);
			WelcomeFragment fragment = new WelcomeFragment();
			// Bundle args = new Bundle();
			// args.putInt(WelcomeFragment.ARG_SECTION_NUMBER,position);
			// fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return WelcomeFragment.FRAGMENT_COUNT;
		}
	}

	public static class WelcomeFragment extends FragmentBase {

		public static final int FRAGMENT_COUNT = 3;
		public static final String TEXRA_SECTION_NUMBER = "ARG_SECTION_NUMBER";

		public int number = 0;

		public WelcomeFragment() {
			number = ExtraUtil.getExtraInt(TEXRA_SECTION_NUMBER, 0);
		}

		@Override
		protected final View onCreateView(LayoutInflater inflater,
				ViewGroup container) {
			// TODO Auto-generated method stub
			switch (number) {
			case 0:
				return inflater.inflate(R.layout.welcome_one, container, false);
			case 1:
				return inflater.inflate(R.layout.welcome_two, container, false);
			case 2:
				return inflater.inflate(R.layout.welcome_three, container,
						false);
			}
			return null;
		}

		@Override
		protected final void onCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
		}

		@Override
		protected void onFirstSwitchOver() {
			// TODO Auto-generated method stub
			mRootView.setOnClickListener(mWelcomeTrends);
		}

	}

}
