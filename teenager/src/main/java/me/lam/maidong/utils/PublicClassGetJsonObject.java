package me.lam.maidong.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import me.lam.maidong.entity.PublicDataClass;

/**
 * Created by abc on 2017/8/11.
 */
public class PublicClassGetJsonObject {

    public static JSONObject [] getPublicClassGetJsonObject(String result){
        JSONObject [] jsonObjects=new JSONObject[2];
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONObject StatusModel=jsonObject.getJSONObject("StatusModel");
            JSONObject Data=jsonObject.getJSONObject("Data");
            jsonObjects[0]=StatusModel;
            jsonObjects[1]=Data;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjects;
    }
}
