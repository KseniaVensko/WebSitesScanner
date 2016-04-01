package com.kseniavensko.HeaderValidators;

import com.kseniavensko.ValidationResult;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XXssProtectionValidator implements IHeaderValidator {
    private List<String> values;
    private Pattern p = Pattern.compile("\\s*1; mode=block\\s*", Pattern.CASE_INSENSITIVE);

    public XXssProtectionValidator(List<String> values) {
        this.values = values;
    }

    /**
     * @return valid or not and detailed info if it is not (ValidationResult)
     * check if value is exactly 1; mode=block
     * fills ValidationResult (set boolean isValid and set detailed info also if it is not)
     */
    public ValidationResult validate() {
        ValidationResult result = new ValidationResult();
        for (String val : values) {
            if (val == null) {
                continue;
            }
            Matcher m = p.matcher(val);
            if (m.matches()) {
                result.setValid(true);
                return result;
            }
        }
        result.setValid(false);
        result.setDetailedInfo("You should use \\'1; mode=block\\' to protect from XSS.");
        return result;
    }
}
