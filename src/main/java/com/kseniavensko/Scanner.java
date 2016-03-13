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
            IConnection con = new FakeConnection(host, headers);
//            IConnection con = new Connection(host, headers);
            Result result = new Result();
            result.setHost(host);
            result.setStringStatus("good");
            Map<String, List<String>> responseHeaders = null;
            try {
                Map<String, List<String>> response = con.getResponseHeaders();
                if (response.containsKey(null)) {
                    response.remove(null);
                }
                responseHeaders = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
                responseHeaders.putAll(response);
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

    private List<Result.Header> parseInformationHeaders(Map<String, List<String>> headers) {
        List<Result.Header> informationHeaderList = new ArrayList<Result.Header>();

        for (String h : informationHeaders) {
            Result.Header header = new Result().new Header();
            header.name = h;
            if (headers != null && headers.containsKey(h)) {
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

    private List<Result.Header> parseSecureHeaders(Map<String, List<String>> headers) {
        List<Result.Header> secureHeaders = new ArrayList<Result.Header>();

        for (String h : recommendedSecureHeaders.keySet()) {
            Result.Header header = new Result().new Header();
            header.name = h;
            if (headers != null && headers.containsKey(h)) {
                //TODO: check correctness
                header.values = headers.get(h);
                header.status = Result.Status.Correct;
            }
            else {
                header.status = Result.Status.Missing;
            }
            secureHeaders.add(header);
        }

        return secureHeaders;
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
