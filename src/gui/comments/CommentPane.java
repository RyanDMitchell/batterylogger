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

import data.Comment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

/**
 *
 * @author jrg75
 */
public class CommentPane extends JPanel{
    private JLabel date = new JLabel();
    private JTextArea comment = new JTextArea();

    public CommentPane(Comment comment) {
        Date commentDate = comment.getDate();
        SimpleDateFormat ft =  new SimpleDateFormat ("dd/MM/yyyy hh:mma");
        this.comment.setLineWrap(true);
        this.comment.setWrapStyleWord(true);
        this.comment.setEditable(false);
        this.date.setText(ft.format(commentDate));
        this.comment.setText(comment.getComment());
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(this.date,c);
        c.weighty = 1.0;
        c.gridy = 1;
        this.add(this.comment,c);
        c.weighty = 0;
        c.gridy = 2;
        this.add(new JSeparator(),c);
    }
}
