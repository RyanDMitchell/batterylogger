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
public class NewBatteryForm implements ActionListener{
    private Batteries batteryList;
    private JFrame frame = new JFrame("Add new Battery");
    private boolean isOpen = false;
    private JTextField name;
    private JTextField numCells;
    private JTextField capacity;
    private JSpinner typeSpinner;
    private BatteryTable table;
    
    private final static int minCells = 1;
    private final static int maxCells = 10;
    private final static int minCapacity = 100;
    private final static int maxCapacity = 20000;

    private final static String defaultNumCells =   "2";
    private final static String defaultName =       "b1n2c2c4400";
    private final static String defaultCapacity =   "4400";
    
    public NewBatteryForm(Batteries batteryList,BatteryTable table){
        this.batteryList = batteryList;
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
        name =     new JTextField(defaultName);
        name.setPreferredSize(new Dimension(20,100));
        numCells = new JTextField(defaultNumCells);
        capacity = new JTextField(defaultCapacity);
        //initialise buttons
        JButton add = new JButton("Add");
        add.addActionListener(this);
        add.setActionCommand("add");
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
        
        //Add the gui element to the pane
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
    
    public void makeVisible(){
        frame.setVisible(true);
        isOpen = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "add":
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
                    //add a new battery to the battery list
                    batteryList.addBattery(name.getText(), cells, cap,type);
                    table.updateTable();
                    frame.dispose();
                    frame.setVisible(false);
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
                    String message = String.format("Capacity must be an integer between %d and %d.",minCapacity,maxCapacity);
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
