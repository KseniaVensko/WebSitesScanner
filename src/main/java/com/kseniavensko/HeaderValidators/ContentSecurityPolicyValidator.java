package com.kseniavensko.HeaderValidators;

import java.util.List;

/**
 *  http://w3c.github.io/webappsec-csp/ - lvl 3; I did for https://www.w3.org/TR/CSP/#csp-request-header
 */
public class ContentSecurityPolicyValidator implements IHeaderValidator {
    private List<String> values;

    public ContentSecurityPolicyValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        return true;
        // Pattern.compile("default-src", Pattern.CASE_INSENSITIVE)
    }
}
