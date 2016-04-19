package org.example.androidsdk.contactsmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ListFragment extends Fragment {

    private EventBus bus = EventBus.getDefault();
    private ArrayList<Contact> contactList = new ArrayList<>();
    ContactListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ContactListAdapter(getActivity(),contactList);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
    @Override
    public void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
    public void onEvent(RequestCompleted event){
        contactList = event.getArrayList();
        adapter.update(contactList);
        adapter.notifyDataSetChanged();
    }
}
