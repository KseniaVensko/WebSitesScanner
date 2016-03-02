package com.kseniavensko;

import java.util.List;
import java.util.Map;


import org.json.JSONObject;
public class ScanResult {
    private List<Result> results;

    public String asText() {
        // TODO: j-text-utils
        StringBuilder resultString = new StringBuilder();
        for (Result result : results) {
            try {
                for (Map.Entry<String, List<String>> entry : result.getInformationHeaders().entrySet()) {
                    resultString.append(entry.getKey());
                    resultString.append("/");
                    resultString.append(entry.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resultString.append("\n\n");
        }
        return resultString.toString();
    }

    public String asJson() {
        JSONObject obj = new JSONObject();
        return new String();
    }
}
