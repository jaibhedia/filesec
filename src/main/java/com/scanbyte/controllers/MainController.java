package com.scanbyte.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.application.Platform;
import com.scanbyte.services.FileScanner;
import com.scanbyte.services.SignatureDetectionEngine;
import com.scanbyte.models.ScanResult;
import com.scanbyte.models.ThreatSignature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private Button scanButton;

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressBar progressBar;

    private FileScanner fileScanner;

    public MainController() {
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
        fileChooser.setTitle("Select File or Directory to Scan");
        File file = fileChooser.showOpenDialog(scanButton.getScene().getWindow());

        if (file != null) {
            statusLabel.setText("Scanning...");
            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            new Thread(() -> {
                ScanResult result = fileScanner.scan(file);
                updateUI(result);
            }).start();
        }
    }

    private void updateUI(ScanResult result) {
        Platform.runLater(() -> {
            progressBar.setProgress(0);
            if (result.isThreatDetected()) {
                statusLabel.setText("Threat Detected: " + result.getThreatDetails());
                statusLabel.setStyle("-fx-text-fill: red;");
            } else {
                statusLabel.setText("No Threats Detected. File is clean.");
                statusLabel.setStyle("-fx-text-fill: green;");
            }
        });
    }
}