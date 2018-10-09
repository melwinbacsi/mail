package gui;

import services.*;

import java.io.IOException;
import java.util.*;

public class Menu {
    private static int origoWeight;

    public void menu() {

        Scanner scanner = new Scanner(System.in);
        MailServices ms = new MailServices();
        Thread md, ps;
        Thread ss = new Thread(new ServerService());
        ss.start();
        Thread lc = new Thread(new LoadCell());
        lc.start();
        int sw = 0;
        int aw =0;
        while (true) {
            System.out.println("\na - start alarm\ns - stop alarm\np - set new password\nc - check password\nw - set weight\nr - read weight\ne - exit");
            char r = scanner.next().charAt(0);
            if (r == 'p' || r == 'c' || r == 'e' || r == 'a' || r == 's' || r == 't' || r == 'w'|| r == 'r') {
                switch (r) {
                    case 'a': {
                        if (PirSensor.isPirStop()) {
                            try {
                                PirSensor.setPirStop(false);
                                ps = new Thread(new PirSensor());
                                ps.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(MotionDetector.isMdStop()){
                            try {
                                MotionDetector.setMdStop(false);
                                md = new Thread(new MotionDetector());
                                md.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case 's': {
                        if (!PirSensor.isPirStop()) {
                            PirSensor.setPirStop(true);
                            MotionDetector.setMdStop(true);
                        }
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
                        try {
                            aw = WeightStore.readActualWeight();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("A kezdő súly: " + sw+"g");
                        System.out.println("Az aktuális súly: " + aw+"g");
                        System.out.println("Teljes fogyás: " + (sw-aw) + "g");
                        break;
                    }
                    case 'e': {
                        if(!PirSensor.isPirStop()){
                            PirSensor.setPirStop(true);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!MotionDetector.isMdStop()){
                            MotionDetector.setMdStop(true);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.exit(0);
                    }
                }
            }
        }
    }

}
