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
import database.Batteries;
import javax.swing.table.*;

/**
 *
 * @author Ryan
 */
public class BatteryTableModel extends AbstractTableModel {
    private Batteries batteryList;
    private String[] columnNames;
    private int numColumns;
    private int numBatteries;
    private boolean hideInactive;
    private boolean showTwoCell;
    private boolean showThreeCell;
    private boolean showLipo;
    private boolean showLife;
    private boolean showHiHM;
    private boolean showOther;
    
    public BatteryTableModel(Batteries batteryList) {
        this.batteryList = batteryList;
        columnNames = Battery.FieldNames;
        numColumns = columnNames.length;
        numBatteries = batteryList.getNumberOfBatteries();
        hideInactive = true;
    }
    
    @Override
    public int getRowCount(){ 
        numBatteries = countFilteredBatteries();
        return numBatteries;
    }

    @Override
    public int getColumnCount() {
        return  numColumns;
    }

    @Override
    public String getColumnName(int col) {
        return  columnNames[col];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object ret = "Error";
        int[] ids = batteryList.getBatteryIDs();
        int count = 0;     
        int index = -1;
        //iterate over the batteries counting the ones
        //which match the active filters
        for(int i = 0; i < batteryList.getNumberOfBatteries(); i++){
            Battery temp = batteryList.getBattery(ids[i]);
            //check each of the filters, if none of them 
            //eliminate the battery then count it
            if(hideInactive && !temp.isActive()){                
            }else if(showTwoCell && temp.getNumOfCells() != 2){                
            }else if(showThreeCell && temp.getNumOfCells() != 3){                
            }else if(showLipo && temp.getType() != Battery.LIPO){                
            }else if(showLife && temp.getType() != Battery.LIFE){                
            }else if(showHiHM && temp.getType() != Battery.NIMH){                
            }else if(showOther && temp.getType() != Battery.UNDEFINED){                
            }else{
                if(count == rowIndex){
                    index = ids[i];
                    break;
                }
                count++;                
            }
        }
        if(index != -1){
            ret = batteryList.getBattery(index).getField(columnIndex);
        }
        return ret;
    }
    
    @Override
    public Class getColumnClass(int col){
        Battery tempBattery = new Battery();
        return tempBattery.getField(col).getClass();
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        boolean isEditable = false;
        //add the logic for cells which are editable here
        return isEditable;
    }
    
    @Override
    public void setValueAt(Object value,int row, int col){
        int[] ids = batteryList.getBatteryIDs();
        Battery tempBattery = batteryList.getBattery(ids[row]);
        tempBattery.setField(col, value);
        fireTableCellUpdated(row, col);
    }
    
    public void setHideInactive(boolean isHide){
        this.hideInactive = isHide;
    }

    public void setShowTwoCell(boolean showTwoCell) {
        this.showTwoCell = showTwoCell;
    }

    public void setShowThreeCell(boolean showThreeCell) {
        this.showThreeCell = showThreeCell;
    }

    public void setShowLipo(boolean showLipo) {
        this.showLipo = showLipo;
    }

    public void setShowLife(boolean showLife) {
        this.showLife = showLife;
    }

    public void setShowHiHM(boolean showHiHM) {
        this.showHiHM = showHiHM;
    }

    public void setShowOther(boolean showOther) {
        this.showOther = showOther;
    }
    
    private int countFilteredBatteries(){
        int[] ids = batteryList.getBatteryIDs();
        int count = 0;
        //iterate over the batteries counting the ones
        //which match the active filters
        for(int i = 0; i < batteryList.getNumberOfBatteries(); i++){
            Battery temp = batteryList.getBattery(ids[i]);
            //check each of the filters, if none of them 
            //eliminate the battery then count it
            if(hideInactive && !temp.isActive()){                
            }else if(showTwoCell && temp.getNumOfCells() != 2){                
            }else if(showThreeCell && temp.getNumOfCells() != 3){                
            }else if(showLipo && temp.getType() != Battery.LIPO){                
            }else if(showLife && temp.getType() != Battery.LIFE){                
            }else if(showHiHM && temp.getType() != Battery.NIMH){                
            }else if(showOther && temp.getType() != Battery.UNDEFINED){                
            }else{
                count++;
            }
        }
        return count;
    }
}
