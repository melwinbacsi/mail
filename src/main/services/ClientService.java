package services;

import gui.Menu;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

public class ClientService implements Runnable {
    private Socket s = null;

    public ClientService(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            serverSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void serverSocket() throws IOException, ClassNotFoundException {
        ImageIcon im;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        String pass = null;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ois = new ObjectInputStream(s.getInputStream());
        pass = (String) ois.readObject();
        if (auth().equals(pass)) {
            System.out.println("remote client connected");
            while (MotionDetector.getPic() != null) {
                im = new ImageIcon(MotionDetector.getPic());
                try {
                    oos.writeUnshared(im);
                    oos.reset();
                } catch (SocketException se) {
                    System.out.println("remote client disconnected");
                    break;
                }
            }
            s.close();
        } else {
            System.out.println("wrong password");
            System.out.println(pass);
            oos.close();
            ois.close();
            s.close();
        }
    }

    private String auth() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("mkjashdgf");
        Properties props = new org.jasypt.properties.EncryptableProperties(encryptor);
        try {
            props.load(new java.io.FileInputStream("conf.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty("password");
    }
}