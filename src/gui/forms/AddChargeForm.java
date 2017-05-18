/*
 *  ----------------------------------------------------------------------------
 *  "THE BEER-WARE LICENSE":
 * Joshua Gibson and Ryan Mitchell wrote this file. As long as you retain this 
 * notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy us a beer.
 *  ----------------------------------------------------------------------------
 */

package gui.forms;

import data.Battery;
import database.Batteries;
import gui.BatteryTable;
import gui.ChargeTable;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Ryan
 */
public class AddChargeForm implements ActionListener{
    private final static double minResistance = 0.1;
    private final static double maxResistance = 100;
    private final static int minCapacity = 100;
    private final static int maxCapacity = 20000;
    private Battery battery;
    private Batteries batteries;
    private BatteryTable table;
    private ChargeTable chargeTable;
    private JFrame frame = new JFrame("Add Charge to battery");
    private boolean isOpen = false;
    private JTextField resistance = new JTextField();
    private JTextField capacity = new JTextField();
    private JLabel batteryName = new JLabel("Name: ");
    private JCheckBox serCharge = new JCheckBox("Service Charge");
    private JLabel resistanceLabel = new JLabel("Resistance: ");
    private JLabel capacityLabel = new JLabel("Capacity: ");
    
    public AddChargeForm(BatteryTable table,ChargeTable chargeTable,Battery battery,Batteries batteries){
        this.battery = battery;
        this.chargeTable = chargeTable;
        this.batteries = batteries;
        this.table = table;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public void addComponentsToPane(Container pane) {
        
        JButton charge = new JButton("Charge");
        charge.addActionListener(this);
        charge.setActionCommand("charge");
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setActionCommand("cancel");
        serCharge.addActionListener(this);
        serCharge.setActionCommand("service");
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        batteryName.setText("Name: "+battery.getName());
        pane.add(batteryName,c);
        c.gridx = 2;
        pane.add(serCharge,c);
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(resistanceLabel,c);
        c.gridx = 2;
        pane.add(capacityLabel,c);
        c.weightx = 0.5;
        c.gridx = 1;
        pane.add(resistance,c);
        c.gridx = 3;
        pane.add(capacity,c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(charge,c);
        c.gridx = 2;
        pane.add(cancel,c);
        
        resistanceLabel.setVisible(false);
        capacityLabel.setVisible(false);
        resistance.setVisible(false);
        capacity.setVisible(false);
    }
    
    private void createAndShowGUI() {
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
               isOpen = false;
               frame.setVisible(false);
               frame.dispose();
            }
        });
        //Display the window.
        frame.setSize(300,100);
        frame.setVisible(true);
        isOpen = true;
    }
    
    public boolean isFrameOpen(){
        return isOpen;
    }
    
    public void makeVisible(Battery battery){
        this.battery = battery;
        frame.setVisible(true);
        batteryName.setText("Name: "+battery.getName());
        isOpen = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "charge":
                if (serCharge.isSelected()){
                    double resistance = 0;
                    double capacity = 0;
                    boolean checkResistance = true;
                    boolean checkCapacity = true;
                    try{
                        resistance = Double.parseDouble(this.resistance.getText());
                        if((resistance < minResistance) | (resistance > maxResistance)){
                            checkResistance = false;
                        }
                    }catch(NumberFormatException ex){
                        checkResistance = false;                   
                    }
                    try{
                        capacity = (double)Integer.parseInt(this.capacity.getText());
                        if((capacity < minCapacity) | (capacity > maxCapacity)){
                            checkCapacity = false;
                        }
                    }catch(NumberFormatException ex){
                        checkCapacity = false;                   
                    }
                    if(checkResistance && checkCapacity){
                        battery.addServiceCharge(resistance,capacity);
                        batteries.updateBatteryXML(battery.getID());
                        int selectedBatteryIndex = table.getSelectedRow();
                        table.updateTable();
                        table.setRowSelectionInterval(selectedBatteryIndex, selectedBatteryIndex);
                        int selectedChargeIndex = chargeTable.getSelectedRow();
                        chargeTable.updateTable(battery);
                        if(selectedChargeIndex != -1){
                            chargeTable.setRowSelectionInterval(selectedChargeIndex, selectedChargeIndex);
                        }
                        frame.dispose();
                        isOpen = false;
                    }else if(checkResistance == false){
                        String message = String.format("Resistance must be an value between %f and %f.",minResistance,maxResistance);
                        JOptionPane.showMessageDialog(frame,
                            message,
                            "Capacity invalid",
                            JOptionPane.ERROR_MESSAGE); 
                    }else if(checkCapacity == false){
                        String message = String.format("Capacity must be an integer between %d and %d.",minCapacity,maxCapacity);
                        JOptionPane.showMessageDialog(frame,
                            message,
                            "Capacity invalid",
                            JOptionPane.ERROR_MESSAGE);  
                    }
                }else{
                    battery.addStandardCharge();
                    batteries.updateBatteryXML(battery.getID());
                    int selectedBatteryIndex = table.getSelectedRow();
                    table.updateTable();
                    table.setRowSelectionInterval(selectedBatteryIndex, selectedBatteryIndex);
                    int selectedChargeIndex = chargeTable.getSelectedRow();
                    chargeTable.updateTable(battery);
                    if(selectedChargeIndex != -1){
                        chargeTable.setRowSelectionInterval(selectedChargeIndex, selectedChargeIndex);
                    }
                    frame.dispose();
                    isOpen = false;
                }
                break;
            case "service":
                if (serCharge.isSelected()){
                    resistanceLabel.setVisible(true);
                    capacityLabel.setVisible(true);
                    resistance.setVisible(true);
                    capacity.setVisible(true);
                }else{
                    resistanceLabel.setVisible(false);
                    capacityLabel.setVisible(false);
                    resistance.setVisible(false);
                    capacity.setVisible(false);
                }
                break;
            case "cancel":
                frame.dispose();
                isOpen = false;
                break;
            default:
                break;
        }
    }
}
