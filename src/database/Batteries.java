/*
 *  ----------------------------------------------------------------------------
 *  "THE BEER-WARE LICENSE":
 * Joshua Gibson and Ryan Mitchell wrote this file. As long as you retain this 
 * notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy us a beer.
 *  ----------------------------------------------------------------------------
 */

package database;

import data.Battery;
import java.io.File;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.*;

/**
 *
 * @author Ryan
 */
public class Batteries {
    private LinkedList<Battery> batteryList;
    private ArrayList<BatteryAddedListener> listeners = new ArrayList<BatteryAddedListener>();
    private ArrayList<ChargeAddedListener> chargeListeners = new ArrayList<ChargeAddedListener>();
    private String xmlLocation;

    public Batteries(String xmlLocation) {
        this.xmlLocation = xmlLocation;
        batteryList = new LinkedList<Battery>();
        checkFolder(xmlLocation);
        //load all batteries from the xmlLocation
        File xmlDir = new File(xmlLocation);
        File[] listOfFiles = xmlDir.listFiles();
        if(listOfFiles.length != 0){
            for(File file : listOfFiles){
                //check that it is a file
                if (file.isFile()){
                    //get the file name and extension
                    String fileName = file.getName();
                    String extension = getFileExtension(file);
                    //make sure it's a .xml file
                    if(".xml".equals(extension)){
                        try {
                            //parse the xml file
                            JAXBContext context = JAXBContext.newInstance(Battery.class);
                            Unmarshaller u = context.createUnmarshaller();
                            Battery batt = (Battery) u.unmarshal(file);
                            //add the battery to the linkedList
                            batteryList.add(batt);
                        } catch (JAXBException ex) {
                            Logger.getLogger(Batteries.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        //sort the list to preserve ID indexes
        Collections.sort(batteryList, BATTERYSORT);
    }
    
    //comparator overide for sorting the battery list by ID
    static final Comparator<Battery> BATTERYSORT = new Comparator<Battery>() {
        @Override
        public int compare(Battery b1, Battery b2) {
            return (b1.getID() < b2.getID() ? -1 :
                   (b1.getID() == b2.getID() ? 0 : 1));
        }
    };
    
    //check if a folder exists, if it doesn't create it
    static private void checkFolder(String path){
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
    }
      
    //returns the file extention of a File object
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }else{ 
            return name.substring(lastIndexOf);
        }
    }
    
    //finds the largest Battery ID in the battery list and
    //returns an integer one larger
    private int findFreeId(){
        int currentMax = -1;
        if(batteryList.isEmpty() == false){
            for(Battery batt : batteryList){
                currentMax = max(currentMax,batt.getID());
            }
        }
        currentMax++;
        return currentMax;
    }
    
    public void addBatteryAddedListener(BatteryAddedListener listener){
        listeners.add(listener);
    }
    
    public void addChargeAddedListener(ChargeAddedListener listener){
        chargeListeners.add(listener);
    }
    
    private void batteryAdded(){
        BatteryAddedListener temp;
        for (int i = 0;i<listeners.size();i++){
            temp = listeners.get(i);
            temp.batteryAdded();
        }
    }
    
    public void addBattery(String name, int numCells, int statedCap, int type){
        try {
            //create required objects for xml parsing
            JAXBContext context = JAXBContext.newInstance(Battery.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            //create new battery
            Battery battery = new Battery();
            battery.init(findFreeId(), name, numCells, statedCap, type);
            //update the xml information
            File file = new File(xmlLocation.toString() + "/" + battery.getID() + ".xml");
            m.marshal(battery, file);
            //add to the linked list
            batteryList.add(battery);
            updateBatteryXML(battery.getID());
            batteryAdded();
        } catch (JAXBException ex) {
            Logger.getLogger(Batteries.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Add Battery failed");
            System.out.println(ex.getMessage());
        }
    }
    
    public Battery getBattery(int ID){
        Battery ret = null;
        for(Battery batt : batteryList){
            if(batt.getID() == ID){
                ret = batt;
            }
        }
        return ret;
    }
    
    
    
    public int[] getBatteryIDs(){
        int numBattey = this.getNumberOfBatteries();
        int[] batteryIDs = new int[numBattey];
        int i = 0;
        for(Battery bat:batteryList){
            batteryIDs[i] = bat.getID();
            i++;
        }
        return batteryIDs;
    }
    
    public int getNumberOfBatteries(){
        return batteryList.size();
    }
    
    public void updateBatteryXML(int ID){
        try {
            //create required objects for xml parsing
            JAXBContext context = JAXBContext.newInstance(Battery.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            for(Battery battery : batteryList){
                if(battery.getID() == ID){
                    File file = new File(xmlLocation.toString() + "/" + battery.getID() + ".xml");
                    if(file.exists()){
                        file.delete();
                    }
                    m.marshal(battery, file);
                }
            }
        } catch (JAXBException ex) { 
            Logger.getLogger(Batteries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAllXML() {
        try {
            //create required objects for xml parsing
            JAXBContext context = JAXBContext.newInstance(Battery.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            for(Battery battery : batteryList){
                File file = new File(xmlLocation.toString() + "/" + battery.getID() + ".xml");
                m.marshal(battery, file);    
            }
        } catch (JAXBException ex) {
            Logger.getLogger(Batteries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setXmlLocation(String xmlLocation) {
        this.xmlLocation = xmlLocation;
    }

    public LinkedList<Battery> getBatteryList() {
        return batteryList;
    }
    public int NumActiveBatteries(){
        int count = 0;
        Battery tempBat;
        for(int i=0;i<batteryList.size();i++){
            tempBat = batteryList.get(i);
            if(tempBat.isActive()){
                count++;
            }
        }
        return(count);
    }
    public double AveAgeOfBatteries(){
        int count = 0;
        double sum = 0;
        Battery tempBat;
        for(int i=0;i<batteryList.size();i++){
            tempBat = batteryList.get(i);
            if(tempBat.isActive()){
                sum += (double)tempBat.getBatteryAgeDays();
                count++;
            }
        }
        return(sum/count);
    }
    public double AveNumChargesOfBatteries(){
        int count = 0;
        double sum = 0;
        Battery tempBat;
        for(int i=0;i<batteryList.size();i++){
            tempBat = batteryList.get(i);
            if(tempBat.isActive()){
                sum += (double)tempBat.getChargeCount();
                count++;
            }
        }
        return(sum/count);
    }
}