package com.example.countrylist;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/

public class ServiceHandler extends AsyncTask<String,Void,ArrayList> {
    // URL
    String URL_GETCOUNTRIES = "http://api.geonames.org/countryInfoJSON";
    ContentValues callParams;

    ProgressDialog pDialog;
    ListView lv;

    static final String DISPLAY = "display";
    static final String CREATE = "create";
    static final String UPDATE = "update";
    static final String DELETE = "delete";

    com.example.countrylist.ServiceCaller serviceCaller = new com.example.countrylist.ServiceCaller();

    Context context;
    ArrayList<com.example.countrylist.Country> countries;

    public ServiceHandler(Context context, ListView lv, ContentValues params){
        this.context = context;
        this.lv = lv;
        callParams = params;
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        switch (params[0]) {
            case DISPLAY:
                countries = new ArrayList<>();
                String json = serviceCaller.call(URL_GETCOUNTRIES, com.example.countrylist.ServiceCaller.GET, callParams);
                if (json != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(json);
                        if (jsonObj != null) {
                            JSONArray accounts = jsonObj.getJSONArray("geonames");
                            for (int i = 0; i < accounts.length(); i++) {
                                JSONObject obj = (JSONObject) accounts.get(i);
                                com.example.countrylist.Country country = new com.example.countrylist.Country(obj.getString("countryName"),
                                        obj.getString("countryCode"),
                                        obj.getString("population"));
                                countries.add(country);
                            }
                        }
                        else {
                            Log.d("JSON Data", "JSON data's format is incorrect!");
                            com.example.countrylist.Country country = new com.example.countrylist.Country("JSON data's format is incorrect!",
                                    "JSON Data", "0");
                            countries.add(country);
                        }
                    } catch (JSONException e) { e.printStackTrace(); }
                } else {
                    Log.d("JSON Data", "Didn't receive any data from server!");
                    com.example.countrylist.Country country = new com.example.countrylist.Country("Didn't receive any data from server!",
                            "JSON Data", "0");
                    countries.add(country);
                }
                break;
            case CREATE: break; case UPDATE: break; case DELETE: break;
        }
        return countries;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Proccesing..");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected void onPostExecute(ArrayList ret) {
        super.onPostExecute(ret);
        if (pDialog.isShowing())
            pDialog.dismiss();
        if (ret == null)
            Toast.makeText(context, "Lỗi - Refresh lại", Toast.LENGTH_SHORT).show();
        loadData();
    }

    private void loadData() {
        if (countries == null) {
            return;
        }
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < countries.size(); i++) {
            com.example.countrylist.Country country = countries.get(i);
            data.add(country.countryCode + "-" + country.countryName + ": " + country.countryPopulation);
        }
        // Tạo adapter cho listivew
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data);
        // Gắn adapter cho listview
        lv.setAdapter(adapter);
    }

    public ArrayList<com.example.countrylist.Country> getData() {
        return countries;
    }
}

