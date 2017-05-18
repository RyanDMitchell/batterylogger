/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.Battery;
import database.Batteries;
import gui.comments.AddCommentForm;
import gui.comments.CommentScrollPane;
import gui.forms.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.*;


/**
 *
 * @author jrg75
 */
public class InfoPane extends JPanel implements ListSelectionListener, ActionListener{
    public final static int WIDTH = 320;
    
    private BatteryTable table;
    private ChargeTable chargeTable;
    private Batteries batteries;
    
    AddChargeForm addChargeForm;
    EditBatteryForm editBatteryForm;
    AddCommentForm addCommentForm;
    CapacityChartForm capChartForm;
    ResistanceChartForm resChartForm;
    CommentScrollPane commentPane = new CommentScrollPane();
    
    JLabel nameInfo = new JLabel();
    JLabel chargeNumInfo = new JLabel();
    JLabel numCellInfo = new JLabel();
    JLabel typeInfo = new JLabel();
    JLabel sinceServInfo = new JLabel();
    JLabel dateNewInfo = new JLabel();
    JLabel ageInfo = new JLabel();
    JLabel capacityInfo = new JLabel();
    
    public InfoPane(BatteryTable table, ChargeTable chargeTable, Batteries batteries) {
        this.batteries = batteries;
        this.chargeTable = chargeTable;
        this.table = table;
        this.table.getSelectionModel().addListSelectionListener(this);
        
        JLabel label = new JLabel("Battery Info");
        JLabel nameLabel = new JLabel("Name: ");
        JLabel chargeNumLabel = new JLabel("Number of Charges: ");
        JLabel capacityLabel = new JLabel("Capacity: ");
        JLabel numCellLabel = new JLabel("Cell Count: ");
        JLabel typeLabel = new JLabel("Batt Type: ");
        JLabel sinceServLabel = new JLabel("Since Service: ");
        JLabel dateNewLabel = new JLabel("Date New: ");
        JLabel ageLabel = new JLabel("Age: ");
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        this.add(label,c);
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        //Add labels
        this.add(nameLabel,c);
        c.gridy = 2;
        this.add(typeLabel,c);
        c.gridy = 3;
        this.add(capacityLabel,c);
        c.gridy = 4;
        this.add(numCellLabel,c);
        c.gridy = 5;
        this.add(chargeNumLabel,c);
        c.gridy = 6;
        this.add(sinceServLabel,c);
        c.gridy = 7;
        this.add(dateNewLabel,c);
        c.gridy = 8;
        this.add(ageLabel,c);
        //Add info panes
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 1;
        this.add(nameInfo,c);
        c.gridy = 2;
        this.add(typeInfo,c);
        c.gridy = 3;
        this.add(capacityInfo,c);
        c.gridy = 4;
        this.add(numCellInfo,c);
        c.gridy = 5;
        this.add(chargeNumInfo,c);
        c.gridy = 6;
        this.add(sinceServInfo,c);
        c.gridy = 7;
        this.add(dateNewInfo,c);
        c.gridy = 8;
        this.add(ageInfo,c);
        //Add buttons
        JButton edit = new JButton("Edit Battery");
        edit.addActionListener(this);
        edit.setActionCommand("edit");
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        this.add(edit,c);
        
        JButton charge = new JButton("Charge Battery");
        charge.addActionListener(this);
        charge.setActionCommand("charge");
        c.gridy = 10;
        this.add(charge,c);
        
        JButton addComment = new JButton("Add Comment");
        addComment.addActionListener(this);
        addComment.setActionCommand("comment");
        c.gridy = 11;
        this.add(addComment,c);
        
        JButton deleteCharge = new JButton("Delete Charge");
        deleteCharge.addActionListener(this);
        deleteCharge.setActionCommand("deleteCharge");
        c.gridy = 12;
        this.add(deleteCharge,c);       
        
        JButton capChart = new JButton("Battery Capacity Chart");
        capChart.addActionListener(this);
        capChart.setActionCommand("capchart");
        c.gridy = 13;
        this.add(capChart,c);
        
        JButton resChart = new JButton("Battery Resistance Chart");
        resChart.addActionListener(this);
        resChart.setActionCommand("reschart");
        c.gridy = 14;
        this.add(resChart,c);
        
        c.weighty = 1.0;
        c.gridy = 15;
        JScrollPane scrollPane = new JScrollPane(commentPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane,c);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        //get the id of the currently selected battery
        int ID = -1;
        if (table.getSelectedRow() != -1){
            ID = (int)table.getValueAt(table.getSelectedRow(), 0);
        }
        if (ID != -1){
            Battery tempBat = this.batteries.getBattery(ID);
            chargeTable.updateTable(tempBat);
            Date tempDate = tempBat.getDateCommissioned();
            nameInfo.setText(tempBat.getName());
            numCellInfo.setText(Integer.toString(tempBat.getNumOfCells()));
            capacityInfo.setText(Integer.toString(tempBat.getStatedCapacity()));
            chargeNumInfo.setText(Integer.toString(tempBat.getChargeCount()));
            sinceServInfo.setText(Integer.toString(tempBat.getSinceSerivceChargeCount()));
            SimpleDateFormat ft =  new SimpleDateFormat ("dd/MM/yyyy");
            dateNewInfo.setText(ft.format(tempDate));
            ageInfo.setText(Integer.toString((int)tempBat.getBatteryAgeDays()));
            commentPane.update(tempBat);
            switch(tempBat.getType()){
                case Battery.LIPO:
                    typeInfo.setText("LiPo");
                    break;
                case Battery.LIFE:
                    typeInfo.setText("LiFePO4");
                    break;
                case Battery.NIMH:
                    typeInfo.setText("NiMH");
                    break;
                case Battery.UNDEFINED:
                    typeInfo.setText("Other");
                    break;
                default:
                    typeInfo.setText("Other");
                    break;
            }
        }else{
            nameInfo.setText("");
            numCellInfo.setText("");
            capacityInfo.setText("");
            chargeNumInfo.setText("");
            sinceServInfo.setText("");
            dateNewInfo.setText("");
            ageInfo.setText("");
            typeInfo.setText("");  
            commentPane.update((Battery)null);   
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //get the id of the currently selected battery
        int ID = -1;
        if (table.getSelectedRow() != -1){
            ID = (int)table.getValueAt(table.getSelectedRow(), 0);
        }
        switch(e.getActionCommand()){
            case "comment":
                if(ID<0){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                        "No Battery Selected",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }else{
                    Battery tempBat = this.batteries.getBattery(ID);
                    if(addCommentForm == null){
                        addCommentForm = new AddCommentForm(tempBat,commentPane,table,batteries);
                    }else{
                        if(addCommentForm.isFrameOpen()){
                            //already open
                        }else{
                            addCommentForm.makeVisible(tempBat);
                        }
                    }
                }
                break;
            case "edit":
                if(ID<0){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                        "No Battery Selected",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }else{
                    Battery tempBat = this.batteries.getBattery(ID);
                    if(editBatteryForm == null){
                        editBatteryForm = new EditBatteryForm(table,tempBat,batteries);
                    }else{
                        if(editBatteryForm.isFrameOpen()){
                            //already open
                        }else{
                            editBatteryForm.makeVisible(tempBat);
                        }
                    }
                }
                break;
                
            case "charge":
                if(ID<0){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                        "No Battery Selected",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }else{
                    Battery tempBat = this.batteries.getBattery(ID);
                    if(addChargeForm == null){
                        addChargeForm = new AddChargeForm(table,chargeTable,tempBat,batteries);
                    }else{
                        if(addChargeForm.isFrameOpen()){
                            //already open
                        }else{
                            addChargeForm.makeVisible(tempBat);
                        }
                    }
                }
                break;
            case "deleteCharge":
                if(ID<0){
                    JFrame frame1 = new JFrame();
                    JOptionPane.showMessageDialog(frame1,
                        "No Battery Selected",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }else if(chargeTable.getSelectedRow() == -1){
                    JFrame frame2 = new JFrame();
                    JOptionPane.showMessageDialog(frame2,
                        "No Charge Selected",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);                    
                }else{
                    Battery tempBat = this.batteries.getBattery(ID);                    
                    JFrame frame3 = new JFrame();
                    int n = JOptionPane.showConfirmDialog(
                        frame3,
                        "Are you sure you wish to delete the selected charge?",
                        "Confirm deletion",
                        JOptionPane.YES_NO_OPTION);
                    if(n == JOptionPane.YES_OPTION){
                        tempBat.removeCharge(chargeTable.getSelectedRow());
                        chargeTable.updateTable(tempBat);
                        
                    }
                }
            break;
            case "capchart":
                if(ID<0){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                        "No Battery Selected",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }else{
                    Battery tempBat = this.batteries.getBattery(ID);
                    if (tempBat.getServiceChargeCount()>0){
                        capChartForm = new CapacityChartForm(tempBat,batteries);
                    }else{
                        JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                        "Selected Battery has no Service Charges",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                    }
                    
                }
                break;
                case "reschart":
                if(ID<0){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                        "No Battery Selected",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }else{
                    Battery tempBat = this.batteries.getBattery(ID);
                    if (tempBat.getServiceChargeCount()>0){
                        resChartForm = new ResistanceChartForm(tempBat,batteries);
                    }else{
                        JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                        "Selected Battery has no Service Charges",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                    }
                    
                }
                break;
            default:
                break;
                
        }
    }
}
