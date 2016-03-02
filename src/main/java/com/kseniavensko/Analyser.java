package com.kseniavensko;

import java.util.List;
import java.util.Map;

public class Analyser {
    private Map<String, List<String>> headers = null;

    public Analyser(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public boolean hasHeader(String header) {
        return headers.containsKey(header);
    }

    public void printHeaders() {
        try {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\n\n");
    }

    public boolean secureHeaderIsCorrect(String header) {
        return true;
    }

    public boolean secureCookieFlagIsSet(String flag) {
        return true;
    }

//    enum SecureHeaders {
//        PublicKeyPinning, HttpStrictTransportSceurity, XFrameOptions, XXSSProtection, XContentTypeOptions, ContentSecurityPolicy, XContentSecurityPolicy, XWebkitCSP
//    }
//
//    enum SecureCookieFlags {
//        HttpOnly, Secure
//    }
//
//    enum
}
