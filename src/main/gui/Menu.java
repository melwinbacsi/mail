package gui;

import services.MailServices;

public class Menu
{
  public Menu() {}
  
  public void menu()
  {
    char r = 'z';
    java.util.Scanner scanner = new java.util.Scanner(System.in);
    MailServices ms = new MailServices();
    Thread tm = null;
    
    while (r != 'g') {
      System.out.println("\nm - set alarm\ng - switch to GUI\ns - stop alarm\np - set new password\nc - check password\ne - exit");
      r = scanner.next().charAt(0);
      if ((r == 'g') || (r == 'p') || (r == 'c') || (r == 'e') || (r == 'm') || (r == 's')) {
        switch (r) {
        case 'm': 
          try {
            services.MotionDetector md = new services.MotionDetector();
            tm = new Thread(md);
            tm.start();
          } catch (Exception e) {
            e.printStackTrace();
          }
        

        case 's': 
          tm.interrupt();
          break;
        
        case 'g': 
          new Gui().mainWindow();
          break;
        
        case 'p': 
          ms.createPropFile(ms.passReader());
          break;
        
        case 'c': 
          String check = ms.passChecker();
          System.out.println(check);
          break;
        
        case 'e': 
          System.exit(0);
        }
      }
    }
  }
}
