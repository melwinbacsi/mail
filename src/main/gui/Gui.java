package gui;

import services.MailServices;
import services.MotionDetector;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame {

    public Gui() {
        new JFrame();

    }

    public void mainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setName("main");
        setSize(300, 300);
        JPanel gui = new JPanel(new GridLayout(0, 1, 15, 10));
        gui.setBorder(new EmptyBorder(20, 30, 20, 30));
        JButton m = new JButton("SET ALARM");
        JButton p = new JButton("SET PASS");
        JButton c = new JButton("CHECK PASS");
        JButton e = new JButton("EXIT");
        MailServices ms = new MailServices();
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
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MotionDetector().motionDetect();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        gui.add(m);
        gui.add(p);
        gui.add(c);
        gui.add(e);
        add(gui);
        setVisible(true);

    }

    public void passWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setName("pass");
        setSize(300, 200);
        JPanel gui = new JPanel(new GridLayout(0, 1, 15, 10));
        gui.setBorder(new EmptyBorder(20, 30, 20, 30));
        JLabel l = new JLabel("New password:");
        JTextField t = new JTextField();
        JButton o = new JButton("OK");
        o.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        gui.add(l);
        gui.add(t);
        gui.add(o);
        add(gui);
        setVisible(true);
    }
}
