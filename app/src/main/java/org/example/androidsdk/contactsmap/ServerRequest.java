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
//                        Log.d("response", response.toString());
//                        ArrayList<String> favlist = new ArrayList<>();
                        ArrayList<Contact> contactArrayList = new ArrayList<>();
                        try{
                            JSONObject object = response.getJSONObject(0);
                            JSONArray contacts = object.getJSONArray("contacts");
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject obj = contacts.getJSONObject(i);
//                                Log.d("phone",String.valueOf(obj.getLong("phone")));
                                Contact contact = new Contact();
                                if(!obj.isNull("name"))
                                    contact.setName(obj.getString("name"));
                                if(!obj.isNull("email"))
                                    contact.setEmail(obj.getString("email"));
                                if(!obj.isNull("phone"))
                                    contact.setPhone(String.valueOf(obj.getLong("phone")));
                                if(!obj.isNull("officePhone"))
                                    contact.setOfficePhone(String.valueOf(obj.getLong("officePhone")));
                                if(!obj.isNull("latitude"))
                                    contact.setLatitude(obj.getDouble("latitude"));
                                if(!obj.isNull("longitude"))
                                    contact.setLongitude(obj.getDouble("longitude"));
                                contactArrayList.add(contact);
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        getResponseCallback.callback(contactArrayList);
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
