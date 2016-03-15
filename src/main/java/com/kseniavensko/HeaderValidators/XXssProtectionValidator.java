package com.kseniavensko.HeaderValidators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XXssProtectionValidator implements IHeaderValidator {
    private List<String> values;
    private Pattern p = Pattern.compile("1; mode=block", Pattern.CASE_INSENSITIVE);

    public XXssProtectionValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        boolean correct = false;
        for (String val : values) {
            Matcher m = p.matcher(val);
            correct |= m.matches();
        }
        return correct;
    }
}
