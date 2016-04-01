package com.kseniavensko.HeaderValidators;

import com.kseniavensko.ValidationResult;

import java.util.List;

public class XFrameOptionsValidator implements IHeaderValidator {
    private List<String> values;

    public XFrameOptionsValidator(List<String> values) {
        this.values = values;
    }

    /**
     * @return valid or not and detailed info if it is not (ValidationResult)
     * * check if value doesn't contain the * url mask and is not allow-from
     * fills ValidationResult (set boolean isValid and set detailed info also if it is not)
     */
    public ValidationResult validate() {
        ValidationResult result = new ValidationResult();
        for (String val : values) {
            if (val == null) {
                continue;
            }
            if (val.contains("allow-from") || val.contains(" *")){
                result.setValid(false);
                result.setDetailedInfo("It is not recommended to use allow-from and especially pattern * to provide Clickjacking protection");
                return result;
            }
        }
        result.setValid(true);
        return result;
    }
}
