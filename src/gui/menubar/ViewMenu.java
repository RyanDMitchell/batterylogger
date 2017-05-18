/*
 *  ----------------------------------------------------------------------------
 *  "THE BEER-WARE LICENSE":
 * Joshua Gibson and Ryan Mitchell wrote this file. As long as you retain this 
 * notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy us a beer.
 *  ----------------------------------------------------------------------------
 */

package gui.menubar;

import gui.BatteryTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author presenter
 */
public class ViewMenu extends JMenu implements ActionListener{
    private BatteryTable batteryTable;
    JCheckBoxMenuItem hideInactive;
    JCheckBoxMenuItem showTwoCell;
    JCheckBoxMenuItem showThreeCell;
    JCheckBoxMenuItem showLIPO;
    JCheckBoxMenuItem showLIFE;
    JCheckBoxMenuItem showNIMH;
    JCheckBoxMenuItem showOTHER;
    JCheckBoxMenuItem showAllCells;
    JCheckBoxMenuItem showAllTypes;
    
    
    public ViewMenu(BatteryTable batteryTable){
        this.batteryTable = batteryTable;
        this.setText("View");
        this.setMnemonic(KeyEvent.VK_V);        
        
        ButtonGroup showCellsGroup = new ButtonGroup();
        ButtonGroup showTypeGroup = new ButtonGroup();
        
        hideInactive = new JCheckBoxMenuItem("Hide Inactive",false);
        hideInactive.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
        hideInactive.addActionListener(this);
        hideInactive.setActionCommand("hideInactive");
        hideInactive.setSelected(true);
        this.add(hideInactive);
        
        this.add(new JSeparator());
        
        showAllCells = new JCheckBoxMenuItem("Show All Cell Numbers",false);
        showAllCells.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
        showAllCells.addActionListener(this);
        showAllCells.setActionCommand("showAll");
        showAllCells.setSelected(true);
        showCellsGroup.add(showAllCells);
        this.add(showAllCells);
        
        showTwoCell = new JCheckBoxMenuItem("Show 2S",false);
        showTwoCell.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
        showTwoCell.addActionListener(this);
        showTwoCell.setActionCommand("showTwoCell");
        showCellsGroup.add(showTwoCell);
        this.add(showTwoCell);
        
        showThreeCell = new JCheckBoxMenuItem("Show 3S",false);
        showThreeCell.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK));
        showThreeCell.addActionListener(this);
        showThreeCell.setActionCommand("showThreeCell");
        showCellsGroup.add(showThreeCell);
        this.add(showThreeCell);
        
        this.add(new JSeparator());  
        
        showAllTypes = new JCheckBoxMenuItem("Show All Types",false);
        showAllTypes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.CTRL_MASK));
        showAllTypes.addActionListener(this);
        showAllTypes.setActionCommand("showAll");
        showAllTypes.setSelected(true);
        showTypeGroup.add(showAllTypes);
        this.add(showAllTypes);
        
        showLIPO = new JCheckBoxMenuItem("Show LiPo",false);
        showLIPO.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.CTRL_MASK));
        showLIPO.addActionListener(this);
        showLIPO.setActionCommand("showLIPO");
        showTypeGroup.add(showLIPO);
        this.add(showLIPO);
        
        showLIFE = new JCheckBoxMenuItem("Show LiFe",false);
        showLIFE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.CTRL_MASK));
        showLIFE.addActionListener(this);
        showLIFE.setActionCommand("showLIFE");
        showTypeGroup.add(showLIFE);
        this.add(showLIFE);
        
        showNIMH = new JCheckBoxMenuItem("Show NiMH",false);
        showNIMH.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, ActionEvent.CTRL_MASK));
        showNIMH.addActionListener(this);
        showNIMH.setActionCommand("showNIMH");
        showTypeGroup.add(showNIMH);
        this.add(showNIMH);
        
        showOTHER = new JCheckBoxMenuItem("Show Other",false);
        showOTHER.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, ActionEvent.CTRL_MASK));
        showOTHER.addActionListener(this);
        showOTHER.setActionCommand("showOTHER");
        showTypeGroup.add(showOTHER);
        this.add(showOTHER);

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {                
        batteryTable.setShowTwoCell(showTwoCell.isSelected());                
        batteryTable.setShowThreeCell(showThreeCell.isSelected());                
        batteryTable.setShowLipo(showLIPO.isSelected());                
        batteryTable.setShowLife(showLIFE.isSelected());                
        batteryTable.setShowHiHM(showNIMH.isSelected());                
        batteryTable.setShowOther(showOTHER.isSelected());              
        switch(e.getActionCommand()){            
            case "hideInactive":              
                batteryTable.setHideInactive(hideInactive.isSelected());                
                break;
            default:
                break;
        }
        batteryTable.updateTable();
    }
}
