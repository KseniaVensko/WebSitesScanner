package com.kseniavensko;

import com.kseniavensko.HeaderValidators.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
//            IConnection con = new FakeConnection(host, headers);
            IConnection con = new Connection(host, headers);
            Result result = new Result();
            result.setHost(host);
            result.setStringStatus("good");
            Map<String, List<String>> responseHeaders = null;
            try {
                Map<String, List<String>> response = con.getResponseHeaders();
                responseHeaders = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
                for (Map.Entry<String, List<String>> entry : response.entrySet()) {
                    if (entry.getKey() != null) {
                        responseHeaders.put(entry.getKey(), entry.getValue());
                    }
                }
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
            } else {
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
                header.values = headers.get(h);
                boolean correct = false;

                Constructor<?>[] c = recommendedSecureHeaders.get(h).getConstructors();
                try {
                    IHeaderValidator v = (IHeaderValidator) c[0].newInstance(header.values);
                    correct = v.valid();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                header.status = correct ? Result.Status.Correct : Result.Status.Warning;
            } else {
                header.status = Result.Status.Missing;
            }
            secureHeaders.add(header);
        }

        return secureHeaders;
    }

    private boolean checkHeaderValues(String h) {

        return false;
    }

    private HashMap<String, Class<? extends IHeaderValidator>> recommendedSecureHeaders = new HashMap<String, Class<? extends IHeaderValidator>>() {
        {
            put("public-key-pins", PublicKeyPinsValidator.class);
            put("strict-transport-security", StrictTransportSecurityValidator.class);
            put("x-frame-options", XFrameOptionsValidator.class);
            put("x-xss-protection", XXssProtectionValidator.class);
            put("x-content-type-options", XContentTypeOptionsValidator.class);
            put("content-security-policy", ContentSecurityPolicyValidator.class);
        }
    };

    private ArrayList<String> informationHeaders = new ArrayList<String>() {
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
