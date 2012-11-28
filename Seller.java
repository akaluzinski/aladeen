/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maczetamaczela;

import java.util.Random;

/**
 *
 * @author Adi
 */
public class Seller extends Creature{
    public Seller(){
        super(100);
        alive = true;
        equipment = new Item[8];
        Random itemrand = new Random();
        for(int i=0; i<8; ++i)
                equipment[i] = new Item( itemrand.nextInt(100) + 1 );
    }

    
    @Override
    public Item[] getItems(){
        alive = true;
        if(isAlive() == false){ 
                alive = true; 
                life = 100;
        }
        return equipment; 
    }

}
