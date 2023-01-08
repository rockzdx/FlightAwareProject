package com.vishwamalyan.app.controller;

import java.util.HashMap;

public class cityCode {

    //API Call to get leagues of the different games
    public String codes(String city) {

        String code = null;
        HashMap<String, String> citycode = new HashMap<String, String>();
        citycode.put("delhi", "DEL-sky");
        citycode.put("mumbai", "BOM-sky");
        citycode.put("india", "IN-sky");
        citycode.put("bengaluru", "BLR-sky");
        citycode.put("hyderabad", "HYD-sky");
        citycode.put("chennai", "MAA-sky");
        citycode.put("kolkata", "CCU-sky");
        citycode.put("ahmedabad", "AMD-sky");
        citycode.put("kochi", "COK-sky");
        citycode.put("pune", "PNQ-sky");
        citycode.put("fort wayne", "FWA-sky");
        citycode.put("united states", "US-sky");
        citycode.put("concord regional", "USA-sky");
        citycode.put("koh samui", "USM-sky");
        citycode.put("samui island", "USM-sky");
        citycode.put("us virgin islands", "VI-sky");
        citycode.put("st augustine", "UST-sky");
        citycode.put("busuanga", "USU-sky");
        citycode.put("ushuaia", "USH-sky");
        citycode.put("saint thomas cyril e king", "STT-sky");
        citycode.put("ust-kamenogorsk", "UKK-sky");
        citycode.put("ulsan", "USN-sky");
        citycode.put("uk", "UK-sky");
        citycode.put("ukraine", "UA-sky");
        citycode.put("kyiv", "KIEV-sky");
        citycode.put("kiev borispol", "KBP-sky");
        citycode.put("kiev zhulhany", "IEV-sky");
        citycode.put("lviv", "LWO-sky");
        citycode.put("odesa central", "ODS-sky");
        citycode.put("kharkiv", "HRK-sky");
        citycode.put("kobe", "UKB-sky");
        citycode.put("ukunda", "UKA-sky");
        code = citycode.get(city.toLowerCase());
        return code;

    }
}