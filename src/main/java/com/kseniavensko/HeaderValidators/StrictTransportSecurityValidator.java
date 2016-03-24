package com.kseniavensko.HeaderValidators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RFC 6797 6.1
 */
public class StrictTransportSecurityValidator implements IHeaderValidator{
    private List<String> values;
    private Pattern p = Pattern.compile("(max-age=\"?\\d+\"?\\s?;?\\s?(includeSubDomains)?\\s?;?\\s?(preload)?)|((includeSubDomains)?\\s?;?\\s?max-age=\"?\\d+\"?)", Pattern.CASE_INSENSITIVE);

    public StrictTransportSecurityValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        for (String val : values) {
            if (val == null) {
                continue;
            }
            Matcher m = p.matcher(val);

            if (m.matches()) {
                return true;
            }
        }
        return false;
    }
}
