package gui;

import java.awt.event.*;
import javax.swing.*;
import services.MailServices;
import services.MotionDetector;

public class Gui extends JFrame
{
  public Gui()
  {
    new JFrame();
  }
  
  public void mainWindow()
  {
    setDefaultCloseOperation(3);
    setName("main");
    setSize(300, 300);
    JPanel gui = new JPanel(new java.awt.GridLayout(0, 1, 15, 10));
    gui.setBorder(new javax.swing.border.EmptyBorder(20, 30, 20, 30));
    JButton m = new JButton("SET ALARM");
    JButton p = new JButton("SET PASS");
    JButton c = new JButton("CHECK PASS");
    JButton e = new JButton("EXIT");
    final MailServices ms = new MailServices();
    p.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        new Gui().passWindow();
      }
      
    });
    c.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        ms.passChecker();
      }
    });
    e.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    m.addActionListener(new ActionListener()
    {
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
  
  public void passWindow()
  {
    setDefaultCloseOperation(2);
    setName("pass");
    setSize(300, 200);
    JPanel gui = new JPanel(new java.awt.GridLayout(0, 1, 15, 10));
    gui.setBorder(new javax.swing.border.EmptyBorder(20, 30, 20, 30));
    javax.swing.JLabel l = new javax.swing.JLabel("New password:");
    javax.swing.JTextField t = new javax.swing.JTextField();
    JButton o = new JButton("OK");
    o.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e) {}

    });
    gui.add(l);
    gui.add(t);
    gui.add(o);
    add(gui);
    setVisible(true);
  }
}
