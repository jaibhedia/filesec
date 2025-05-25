package com.scanbyte.models;

public class ScanResult {
    private boolean threatDetected;
    private String filePath;
    private String threatDetails;

    public ScanResult(boolean threatDetected, String filePath, String threatDetails) {
        this.threatDetected = threatDetected;
        this.filePath = filePath;
        this.threatDetails = threatDetails;
    }

    // Convenience constructor for message-only results
    public ScanResult(boolean threatDetected, String message) {
        this.threatDetected = threatDetected;
        this.filePath = "";
        this.threatDetails = message;
    }

    public boolean isThreatDetected() {
        return threatDetected;
    }

    public void setThreatDetected(boolean threatDetected) {
        this.threatDetected = threatDetected;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getThreatDetails() {
        return threatDetails;
    }

    public void setThreatDetails(String threatDetails) {
        this.threatDetails = threatDetails;
    }

    // Convenience method for compatibility
    public String getMessage() {
        return threatDetails;
    }

    @Override
    public String toString() {
        return "ScanResult{" +
                "threatDetected=" + threatDetected +
                ", filePath='" + filePath + '\'' +
                ", threatDetails='" + threatDetails + '\'' +
                '}';
    }
}