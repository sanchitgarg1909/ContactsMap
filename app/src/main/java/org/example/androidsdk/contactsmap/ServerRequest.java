package org.example.androidsdk.contactsmap;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerRequest {
    Context context;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    ProgressDialog progressDialog;
    HashMap<String,String> params;
    String url;
    String SERVER_ADDRESS = "http://private-b08d8d-nikitest.apiary-mock.com/";

    public ServerRequest(Context context) {
        this.context = context;
        params = new HashMap<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please Wait...");
    }

    public void getContacts(GetResponseCallback getResponseCallback){
        progressDialog.show();
        url = "contacts";
        serverRequest(url, getResponseCallback);
    }

    public void serverRequest(String url, final GetResponseCallback getResponseCallback){
        url = SERVER_ADDRESS + url;
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", response.toString());
//                        ArrayList<String> favlist = new ArrayList<>();
//                        ArrayList<Place> placeList = new ArrayList<>();
//                        try{
//                            JSONArray events = response.getJSONArray(0);
//                            JSONArray favourites = response.getJSONArray(1);
//                            for(int i = 0; i < favourites.length(); i++){
//                                favlist.add(favourites.getString(i));
//                            }
//                            for (int i = 0; i < events.length(); i++) {
//                                JSONObject obj = events.getJSONObject(i);
//                                Place place = new Place();
//                                place.setTitle(obj.getString("name"));
//                                place.setThumbnailUrl(obj.getJSONArray("userimageurl").getString(0));
//                                place.setLocation(obj.getString("location"));
//                                place.setRating(Double.parseDouble(obj.getJSONObject("rating").getString("value")));
//                                place.setPrice(Integer.parseInt(obj.getString("price")));
//                                place.setVisibility(true);
//                                place.setEventId(obj.getString("_id"));
//                                place.setLatitude(Double.parseDouble(obj.getString("lat")));
//                                place.setLongitude(Double.parseDouble(obj.getString("long")));
//                                if(favlist.contains(obj.get("_id")))
//                                    place.setFavourite(true);
//                                else
//                                    place.setFavourite(false);
//                                placeList.add(place);
//                            }
//                        }
//                        catch (JSONException e){
//                            e.printStackTrace();
//                        }
                        getResponseCallback.callback(response.toString());
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(CONNECTION_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

}
