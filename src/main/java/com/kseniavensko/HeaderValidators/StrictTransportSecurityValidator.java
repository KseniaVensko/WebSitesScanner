package com.kseniavensko.HeaderValidators;

import com.kseniavensko.ValidationResult;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RFC 6797 6.1
 */
public class StrictTransportSecurityValidator implements IHeaderValidator{
    private List<String> values;
    private Pattern p = Pattern.compile("(max-age=\"?\\d+\"?\\s?;?\\s?(includeSubDomains)?\\s?;?\\s?(preload)?)|((includeSubDomains)?\\s?;?\\s?max-age=\"?\\d+\"?)", Pattern.CASE_INSENSITIVE);

    public StrictTransportSecurityValidator(List<String> values) {
        this.values = values;
    }

    /**
     * @return valid or not and detailed info if it is not (ValidationResult)
     * check if the value of the header contains required attribute max-age
     * doesn't check the content of the attributes
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
        result.setDetailedInfo("Max-age attribute is required. The proper usage of this header helps defend against MITM attacks and leaking session data.");
        result.setValid(false);
        return result;
    }
}
