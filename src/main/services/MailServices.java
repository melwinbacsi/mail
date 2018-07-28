package services;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
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
        encryptor.setPassword("mkjashdgf");
        Properties props = new EncryptableProperties(encryptor);
        try {
            props.load(new FileInputStream("conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Session mailSession = Session.getDefaultInstance(props);
        Message msg = new MimeMessage(mailSession);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(to)));
            msg.setSubject(subject);
            msg.setText(messageText);

            Transport transport = mailSession.getTransport("smtp");
            try{transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("username"), props.getProperty("password"));}
            catch (AuthenticationFailedException e){
                System.out.println("Wrong password!");
                return;}
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("Message sent");


        }    catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void createPropFile(char[] pass) {
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword("mkjashdgf");
            String encryptedText = encryptor.encrypt(String.valueOf(pass));
            Properties props = new Properties();
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.required", "true");
            props.setProperty("username", "314malna");
            props.setProperty("password", "ENC(" + encryptedText + ")");

            File file = new File("conf.properties");
            FileOutputStream fOs = new FileOutputStream(file);
            props.store(fOs, "SMTP");
            fOs.close();
            Set<PosixFilePermission> set = new HashSet<>();
            set.add(PosixFilePermission.OWNER_READ);
            set.add(PosixFilePermission.OWNER_WRITE);
            Files.setPosixFilePermissions(Paths.get("conf.properties"), set);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char[] passReader() {
        Console con = System.console();
        if (con == null) {
            System.out.println("no console");
            System.exit(0);
        }
        System.out.println("Enter password: ");
        char[] pass = con.readPassword();
        return pass;
    }

    public String passChecker() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("mkjashdgf");
        Properties props = new EncryptableProperties(encryptor);
        try {
            props.load(new FileInputStream("conf.properties"));
            Session mailSession = Session.getDefaultInstance(props);

            Transport transport = null;
            transport = mailSession.getTransport("smtp");
            try {
                transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("username"), props.getProperty("password"));
            } catch (AuthenticationFailedException e) {
                return "Wrong password!";
            }
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Password OK!";
    }

}


