package com.kseniavensko.HeaderValidators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XXssProtectionValidator implements IHeaderValidator {
    private List<String> values;
    private Pattern p = Pattern.compile("\\s*1; mode=block\\s*", Pattern.CASE_INSENSITIVE);

    public XXssProtectionValidator(List<String> values) {
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
