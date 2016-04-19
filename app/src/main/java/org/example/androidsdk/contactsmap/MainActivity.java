package org.example.androidsdk.contactsmap;

import android.content.ContentResolver;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contactList;
    private EventBus bus = EventBus.getDefault();

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
        pagerAdapter.addFragment(new ListFragment(), "All Contacts");
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
            public void callback(ArrayList<Contact> arrayList) {
                contactList = arrayList;
                postEventBus();
                for(int i = 0;i<contactList.size();i++) {
                    Contact c = contactList.get(i);
                    ContactHelper.insertContact(getContentResolver(),c.getName(),c.getEmail(),c.getPhone(),c.getOfficePhone());
                }
            }
        });
    }
    public void postEventBus(){
        bus.post(new RequestCompleted(contactList));
    }

}
