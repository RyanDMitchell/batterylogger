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
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author jrg75
 */
public class CommentScrollPane extends JPanel{

    public CommentScrollPane() {
        BorderLayout layout = new BorderLayout(1,1);
        this.setLayout(layout);
        this.add(new CommentPanel(),BorderLayout.PAGE_START);
    }
    
    public void update(Battery battery){
        this.removeAll();
        if(battery != null){
            this.add(new CommentPanel(battery),BorderLayout.PAGE_START);
        }else{
            this.add(new CommentPanel(),BorderLayout.PAGE_START);
        }
        this.revalidate();
        this.repaint();
    }
}
