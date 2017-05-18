package data;

import java.util.Date;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Ryan
 */
@XmlRootElement(name = "charge")
@XmlAccessorType (XmlAccessType.FIELD)
public class Charge {
    private Date chargeDate;
    private Boolean serviceCharge;
    private double resistance;
    private double capacity;
    
    //used for displaying the table
    public static final String[] FieldNames= {"Date","Service","Resistance","Capacity"};
    public static final Object[] displayClasses = {new Date(),(boolean)true,"",""};
    
    public Charge() {
        chargeDate = new Date();
        serviceCharge = false;
        resistance = -1;
        capacity = -1;
    }
    
    public void setChargeDate(Date chargeDate) {
        this.chargeDate = chargeDate;
    }

    public void setServiceCharge(Boolean serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public Date getChargeDate() {
        return chargeDate;
    }

    public Boolean isServiceCharge() {
        return serviceCharge;
    }

    public double getResistance() {
        return resistance;
    }

    public double getCapacity() {
        return capacity;
    }
    
    public Object getField(int index){
        Object ret;
        switch(FieldNames[index]){
            case("Date"):
                ret = getChargeDate();
            break;
            case("Service"):
                ret = isServiceCharge();
            break;
            case("Resistance"):
                if(getResistance() == -1){
                    ret = "N/A";
                }else{
                    ret = Double.toString(getResistance());
                }
            break;
            case("Capacity"):
                if(getCapacity() == -1){
                    ret = "N/A";
                }else{
                    ret = Double.toString(getCapacity());
                }
            break;            
            default:
                ret = "error";
            break;                
        }//end switch
        return ret;
    }
    
    public void setField(int index, Object value){
        switch(FieldNames[index]){
            case("Date"):
                setChargeDate((Date)value);
            break;
            case("Service"):
                setServiceCharge((boolean)value);
            break;
            case("Resistance"):
                setResistance((double)value);
            break;
            case("Capacity"):
                setCapacity((double)value);
            break;            
            default:
                //Wrong index number
            break;                
        }//end switch
    }
}
