package gui;

import services.MailServices;
import services.MotionDetector;
import services.ServerService;
import java.util.Scanner;

public class Menu {
    private static char r = 'z';

    public static char getR() {
        return r;
    }

    public void menu(){

        boolean isRunning = false;
        Scanner scanner = new Scanner(System.in);
        MailServices ms = new MailServices();
        Thread ss = new Thread(new ServerService());
        ss.start();

        while (r != 'g') {
            System.out.println("\nm - set alarm\ng - switch to GUI\np - set new password\nc - check password\ne - exit");
            r = scanner.next().charAt(0);
            if (r == 'g' || r == 'p' || r == 'c' || r == 'e' || r == 'm') {
                switch (r) {
                    case 'm': {
                        if(!isRunning){
                        try {
                            isRunning=true;
                            MotionDetector md = new MotionDetector();
                            Thread tm = new Thread(md);
                            tm.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }}
                        break;
                    }
                    case 'g': {
                        new Gui().mainWindow();
                        break;
                    }
                    case 'p': {
                        ms.createPropFile(ms.passReader());
                        break;
                    }
                    case 'c': {
                        String check = ms.passChecker();
                        System.out.println(check);
                        break;
                    }
                    case 'e': {
                        System.exit(0);
                    }
                }
            }
        }
    }
}
