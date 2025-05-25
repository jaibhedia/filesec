package com.scanbyte.services;

import com.scanbyte.models.ScanResult;
import com.scanbyte.models.ThreatSignature;
import com.scanbyte.utils.HashUtils;

import java.io.File;
import java.util.List;

public class FileScanner {

    private final SignatureDetectionEngine detectionEngine;

    public FileScanner(SignatureDetectionEngine detectionEngine) {
        this.detectionEngine = detectionEngine;
    }

    public ScanResult scanFile(File file) {
        if (!file.exists() || !file.isFile()) {
            return new ScanResult(false, "File does not exist or is not a valid file.");
        }

        try {
            String fileHash = HashUtils.generateFileHash(file);
            List<ThreatSignature> signatures = detectionEngine.getThreatSignatures();
            
            // Show the calculated hash for debugging
            System.out.println("File: " + file.getName() + ", Hash: " + fileHash);
            
            for (ThreatSignature signature : signatures) {
                System.out.println("Comparing with signature: " + signature.getName() + ", Hash: " + signature.getHash());
                if (signature.getHash().equals(fileHash)) {
                    return new ScanResult(true, "Threat detected: " + signature.getName());
                }
            }
            return new ScanResult(false, "No threats detected. File hash: " + fileHash);
        } catch (Exception e) {
            e.printStackTrace();
            return new ScanResult(false, "Error reading file: " + e.getMessage());
        }
    }

    // Add alias method for compatibility
    public ScanResult scan(File file) {
        return scanFile(file);
    }

    public ScanResult scanDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            return new ScanResult(false, "Directory does not exist or is not valid.");
        }

        StringBuilder report = new StringBuilder();
        boolean threatFound = false;

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                ScanResult result = scanFile(file);
                report.append(result.getMessage()).append("\n");
                if (result.isThreatDetected()) {
                    threatFound = true;
                }
            }
        }

        return new ScanResult(threatFound, report.toString());
    }
}