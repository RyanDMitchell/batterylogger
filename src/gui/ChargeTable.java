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
import database.ChargeAddedListener;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Ryan
 */
public class ChargeTable extends JTable implements ChargeAddedListener{
    private ChargeTableModel tableModel;
    public ChargeTable(){
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableModel = new ChargeTableModel();
        this.setModel(tableModel);
        this.getTableHeader().setReorderingAllowed(false);
        
        TableCellRenderer tableDateRenderer = new DefaultTableCellRenderer() {
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yy hh:mm");
            public Component getTableCellRendererComponent(JTable table, 
                                                Object value, boolean isSelected, 
                                                boolean hasFocus, 
                                                int row, int column){
                if( value instanceof Date) {
                    value = f.format(value);
                }
                return super.getTableCellRendererComponent(table, value, 
                                                        isSelected, hasFocus,
                                                        row, column);
            }
        };
        this.getColumnModel().getColumn(0).setCellRenderer(tableDateRenderer);
    }

    @Override
    public void chargeAdded() {
        System.out.println("Charge Added");
        tableModel.fireTableDataChanged();
    } 
    
    public void updateTable(Battery battery){
        tableModel.setDisplayBattery(battery);
        tableModel.fireTableDataChanged();
    }
    
}
