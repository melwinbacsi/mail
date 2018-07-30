import services.MailServices;
import gui.Gui;
import java.util.Scanner;


public class Pi {
    public static void main(String[] args) {
        char r = 'z';
        Scanner scanner = new Scanner(System.in);
        MailServices ms = new MailServices();
        while (r!='g') {
            System.out.println("\ng - switch to GUI\ns - send e-mail\np - set new password\nc - check password\ne - exit");
            r = scanner.next().charAt(0);
            if (r == 'g' || r == 's' || r == 'p' || r == 'c' || r == 'e') {
                switch (r) {
                    case 'g': {
                        new Gui().mainWindow();
                        break;
                    }
                    case 's': {
                        ms.mailSend();
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


//
//        if(r==)
//        MailServices ms = new MailServices();
//        ms.mailSend();
        }
    }
}
