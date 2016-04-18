package org.example.androidsdk.contactsmap;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViewPagerAndTabs();
        makeServerRequest();
    }
    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
    }
    private void initViewPagerAndTabs() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new MapFragment(), "All Contacts");
        pagerAdapter.addFragment(new MapFragment(), "Contacts Map");
        viewPager.setAdapter(pagerAdapter);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
    private void makeServerRequest(){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.getContacts(new GetResponseCallback() {
            @Override
            public void callback(String response) {
//                placeList = arrayList;
//                postEventBus();
            }
        });
    }
}
