package com.kseniavensko.HeaderValidators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrictTransportSecurityValidator implements IHeaderValidator{
    private List<String> values;

    public StrictTransportSecurityValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        return true;
//        Pattern p = Pattern.compile("(max-age=\"?\\d+\"?\\s?;?\\s?(includeSubDomains)?\\s?;?\\s?(preload)?)|((includeSubDomains)?\\s?;?\\s?max-age=\"?\\d+\"?)", Pattern.CASE_INSENSITIVE);                      // RFC 6797 6.1
//        Matcher m = p.matcher()
    }
}
