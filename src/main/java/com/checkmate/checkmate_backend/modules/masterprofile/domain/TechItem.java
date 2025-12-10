package com.checkmate.checkmate_backend.modules.masterprofile.domain;

public class TechItem {

    private String name;
    private String category;
    private String level;

    // ============================
    // Getters & Setters
    // ============================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
