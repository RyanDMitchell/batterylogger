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
import gui.EasterEgg;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author Ryan
 */
public class HelpForm extends JFrame{
    public HelpForm() {
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);   

        try {
          File helpLocation = new File("./help/help.html");
          jep.setPage(helpLocation.toURI().toURL());
        }catch (IOException e) {
          jep.setContentType("text/html");
          jep.setText("<html>Could not load</html>");
        } 

        JScrollPane scrollPane = new JScrollPane(jep);    
        scrollPane.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){
                    EasterEgg easterEgg = new EasterEgg();
                }
                System.out.println("pressed key");
            }
            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("2test2");
            }

            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("3test3");
            }
        });
        this.getContentPane().add(scrollPane);
        this.setPreferredSize(new Dimension(800,600));
        this.setVisible(true);
        this.pack();
        this.setFocusable(true);

    }       
}
