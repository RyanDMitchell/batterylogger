/*
 *  ----------------------------------------------------------------------------
 *  "THE BEER-WARE LICENSE":
 * Joshua Gibson and Ryan Mitchell wrote this file. As long as you retain this 
 * notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy us a beer.
 *  ----------------------------------------------------------------------------
 */

package gui;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Ryan
 */
public class EasterEgg{
    
    public EasterEgg(){
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);   

        try {
          jep.setPage("http://www.web-adventures.org/cgi-bin/webfrotz?s=ZorkDungeon");
        }catch (IOException e) {
          jep.setContentType("text/html");
          jep.setText("<html>Could not load</html>");
        } 

        JScrollPane scrollPane = new JScrollPane(jep);     
        JFrame f = new JFrame("It's Easter!");
        f.getContentPane().add(scrollPane);
        f.setPreferredSize(new Dimension(800,600));
        f.setVisible(true);
        f.pack();
        
    }
    
}
