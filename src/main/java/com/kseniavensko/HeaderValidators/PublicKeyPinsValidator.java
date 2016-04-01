package com.kseniavensko.HeaderValidators;

import com.kseniavensko.ValidationResult;

import java.util.List;

/**
 * rfc 7469
 **/
public class PublicKeyPinsValidator implements IHeaderValidator {
    private List<String> values;

    public PublicKeyPinsValidator(List<String> values) {
        this.values = values;
    }

/**
 * @return valid or not and detailed info if it is not (ValidationResult)
 *
 *  check if the value of  the header contains required attribute max-age and attribute pin-sha256
 * doesn't validate the content of attributes
 * fills ValidationResult (set boolean isValid and set detailed info also if it is not)
 */
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
