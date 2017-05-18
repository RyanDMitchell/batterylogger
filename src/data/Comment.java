/*
 *  ----------------------------------------------------------------------------
 *  "THE BEER-WARE LICENSE":
 * Joshua Gibson and Ryan Mitchell wrote this file. As long as you retain this 
 * notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy us a beer.
 *  ----------------------------------------------------------------------------
 */

package data;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ryan
 */
@XmlRootElement(name = "comment")
@XmlAccessorType (XmlAccessType.FIELD)
public class Comment {
    private Date date;
    private String comment;
    
    public Comment(){
        date = new Date();
        comment = "";        
    }
    
    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
