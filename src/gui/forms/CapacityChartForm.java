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
import data.Charge;
import database.Batteries;
import java.util.Date;
import java.util.LinkedList;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javax.swing.*;

/**
 *
 * @author Ryan
 */
public class CapacityChartForm{
    private Battery battery;
        
    public CapacityChartForm(Battery battery,Batteries batteries){
        this.battery = battery;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
    private void initAndShowGUI() {
        // This method is invoked on the EDT thread
        JFrame frame = new JFrame("Battery Capacity");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(550, 400);
        frame.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private Scene createScene() {
        Group root  =  new  Group();
        Scene scene  =  new  Scene(root, Color.ALICEBLUE);
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Battery Age (days)");
        yAxis.setLabel("Capacity (mAh)");
        final LineChart<Number,Number> lineChart = 
                new LineChart<>(xAxis,yAxis);
                
        lineChart.setTitle("Battery Capacity");
        XYChart.Series series = new XYChart.Series();
        series.setName(battery.getName());
        LinkedList<Charge> charges = battery.getCharges();
        Charge tempCharge;
        Date dateNew = battery.getDateCommissioned();
        Date chargeDate;
        
        long diff;
        for(int i = 0;i<battery.getChargeCount();i++){
             tempCharge = charges.get(i);
             chargeDate = tempCharge.getChargeDate();
             if(tempCharge.isServiceCharge()){
                diff = (chargeDate.getTime() - dateNew.getTime())/86400000;
                series.getData().add(new XYChart.Data(diff, tempCharge.getCapacity()));
             }
        }
        
        lineChart.getData().add(series);

        root.getChildren().add(lineChart);

        return (scene);
    }
}
