package com.kseniavensko.HeaderValidators;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  http://w3c.github.io/webappsec-csp/ - lvl 3; I did for https://www.w3.org/TR/CSP/#csp-request-header
 */
public class ContentSecurityPolicyValidator implements IHeaderValidator {
    private List<String> values;
    private Pattern directiveToken = Pattern.compile("\\s*([a-zA-Z_0-9-]*)\\s(.*)");

    public ContentSecurityPolicyValidator(List<String> values) {
        this.values = values;
    }

    public boolean valid() {
        Set<String> directives = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        // adding additional policies to the list of policies to enforce can only further restrict the capabilities of the protected resource
        boolean valid = true;

        for (String val : values) {
            String[] policyTokens = val.split(";");

            for (String token: policyTokens) {
                Matcher m = directiveToken.matcher(token);
                if (m.matches()) {
                    String name = m.group(1);
                    String value = m.group(2);
                    if (directives.contains(name)) {
                        continue;                       // If the set of directives already contains a directive whose name is a case insensitive match for directive name, ignore this instance of the directive
                    }
                    directives.add(name);
                    valid &= checkIsValid(name, value);
                }
            }
        }

        if (!directives.contains("frame-ancestors") || !directives.contains("form-action")) {  //If a frame-ancestors directive is not explicitly included in the policy, then allowed frame ancestors is "*".
            return false;
        }

        return valid;
    }

    private boolean checkIsValid(String name, String value) {
        if (value.matches("\\s*\\*\\s*") || value.toLowerCase().contains("data:") || value.toLowerCase().contains("unsafe-inline") || value.toLowerCase().contains("blob:") || value.toLowerCase().contains("filesystem:") || value.toLowerCase().contains("unsafe-eval")) {
            return false;                              // data: equals unsafe-inline, blob: filesystem: equals unsafe-eval
        }

        return true;
    }

    private ArrayList<String> directives = new ArrayList<String>() {
        {
            add("base-uri");
            add("child-src");
            add("connect-src");
            add("default-src");
            add("font-src");
            add("form-action");
            add("frame-ancestors");
            add("frame-src");
            add("img-src");
            add("media-src");
            add("object-src");
            add("plugin-types");
            add("report-uri");
            add("sandbox");
            add("script-src");
            add("style-src");
        }
    };
}
