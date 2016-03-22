package com.kseniavensko;

import com.kseniavensko.Fakes.FakeConnection;
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
    private Pattern cookiePattern = Pattern.compile("\\s*([a-zA-Z_0-9-]*)=.*");

    public void scan(List<URL> hosts, String proxy_type, String proxy_addr, Map<String, String> headers, boolean resolveDns) {
        int i = 0;
        int j = hosts.size();
        for (URL host : hosts) {
          //  IConnection con = new FakeConnection(host, headers);
            IConnection con;
            if (proxy_addr != null && proxy_type != null) {
                con = new Connection(host, proxy_type, proxy_addr, headers);
            }
            else {
                con = new Connection(host, headers);
            }
            Result result = new Result();
            result.setHost(host);

            result.setStringStatus("Connection established\n");
            Map<String, List<String>> responseHeaders = null;
            try {
                Map<String, List<String>> response = con.getResponseHeaders();
                // you should call getRedirectedHost after calling getResponseHeaders
                result.setRedirectedHost(con.getRedirectedHost());
                responseHeaders = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
                for (Map.Entry<String, List<String>> entry : response.entrySet()) {
                    if (entry.getKey() != null) {
                        responseHeaders.put(entry.getKey(), entry.getValue());
                    }
                }
            } catch (IOException e) {
                result.setStringStatus("Connection failed\n");
            }
            if (responseHeaders != null) {
                result.setInformationHeaders(parseInformationHeaders(responseHeaders));
                result.setSecureHeaders(parseSecureHeaders(responseHeaders));
                result.setSecureCookieFlags(parseCookieHeader(responseHeaders.get("set-cookie")));
            }

            results.add(result);
            i++;
            setChanged();
            notifyObservers("scanned: " + i + "/" + j);
        }
    }

    private HashMap<String, Result.Status> parseCookieHeader(List<String> cookies) {
        HashMap<String, Result.Status> result = new HashMap<>();
        if (cookies != null) {
            for (String cookie : cookies) {
                Matcher m = cookiePattern.matcher(cookie);
                if (m.matches()) {
                    String name = m.group(1);
                    // skip yandex metrika and google analytics
                    if (googleCookies.contains(name) || yandexCookies.contains(name)) {
                        continue;
                    }
                }
//                if (cookie.toLowerCase().contains("expires") || cookie.toLowerCase().contains("max-age"))       // session cookie doesn`t contain this fields (rfc 6265 4.1.2.2)
//                    continue;

                if (cookie.toLowerCase().contains("httponly") && cookie.toLowerCase().contains("secure")) {
                    result.put(cookie, Result.Status.Correct);
                } else {
                    result.put(cookie, Result.Status.Warning);
                }
            }
        }
        return result;
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

    // https://developers.google.com/analytics/devguides/collection/analyticsjs/cookie-usage?hl=ru#gajs
    private Set<String> googleCookies = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER) {
        {
            add("__utma");
            add("__utmt");
            add("__utmb");
            add("__utmc");
            add("__utmz");
            add("__utmv");
            add("_gat");
            add("_ga");
            add("NID");
        }
    };

    private Set<String> yandexCookies = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER) {
        {
            add("_ym_visorc");
            add("yabs-sid");
            add("yandexuid");
        }
    };
}
