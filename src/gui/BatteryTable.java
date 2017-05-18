/*
 *  ----------------------------------------------------------------------------
 *  "THE BEER-WARE LICENSE":
 * Joshua Gibson and Ryan Mitchell wrote this file. As long as you retain this 
 * notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy us a beer.
 *  ----------------------------------------------------------------------------
 */

package gui;

import data.Battery;
import database.BatteryAddedListener;
import database.Batteries;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Ryan
 */
public class BatteryTable extends JTable implements BatteryAddedListener{
    private BatteryTableModel tableModel;
    public BatteryTable(Batteries batteryList){
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableModel = new BatteryTableModel(batteryList);
        this.setModel(tableModel);
        this.setAutoCreateRowSorter(true);
        this.getTableHeader().setReorderingAllowed(false);
        
        //set the column sizes
        for(int i = 0; i < tableModel.getColumnCount();i++){
            this.getColumnModel().getColumn(i).setPreferredWidth(Battery.FieldDisplayWidths[i]);
            
        }     
    }

    @Override
    public void batteryAdded() {
        this.updateTable();
    } 
    
    public void updateTable(){
        tableModel.fireTableDataChanged();
         //get the column sizes for formatting
        //for(int i = 0; i < tableModel.getColumnCount();i++){
            //System.out.print(this.getColumnModel().getColumn(i).getWidth() + " ");            
        //}     
        //System.out.println();
    }
    
    public void setHideInactive(boolean isHide){
        tableModel.setHideInactive(isHide);
    }
    
    public void setShowTwoCell(boolean isShow){
        tableModel.setShowTwoCell(isShow);
    }
        
    public void setShowThreeCell(boolean isShow){
        tableModel.setShowThreeCell(isShow);
    }
            
    public void setShowLipo(boolean isShow){
        tableModel.setShowLipo(isShow);
    }
                
    public void setShowLife(boolean isShow){
        tableModel.setShowLife(isShow);
    }
                    
    public void setShowHiHM(boolean isShow){
        tableModel.setShowHiHM(isShow);
    }
    
    public void setShowOther(boolean isShow){
        tableModel.setShowOther(isShow);
    }
}
