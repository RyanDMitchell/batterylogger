/*
 *  ----------------------------------------------------------------------------
 *  "THE BEER-WARE LICENSE":
 * Joshua Gibson and Ryan Mitchell wrote this file. As long as you retain this 
 * notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy us a beer.
 *  ----------------------------------------------------------------------------
 */

package gui.menubar;

import database.Batteries;
import gui.BatteryTable;
import gui.forms.NewBatteryForm;
import gui.forms.StatsForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author presenter
 */
public class FileMenu extends JMenu implements ActionListener{
    private Batteries batteryList;
    private NewBatteryForm newBattForm;
    private BatteryTable table;

    
    public FileMenu(Batteries batteryList, BatteryTable table){
        
        this.table = table;
        this.batteryList = batteryList;
        this.setText("File");
        this.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem newBattery = new JMenuItem("New Battery",KeyEvent.VK_N);
        newBattery.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newBattery.addActionListener(this);
        newBattery.setActionCommand("newBattery");
        this.add(newBattery);
        
        JMenuItem stats = new JMenuItem("Stats",KeyEvent.VK_S);
        stats.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        stats.addActionListener(this);
        stats.setActionCommand("stats");
        this.add(stats);

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "stats":
                StatsForm statsForm = new StatsForm(batteryList);
                break;
            case "newBattery":
                if(newBattForm == null){
                    newBattForm = new NewBatteryForm(batteryList,table);
                }else{
                    if(newBattForm.isFrameOpen()){
                        //already open
                    }else{
                        newBattForm.makeVisible();
                    }
                }
                break;
            default:
                break;
        }
    }
}
