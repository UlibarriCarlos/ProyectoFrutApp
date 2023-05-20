package com.example.myapplication.Controlador;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ControladorContraseñas {
    private static final int MAX_PASSWORD_LENGTH = 20; // longitud máxima de la contraseña

    // Encriptar una cadena de texto utilizando MD5
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passwordBytes = password.getBytes();
            byte[] hashBytes = md.digest(passwordBytes);
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Desencriptar una cadena de texto utilizando MD5
    public static String decrypt(String encryptedPassword) {
        // No se puede desencriptar MD5, ya que es una función de hash unidireccional
        return null;
    }

    // Método auxiliar para convertir un array de bytes a una cadena hexadecimal
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}