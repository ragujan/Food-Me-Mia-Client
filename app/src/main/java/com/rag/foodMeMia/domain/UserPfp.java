package com.rag.foodMeMia.domain;

public class UserPfp {
    private String pfpUrl;
    private String email;

    public UserPfp(String pfpUrl, String email) {
        this.pfpUrl = pfpUrl;
        this.email = email;
    }

    public String getPfpUrl() {
        return pfpUrl;
    }

    public void setPfpUrl(String pfpUrl) {
        this.pfpUrl = pfpUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
