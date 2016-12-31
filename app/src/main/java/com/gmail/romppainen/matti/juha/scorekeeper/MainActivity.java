package com.gmail.romppainen.matti.juha.scorekeeper;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity
                .this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                invalidateOptionsMenu();
            }

            @Override
            public void onPageSelected(int position) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (viewPager.getCurrentItem() == 0){
            menu.findItem(R.id.action_new_player).setVisible(false);
            menu.findItem(R.id.action_new_course).setVisible(false);
        } else if(viewPager.getCurrentItem() == 1){
            menu.findItem(R.id.action_new_player).setVisible(false);
            menu.findItem(R.id.action_new_course).setVisible(false);
        } else if(viewPager.getCurrentItem() == 2) {
            menu.findItem(R.id.action_new_player).setVisible(true);
            menu.findItem(R.id.action_new_course).setVisible(false);
        } else if(viewPager.getCurrentItem() == 3) {
            menu.findItem(R.id.action_new_player).setVisible(false);
            menu.findItem(R.id.action_new_course).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_new_player:
                MainActivityTabFragment3.NewPlayer(this);
                return true;
            case R.id.action_new_course:
                MainActivityTabFragment4.NewCourse(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {
        String tabTitles[] = getResources().getStringArray(R.array.main_tab_titles);
        Context context;

        PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainActivityTabFragment1();
                case 1:
                    return new MainActivityTabFragment2();
                case 2:
                    return new MainActivityTabFragment3();
                case 3:
                    return new MainActivityTabFragment4();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}