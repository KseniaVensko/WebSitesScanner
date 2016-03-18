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
                String[] parts = h.split(":", 2);
                String name = parts[0];
                String value = parts[1];
                if (parts.length < 2) {
                    map.put(name, null);
                }
                else {
                    map.put(name, value);
                }
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return map;
    }
}
