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
import data.Charge;
import javax.swing.table.*;

/**
 *
 * @author Ryan
 */
public class ChargeTableModel extends AbstractTableModel {
    private Battery battery;
    private String[] columnNames;
    private int numColumns;
    private int numCharges;
    
    public ChargeTableModel() {
        columnNames = Charge.FieldNames;
        numColumns = columnNames.length;
        numCharges = 0;
    }
    
    @Override
    public int getRowCount(){
        if(battery != null){
            numCharges = battery.getChargeCount();
        }else{
            numCharges = 0;
        }
        return numCharges;
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
        Charge tempCharge = battery.getCharge(rowIndex);
        return tempCharge.getField(columnIndex);
    }
    
    @Override
    public Class getColumnClass(int col){        
        return Charge.displayClasses[col].getClass();
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        boolean isEditable = false;
        //add the logic for cells which are editable here
        return isEditable;
    }
    
    @Override
    public void setValueAt(Object value,int row, int col){
        Charge tempCharge = battery.getCharge(row);
        tempCharge.setField(col, value);
        fireTableCellUpdated(row, col);
    }
    
    public void setDisplayBattery(Battery battery){
        this.battery = battery;
    }    
}
