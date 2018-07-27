import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;

public class MailServices {
    public void mailSend() {
        String to = "cyberserker@gmail.com";
        String from = "314malna@gmail.com";
        String subject = "event detected";
        String messageText = "test test test";

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt");
        Properties props = new EncryptableProperties(encryptor);
        try {
            props.load(new FileInputStream("conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session mailSession = Session.getDefaultInstance(props);
        Message msg = new MimeMessage(mailSession);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(to)));
            msg.setSubject(subject);
            msg.setText(messageText);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("username"), props.getProperty("password"));
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println(props.getProperty("Message sent"));

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
public void createPropFile(){
    try {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt");
        String encryptedText = encryptor.encrypt("");
        Properties props = new Properties();
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.required", "true");
        props.setProperty("username", "314malna");
        props.setProperty("password", "ENC("+encryptedText+")");

        File file = new File("conf.properties");
        FileOutputStream fOs = new FileOutputStream(file);
        props.store(fOs, "SMTP");
        fOs.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
