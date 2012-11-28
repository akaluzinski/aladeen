package maczetamaczela;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author Adrian Kałuziński
 */

public class Creature extends MapElement{
    protected int life;
    protected Item equipment[];
    protected boolean alive;
    protected int attack;
    protected int defense;
    protected int level;
    protected int experience;
    protected long oldseed;
 
    public int getLife(){
    return life;
    }
    
    public void getKilled()
    {
        Random itemrand = new Random();
        int noItems = getRand(1,7);
        equipment = new Item[noItems];
        for(int i=0; i<noItems; ++i)
            equipment[i] = new Item( itemrand.nextInt(100) + 1 );
            
        
        alive = false;
        life = 0;
    }
    
    public boolean isAlive(){
        return alive;
    }
    public int getExperience(){ 
        return experience;
    }
    
    protected int getRand(int min, int max){
		Date now = new Date();
		long seed = now.getTime() + oldseed;
 		Random random = new Random(seed);
		int n = max - min + 1;
		int i = random.nextInt(n);
		if (i < 0) i = -i;
		return min + i;
    }
    
    public Creature(){
        
    }
    
    public Creature(int inLevel){
        level = inLevel;
        alive = true;
        attack = level*getRand(1, 5);
        defense = level*getRand(1, 5);
      //defense = 10;
        life = level*10;
        experience = level*getRand(1,10) + 2*attack + defense + life;
        testcreated();
    }
    
    public void testcreated(){
        x = 0;
        type = 10;
        System.out.println("Utworzono obiekt: " + type);
        System.out.println("Zycie: " + life);
        System.out.println("Level: " + level);
        System.out.println("Atak: " + attack);
        System.out.println("Obrona: " + defense);
        System.out.println("Doswiadczenie " + experience );
      /*
        for (int i = 0; i<4; ++i){
                 System.out.println("Przykladowy atak: " + attack());
                 
        }         
        while( this.isAlive() ){
                 System.out.println("Otrzymane obrazenia przy ciosie 10: " + getHit(10));
                 System.out.println("Życie: " + life);
                 
        }
        
        if(isAlive() == false){
                     Item[] loot = getItems();
                     for(int i=0; i<loot.length; ++i)
                            System.out.println(i +" " +loot[i]);
                 }
        */
        
    }
    
    public int attack(){
        Random multiply = new Random(); 
        return (int) (multiply.nextDouble()*attack);
    }
    
    public int getHit(int damage){
        Random multiply = new Random();
        int getDamage = (damage - (int)(1.5*multiply.nextDouble()*defense));
        if(getDamage <= 0){
            return 0;
        }else{
            life = life - getDamage;
            if(life <= 0) getKilled(); 
            return getDamage;
        }     
        
    }
    
    public Item[] getItems(){
    if (this.isAlive()==false)
        return equipment;
    else return null;
        
    }    
}


