package com.example.myapplication.Controlador;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ControladorContraseñas {

    private static final String KEY = "mySecretKey123"; // clave secreta para encriptación/desencriptación
    private static final int MAX_PASSWORD_LENGTH = 20; // longitud máxima de la contraseña

    // Encriptar una cadena de texto
    public static String encrypt(String password) {
        try {
            byte[] keyData = KEY.getBytes();
            SecretKey secretKey = new SecretKeySpec(keyData, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] passwordBytes = password.getBytes();
            if (passwordBytes.length > MAX_PASSWORD_LENGTH) {
                byte[] truncatedBytes = new byte[MAX_PASSWORD_LENGTH];
                System.arraycopy(passwordBytes, 0, truncatedBytes, 0, MAX_PASSWORD_LENGTH);
                passwordBytes = truncatedBytes;
            }
            byte[] encryptedBytes = cipher.doFinal(passwordBytes);
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Desencriptar una cadena de texto
    public static String decrypt(String encryptedPassword) {
        try {
            byte[] keyData = KEY.getBytes();
            SecretKey secretKey = new SecretKeySpec(keyData, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.decode(encryptedPassword, Base64.DEFAULT);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String password = new String(decryptedBytes);
            if (password.length() > MAX_PASSWORD_LENGTH) {
                password = password.substring(0, MAX_PASSWORD_LENGTH);
            }
            return password;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
