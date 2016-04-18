package org.example.androidsdk.contactsmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ListFragment extends Fragment {

//    private EventBus bus = EventBus.getDefault();
//    private ArrayList<Place> placeList = new ArrayList<>();
//    PlacesAdapter adapter;
//    SwipeRefreshLayout swipeRefreshLayout;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        bus.register(this);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
//        adapter = new PlacesAdapter(getActivity(),placeList,"explore");
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
////                Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
//                ServerRequest serverRequest = new ServerRequest(getActivity());
//                serverRequest.addToRecent("56f1a6f0272e3b34551fc600", placeList.get(position).getEventId(), new GetStringCallback() {
//                    @Override
//                    public void callback(String response) {
//                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                Intent intent = new Intent(getActivity(), PlacesDetailActivity.class);
//                intent.putExtra("eventid",placeList.get(position).getEventId());
//                getActivity().startActivity(intent);
//            }
//        });
//
//        ((HomeActivity) getActivity()).postEventBus();
//        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
        setHasOptionsMenu(true);
        return rootView;
    }
//    @Override
//    public void onDestroy() {
//        bus.unregister(this);
//        super.onDestroy();
//    }
//    public void onEvent(RequestCompleted event){
//        placeList = event.getArrayList();
//        adapter.update(placeList);
//        adapter.notifyDataSetChanged();
//        if(swipeRefreshLayout!=null)
//            swipeRefreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void onRefresh() {
//        ((HomeActivity) getActivity()).makeServerRequest();
//    }
}
