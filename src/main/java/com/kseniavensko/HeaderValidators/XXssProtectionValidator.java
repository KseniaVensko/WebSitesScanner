package com.kseniavensko.HeaderValidators;

import java.util.List;


public class XXssProtectionValidator implements IHeaderValidator {
    private List<String> values;

    public XXssProtectionValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        return true;
        // Pattern.compile("1; mode=block", Pattern.CASE_INSENSITIVE)
    }
}
