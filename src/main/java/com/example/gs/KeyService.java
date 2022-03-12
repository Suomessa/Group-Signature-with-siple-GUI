package com.example.gs;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeyService {
    private static final List<PublicKeyPojo> publicKeys = new ArrayList<>();
    private static final List<PublicKey> publicKeys2 = new ArrayList<>();
    private static final List<User> users = new ArrayList<>();
    private static final int KEYS_PER_USER_COUNT = 3;
    private static final int USERS_PER_GROUP = 5;
    private static final int GROUPS_COUNT = 3;
    private static final Random rand = new Random();
    private static KeyPairGenerator keyPairGen;
    private static Cipher cipher;
    static {
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<PublicKey> getPublicKeys() {
        return publicKeys2;
    }

    public static List<PublicKeyPojo> getPublicKeysPojo() {
        return publicKeys;
    }

    public static void generateData() {
        for (int i = 1; i < GROUPS_COUNT+1; i++) {
            String groupName = "Group " + i;
            for (int j = 1; j < USERS_PER_GROUP+1; j++) {
                List<PublicKey> localPublicKeys = new ArrayList<>();
                List<PrivateKey> privateKeys = new ArrayList<>();
                for (int k = 0; k < KEYS_PER_USER_COUNT; k++) {
                    KeyPair keyPair = keyPairGen.generateKeyPair();
                    privateKeys.add(keyPair.getPrivate());
                    localPublicKeys.add(keyPair.getPublic());
                    publicKeys.add(new PublicKeyPojo(keyPair.getPublic(), groupName));
                    publicKeys2.addAll(localPublicKeys);
                }
                users.add(new User("User " + j, groupName, privateKeys, localPublicKeys));
            }
        }
    }


    public static byte[] encryptMessage(PrivateKey key, String message) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.update(message.getBytes());
        return cipher.doFinal();
    }

    public static DecryptedMessage decryptMessage(List<PublicKeyPojo> keys, byte[] message) throws InvalidKeyException {
        for (PublicKeyPojo publicKey : publicKeys) {
            cipher.init(Cipher.DECRYPT_MODE, publicKey.getKey());
            try {
                return new DecryptedMessage(new String(cipher.doFinal(message), StandardCharsets.UTF_8),
                        publicKey.getGroupName());
            } catch (Exception e) {
            }
        }

        return null;
    }
}
