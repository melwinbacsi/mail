package services;

import gui.Menu;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerService implements Runnable {

    @Override
    public void run() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(55555);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                new Thread(new ClientService(ss.accept())).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
