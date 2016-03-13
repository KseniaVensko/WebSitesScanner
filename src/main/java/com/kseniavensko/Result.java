package com.kseniavensko;

import org.junit.Assert;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class Result {
    private URL host;
    private List<Header> secureHeaders;
    private Map<String, String> secureCookieFlags;
    private List<Header> informationHeaders;
    private String stringStatus;

    public List<Header> getSecureHeaders() {
        return secureHeaders;
    }

    public void setSecureHeaders(List<Header> secureHeaders) {
        Assert.assertNotNull(secureHeaders);
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

    public List<Header> getInformationHeaders() {
        return informationHeaders;
    }

    public void setInformationHeaders(List<Header> informationHeaders) {
        Assert.assertNotNull(informationHeaders);
        this.informationHeaders = informationHeaders;
    }

    enum Status {
        Correct, Missing, Warning
    }

    class Header {
        Status status;
        String name;
        List<String> values;
    }
}
