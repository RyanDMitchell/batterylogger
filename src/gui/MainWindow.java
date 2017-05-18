package gui;

import database.Batteries;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;


/**
 *
 * @author jrg75
 */
public class MainWindow extends JFrame implements ComponentListener{
    public final static int MINWIDTH = 1000;
    public final static int MINHEIGHT = 700;
    private Batteries batteryList;
    private JSplitPane splitPane;
    public MainWindow(Batteries batteryList){
        this.batteryList = batteryList;
        //create required BatteryTable objects
        BatteryTable table = new BatteryTable(batteryList);
        batteryList.addBatteryAddedListener(table);
        JScrollPane tableScroller = new JScrollPane(table);
        this.setPreferredSize(new Dimension(1000, 700));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create required ChargeTable objects        
        ChargeTable chargeTable = new ChargeTable();
        batteryList.addChargeAddedListener(chargeTable);
        JScrollPane chargeTableScroller = new JScrollPane(chargeTable);
        //create the infopane
        InfoPane info = new InfoPane(table,chargeTable,batteryList);
        info.setMinimumSize(new Dimension(InfoPane.WIDTH,100));
        //Create and set the menu 
        Menu menu = new Menu(batteryList,table);
        this.setJMenuBar(menu);
        //Setup layout/behaviour
        this.setTitle("Battery Manager");
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setLayout(new GridLayout(1,1));
        JSplitPane splitPaneTables = new JSplitPane(JSplitPane.VERTICAL_SPLIT,tableScroller, chargeTableScroller);
        splitPaneTables.setOneTouchExpandable(true);
        splitPaneTables.setDividerLocation(400);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitPaneTables, info);
        splitPane.setOneTouchExpandable(true);
        //splitPane.setDividerLocation(400);
        this.add(splitPane);
        
        //display it
        this.pack();
        this.setVisible(true);
        splitPane.setDividerLocation(splitPane.getMaximumDividerLocation());
        this.addComponentListener(this);
    }
    @Override
    public void componentResized(ComponentEvent e) {
        splitPane.setDividerLocation(splitPane.getMaximumDividerLocation());
    }
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
}
