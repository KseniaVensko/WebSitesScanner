package com.kseniavensko.HeaderValidators;

import java.util.List;
import java.util.regex.Pattern;

public class PublicKeyPinsValidator implements IHeaderValidator {
    private List<String> values;

    public PublicKeyPinsValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        return true;
        //Pattern.compile("pin-sha256=.*", Pattern.CASE_INSENSITIVE)
    }
}
