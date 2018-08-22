package services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class MailServices
{
  public MailServices() {}
  
  public void mailSend(String path)
  {
    String to = "314malna@gmail.com";
    String from = "314malna@gmail.com";
    String subject = "event detected";
    String messageText = "Zaba van!";
    
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword("mkjashdgf");
    Properties props = new org.jasypt.properties.EncryptableProperties(encryptor);
    try {
      props.load(new java.io.FileInputStream("conf.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Session mailSession = Session.getDefaultInstance(props);
    Message msg = new javax.mail.internet.MimeMessage(mailSession);
    try {
      msg.setFrom(new javax.mail.internet.InternetAddress(from));
      msg.setRecipient(javax.mail.Message.RecipientType.TO, new javax.mail.internet.InternetAddress(to));
      msg.setSubject(subject);
      Multipart multipart = new javax.mail.internet.MimeMultipart();
      
      MimeBodyPart textBodyPart = new MimeBodyPart();
      textBodyPart.setText(messageText);
      
      MimeBodyPart attachmentBodyPart = new MimeBodyPart();
      javax.activation.DataSource source = new javax.activation.FileDataSource(path);
      attachmentBodyPart.setDataHandler(new javax.activation.DataHandler(source));
      attachmentBodyPart.setFileName("alarm.jpg");
      
      multipart.addBodyPart(textBodyPart);
      multipart.addBodyPart(attachmentBodyPart);
      
      msg.setContent(multipart);
      

      Transport transport = mailSession.getTransport("smtp");
      try { transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("username"), props.getProperty("password"));
      } catch (AuthenticationFailedException e) {
        System.out.println("Wrong password!");
        return; }
      transport.sendMessage(msg, msg.getAllRecipients());
      transport.close();
      System.out.println("Message sent");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void createPropFile(char[] pass)
  {
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
      
      java.io.File file = new java.io.File("conf.properties");
      FileOutputStream fOs = new FileOutputStream(file);
      props.store(fOs, "SMTP");
      fOs.close();
      java.util.Set<java.nio.file.attribute.PosixFilePermission> set = new java.util.HashSet();
      set.add(java.nio.file.attribute.PosixFilePermission.OWNER_READ);
      set.add(java.nio.file.attribute.PosixFilePermission.OWNER_WRITE);
      java.nio.file.Files.setPosixFilePermissions(java.nio.file.Paths.get("conf.properties", new String[0]), set);
    } catch (java.io.FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public char[] passReader() {
    java.io.Console con = System.console();
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
    Properties props = new org.jasypt.properties.EncryptableProperties(encryptor);
    try {
      props.load(new java.io.FileInputStream("conf.properties"));
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
