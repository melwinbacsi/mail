import java.io.Console;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;

public class MailServices {
    public void mailSend(String pass) {
        String host = "smtp.gmail.com";
        String user = "314malna";
        String to = "cyberserker@gmail.com";
        String from = "314malna@gmail.com";
        String subject = "event detected";
        String messageText = "test test test";
        boolean sessionDebug = false;

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");

        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(sessionDebug);
        Message msg = new MimeMessage(mailSession);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(to)));
            msg.setSubject(subject);
            msg.setText(messageText);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("Message sent.");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String passReader() {
        Console con = System.console();
        if (con == null) {
            System.out.println("no console");
            System.exit(0);
        }
        System.out.println("Enter password: ");
        char[] pass = con.readPassword();
        String password = String.valueOf(pass);
        System.out.println(password);
        return password;
    }

}
