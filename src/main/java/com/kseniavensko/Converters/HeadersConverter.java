package com.kseniavensko.Converters;

import com.beust.jcommander.IStringConverter;

import java.util.HashMap;
import java.util.Map;

public class HeadersConverter implements IStringConverter<Map<String, String>> {
    public Map<String, String> convert(String s) {
        HashMap<String,String> map = new HashMap<String, String>();
        String[] headers = s.split(",");
        try {
            for (String h : headers) {
                String[] entry = h.split(":");
                map.put(entry[0], entry[1]);
            }
        }
        // TODO: check for exceptions
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return map;
    }
}
