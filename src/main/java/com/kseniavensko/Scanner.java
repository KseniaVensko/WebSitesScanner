package com.kseniavensko;

import com.kseniavensko.Fakes.FakeConnection;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Scanner extends Observable implements IScanner {
    private List<Result> results = new ArrayList<Result>();

    public void scan(List<URL> hosts, String proxy_type, URL proxy_addr, Map<String, String> headers, boolean resolveDns) {
        int i = 0;
        int j = hosts.size();
        for (URL host : hosts) {
            //IConnection con = new FakeConnection(host);
            IConnection con = new Connection(host, headers);
            Result result = new Result();
            result.setHost(host);
            result.setStringStatus("good");
            Map<String, List<String>> responseHeaders = con.getResponseHeaders();
            result.setInformationHeaders(parseInformationHeaders(responseHeaders));
            result.setSecureHeaders(parseSecureHeaders(responseHeaders));
            results.add(result);
            i++;
            setChanged();
            notifyObservers("scanned: " + i + "/" + j);
        }
    }

    public ScanResult returnResults() {
        return new ScanResult(results);
    }

    private List<Result.informationHeader> parseInformationHeaders(Map<String, List<String>> headers) {
        List<Result.informationHeader> informationHeaders = new ArrayList<Result.informationHeader>();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            Result.informationHeader h = new Result().new informationHeader();
            h.name = entry.getKey();
            h.values = entry.getValue();
            informationHeaders.add(h);
        }
        return informationHeaders;
    }

    private List<Result.secureHeader> parseSecureHeaders(Map<String, List<String>> headers) {
        List<Result.secureHeader> secureHeaders = new ArrayList<Result.secureHeader>();

            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey() != null && recommendedSecureHeaders.containsKey(entry.getKey().toLowerCase())) {
                    Result.secureHeader h = new Result().new secureHeader();
                    h.name = entry.getKey().toLowerCase();
                    h.values = entry.getValue();
                    h.correct = true;
                    for (String value : h.values) {
                        h.correct &= containsRecommendedOption(h.name, value);
                    }
                    //  h.correct = containsRecommendedOption(h.name, h.values);
                    secureHeaders.add(h);
                }
            }

        return secureHeaders;
    }

    private void processHeaders() {

    }

    private boolean containsRecommendedOption(String header, String value) {
        List<String> options = recommendedSecureHeaders.get(header);
        if (value == null) return false;
        for (String option : options) {
            if (option.equals(value.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private HashMap<String, List<String>> recommendedSecureHeaders = new HashMap<String, List<String>>() {
        {
            put("x-content-type-options", new ArrayList<String>() {
                {
                    add("nosniff");
                }
            });
            put("x-xss-protection", new ArrayList<String>() {
                {
                    add("1; mode=block");
                }
            });
        }
    };

    private ArrayList<String> informationHeaders = new ArrayList<String>(){
        {
            add("server");
            add("x-powered-by");
            add("x-aspnet-version");
            add("x-runtime");
            add("x-version");
            add("x-powered-cms");
            add("content-security-policy-report-only");
        }
    };
}
