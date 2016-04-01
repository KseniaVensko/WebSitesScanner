package com.kseniavensko;

/**
 * represents the result of validation
 * if valid is true then detailedInfo should be empty
 */
public class ValidationResult {
    private boolean valid;
    private StringBuilder detailedInfo = new StringBuilder();

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getDetailedInfo() {
        return detailedInfo.toString();
    }

    public void setDetailedInfo(String detailedInfo) {
        this.detailedInfo.append(detailedInfo);
    }
}
