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
import data.Comment;
import gui.InfoPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author jrg75
 */
public class CommentPanel extends JPanel{
    public CommentPanel(Battery battery){
        this.setLayout(new GridBagLayout());
        JLabel label = new JLabel("Comments");
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(label);
        LinkedList<Comment> comments = battery.getComments();
        for(int i=0;i<battery.getNumComments();i++){
            c.gridy = battery.getNumComments()-i;
            this.add(new CommentPane(comments.get(i)),c);
        }
    }
    public CommentPanel(){
        this.setLayout(new GridBagLayout());
        JLabel label = new JLabel("Comments");
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(label);
    }
}
