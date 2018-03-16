package com.fahmiyuseri.psisingapore;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fahmiyuseri.psisingapore.Controller.getPSIController;
import com.fahmiyuseri.psisingapore.Model.LocationModel;
import com.fahmiyuseri.psisingapore.Model.PSIModel;
import com.fahmiyuseri.psisingapore.Util.HttpAsync;
import com.fahmiyuseri.psisingapore.Util.HttpCallBack;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,HttpCallBack {

    private static final int CODE_MAPAPI = 1111;
    private GoogleMap mMap;
    private List<LocationModel> locationModelList = new ArrayList<>();
    private List<PSIModel> psiIndex;
    int[][] psiIndexArray;
    String[] psiNameArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
init();

    }

    @Override
    public void onReceiveHttpCallBack(String result, int requestCode) {

        if (requestCode == CODE_MAPAPI) {
            locationModelList = new getPSIController().getPSI(result);
            for (LocationModel locationModel2 : locationModelList) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(locationModel2.getLatitude(), locationModel2.getLongitude()));
                markerOptions.title(locationModel2.getName());
                String desc = "";
                // markerOptions.snippet("\n"+  psiNameArray[y] + " : " + locationModel.getPsiIndex().get(y));

                for (int y = 0; y < locationModel2.getPsiModels().size(); y++) {
                    desc += "\n" + locationModel2.getPsiModels().get(y).getName() + " : " + locationModel2.getPsiModels().get(y).getIndex();
                }
                markerOptions.snippet(String.valueOf(desc));
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                        LinearLayout info = new LinearLayout(context);
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(context);
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(context);
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });
                mMap.addMarker(markerOptions);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(1.35735, 103.7)));
        }


    }

    public void init(){
        HttpAsync httpAsync = new HttpAsync(this,this,CODE_MAPAPI);
        httpAsync.setURLs("https://api.data.gov.sg/v1/environment/psi");
        httpAsync.execute();
    }
}
