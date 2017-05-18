/*
 *  ----------------------------------------------------------------------------
 *  "THE BEER-WARE LICENSE":
 * Joshua Gibson and Ryan Mitchell wrote this file. As long as you retain this 
 * notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy us a beer.
 *  ----------------------------------------------------------------------------
 */

package gui.menubar;

import gui.BatteryTable;
import gui.EasterEgg;
import gui.forms.HelpForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author presenter
 */
public class HelpMenu extends JMenu implements ActionListener{   
    private JMenuItem helpMenu;
        public HelpMenu(){
        this.setText("Help");
        this.setMnemonic(KeyEvent.VK_H);
        
        helpMenu = new JMenuItem("Help");
        helpMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        helpMenu.addActionListener(this);
        helpMenu.setActionCommand("help");
        this.add(helpMenu);

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {                
             
        switch(e.getActionCommand()){            
            case "help":              
                new HelpForm();           
                break;
            default:
                break;
        }
    }
}
