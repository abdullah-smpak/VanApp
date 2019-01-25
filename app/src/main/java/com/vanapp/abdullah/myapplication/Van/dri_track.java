package com.vanapp.abdullah.myapplication.Van;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vanapp.abdullah.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class dri_track extends FragmentActivity implements OnMapReadyCallback {


    String date;
    private GoogleMap mMap, mMap2;

    private Marker currentLocationMaker;
    private LatLng currentLocationLatLong;
    Marker ma;
    String van_no;
    String lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dri_track);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        van_no = getIntent().getExtras().getString("van");

        String type = "login";
        BackgroundDriverLogin loginwork = new BackgroundDriverLogin(dri_track.this);
        loginwork.execute(type, van_no);

        //van_no = "2192";
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference commandsRef = rootRef.child("Location");


        DatabaseReference zone1Ref = commandsRef.child(van_no);
        zone1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lat = dataSnapshot.child("latitude").getValue().toString();
                lon = dataSnapshot.child("longitude").getValue().toString();


                if(ma != null)
                {
                    ma.remove();
                }

               ma = mMap2.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon))).title(van_no).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                mMap2.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)), 18f));

                //  Log.d("chk", ma.getTitle(). + "");

                //animateMarker(mLastLocation,mk);
                // Toast.makeText(dri_track.this, lat + "," + lon, Toast.LENGTH_SHORT).show();
                //Log.d("msg", lat + "," + lon);

        }
        @Override
        public void onCancelled (@NonNull DatabaseError databaseError){

        }
    });

//        setContentView(R.layout.activity_dri_track);


    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       /* final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/


}

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap2 = googleMap;


    }

public class BackgroundDriverLogin extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    Context context;
    OutputStream outputStream;
    URL url;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;


    BackgroundDriverLogin(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context, "Please Wait...", null, true, true);
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onPostExecute(String result) {

        progressDialog.dismiss();
        try {

            // De-serialize the JSON string into an array of city objects
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObj = jsonArray.getJSONObject(i);
                LatLng latLng = new LatLng(Double.parseDouble(jsonObj.getString("lat")), Double.parseDouble(jsonObj.getString("lon")));

                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(jsonObj.getString("kid_nam"));


                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
                   /* mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(jsonObj.getString("kid_nam"))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))*/
                //   );
                // Toast.makeText(context, latLng+"", Toast.LENGTH_SHORT).show();

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://www.mucaddam.pk/abdullah/vanapp/fetch_location.php";
        if (type.equals("login")) {
            try {
                String mobno = params[1];

                url = new URL(login_url);
                //
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(14000);

                httpURLConnection.setConnectTimeout(14000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("van_no", "UTF-8") + "=" + URLEncoder.encode(mobno, "UTF-8");
                bufferedWriter.write(post_data);

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }

}
}
