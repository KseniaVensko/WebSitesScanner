package com.kseniavensko;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


//public class ScanResult<T extends Result> extends ArrayList<T> {
public class ScanResult {
    private List<Result> results;

    public ScanResult(List<Result> results) {
        this.results = results;
    }

    public void toConsole() {
        // TODO: j-text-utils
        StringBuilder resultString = new StringBuilder();
        for (Result result : results) {
            resultString.append("\n" + result.getHost().toString() + "\n");
            try {
                if (result.getSecureHeaders() != null) {
                    resultString.append("\nSecure headers:\n");
                    for (Result.secureHeader secureHeader : result.getSecureHeaders()) {
                        resultString.append(secureHeader.name + "\t" + secureHeader.correct);
                        for (String value : secureHeader.values) {
                            resultString.append(value);
                        }
                        resultString.append("\n");
                    }
                }
                if (result.getInformationHeaders() != null) {
                    resultString.append("\nInformation headers:\n");

                    for (Result.informationHeader header : result.getInformationHeaders()) {
                        resultString.append(header.name + " : ");
                        for (String value : header.values) {
                            resultString.append("\n\t" + value);
                        }
                            resultString.append("\n");
                    }


//                    for (Map.Entry<String, List<String>> entry : result.getInformationHeaders().) {
//                        for (String v : entry.getValue()){
//                            resultString.append(entry.getKey() + ":" + v);
//                        }
////                        resultString.append(entry.getKey());
////                        resultString.append(" / ");
////                        resultString.append(entry.getValue());
//                        resultString.append("\n");
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resultString.append("\n" + result.getStringStatus());
            resultString.append("\n\n");
        }
        System.out.println( resultString.toString());
    }

    public void toJsonFile(String file) {
        JSONObject obj = new JSONObject();

        for (Result result : results) {
                obj.put("host", result.getHost().toString());
                if (result.getSecureHeaders() != null) {
                    JSONArray secureHeaders = new JSONArray();
                    secureHeaders.addAll(result.getSecureHeaders());
                    obj.put("Secure headers", secureHeaders);
                }
//                if (result.getInformationHeaders() != null) {
//                    JSONArray informationHeaders = new JSONArray();
//                    informationHeaders.addAll(result.getInformationHeaders().keySet());
//                    obj.put("Information headers", informationHeaders);
//                }
        }
        try {
            System.out.println("\nJSON Object: " + obj);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(obj.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
