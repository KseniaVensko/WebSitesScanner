package com.kseniavensko;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


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
                resultString.append("\nInformation headers\n");
                for (Result.Header header : result.getInformationHeaders()) {
                    appendHeader(resultString, header);
                }

                resultString.append("\nSecure headers\n");
                for (Result.Header header : result.getSecureHeaders()) {
                    appendHeader(resultString, header);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            resultString.append("\n" + result.getStringStatus());
            resultString.append("\n\n");
        }
        System.out.println(resultString.toString());
    }

    public void toJsonFile(String file) {
        JSONObject hosts = new JSONObject();

        for (Result result : results) {
            JSONObject options = new JSONObject();

            JSONObject informationHeaders = new JSONObject();
            for (Result.Header h : result.getInformationHeaders()) {
                addHeaderObject(informationHeaders, h);
            }
            options.put("information_headers", informationHeaders);


            JSONObject secureHeaders = new JSONObject();
            for (Result.Header h : result.getSecureHeaders()) {
                addHeaderObject(secureHeaders, h);
            }
            options.put("secure_headers", secureHeaders);
            hosts.put(result.getHost().toString(), options);

        }

        try {
            System.out.println("\nJSON Object: " + hosts);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(hosts.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendHeader(StringBuilder resultString, Result.Header header) {
        resultString.append(header.name + " : ");
        resultString.append(header.status.toString());
        if (header.status != Result.Status.Missing) {
            for (String value : header.values) {
                resultString.append("\n\t" + value);
            }
        }
        resultString.append("\n");
    }

    private void addHeaderObject(JSONObject headers, Result.Header h) {
        JSONObject header = new JSONObject();
        JSONArray values = new JSONArray();

        if (h.status != Result.Status.Missing) {
            for (String v : h.values) {
                values.add(v);
            }
            header.put("values", values);
        }
        header.put("status", h.status.toString());

        headers.put(h.name, header);
    }
}
