package data;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javax.xml.bind.annotation.*;


/**
 *
 * @author Ryan
 */
@XmlRootElement(name = "battery")
@XmlAccessorType (XmlAccessType.FIELD)
public class Battery {
    //The number of displayable data fields in the Battery object
    //required for displaying the data in a table
    public static final String[] FieldNames= {"ID","Name","Cells","Capacity","Type","Commission Date","Age","Active","Charges Since Service","Charges","Comments"};
    public static final String[] typeLabels = {"LiPo","LiFe","NiMH","Other"};
    public static final int[] FieldDisplayWidths = {26, 79, 34, 53, 53, 91, 33, 45, 72, 77, 77 };
        
    public static final int LIPO = 0;
    public static final int LIFE = 1;
    public static final int NIMH = 2;
    public static final int UNDEFINED = 3;
    
    private int ID;
    private String name;
    private int numOfCells;
    private int statedCapacity;
    private int type;
    private Date dateCommissioned;    
    private boolean active;
    private int sinceSerivceChargeCount;
    @XmlElement(name = "charge")
    private LinkedList<Charge> charges;
    @XmlElement(name = "comment")
    private LinkedList<Comment> comments;
    
    public Battery() {
        ID = -1;
        numOfCells = -1;
        name = "uninitialised";
        statedCapacity = -1;
        type = UNDEFINED;        
        dateCommissioned = new Date();
        active = false;
        sinceSerivceChargeCount = 0;        
        charges = new LinkedList<Charge>();
        comments = new LinkedList<Comment>();
    }
    
    public void init(int ID, String name , int numOfCells, int statedCapacity, int type){
        this.ID = ID;
        this.numOfCells = numOfCells;
        this.name = name;
        this.statedCapacity = statedCapacity;
        switch (type){
            case LIPO: this.type = LIPO; break;
            case LIFE: this.type = LIFE; break;
            case NIMH: this.type = NIMH; break;
            default: this.type = UNDEFINED; break;  
        }
        
        dateCommissioned = new Date();
        active = true;
        sinceSerivceChargeCount = 0;
        charges = new LinkedList<Charge>();   
        comments = new LinkedList<Comment>(); 
    }
    
   
    public LinkedList<Comment> getComments() {
        return comments;
    }

    public void setComments(LinkedList<Comment> comments) {
        this.comments = comments;
    }
    
    //Getters
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getNumOfCells() {
        return numOfCells;
    }

    public int getStatedCapacity() {
        return statedCapacity;
    }
    
    public int getType(){
        return type;
    }

    public Date getDateCommissioned() {
        return dateCommissioned;
    }

    public boolean isActive() {
        return active;
    }

    public int getChargeCount() {
        return charges.size();
    }

    public LinkedList<Charge> getCharges() {
        return charges;
    }
    
    public Charge getCharge(int index){
        return charges.get(index);
    }
    
    public int getSinceSerivceChargeCount() {
        return sinceSerivceChargeCount;
    }
    
    //count the service/standard charges functions
    public int getServiceChargeCount() {
        int count = 0;
        for ( Charge item : charges){
            if(item.isServiceCharge()){
                count++;
            }
        }
        return count;
    }
    
    public int getStandardChargeCount(){
        int count = 0;
        for ( Charge item : charges){
            if(!item.isServiceCharge()){
                count++;
            }
        }
        return count;
    }

    //calculate the age in days
    public long getBatteryAgeDays(){
        Date now = new Date();
        long diff = now.getTime() - dateCommissioned.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    public int getNumComments(){
        return comments.size();
    }
    

    //setters
    public void setActive(boolean active) {
        this.active = active;
    }

    public void removeAllCharges(){
        charges.clear();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfCells(int numOfCells) {
        this.numOfCells = numOfCells;
    }

    public void setStatedCapacity(int statedCapacity) {
        this.statedCapacity = statedCapacity;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDateCommissioned(Date dateCommissioned) {
        this.dateCommissioned = dateCommissioned;
    }

    public void setSinceSerivceChargeCount(int sinceSerivceChargeCount) {
        this.sinceSerivceChargeCount = sinceSerivceChargeCount;
    }

    public void setCharges(LinkedList<Charge> charges) {
        this.charges = charges;
    }
    
    //Add/remove charges from the list
    public void addServiceCharge(double resistance, double capacity) {
        Date now = new Date();
        Charge charge = new Charge();
        charge.setChargeDate(now);
        charge.setServiceCharge(true);
        charge.setCapacity(capacity);
        charge.setResistance(resistance);
        charges.add(charge);
        sinceSerivceChargeCount = 0;
    }
    
    public void addServiceCharge(double resistance, double capacity,Date date) {
        Charge charge = new Charge();
        charge.setChargeDate(date);
        charge.setServiceCharge(true);
        charge.setCapacity(capacity);
        charge.setResistance(resistance);
        charges.add(charge);
        sinceSerivceChargeCount = 0;
    }

    public void addStandardCharge() {
        Date now = new Date();
        Charge charge = new Charge();
        charge.setChargeDate(now);
        charge.setServiceCharge(false);
        charge.setCapacity(-1);
        charge.setResistance(-1);
        charges.add(charge);
        sinceSerivceChargeCount++;
    }
    
    public void addStandardCharge(Date date) {
        Charge charge = new Charge();
        charge.setChargeDate(date);
        charge.setServiceCharge(false);
        charge.setCapacity(-1);
        charge.setResistance(-1);
        
        charges.add(charge);
        sinceSerivceChargeCount++;
    }    
    
    //add comments
    public void addComment(String comment){
        Comment temp;
        Date now = new Date();
        temp = new Comment();
        temp.setDate(now);
        temp.setComment(comment);
        comments.add(temp);
    }
    
    public void addComment(Date date, String comment){
        Comment temp;
        temp = new Comment();
        temp.setDate(date);
        temp.setComment(comment);
        comments.add(temp);
    }
    
    public Object getField(int index){
        Object ret;
        switch(FieldNames[index]){
            case("ID"):
                ret = getID();
            break;
            case("Name"):
                ret = getName();
            break;
            case("Cells"):
                ret = getNumOfCells();
            break;
            case("Capacity"):
                ret = getStatedCapacity();
            break;
            case("Type"):
                ret = "Error";
                switch (getType()){
                    case LIPO: ret = "LiPo"; break;
                    case LIFE: ret = "LiFe"; break;
                    case NIMH: ret = "NiHM"; break;
                    default: ret = "Undefined"; break;  
                }
            break;
            case("Commission Date"):
                ret = getDateCommissioned();
            break;
            case("Age"):
                ret = getBatteryAgeDays();
            break;
            case("Active"):
                ret = isActive();
            break;
            case("Charges Since Service"):
                ret = getSinceSerivceChargeCount();
            break;
            case("Charges"):
                ret = getChargeCount();
            break;
            case("Comments"):
                ret = getNumComments();
            break;
            default:
                ret = "error";
            break;                
        }//end switch
        return ret;
    }
    
    public void setField(int index, Object value){
        switch(FieldNames[index]){
            case("ID"):
                setID((int)value);
            break;
            case("Name"):
                setName((String)value);
            break;
            case("Cells"):
                setNumOfCells((int)value);
            break;
            case("Capacity"):
                setStatedCapacity((int)value);
            break;
            case("Type"):
                setType((int)value);
            break;
            case("Commission Date"):
                setDateCommissioned((Date)value);
            break;
            case("Age"):
                //can't set explicitly
            break;
            case("Active"):
                setActive((boolean)value);
            break;
            case("Charges Since Service"):
                //can't set explicitly
            break;
            case("Charges"):
                //can't set explicitly
            break;
            case("Comments"):
                //can't set explicitly
            break;
            default:
                //Wrong index number
            break;                
        }//end switch
    }
    
    public void removeCharge(int index){
        charges.remove(index);
    }
}
