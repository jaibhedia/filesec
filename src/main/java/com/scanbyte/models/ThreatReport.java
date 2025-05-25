package com.scanbyte.models;

import java.util.ArrayList;
import java.util.List;

public class ThreatReport {
    private String fileName;
    private String filePath;
    private boolean isThreatDetected;
    private List<String> threatSignatures;

    public ThreatReport(String fileName, String filePath, boolean isThreatDetected) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.isThreatDetected = isThreatDetected;
        this.threatSignatures = new ArrayList<>();
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isThreatDetected() {
        return isThreatDetected;
    }

    public List<String> getThreatSignatures() {
        return threatSignatures;
    }

    public void addThreatSignature(String signature) {
        this.threatSignatures.add(signature);
    }

    @Override
    public String toString() {
        return "ThreatReport{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", isThreatDetected=" + isThreatDetected +
                ", threatSignatures=" + threatSignatures +
                '}';
    }
}