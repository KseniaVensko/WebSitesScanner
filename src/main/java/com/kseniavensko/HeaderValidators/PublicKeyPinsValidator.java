package com.kseniavensko.HeaderValidators;

import java.util.List;

/**
 * rfc 7469
 */
public class PublicKeyPinsValidator implements IHeaderValidator {
    private List<String> values;

    public PublicKeyPinsValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        for (String val : values) {
            if (val.toLowerCase().contains("max-age") && val.toLowerCase().contains("pin-sha256"))
                return true;
        }
        return false;
    }
}
