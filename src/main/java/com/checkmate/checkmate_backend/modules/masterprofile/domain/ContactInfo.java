package com.checkmate.checkmate_backend.modules.masterprofile.domain;

import java.util.List;

public class ContactInfo {

    private String email;
    private String phone;
    private List<String> links;

    // ============================
    // Getters & Setters
    // ============================

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
