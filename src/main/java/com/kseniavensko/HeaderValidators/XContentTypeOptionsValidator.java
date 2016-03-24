package com.kseniavensko.HeaderValidators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XContentTypeOptionsValidator implements IHeaderValidator {
    private List<String> values;
    private Pattern p = Pattern.compile("\\s*nosniff\\s*", Pattern.CASE_INSENSITIVE);

    public XContentTypeOptionsValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        for (String s : values) {
            if (s == null) {
                continue;
            }
            Matcher m = p.matcher(s);
            if (m.matches()) return true;
        }
        return false;
    }
}
