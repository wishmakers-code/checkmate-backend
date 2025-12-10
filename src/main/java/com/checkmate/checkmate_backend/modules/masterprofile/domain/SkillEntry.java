package com.checkmate.checkmate_backend.modules.masterprofile.domain;

public class SkillEntry {

    private String name;
    private String level;
    private Integer years;

    // ============================
    // Getters & Setters
    // ============================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }
}
