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
package gui.forms;

import database.Batteries;
import gui.EasterEgg;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author jrg75
 */
public class StatsForm {
    private JFrame frame = new JFrame("Stats");
    private Batteries batteries;
    private boolean isEaster = false;
    
    public StatsForm(Batteries batteries) {
        this.batteries = batteries;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    public void addComponentsToPane(Container pane) {
        LinkedList<JLabel> labels = new LinkedList<JLabel>();
        LinkedList<JLabel> stats = new LinkedList<JLabel>();
        
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        
        labels.add(new JLabel("Number of Active Batteries"));
        stats.add(new JLabel(Integer.toString(batteries.NumActiveBatteries())));
        labels.add(new JLabel("Average Age of Batteries"));
        stats.add(new JLabel(df.format(batteries.AveAgeOfBatteries())+" Days"));
        labels.add(new JLabel("Average Number Of Charges"));
        stats.add(new JLabel(df.format(batteries.AveNumChargesOfBatteries())));
        
        pane.setLayout(new GridLayout(labels.size(),2));
        for (int i=0;i<labels.size();i++){
            pane.add(labels.get(i));
            pane.add(stats.get(i));
        }
            frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){
                    if(!isEaster){
                        EasterEgg easterEgg = new EasterEgg();
                        isEaster = true;
                    }
                }
                //System.out.println("pressed key");
            }
            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("2test2");
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //System.out.println("3test3");
            }
        });
    }
    
    private void createAndShowGUI() {
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
               frame.setVisible(false);
               frame.dispose();
            }
        });
        //Display the window.
        frame.setSize(300,100);
        frame.setVisible(true);
    }
}
