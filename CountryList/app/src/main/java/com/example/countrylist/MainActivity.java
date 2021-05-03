package com.example.countrylist;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    ListView lvCountry;
    ArrayList<String> arrayCountry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetJSON().execute("http://api.geonames.org/countryInfoJSON?username=btandroid2");
    }


    private class GetJSON extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Đang lấy dữ liệu...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        //Hàm làm việc lấy dữ liệu
        @Override
        protected String doInBackground(String...strings) {
            StringBuilder content = new StringBuilder();

            try {
                //Khai báo URL
                URL url = new URL(strings[0]);
                //Khởi tạo input stream để đọc dữ liệu json connect từ url
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                //Gán vào BufferedReader
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //Khởi tạo một biến String để chứa data json all
                String line = "";

                //Vòng while đọc dữ liệu từ BufferedReader, gán vào String line
                while ( (line = bufferedReader.readLine()) != null ){
                    content.append(line);
                }

                //Đóng bufferedReader
                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        //Dữ liệu trả về hàm này
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Tắt progress dialog ở onPreExcute
            pDialog.dismiss();
            try {
                //Khởi tạo đối tượng Json nhận được
                JSONObject jsonObject = new JSONObject(s);
                //Khởi tạo JSONArray, truyền vào name JSONArray muốn lấy
                JSONArray jsonGeonames = jsonObject.getJSONArray("geonames");
                //Vòng lặp gán dữ liệu tên quốc gia vào ArrayList
                for (int i = 0; i < jsonGeonames.length(); i++){
                    JSONObject object = jsonGeonames.getJSONObject(i);
                    String countryName = object.getString("countryName");
                    arrayCountry.add(countryName);
                }

                lvCountry = (ListView) findViewById(R.id.lstCountries);
                ArrayAdapter adapterArray = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayCountry);
                lvCountry.setAdapter(adapterArray);

                //Sự kiện onlick item
                lvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(MainActivity.this, InfoNational.class);
                        intent.putExtra("national_name", arrayCountry.get(position));

                        startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}