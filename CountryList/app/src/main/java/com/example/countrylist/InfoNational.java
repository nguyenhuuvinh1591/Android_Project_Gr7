package com.example.countrylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Math;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class InfoNational extends AppCompatActivity {
    ProgressDialog pDialog;
    TextView tvNationalName, tvDienTich, tvDanSo, tvCountryCode;
    Button btnQuayLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_national);

        new GetInfoNational().execute("http://api.geonames.org/countryInfoJSON?username=generalminh1");

        ClickQuayLai();
    }

    private class GetInfoNational extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experience
            pDialog = new ProgressDialog(InfoNational.this);
            pDialog.setMessage("Đang lấy dữ liệu...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";

                while ( (line = bufferedReader.readLine()) != null ){
                    content.append(line);
                }

                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent intent = getIntent();
            String x = "Km";
            int y = 2;
            String nationalName = intent.getStringExtra("national_name");
            //Tắt progress dialog ở onPreExcute
            pDialog.dismiss();
            try {
                //Khởi tạo đối tượng Json nhận được
                JSONObject jsonObject = new JSONObject(s);
                //Khởi tạo JSONArray, truyền vào name JSONArray muốn lấy
                JSONArray jsonGeonames = jsonObject.getJSONArray("geonames");
                //Vòng lặp gán dữ liệu
                for (int i = 0; i < jsonGeonames.length(); i++){
                    JSONObject object = jsonGeonames.getJSONObject(i);

                    String countryName = object.getString("countryName");

                    if ( countryName.equals(nationalName) ){
                        tvNationalName = findViewById(R.id.name_national);
                        tvDanSo = findViewById(R.id.danSo_value);
                        tvDienTich = findViewById(R.id.dienTich_value);

                        tvNationalName.setText(object.getString("countryName"));
                        tvDanSo.setText("Dân số: " + object.getString("population") + " người");
                        tvDienTich.setText("Diện tích: " + object.getString("areaInSqKm") + " " + "km\u00B2");

                        String urlImage = "https://img.geonames.org/flags/m/" + object.getString("countryCode").toLowerCase() + ".png" ;

                        new ImageTask( (ImageView) findViewById(R.id.image_national)).execute(urlImage);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    //Class Load image từ url
    private class ImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
    }

    public void ClickQuayLai(){
        btnQuayLai = findViewById(R.id.btnQuayLai);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}