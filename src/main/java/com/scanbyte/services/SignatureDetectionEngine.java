package com.scanbyte.services;

import com.scanbyte.models.ThreatSignature;
import com.scanbyte.models.ScanResult;
import com.scanbyte.utils.HashUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SignatureDetectionEngine {

    private List<ThreatSignature> signatures;

    public SignatureDetectionEngine(List<ThreatSignature> signatures) {
        this.signatures = signatures;
    }

    public List<ThreatSignature> getThreatSignatures() {
        return signatures;
    }

    public ScanResult scanFile(File file) {
        try {
            String fileHash = HashUtils.generateFileHash(file);
            for (ThreatSignature signature : signatures) {
                if (signature.getHash().equals(fileHash)) {
                    return new ScanResult(true, signature.getName(), file.getAbsolutePath());
                }
            }
            return new ScanResult(false, null, file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            return new ScanResult(false, "Error reading file", file.getAbsolutePath());
        }
    }

    public void loadSignaturesFromFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                signatures.add(new ThreatSignature(parts[0], parts[1]));
            }
        }
    }
}