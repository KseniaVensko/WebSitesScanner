package com.kseniavensko.HeaderValidators;

import java.util.List;

public class XContentTypeOptionsValidator implements IHeaderValidator {
    private List<String> values;

    public XContentTypeOptionsValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        for (String s : values) {
            if (s.equalsIgnoreCase("nosniff")) return true;
        }
        return false;
    }
}
