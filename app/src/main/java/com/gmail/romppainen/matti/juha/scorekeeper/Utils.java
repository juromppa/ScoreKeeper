package com.gmail.romppainen.matti.juha.scorekeeper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Juha on 4.1.2017.
 */

class Utils {

    static String ConvertDate(int epoch) {
        Date date = new Date(epoch * 1000L);
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
        return df2.format(date);
    }

    static String ArrayListToString(ArrayList arrayList) {
        JSONObject json = new JSONObject();
        try {
            json.put("uniqueArrays", new JSONArray(arrayList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = json.toString();

        return str;
    }

    static ArrayList<Integer> StringToArrayList(String str) {
        JSONObject json = null;
        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jArray = json.optJSONArray("uniqueArrays");

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < jArray.length();i++){
            try {
                arrayList.add(Integer.parseInt(jArray.getString(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return arrayList;
    }
}
