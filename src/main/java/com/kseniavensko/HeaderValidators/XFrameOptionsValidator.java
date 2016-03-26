package com.kseniavensko.HeaderValidators;

import com.kseniavensko.ValidationResult;

import java.util.List;


public class XFrameOptionsValidator implements IHeaderValidator {
    private List<String> values;

    public XFrameOptionsValidator(List<String> values) {
        this.values = values;
    }

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
