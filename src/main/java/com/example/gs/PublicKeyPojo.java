package com.example.gs;

import java.security.PublicKey;

public class PublicKeyPojo {
    private PublicKey key;

    private String groupName;

    public PublicKeyPojo(PublicKey key, String groupName) {
        this.key = key;
        this.groupName = groupName;
    }

    public PublicKey getKey() {
        return key;
    }

    public void setKey(PublicKey key) {
        this.key = key;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
