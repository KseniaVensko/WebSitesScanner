package com.kseniavensko.HeaderValidators;

import java.util.List;


public class XFrameOptionsValidator implements IHeaderValidator {
    private List<String> values;

    public XFrameOptionsValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        return true;
        // Pattern.compile("deny|sameorigin", Pattern.CASE_INSENSITIVE)
    }
}
