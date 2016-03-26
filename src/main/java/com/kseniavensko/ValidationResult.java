package com.kseniavensko;

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
