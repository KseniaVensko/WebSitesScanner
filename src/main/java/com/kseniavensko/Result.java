package com.kseniavensko;

import java.util.List;
import java.util.Map;

public class Result {
    private Status status;
    private Analyse analyse;
    private List<secureHeader> secureHeaders;
    private Map<String, String> secureCookieFlags;
    private Map<String, List<String>> informationHeaders;
    private String stringStatus;

    public Map<String, List<String>> getInformationHeaders() {
        return informationHeaders;
    }

    public void setInformationHeaders(Map<String, List<String>> informationHeaders) {
        this.informationHeaders = informationHeaders;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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

    enum Status {
        Checked, Failed
    }

    class Analyse {
        // TODO: Do I need it?
    }

    class secureHeader {
        boolean correct;
        String name;
        List<String> values;
    }

    //TODO: Do I need it?
    class informationHeader {
        boolean present;
        String name;
        List<String> values;
    }
}
