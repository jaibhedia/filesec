package com.scanbyte.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.scanbyte.models.ThreatSignature;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/database/signatures.db";

    public void addSignature(ThreatSignature signature) {
        String sql = "INSERT INTO signatures(name, hash) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, signature.getName());
            pstmt.setString(2, signature.getHash());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ThreatSignature> getAllSignatures() {
        List<ThreatSignature> signatures = new ArrayList<>();
        String sql = "SELECT name, hash FROM signatures";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ThreatSignature signature = new ThreatSignature(rs.getString("name"), rs.getString("hash"));
                signatures.add(signature);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return signatures;
    }

    public void deleteSignature(String hash) {
        String sql = "DELETE FROM signatures WHERE hash = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hash);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}