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
            resultString.append("\n" + result.getHost().toString());
            if (result.getRedirectedHost() != null) {
                resultString.append(" ---> " + result.getRedirectedHost());
            }
            resultString.append("\n");
            resultString.append("\n" + result.getStringStatus());
            try {
                if (result.getInformationHeaders() != null) {
                    resultString.append("\nInformation headers\n");
                    for (Result.Header header : result.getInformationHeaders()) {
                        appendHeader(resultString, header);
                    }
                }
                if (result.getSecureHeaders() != null) {
                    resultString.append("\nSecure headers\n");
                    for (Result.Header header : result.getSecureHeaders()) {
                        appendHeader(resultString, header);
                    }
                }
                if (result.getSecureCookieFlags() != null) {
                    resultString.append("\nSession cookies\n");
                    for (Map.Entry<String, Result.Status> sessionCookie : result.getSecureCookieFlags().entrySet()) {
                        resultString.append(sessionCookie.getKey() + " : " + sessionCookie.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            resultString.append("\n\n");
        }
        System.out.println(resultString.toString());
    }

    public void toJsonFile(String file) {
        JSONObject hosts = new JSONObject();

        for (Result result : results) {
            JSONObject options = new JSONObject();

            JSONObject informationHeaders = new JSONObject();
            if (result.getInformationHeaders() != null) {
                for (Result.Header h : result.getInformationHeaders()) {
                    addHeaderObject(informationHeaders, h);
                }
            }
            options.put("information_headers", informationHeaders);


            JSONObject secureHeaders = new JSONObject();
            if (result.getSecureHeaders() != null) {
                for (Result.Header h : result.getSecureHeaders()) {
                    addHeaderObject(secureHeaders, h);
                }
            }
            options.put("secure_headers", secureHeaders);

            JSONObject sessionCookies = new JSONObject();
            if (result.getSecureCookieFlags() != null) {
                for (Map.Entry<String, Result.Status> sessionCookie : result.getSecureCookieFlags().entrySet()) {
                    sessionCookies.put(sessionCookie.getKey(), sessionCookie.getValue().toString());
                }
                options.put("session_cookies", sessionCookies);
            }
            if (result.getRedirectedHost() != null) {
                hosts.put(result.getHost().toString() + " ---> " + result.getRedirectedHost(), options);
            }
            else {
                hosts.put(result.getHost().toString(), options);
            }
        }

        try {
            System.out.println("Writing output to json file");
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
