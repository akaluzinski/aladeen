package maczetamaczela;

/**
 *
 * @author Adi
 */
public class Fight extends Thread {
    Fight(Creature creature, Hero hero){    
    do{
               
                System.out.println("Otrzymane obrazenia po ataku bohatera: " + creature.getHit( hero.attack() ));
                 System.out.println("Życie potwora: " + creature.getLife());   
                 try{ 
                     this.sleep(100);
                 }catch(Exception e){}
                 System.out.println("Otrzymane obrazenia po kontrataku potwora: " + hero.getHit( creature.attack())  );
                 System.out.println("Życie bohatera: " + hero.getLife());
           }while( creature.isAlive() && hero.isAlive() );
           
           if(creature.isAlive() == false){
                    hero.addExperience(creature.getExperience());                    
                    Item[] loot = creature.getItems(); //system zbierania przedmiotow
                    System.out.println("Otrzymano przdmioty: "); 
                    for(int i=0; i<loot.length; ++i)
                            System.out.println(i +" " +loot[i]);
                 }  
    }
}
