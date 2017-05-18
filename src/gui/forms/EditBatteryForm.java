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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Ryan
 */
public class EditBatteryForm implements ActionListener{
    private Batteries batteryList;
    private Battery battery;
    private BatteryTable table;
    private JFrame frame = new JFrame("Edit Battery");
    private boolean isOpen = false;
    private JTextField name;
    private JTextField numCells;
    private JTextField capacity;
    private JSpinner typeSpinner;
    private JCheckBox isActive;
    
    private final static int minCells = 1;
    private final static int maxCells = 10;
    private final static int minCapacity = 100;
    private final static int maxCapacity = 20000;
    
    public EditBatteryForm(BatteryTable table, Battery battery, Batteries batteryList){
        this.batteryList = batteryList;
        this.battery = battery;
        this.table = table;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public void addComponentsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        //initialise text fields
        name =     new JTextField(battery.getName());
        name.setPreferredSize(new Dimension(20,100));
        numCells = new JTextField(String.valueOf(battery.getNumOfCells()));
        capacity = new JTextField(String.valueOf(battery.getStatedCapacity()));
        isActive = new JCheckBox();
        isActive.setSelected(battery.isActive());
        //initialise buttons
        JButton add = new JButton("Save");
        add.addActionListener(this);
        add.setActionCommand("save");
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setActionCommand("cancel");
        //spinner to select battery type
        SpinnerListModel typeModel = new SpinnerListModel(Battery.typeLabels);
        typeSpinner = new JSpinner(typeModel);
        //disable text editing
        if ( typeSpinner.getEditor() instanceof JSpinner.DefaultEditor ) {
            JSpinner.DefaultEditor editor = ( JSpinner.DefaultEditor ) typeSpinner.getEditor();
            editor.getTextField().setEnabled( true );
            editor.getTextField().setEditable( false );
        }
        
        switch(battery.getType()){
            case(Battery.LIPO):typeSpinner.setValue("LiPo"); break;
            case(Battery.LIFE):typeSpinner.setValue("LiFe"); break;
            case(Battery.NIMH):typeSpinner.setValue("NiMH"); break;
            default: typeSpinner.setValue("Other"); break;
        }        
        //set grid bag constrants
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.25;
        c.weightx = 0;
        c.ipadx = 5;
        c.ipady = 5;
        c.fill = GridBagConstraints.BOTH;
        pane.add(new JLabel("Battery Identifier:"),c);
        c.gridx = 1;
        c.weightx = 0.5;
        pane.add(name,c);
        
        c.gridx = 2;
        c.weightx = 0;
        pane.add(new JLabel("Number of Cells:"),c);
        c.gridx = 3;
        c.weightx = 0.5;
        pane.add(numCells,c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        pane.add(new JLabel("Capacity:"),c);
        c.gridx = 1;
        c.weightx = 0.5;
        pane.add(capacity,c);
        
        c.gridx = 2;
        c.weightx = 0;
        pane.add(new JLabel("Battery Type:"),c);
        c.gridx = 3;
        c.weightx = 0.5;
        pane.add(typeSpinner,c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;        
        pane.add(new JLabel("Active:"),c);
        c.gridx = 1;
        c.weightx = 0.5;
        pane.add(isActive,c);
        
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        pane.add(add,c);
        c.gridx = 2;
        pane.add(cancel,c);
	
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
        frame.setSize(450,150);
        frame.setVisible(true);
        isOpen = true;
    }
    
    public boolean isFrameOpen(){
        return isOpen;
    }
    
    public void makeVisible(Battery battery){
        this.battery = battery;
        name.setText(battery.getName());
        numCells.setText(String.valueOf(battery.getNumOfCells()));
        capacity.setText(String.valueOf(battery.getStatedCapacity()));
        isActive.setSelected(battery.isActive());
        switch(battery.getType()){
            case(Battery.LIPO):typeSpinner.setValue("LiPo"); break;
            case(Battery.LIFE):typeSpinner.setValue("LiFe"); break;
            case(Battery.NIMH):typeSpinner.setValue("NiMH"); break;
            default: typeSpinner.setValue("Other"); break;
        }
        
        frame.setVisible(true);
        isOpen = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "save":
                boolean checkNumCells = true;
                boolean checkCapacity = true;
                boolean checkName = true;
                int cells = 0;
                int cap = 0;
                
                //Check that the values of capacity and number of cells fall
                //into acceptable ranges and are integers
                try{
                    cells = Integer.parseInt(numCells.getText());
                    if((cells < minCells) | (cells > maxCells)){
                        checkNumCells = false;
                    }
                }catch(NumberFormatException ex){
                    checkNumCells = false;                   
                }
                
                try{
                    cap = Integer.parseInt(capacity.getText());
                    if((cap < minCapacity) | (cap > maxCapacity)){
                        checkCapacity = false;
                    }
                }catch(NumberFormatException ex){
                    checkCapacity = false;                   
                }
                
                //check the name is not empty
                if(name.getText().isEmpty()){
                    checkName = false;
                }
                //get the type of battery from the spinner
                int type;
                switch((String)typeSpinner.getModel().getValue()){
                    case("LiPo"):type = Battery.LIPO; break;
                    case("LiFe"):type = Battery.LIFE; break;
                    case("NiMH"):type = Battery.NIMH; break;
                    default:type = Battery.UNDEFINED; break;
                }
               
                //Display nessesary error messages for invalid inputs 
                //and create a new battery if 
                if (checkNumCells && checkCapacity && checkName){
                    battery.setName(name.getText());
                    battery.setNumOfCells(cells);
                    battery.setStatedCapacity(cap);
                    battery.setType(type);
                    battery.setActive(isActive.isSelected());
                    batteryList.updateAllXML();
                    table.updateTable();
                    frame.dispose();
                    isOpen = false;
                }else if(checkName == false){
                    JOptionPane.showMessageDialog(frame,
                        "Name must not be empty.",
                        "Name invalid",
                        JOptionPane.ERROR_MESSAGE);                   
                }else if(checkNumCells == false){
                    String message = String.format("Number of cells must be an integer between %d and %d.",minCells, maxCells);
                    JOptionPane.showMessageDialog(frame,
                        message,
                        "Number of cells invalid",
                        JOptionPane.ERROR_MESSAGE);
                }else if(checkCapacity == false){
                    String message = String.format("Capacity m ust be an integer between %d and %d.",minCapacity,maxCapacity);
                    JOptionPane.showMessageDialog(frame,
                        message,
                        "Capacity invalid",
                        JOptionPane.ERROR_MESSAGE);                    
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
