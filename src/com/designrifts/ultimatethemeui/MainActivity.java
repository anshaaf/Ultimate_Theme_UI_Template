/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.designrifts.ultimatethemeui;


import java.util.Locale;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardAdapter;
import com.afollestad.cardsui.CardHeader;
import com.afollestad.cardsui.CardListView;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;


public class MainActivity extends FragmentActivity implements Card.CardMenuListener<Card>{

	private final Handler handler = new Handler();

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF3F9FE0;
	
	
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        // This is quick way of theming the action bar without using styles.xml (e.g. using ActionBar Style Generator)
	        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_blue_dark)));
	        getActionBar().setDisplayShowHomeEnabled(false);

	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
			pager = (ViewPager) findViewById(R.id.pager);
			adapter = new MyPagerAdapter(getSupportFragmentManager());

			pager.setAdapter(adapter);
			
	        // Initializes a CardAdapter with a blue accent color and basic popup menu for each card
	        CardAdapter<Card> cardsAdapter = new CardAdapter<Card>(this)
	                .setAccentColorRes(android.R.color.holo_blue_light)
	                .setPopupMenu(R.menu.card_popup, this);

	        cardsAdapter.add(new CardHeader(this, R.string.themeheader));
	        cardsAdapter.add(new Card("Action", "Launcher")
	        		.setThumbnail(this, R.drawable.apps_actionlauncherpro));  // sets a thumbnail image from drawable resources
	        cardsAdapter.add(new Card("ADW", "Launcher")
					.setThumbnail(this, R.drawable.apps_adwex));  // sets a thumbnail image from drawable resources
	        cardsAdapter.add(new Card("Apex", "Launcher")
					.setThumbnail(this, R.drawable.apps_apexlauncher));  // sets a thumbnail image from drawable resources
	        cardsAdapter.add(new Card("Nova", "Launcher")
					.setThumbnail(this, R.drawable.apps_novalauncher));  // sets a thumbnail image from drawable resources

	        CardListView cardsList = (CardListView) findViewById(R.id.cardsList);
	        cardsList.setAdapter(cardsAdapter);

			final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
					.getDisplayMetrics());
			pager.setPageMargin(pageMargin);

			tabs.setViewPager(pager);

	    }
	

	    @Override
	    public void onMenuItemClick(Card card, MenuItem item) {
	        Toast.makeText(this, card.getTitle() + ": " + item.getTitle(), Toast.LENGTH_SHORT).show();
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_contact:
			QuickContactFragment dialog = new QuickContactFragment();
			dialog.show(getSupportFragmentManager(), "QuickContactFragment");
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}


	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final int[] TITLES = { R.string.tab1, R.string.tab2, R.string.tab3 };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.tab1).toUpperCase(l);
			case 1:
				return getString(R.string.tab2).toUpperCase(l);
			case 2:
				return getString(R.string.tab3).toUpperCase(l);
			}
			return null;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment f = new Fragment();
			switch(position){
			case 0:
				f=ThemeCardFragment.newInstance(position);	
				break;
			case 1:
				f=ThemeCardFragment.newInstance(position);	
				break;
			}
			return f;
		}
		
		@Override
		public int getCount() {
			return TITLES.length;
		}	
		
	}

}