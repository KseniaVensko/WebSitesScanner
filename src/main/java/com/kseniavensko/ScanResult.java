package com.kseniavensko;

import com.inamik.utils.SimpleTableFormatter;
import com.inamik.utils.TableFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ScanResult {
    private List<Result> results;
    Logger logger = Logger.getInstance();

    public ScanResult(List<Result> results) {
        this.results = results;
    }

    public void toConsole() {
        for (Result result : results) {
            System.out.print("\n" + result.getHost().toString());
            if (result.getRedirectedHost() != null) {
                System.out.println(" ---> " + result.getRedirectedHost());
            }
            System.out.println(result.getStringStatus());
            try {
                if (result.getInformationHeaders() != null && !result.getInformationHeaders().isEmpty()) {
                    System.out.println("Information headers");
                    TableFormatter tf = tableWithHat();
                    for (Result.Header header : result.getInformationHeaders()) {
                        appendHeader(tf, header);
                    }
                    printTable(tf);
                }
                if (result.getSecureHeaders() != null && !result.getSecureHeaders().isEmpty()) {
                    System.out.println("\nSecurity headers");
                    TableFormatter tf = tableWithHat();
                    for (Result.Header header : result.getSecureHeaders()) {
                        appendHeader(tf, header);
                    }
                    printTable(tf);
                }
                if (result.getSecureCookieFlags() != null && !result.getSecureCookieFlags().isEmpty()) {
                    System.out.println("\nSession cookies");
                    for (Map.Entry<String, Result.Status> sessionCookie : result.getSecureCookieFlags().entrySet()) {
                        System.out.println(sessionCookie.getKey() + " : " + sessionCookie.getValue());
                    }
                }
            } catch (Exception e) {
                logger.log(e.getMessage());
            }

            System.out.println();
        }
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
            } else {
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
            logger.log("Error while writing to output file " + file);
        }
    }

    private void appendHeader(TableFormatter tf, Result.Header header) {
        tf.nextRow().nextCell(TableFormatter.ALIGN_LEFT, TableFormatter.VALIGN_CENTER)
                .addLine(header.name)
                .nextCell(TableFormatter.ALIGN_LEFT, TableFormatter.VALIGN_CENTER);
        if (header.status != Result.Status.Missing) {
            for (String value : header.values) {
                if (value.length() > 35) {
                    for (String s : value.split("(?<=\\G.{35})")) {
                        tf.addLine(s);
                    }
                }
                else {
                    tf.addLine(value);
                }
            }
        }
        tf.nextCell(TableFormatter.ALIGN_LEFT, TableFormatter.VALIGN_CENTER)
                .addLine(header.status.toString())
                .nextCell(TableFormatter.ALIGN_LEFT, TableFormatter.VALIGN_CENTER);
        if (header.detailedInfo != null && header.detailedInfo.length() > 35) {
            for (String s : header.detailedInfo.split("(?<=\\G.{35})")) {
                tf.addLine(s);
            }
        }
        else {
            tf.addLine(header.detailedInfo);
        }
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
        header.put("detailed_info", h.detailedInfo);

        headers.put(h.name, header);
    }

    private TableFormatter tableWithHat() {
        TableFormatter tf = new SimpleTableFormatter(true);
        tf.nextRow().nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER)
                .addLine("header")
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER)
                .addLine("value")
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER)
                .addLine("correctness")
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER)
                .addLine("description");
        return tf;
    }

    private void printTable(TableFormatter tf) {
        String[] table = tf.getFormattedTable();

        for (int i = 0, size = table.length; i < size; i++) {
            System.out.println( "\t" + table[i]);
        }
    }
}
