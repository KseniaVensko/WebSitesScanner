package com.kseniavensko.HeaderValidators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XFrameOptionsValidator implements IHeaderValidator {
    private List<String> values;

    public XFrameOptionsValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        for (String val : values) {
            if (val.contains("allow-from")) return false;
        }
        return true;
    }
}
