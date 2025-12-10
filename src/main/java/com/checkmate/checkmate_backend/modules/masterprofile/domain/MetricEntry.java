package com.checkmate.checkmate_backend.modules.masterprofile.domain;

public class MetricEntry {

    private String label;
    private Double value;
    private String unit;
    private String context;

    // ============================
    // Getters & Setters
    // ============================

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
