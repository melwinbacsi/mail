package services;

import gui.Menu;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import javax.imageio.ImageIO;
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

    /*   private void serverSocket() throws IOException, ClassNotFoundException {
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
               while (!MotionDetector.isMdStop()) {
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
   */
    private void serverSocket() throws IOException, ClassNotFoundException {
        DataOutputStream dos = null;
        DataInputStream dis = null;
        String pass = null;
        dos = new DataOutputStream(s.getOutputStream());
        dis = new DataInputStream(s.getInputStream());
        String req = dis.readUTF();
        String menuOrder = null;
        if (req.equals("login")) {
            pass = dis.readUTF();
            if (auth().equals(pass)) {
                System.out.println("remote client connected");
                dos.write(1);
            } else {
                dos.write(0);
            }
            if (MotionDetector.isMdStop()) {
                dos.write(0);
            } else {
                dos.write(1);
            }
            dos.close();
            dis.close();
            s.close();
        }
        if (req.equals("menu")) {
            menuOrder = dis.readUTF();
            if (menuOrder.equals("start")) {
                if (MotionDetector.isMdStop()) {
                    try {
                        MotionDetector.setMdStop(false);
                        Thread tm = new Thread(new MotionDetector());
                        tm.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Server started");
                }
                if (MotionDetector.isMdStop()) {
                    dos.write(0);
                } else {
                    dos.write(1);
                }
            }
            if (menuOrder.equals("stop")) {
                if (!MotionDetector.isMdStop()) {
                    MotionDetector.setMdStop(true);
                    System.out.println("Server stopped");
                }
                if (MotionDetector.isMdStop()) {
                    dos.write(0);
                } else {
                    dos.write(1);
                }
            }
            if (menuOrder.equals("screen")){
                ImageIO.write(MotionDetector.getPic(), "jpg", new File("/home/pi/apache-tomcat-8.5.34/webapps/WebClient/screen.jpg"));
            }
            dos.close();
            dis.close();
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