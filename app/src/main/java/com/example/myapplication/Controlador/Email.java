package com.example.myapplication.Controlador;

import android.util.Log;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {

    private static final String TAG = Email.class.getSimpleName();

    public boolean enviarCorreo(String destinatario, String asunto, String texto, String adjuntoRuta) {
        String remitente = "frutapp@appinformaticas.com";
        String clave = "FrutApp2023!";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "mailserver.appinformaticas.com");
        props.put("mail.smtp.port", "465");

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
            message.setSubject(asunto);

            // Configuraracion contenido correo
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(texto);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Ruta de adjunto válida
            if (adjuntoRuta != null && !adjuntoRuta.isEmpty()) {
                BodyPart adjuntoBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(adjuntoRuta);
                adjuntoBodyPart.setDataHandler(new DataHandler(source));
                adjuntoBodyPart.setFileName(source.getName());
                multipart.addBodyPart(adjuntoBodyPart);
            }

            message.setContent(multipart);

            // Enviar el correo
            Transport.send(message);

            Log.d(TAG, "Correo electrónico enviado a " + destinatario);

            return true; // Devuelve true si el correo electrónico se envió correctamente.

        } catch (MessagingException e) {
            Log.e(TAG, "Error al enviar el correo electrónico", e);
            return false; // Devuelve false si hubo un error al enviar el correo electrónico.
        }
    }
}
