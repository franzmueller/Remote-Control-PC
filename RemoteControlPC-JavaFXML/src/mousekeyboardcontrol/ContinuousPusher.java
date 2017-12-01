/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousekeyboardcontrol;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * This class is required to let users step down the volume for more
 * than just a single step. Launch the Thread and it'll push the provided button
 * until you interrupt it. Notify the thread and it'll begin pushing again. * 
 */
public class ContinuousPusher extends Thread{
    NativeKeyEvent nke;
    final Object monitor;
    /**
     * 
     * @param keyCode This should be a keyCode form NativeKeyEvent
     * @param monitor A monitor on which the thread will wait when it has been interrupted.
     */
    public ContinuousPusher(int keyCode, Object monitor){
        nke = new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED,0,0,keyCode,NativeKeyEvent.CHAR_UNDEFINED);       
        this.monitor = monitor;
    }
    
    @Override
    public void run(){
        while(true){
            GlobalScreen.postNativeEvent(nke);
            try{
                Thread.sleep(300);
            }catch(InterruptedException ie){                
                synchronized(monitor){
                    try {
                        monitor.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ContinuousPusher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }                    
                
            }            
        }
    }
            
}
