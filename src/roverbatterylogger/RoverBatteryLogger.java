package roverbatterylogger;

import database.Batteries;
import gui.EasterEgg;
import gui.MainWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Ryan
 */
public class RoverBatteryLogger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            String xmlDir = "./data";
            Batteries batteries = new Batteries(xmlDir);
            MainWindow frame = new MainWindow(batteries);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoverBatteryLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(RoverBatteryLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RoverBatteryLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(RoverBatteryLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
