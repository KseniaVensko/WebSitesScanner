package com.kseniavensko;

import com.kseniavensko.HeaderValidators.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner extends Observable implements IScanner {
    private List<Result> results = new ArrayList<Result>();
    private Pattern cookiePattern = Pattern.compile("\\s*([a-zA-Z_0-9-]*)=.*");
    private Logger logger = Logger.getInstance();

    public void scan(List<URL> hosts, ProxyToScan proxy, Map<String, String> headers, boolean resolveDns) {
        int i = 0;
        int j = hosts.size();
        for (URL host : hosts) {
            //  IConnection con = new FakeConnection(host, headers);
            IConnection con;
            if (proxy != null) {
                con = new Connection(host, proxy, headers);
            } else {
                con = new Connection(host, headers);
            }
            Result result = new Result();
            result.setHost(host);

            result.setStringStatus("Connection established\n");

            Map<String, List<String>> responseHeaders = new TreeMap<>();
            try {
                Map<String, List<String>> response = con.getResponseHeaders();
                // you should call getRedirectedHost after calling getResponseHeaders
                result.setRedirectedHost(con.getRedirectedHost());
                responseHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                for (Map.Entry<String, List<String>> entry : response.entrySet()) {
                    if (entry.getKey() != null) {
                        responseHeaders.put(entry.getKey(), entry.getValue());
                    }
                }
            } catch (IOException e) {
                logger.log("Can not read from connection.");
                result.setStringStatus("Connection failed\n");
            } catch (RuntimeException e) {
                logger.log("Can not open connection to proxy " + proxy.getAddr());
                result.setStringStatus("Connection failed\n");
            } catch (Exception e) {
                logger.log(e.getMessage());
            }

            result.setInformationHeaders(parseInformationHeaders(responseHeaders));
            result.setSecureHeaders(parseSecureHeaders(responseHeaders));
            result.setSecureCookieFlags(parseCookieHeader(responseHeaders.get("set-cookie")));

            results.add(result);
            i++;
            setChanged();
            notifyObservers("scanned: " + i + "/" + j);
        }
    }

    private HashMap<String, Result.Status> parseCookieHeader(List<String> cookies) {
        HashMap<String, Result.Status> result = new HashMap<>();
        if (cookies != null) {
            PropertiesReader reader = new PropertiesReader();
            TreeSet<String> metricCookies = new TreeSet<>();
            try {
                metricCookies = reader.readCookies();
            } catch (Exception e) {
                logger.log("Can not read metric cookies from property file. Returning empty cookies set.");
            }
            for (String cookie : cookies) {
                Matcher m = cookiePattern.matcher(cookie);
                if (m.matches()) {
                    String name = m.group(1);
                    if (metricCookies.contains(name)) {
                        continue;
                    }
                }

                if (cookie != null) {
                    if (cookie.toLowerCase().contains("httponly") && cookie.toLowerCase().contains("secure")) {
                        result.put(cookie, Result.Status.Correct);
                    } else {
                        result.put(cookie, Result.Status.Warning);
                    }
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
        if (headers != null) {
            for (String h : informationHeaders) {
                Result.Header header = new Result().new Header();
                header.name = h;
                if (headers.containsKey(h)) {
                    header.values = headers.get(h);
                    header.status = Result.Status.Exists;
                } else {
                    header.status = Result.Status.Missing;
                }
                informationHeaderList.add(header);
            }
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
                ValidationResult result = new ValidationResult();

                Constructor<?>[] c = recommendedSecureHeaders.get(h).getConstructors();
                try {
                    IHeaderValidator v = (IHeaderValidator) c[0].newInstance(header.values);
                    result = v.validate();
                } catch (Exception e) {
                    logger.log("Something wrong with validator for " + h);
                }

                header.status = result.isValid() ? Result.Status.Correct : Result.Status.Warning;
                header.detailedInfo = result.getDetailedInfo();
            } else {
                header.status = Result.Status.Missing;
            }
            secureHeaders.add(header);
        }

        return secureHeaders;
    }

    private HashMap<String, Class<? extends IHeaderValidator>> recommendedSecureHeaders = new HashMap<String, Class<? extends IHeaderValidator>>() {
        {
            put("Public-Key-Pins", PublicKeyPinsValidator.class);
            put("Strict-Transport-Security", StrictTransportSecurityValidator.class);
            put("X-Frame-Options", XFrameOptionsValidator.class);
            put("X-Xss-Protection", XXssProtectionValidator.class);
            put("X-Content-Type-Options", XContentTypeOptionsValidator.class);
            put("Content-Security-Policy", ContentSecurityPolicyValidator.class);
        }
    };

    private ArrayList<String> informationHeaders = new ArrayList<String>() {
        {
            add("Server");
            add("X-Powered-By");
            add("X-Aspnet-Version");
            add("X-Runtime");
            add("X-Version");
            add("X-Powered-Cms");
            add("Content-Security-Policy-Report-Only");
        }
    };
}
