/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batterynofity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author Youtube
 */
public class BatteryNofity {
    private static IsCharging isCharging = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        
        final long timeInterval = 1000;
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    if(!batteryStatus.getACLineStatusString().equalsIgnoreCase("Online")){
                        if (isCharging == null) isCharging = new IsCharging();
                        
                        if(isCharging.getCanSee()){
                            System.out.println("visible");
                        }
                        else{
                            isCharging.dissapearVisible();
                            System.out.println("nonvisible");
                            
                            isCharging.makeVisible();
                        }
                    }
                    else{
                        if(isCharging != null)
                            isCharging.dissapearVisible();
                    }
                    
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        
        Thread thread = new Thread(runnable);
        thread.start();
    }
    
}
