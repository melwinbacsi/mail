package gui;

import services.MailServices;
import services.MotionDetector;
import services.ServerService;
import services.Still;

import java.util.*;

public class Menu {
    private static char r = 'z';

    public void menu() {

        Scanner scanner = new Scanner(System.in);
        MailServices ms = new MailServices();
        Thread tm;
        Thread ss = new Thread(new ServerService());
        ss.start();
        while (r != 'g') {
            System.out.println("\na - start alarm\ns - stop alarm\ng - switch to GUI\np - set new password\nc - check password\ne - exit");
            r = scanner.next().charAt(0);
            if (r == 'g' || r == 'p' || r == 'c' || r == 'e' || r == 'a' || r == 's' || r == 't') {
                switch (r) {
                    case 'a': {
                        if (MotionDetector.isMdStop()) {
                            try {
                                MotionDetector.setMdStop(false);
                                tm = new Thread(new MotionDetector());
                                tm.start();
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
                    case 't': {
                    Thread st = new Thread(new Still(true));
                    st.start();
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
