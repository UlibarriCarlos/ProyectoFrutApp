package com.example.myapplication.Controlador;

import android.util.Log;

import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    private static final String TAG = Email.class.getSimpleName();

    public boolean enviarCorreo(String destinatario) {
        String remitente = "frutappgestion@gmail.com";
        String clave = "FrutApp1234";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(remitente, clave);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));
            message.setSubject("ALta en FrutAPP");
            message.setText("Bienvenido a FrutApp");

            Transport.send(message);

            Log.d(TAG, "Correo electrónico enviado a " + destinatario);

            return true; // Devuelve true si el correo electrónico se envió correctamente y asi podemos activar la cuenta.

        } catch (MessagingException e) {
            Log.e(TAG, "Error al enviar el correo electrónico", e);
            return false; // Devuelve false si hubo un error al enviar el correo electrónico y la cuenta queda desactivada.
        }
    }
}
