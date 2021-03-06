package com.kseniavensko.HeaderValidators;

import com.kseniavensko.ValidationResult;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XContentTypeOptionsValidator implements IHeaderValidator {
    private List<String> values;
    private Pattern p = Pattern.compile("\\s*nosniff\\s*", Pattern.CASE_INSENSITIVE);

    public XContentTypeOptionsValidator(List<String> values) {
        this.values = values;
    }

    /**
     * @return valid or not and detailed info if it is not (ValidationResult)
     * * check if the value were exactly nosniff
     * fills ValidationResult (set boolean isValid and set detailed info also if it is not)
     */
    public ValidationResult validate() {
        ValidationResult result = new ValidationResult();
        for (String s : values) {
            if (s == null) {
                continue;
            }
            Matcher m = p.matcher(s);
            if (m.matches()){
                result.setValid(true);
                return result;
            }
        }
        result.setValid(false);
        result.setDetailedInfo("It is recommended to use noshiff option for this header to prevent from MIME-sniffing.");
        return result;
    }
}
