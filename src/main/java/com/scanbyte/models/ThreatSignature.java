package com.scanbyte.models;

public class ThreatSignature {
    private String name;
    private String hash;

    public ThreatSignature(String name, String hash) {
        this.name = name;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "ThreatSignature{" +
                "name='" + name + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}