package com.scanbyte.controllers;

import com.scanbyte.services.FileScanner;
import com.scanbyte.services.SignatureDetectionEngine;
import com.scanbyte.models.ScanResult;
import com.scanbyte.models.ThreatSignature;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanController {

    @FXML
    private Button scanButton;

    @FXML
    private TextArea resultArea;

    private FileScanner fileScanner;

    public ScanController() {
        // Initialize with default threat signatures
        List<ThreatSignature> signatures = loadDefaultSignatures();
        SignatureDetectionEngine engine = new SignatureDetectionEngine(signatures);
        fileScanner = new FileScanner(engine);
    }

    private List<ThreatSignature> loadDefaultSignatures() {
        List<ThreatSignature> signatures = new ArrayList<>();
        // Add some sample threat signatures
        signatures.add(new ThreatSignature("Trojan.Generic", "d41d8cd98f00b204e9800998ecf8427e"));
        signatures.add(new ThreatSignature("Malware.Sample", "5d41402abc4b2a76b9719d911017c592"));
        return signatures;
    }

    @FXML
    private void initialize() {
        scanButton.setOnAction(event -> startScan());
    }

    private void startScan() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Scan");
        File file = fileChooser.showOpenDialog(scanButton.getScene().getWindow());

        if (file != null) {
            ScanResult result = fileScanner.scanFile(file);
            displayResult(result);
        } else {
            showAlert("No file selected", "Please select a file to scan.");
        }
    }

    private void displayResult(ScanResult result) {
        if (result.isThreatDetected()) {
            resultArea.setText("Threat Detected: " + result.getThreatDetails());
        } else {
            resultArea.setText("No Threat Detected.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}