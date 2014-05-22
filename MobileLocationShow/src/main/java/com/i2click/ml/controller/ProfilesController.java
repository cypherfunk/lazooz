package com.i2click.ml.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.i2click.ml.service.ProfilesManager;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

@Controller
public class ProfilesController {

    @Autowired
    private ProfilesManager profilesManager;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listProfiless(ModelMap map) {
        
        ArrayList<HashMap> location = new ArrayList<HashMap>();
        ArrayList<HashMap> loggedInLocation = new ArrayList<HashMap>();
        ArrayList<Object> obj = profilesManager.locationList();
        if(obj != null){
            JsonElement json = new Gson().toJsonTree(obj);
            if(json != null){
                JsonArray jsonArray = json.getAsJsonArray();
                for(int i=0; i<jsonArray.size(); i++){
                    JsonArray jArray = jsonArray.get(i).getAsJsonArray();
                    if(jArray != null){
                        JsonObject jProfile = jArray.get(0).getAsJsonObject();
                        JsonObject jLocation = jArray.get(1).getAsJsonObject();

                        HashMap hashMap = new HashMap();
                        if(jLocation != null){
                            hashMap.put("latitude", jLocation.get("latitude").getAsString());
                            hashMap.put("longitude", jLocation.get("longitude").getAsString());
                            hashMap.put("time", jLocation.get("last_datetime").getAsString());
                        }
                        if(jProfile != null){
                            hashMap.put("name", jProfile.get("fullname").getAsString());
                            hashMap.put("mobilenumber", jProfile.get("mobilenumber").getAsString());
                            String sessionkey = jProfile.get("sessionkey").getAsString();
                            if(sessionkey != null && !sessionkey.isEmpty()){
                                loggedInLocation.add(hashMap);
                            }
                        }
                        location.add(hashMap);
                    }
                }
            }
            System.out.println("json :: "+ new Gson().toJson(location));
        }
        map.addAttribute("AllLocationWithProfilesList", new Gson().toJson(location));
        map.addAttribute("LoggedInLocationWithProfilesList", new Gson().toJson(loggedInLocation));

        return "homePage";
    }
}
