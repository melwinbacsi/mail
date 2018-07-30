package gui;

import services.MailServices;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame {

    public Gui() {
        new JFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void mainWindow() {
        setName("main");
        setSize(300, 300);
        JPanel gui = new JPanel(new GridLayout(0, 1, 15, 10));
        gui.setBorder(new EmptyBorder(20, 30, 20, 30));
        JButton s = new JButton("SEND MAIL");
        JButton p = new JButton("SET PASS");
        JButton c = new JButton("CHECK PASS");
        JButton e = new JButton("EXIT");
        MailServices ms = new MailServices();
        s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ms.mailSend();
            }
        });
        p.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new Gui().passWindow();

            }
        });
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ms.passChecker();
            }
        });
        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gui.add(s);
        gui.add(p);
        gui.add(c);
        gui.add(e);
        add(gui);
        setVisible(true);

    }

    public void passWindow() {
        setName("pass");
        setSize(300, 100);
        JPanel gui = new JPanel(new GridLayout(0, 1, 15, 10));
        gui.setBorder(new EmptyBorder(20, 30, 20, 30));
        JTextField t = new JTextField();
        gui.add(t);
        add(gui);
        setVisible(true);
    }
}
