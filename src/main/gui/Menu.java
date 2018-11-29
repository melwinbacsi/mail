package gui;

import services.*;

import java.io.IOException;
import java.util.*;

public class Menu {
    private static int origoWeight;

    public void menu() {

        Scanner scanner = new Scanner(System.in);
        MailServices ms = new MailServices();
        Thread ps = new Thread(new PirSensor());
        ps.start();
        Thread ss = new Thread(new ServerService());
        ss.start();
        Thread md = new Thread(new MotionDetector());
        md.start();
        Thread lc = new Thread(new LoadCell());
        lc.start();
        int sw = 0;
        int aw = 0;
        while (true) {
            System.out.println("\np - set new password\nc - check password\nw - set weight\nr - read weight\ne - exit");
            char r = scanner.next().charAt(0);
            if (r == 'p' || r == 'c' || r == 'e' ||  r == 't' || r == 'w' || r == 'r') {
                switch (r) {
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
                        new Still();
                        break;
                    }
                    case 'w': {
                        aw = LoadCell.getWeight();
                        try {
                            WeightStore.setOrigoWeight(aw);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            WeightStore.setActualWeight(aw);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case 'r': {

                        try {
                            sw = WeightStore.readOrigoWeight();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        aw = LoadCell.getWeight();
                        System.out.println("A kezdő súly: " + sw + "g");
                        System.out.println("Az aktuális súly: " + aw + "g");
                        System.out.println("Teljes fogyás: " + (sw - aw) + "g");
                        break;
                    }
                    case 'e': {
                        if (!PirSensor.isPirStop()) {
                            PirSensor.setPirStop(true);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                            }
                        }
                        if (!MotionDetector.isMdStop()) {
                            MotionDetector.setMdStop(true);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                            }
                        }
                        System.exit(0);
                    }
                }
            }
        }
    }

}
