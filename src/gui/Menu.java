/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import database.Batteries;
import gui.menubar.FileMenu;
import gui.menubar.HelpMenu;
import gui.menubar.ViewMenu;
import java.awt.Dimension;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Menu extends JMenuBar{
    private Batteries batteryList;
    private BatteryTable batteryTable;
    
    public Menu(Batteries batteryList,BatteryTable batteryTable) {
        this.batteryList = batteryList;
        this.batteryTable = batteryTable;
        JMenu file;
        JMenu view;
        JMenu help;
        
        file = new FileMenu(batteryList,batteryTable);
        this.add(file);
        
        view = new ViewMenu(batteryTable);
        this.add(view);
        
        help = new HelpMenu();
        this.add(help);
        
        //add additional menus here
        
    }
    
}
