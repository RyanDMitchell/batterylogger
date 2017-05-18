/*
 * Copyright (C) 2014 jrg75
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gui.comments;

import data.Battery;
import database.Batteries;
import gui.BatteryTable;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author jrg75
 */
public class AddCommentForm implements ActionListener{
    private Battery battery;
    private Batteries batteries;
    private CommentScrollPane commentPane;
    private BatteryTable table;
    private JFrame frame = new JFrame("Add Charge to battery");
    private boolean isOpen = false;
    private JTextField comment = new JTextField();
    
    public AddCommentForm(Battery battery,CommentScrollPane commentPane,BatteryTable table,Batteries batteries){
        this.battery = battery;
        this.batteries = batteries;
        this.commentPane = commentPane;
        this.table = table;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public void addComponentsToPane(Container pane) {
        
        JButton add = new JButton("Add");
        add.addActionListener(this);
        add.setActionCommand("add");
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setActionCommand("cancel");
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        comment.setText("Enter Comment Here");
        pane.add(comment,c);
        c.gridwidth = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(add,c);
        c.gridx = 1;
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
        comment.setText("Enter Comment Here");
        isOpen = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "add":
                    battery.addComment(comment.getText());
                    batteries.updateBatteryXML(battery.getID());
                    commentPane.update(battery);
                    int selectedBatteryIndex = table.getSelectedRow();
                    table.updateTable();
                    table.setRowSelectionInterval(selectedBatteryIndex, selectedBatteryIndex);
                    frame.dispose();
                    isOpen = false;
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
