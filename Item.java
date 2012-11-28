/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maczetamaczela;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class Item {
    
    public int item_code;
    private long oldseed;
    
    private int getRand(int min, int max){
		Date now = new Date();
		long seed = now.getTime() + oldseed;
 		Random random2 = new Random(seed+2*oldseed);
		int n = max - min + 1;
		int i = random2.nextInt(n);
		if (i < 0) i = -i;
		return min + i;
    }
    
    Item(int code)
    {
      item_code = code;
    }
    
    @Override
    public String toString(){
       return new Integer(item_code).toString();
    }
    
}
