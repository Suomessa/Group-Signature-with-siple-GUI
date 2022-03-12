package com.example.gs;

public class DecryptedMessage {
    private String message;
    private String group;

    public DecryptedMessage(String message, String group) {
        this.message = message;
        this.group = group;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
