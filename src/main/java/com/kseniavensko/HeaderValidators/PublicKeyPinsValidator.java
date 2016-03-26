package com.kseniavensko.HeaderValidators;

import com.kseniavensko.ValidationResult;

import java.util.List;

/**
 * rfc 7469
 */
public class PublicKeyPinsValidator implements IHeaderValidator {
    private List<String> values;

    public PublicKeyPinsValidator(List<String> values) {
        this.values = values;
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult();
        for (String val : values) {
            if (val == null) {
                continue;
            }
            if (val.toLowerCase().contains("max-age") && val.toLowerCase().contains("pin-sha256")) {
                result.setValid(true);
                return result;
            }
        }
        result.setValid(false);
        result.setDetailedInfo("Max-age attribute is required and you need to specify at least one pin-sha-256 attribute.");
        return result;
    }
}
