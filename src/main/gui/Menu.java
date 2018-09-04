package gui;

import services.MailServices;
import services.MotionDetector;
import services.ServerService;
import java.util.Scanner;
import java.util.Set;

public class Menu {
    private static char r = 'z';

    public static char getR() {
        return r;
    }

    public void menu() {

        Scanner scanner = new Scanner(System.in);
        MailServices ms = new MailServices();
        Thread ss = null;
        Thread tm = null;

        while (r != 'g') {
            System.out.println("\na - start alarm\n\ns - stop alarm\ng - switch to GUI\np - set new password\nc - check password\ne - exit");
            r = scanner.next().charAt(0);
            if (r == 'g' || r == 'p' || r == 'c' || r == 'e' || r == 'a' || r == 's') {
                switch (r) {
                    case 'a': {
                        if (MotionDetector.isMdStop()) {
                            try {
                                MotionDetector.setMdStop(false);
                                tm = new Thread(new MotionDetector());
                                tm.start();
                                ss = new Thread(new ServerService());
                                ss.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case 's': {
                        if (!MotionDetector.isMdStop()) {
                                MotionDetector.setMdStop(true);
                            }

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
