/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maczetamaczela;

/**
 *
 * @author Adi
 */
public class Healing extends Thread{
    Healing(Hero hero)
    {
        try{
            if (  System.in.read() != -1){ 
                this.sleep(100);
                hero.heal();
            }
         }catch(Exception e){}
    }
}
