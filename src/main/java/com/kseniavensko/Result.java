package com.kseniavensko;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class Result {
    private URL host;
    private List<secureHeader> secureHeaders;
    private Map<String, String> secureCookieFlags;
    private List<informationHeader> informationHeaders;
    private String stringStatus;

    public List<secureHeader> getSecureHeaders() {
        return secureHeaders;
    }

    public void setSecureHeaders(List<secureHeader> secureHeaders) {
        this.secureHeaders = secureHeaders;
    }

    public String getStringStatus() {
        return stringStatus;
    }

    public void setStringStatus(String stringStatus) {
        this.stringStatus = stringStatus;
    }

    public Map<String, String> getSecureCookieFlags() {
        return secureCookieFlags;
    }

    public void setSecureCookieFlags(Map<String, String> secureCookieFlags) {
        this.secureCookieFlags = secureCookieFlags;
    }

    public URL getHost() {
        return host;
    }

    public void setHost(URL host) {
        this.host = host;
    }

    public List<informationHeader> getInformationHeaders() {
        return informationHeaders;
    }

    public void setInformationHeaders(List<informationHeader> informationHeaders) {
        this.informationHeaders = informationHeaders;
    }

    enum Status {
        Correct, Missing, Warning
    }

    class secureHeader {
        boolean correct;
        String name;
        List<String> values;
    }

    class informationHeader {
        Status status;
        String name;
        List<String> values;
    }
}
