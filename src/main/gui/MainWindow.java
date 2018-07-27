package gui;

import services.MailServices;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    private JPanel panel1;
    private JButton sendButton;
    private JButton modifyButton;
    private JButton passButton;
    private JButton newPassButton;

    public MainWindow() {
        newPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MailServices ms = new MailServices();
                ms.createPropFile();
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MailServices ms = new MailServices();
                ms.mailSend();
            }
        });
    }
}
