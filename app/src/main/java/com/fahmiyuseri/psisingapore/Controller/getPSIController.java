package com.fahmiyuseri.psisingapore.Controller;

import android.app.Activity;
import android.util.Log;

import com.fahmiyuseri.psisingapore.Model.LocationModel;
import com.fahmiyuseri.psisingapore.Model.PSIModel;
import com.fahmiyuseri.psisingapore.Util.HttpAsync;
import com.fahmiyuseri.psisingapore.Util.HttpCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IRSB on 15/3/2018.
 */

public class getPSIController {
    private List<LocationModel> locationModelList = new ArrayList<>();
    private List<PSIModel> psiIndex;
    int[][] psiIndexArray;
    String[] psiNameArray;
    private static final int CODE_MAPAPI = 1111;

    public List<LocationModel> getPSI(String result){
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(result);
            JSONArray region_metadata;
            JSONArray items;

            region_metadata = jsonObject.getJSONArray("region_metadata");
            items = jsonObject.getJSONArray("items");

            LocationModel locationModel;

            for (int i = 0; i < items.length(); i++) {
                JSONObject data = items.getJSONObject(i);
                JSONObject readings = data.getJSONObject("readings");
                psiNameArray = new String[readings.names().length()];
                Log.d("aaaa", String.valueOf(readings.names()));
                psiIndexArray = new int[6][readings.names().length()];
                for (int y = 0; y < readings.names().length(); y++) {
                    JSONObject index = readings.getJSONObject(readings.names().getString(y));
                    psiNameArray[y] = readings.names().getString(y);


                    psiIndexArray[0][y] = index.getInt("west");

                    psiIndexArray[1][y] = index.getInt("national");
                    psiIndexArray[2][y] = index.getInt("east");
                    psiIndexArray[3][y] = index.getInt("central");
                    psiIndexArray[4][y] = index.getInt("south");
                    psiIndexArray[5][y] = index.getInt("north");

                }

            }
            Log.d("aaaa", String.valueOf(psiIndexArray[0][1]));


            for (int i = 0; i < region_metadata.length(); i++) {
                locationModel = new LocationModel();
                JSONObject data = region_metadata.getJSONObject(i);
                JSONObject location = data.getJSONObject("label_location");

                String name = data.getString("name");
                double latitude = location.getDouble("latitude");
                double longitude = location.getDouble("longitude");
                locationModel.setName(name);
                locationModel.setLatitude(latitude);
                locationModel.setLongitude(longitude);
                psiIndex = new ArrayList<>();
                psiIndex.clear();

                for (int y = 0; y < psiIndexArray[i].length; y++) {
                    //   Log.d("aaaa", String.valueOf(i));

                    psiIndex.add(new PSIModel(psiNameArray[y], psiIndexArray[i][y]));

                }
                locationModel.setPsiModels(psiIndex);

                locationModelList.add(locationModel);
                Log.d("aaaa", new Gson().toJson(locationModel));


            }
            Log.d("aaaa", new Gson().toJson(locationModelList));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return locationModelList;
    }

}
