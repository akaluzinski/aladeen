package maczetamaczela;

import java.util.Random;

/**
 *
 * @author Adi
 */
public class Hero extends Creature
{
    private int energy = 1;
    private int vitality = 1;
    private int strength = 1;
    private int armor = 1;
    private int mana;
    private int charisma = 1; //daje lepsze ceny w sklepie
    private int luck = 1;//krytyki
    private int levels[] = {0, 50,  100,  150,  220, 280, 300, 380, 400, 520, 580};  
    
    
    public void advance(){
        level++;
        energy++;
        vitality++;
        strength++;
        
        attack = 10*level + strength;
        defense = 10*level + armor;
        life = 100 + vitality*10;
        mana = 50 + energy*10;
        //wywoluje metode dodawania statystyk
    }
    
    Hero(){
        //super(1);
        level = 1;
        alive = true;
        attack = 10*level + strength; //* uwzglednic bron! */
        defense = 10*level + armor;
      //defense = 10;
        experience = 0;
        
        life = 100 + vitality*10;
        mana = 50 + energy*10;
        type = 9;
        testcreated();
    }
    
 /* polimorfizm dla ataku i obrony ! */
      
    public void addExperience(int exp){
        this.experience = this.experience + exp;
        System.out.println("Otrzymano " + exp + " doświadczenia!" );
        if(experience > levels[level])
            {
                ++level;
                advance();
                System.out.println("Postać awansowała na poziom " + level);
            }    
                        
        //sprawdzenie czy mozna awansowac
    }
    
    @Override
    public void testcreated(){
    System.out.println("Utworzono bohatera: " + type);
        System.out.println("Zycie: " + life);
        System.out.println("mana: " + mana);
        System.out.println("Level: " + level);
        System.out.println("Atak: " + attack);
        System.out.println("Obrona: " + defense);
        System.out.println("Doswiadczenie " + experience );
        
    }

    public void heal() {
        life += 100;
        System.out.println("Uleczono bohatera");
    }    
    
    
}
