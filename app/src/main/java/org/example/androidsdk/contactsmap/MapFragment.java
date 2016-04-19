package org.example.androidsdk.contactsmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    public GoogleMap googleMap;
    private EventBus bus = EventBus.getDefault();
    private SupportMapFragment mapFragment;
    private ArrayList<Contact> contactList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = new SupportMapFragment(){
            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                initialiseMap();
            }
        };
        getChildFragmentManager().beginTransaction().add(R.id.container_map,mapFragment).commit();
        return rootView;
    }
    public void initialiseMap(){
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
//        googleMap.setOnInfoWindowClickListener(this);
//        googleMap.setMyLocationEnabled(true);
        googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
//        arrayList = ((ListingActivity)getActivity()).postEventBus();
//        ((HomeActivity)getActivity()).postEventBus();
//        fixedLocation(arrayList);
    }

    @Override
    public void onDestroy() {
        // Unregister
        bus.unregister(this);
        super.onDestroy();
    }

    public void onEvent(RequestCompleted event){
        contactList = event.getArrayList();
        fixedLocation(contactList);
    }

    public void fixedLocation(ArrayList<Contact> arrayList) {
        boolean flag = false;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        googleMap.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            Contact c = arrayList.get(i);
            double latitude=c.getLatitude();
            double longitude=c.getLongitude();
            LatLng latlng=new LatLng(latitude,longitude);
            Marker marker = googleMap.addMarker(new MarkerOptions().draggable(false).position(latlng).title(c.getName()).snippet("{\"email\":\"" + c.getEmail() + "\", \"phone\":\"" + c.getPhone() + "\", \"officephone\":\"" + c.getOfficePhone() + "\"}"));
            builder.include(marker.getPosition());
            flag = true;
        }
        if(flag) {
            LatLngBounds bounds = builder.build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
        }else
            Toast.makeText(getContext(), "No Contacts", Toast.LENGTH_SHORT).show();
    }

    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

        protected TextView tv_name,tv_email,tv_phone,tv_officephone;
        private final View convertView;

        MyInfoWindowAdapter(){
            convertView = getActivity().getLayoutInflater().inflate(R.layout.list_contacts, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            tv_name = (TextView) convertView.findViewById(R.id.name);
            tv_email = (TextView) convertView.findViewById(R.id.email);
            tv_phone = (TextView) convertView.findViewById(R.id.phone);
            tv_officephone = (TextView) convertView.findViewById(R.id.officephone);
            tv_name.setText(marker.getTitle());
            try {
                JSONObject object = new JSONObject(marker.getSnippet());
                String email = object.getString("email");
                String phone = object.getString("phone");
                String officephone = object.getString("officephone");
                tv_email.setText(email);
                tv_phone.setText(phone);
                tv_officephone.setText(officephone);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
