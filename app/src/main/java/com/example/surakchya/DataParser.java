package com.example.surakchya;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
   private HashMap<String,String> getPlace(JSONObject googlePlaceJson){
       // one place is stored in HashMap
       HashMap<String, String> googlePlacesMap = new HashMap<>();
       String palceName= "-NA-";
       String vicinity = "-NA-";
       String lattitude ="";
       String longitude= "";
       String reference = "";
       try {
           if (!googlePlaceJson.isNull("name")) {
                   palceName = googlePlaceJson.getString("name");
           }
           if(!googlePlaceJson.isNull("vicinity")){
               vicinity = googlePlaceJson.getString("vicinity");
           }
           lattitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
           longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

           reference = googlePlaceJson.getString("reference");

           googlePlacesMap.put("place_name",palceName);
           googlePlacesMap.put("vicinity",vicinity);
           googlePlacesMap.put("lat",lattitude);
           googlePlacesMap.put("lng",longitude);

       }catch (JSONException e){
           e.printStackTrace();
       }
       return googlePlacesMap;
   }

   private List<HashMap<String,String>> getPlaces(JSONArray jsonArray){
       // all nearby places are stored in list of HashMap
       int count = jsonArray.length();
        List<HashMap<String,String>> placesList = new ArrayList<>();
        HashMap<String,String> placeMap= null;
        for(int i = 0; i<count;i++){
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
   }
   // we will first call the parse method which will parse the JSON  and send it to getPlaces method where getPlace method be called for fetching each element within the json
    // array which will be stored in the placeList
   public List<HashMap<String,String>> parse(String jsonData){
       JSONArray jsonArray = null;
       JSONObject jsonObject;

       try {
           jsonObject = new JSONObject(jsonData);
           jsonArray = jsonObject.getJSONArray("results");
       } catch (JSONException e) {
           e.printStackTrace();
       }
       return getPlaces(jsonArray);
   }


}
