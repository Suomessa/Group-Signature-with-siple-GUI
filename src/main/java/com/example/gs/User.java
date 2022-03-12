package com.example.gs;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Random;

public class User {

    private String name;

    private String group;

    private List<PrivateKey> privateKeys;

    private List<PublicKey> publicKeys;

    public User(String name, String group, List<PrivateKey> privateKeys, List<PublicKey> publicKeys) {
        this.name = name;
        this.group = group;
        this.privateKeys = privateKeys;
        this.publicKeys = publicKeys;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public byte[] encryptMessage(String message, Cipher cipher, Random rand) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, privateKeys.get(rand.nextInt(privateKeys.size())));
        cipher.update(message.getBytes());
        return cipher.doFinal();
    }

    public List<PrivateKey> getPrivateKeys() {
        return privateKeys;
    }
}
