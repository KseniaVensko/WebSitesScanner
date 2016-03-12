package com.kseniavensko;

import com.kseniavensko.Fakes.FakeConnection;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Map<String, List<String>> responseHeaders = null;
            try {
                responseHeaders = con.getResponseHeaders();
            } catch (IOException e) {
                result.setStringStatus("bad");
            }
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
        List<Result.informationHeader> informationHeaderList = new ArrayList<Result.informationHeader>();

        for (String h : informationHeaders) {
            Result.informationHeader header = new Result().new informationHeader();
            header.name = h;
            if (headers.containsKey(h)) {
                header.values = headers.get(h);
                header.status = Result.Status.Correct;
            }
            else {
                header.status = Result.Status.Missing;
            }
            informationHeaderList.add(header);
        }

        return informationHeaderList;
    }

    private List<Result.secureHeader> parseSecureHeaders(Map<String, List<String>> headers) {
        List<Result.secureHeader> secureHeaders = new ArrayList<Result.secureHeader>();

            for (final Map.Entry<String, List<String>> entry : headers.entrySet()) {
                final String header = entry.getKey() == null ? null: entry.getKey().toLowerCase();

                if (recommendedSecureHeaders.containsKey(header)) {
                    Result.secureHeader h = new Result().new secureHeader();
                    h.name = header;
                    h.values = entry.getValue();
                    h.correct = true;

                    for (String value : h.values) {
                        // TODO: this is not correct
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
        if (value == null) return false;
        Pattern option = recommendedSecureHeaders.get(header);
        Matcher m = option.matcher(value.toLowerCase());
        return m.matches();
    }

    private HashMap<String, Pattern> recommendedSecureHeaders = new HashMap<String, Pattern>() {
        {
            put("x-content-type-options", Pattern.compile("nosniff"));
            put("x-xss-protection", Pattern.compile("1; mode=block"));
            put("public-key-pins", Pattern.compile("pin-sha256=.*"));
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
