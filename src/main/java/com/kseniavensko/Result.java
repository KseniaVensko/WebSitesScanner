package com.kseniavensko;

import java.util.List;
import java.util.Map;

public class Result {
    private Status status;
    private Analyse analyse;
    private List<secureHeader> secureHeaders;
    private Map<String, String> secureCookieFlags;
    private Map<String, List<String>> informationHeaders;

    public Map<String, List<String>> getInformationHeaders() {
        return informationHeaders;
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
