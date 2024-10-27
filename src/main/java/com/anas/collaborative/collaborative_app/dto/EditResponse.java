package com.anas.collaborative.collaborative_app.dto;



public class EditResponse {
    private String status;
    private String content;

    public EditResponse(String status, String content) {
        this.status = status;
        this.content = content;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
