package maczetamaczela;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/** 
 * Klasa generatora map  
 * @version 0.7
 * @author Adrian Kałuziński
 * @date  3.10.2012
 */
public class MapGenerator{
	/** Maksymalny rozmiar mapy */
	private int xmax = 80; //80 columns
	private int ymax = 25; //25 rows
 
	/** Rozmiar mapy */
	private int xsize = 0;
	private int ysize = 0;
 
	/** Liczba pomieszczen ma mapie */
	private int room = 0;
        
        /** Liczba potworów na mapie */
        private int monster = 0; 
        private int monstersMax = 0;
 
	/** Tablica z mapą przesylana do klasy Map */
	private int[] map = { };
 
	/** stare ziarno generatora  */
	private long oldseed = 0;
 
 
	/** Lista możliwych typów obiektów */
	final private int mapElementUnused = 0;
	final private int mapElementWall = 1;
	final private int mapElementFloor = 2;
	final private int mapElementLimit = 3;
	final private int mapElementUnused2 = 4; /* wymaganie dodatkowe */
	final private int mapElementDoor = 5;
	final private int mapElementUpStairs = 6;
	final private int mapElementDownStairs = 7;
	final private int mapElementChest = 8;    /* wymaganie dodatkowe */
    final private int mapElementHero = 9;
    final private int mapElementMonster = 10;
        
        
        /** Stany generowania pomieszczenia: */
        final private int generatedRooms = 0;
        final private int generatedDownStairs = 1;
        final private int generatedUpStairs = 2;
        final private int generatedEnd = 8; 
 
	/** Komunikaty testowe do napisania */
	private String msgXSize = "Rozmiar X labiryntu: \t";
	private String msgYSize = "Rozmiar Y labiryntu: \t";
	private String msgMaxRooms = "Maksymalna liczba pomieszczeń \t";
	private String msgNumRooms = "Liczba wygenerowanych pomieszczeń  \t";
        private String msgMaxMonsters = "Maksymalna liczba potworów \t";
        private String msgNumMonsters = "Liczba wygenerowanych potworów \t";
 
 
        
	/** 
         * Metoda ustala typ komórki 
         *  @param x - wymiar x 
         *  @param y - wymiar y
         *  @param type - typ elementu
         *  @return - setter
        */
	public void setMapElement(int x, int y, int type){
		map[x + xsize * y] = type;
	}
 
        /** 
         * Metoda zwraca typ komorki
         *  @param x - wymiar x 
         *  @param y - wymiar y
         *  @return - typ elementu [x,y] 
        */
	public int getMapElement(int x, int y){
		return map[x + xsize * y];
	}
 
        
        /** Metoda zwracająca mapę reszcie aplikacji
         * 
         * @return map - mapa 2D przekazywana do GUI
         */
        public int[][] getMap(){
            int tmap[][] = new int[xsize][ysize];
            for(int x = 0; x < xsize; x++)
                for(int y = 0; y < ysize; y++){
                    tmap[x][y] = getMapElement(x,y);
                   /* 
                     System.out.print(tmap[x][y]);
                    
                     if(y == ysize-1) 
                                    System.out.println("\n");
                   */                  
                    
                }
            return tmap;
        }
        
	/** 
         *  Renerator liczb losowych
        */
	private int getRand(int min, int max){
		//Ziarno jest oparte na aktualnej dacie w miliseknundach oraz ziarnie
		Date now = new Date();
		long seed = now.getTime() + oldseed;
		oldseed = seed;
 		Random random = new Random(seed);
		int n = max - min + 1;
		int i = random.nextInt(n);
		if (i < 0) i = -i;
		//System.out.println("seed: " + seed + "\tnum:  " + (min + i));
		return min + i;
	}
 
        public void writeMapToFile(){
	try{
        Integer temp;
	FileWriter fw = new FileWriter("mapOut.txt");
        
        for (int y = 0; y < ysize; y++){
			for (int x = 0; x < xsize; x++){
			
				switch(getMapElement(x, y)){
				case mapElementUnused:
					fw.write(" ");
					break;
				case mapElementWall:
					fw.write("#");
					break;
				case mapElementFloor:
					fw.write(".");
					break;
				case mapElementLimit:
					fw.write("O");
					break;
				case mapElementUnused2:
					fw.write(" ");
					break;
				case mapElementDoor:
					fw.write("D");
					break;
				case mapElementUpStairs:
					fw.write("<");
					break;
				case mapElementDownStairs:
					fw.write(">");
					break;
				case mapElementChest:
					fw.write("*");
					break;
                                case mapElementHero:
                                        fw.write("@");
                                        break;
                                case mapElementMonster:
                                        fw.write("M");
                                        break;
				}
			}
			if (xsize <= xmax) fw.write("\n");
		    
        }
        fw.write("test ");
	fw.close();
        }
        catch(IOException IOerror){}
        } 
        
        
        /** Generowanie pomieszczenia */
	private boolean mapElementFloor(int x, int y, int xlength, int ylength, int direction){
		/** 
                 * Zdefiniowanie wielkości pomieszczeń. Co najmniej 4 kratki
                 * 2x2+ - do poruszania się, natomiast resztę zużywają ściany
                 */
		int xlen = getRand(4, xlength);
		int ylen = getRand(4, ylength);

		int dir = 0;
		if (direction > 0 && direction < 4) 
                            dir = direction;
 
               if(dir == 0){                 
		/** Polnoc */
			/** Sprawdz czy jest dostatecznie duzy */
			for (int ytemp = y; ytemp > (y-ylen); ytemp--)
                        {
				if (ytemp < 0 || ytemp > ysize) return false;
				for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++)
                                {
					if (xtemp < 0 || xtemp > xsize) 
                                            return false;
					if (getMapElement(xtemp, ytemp) != mapElementUnused) 
                                            return false; //brak wolnego miejca
				}
			}
 
			/** budowanie mapy */
			for (int ytemp = y; ytemp > (y-ylen); ytemp--)
                        {
				for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++)
                                {
					/** zacznij od scian */			
                                        if (xtemp == (x-xlen/2) ) 
                                                                setMapElement(xtemp, ytemp, mapElementWall);
					else if (xtemp == (x+(xlen-1)/2) ) 
                                                                setMapElement(xtemp, ytemp, mapElementWall);
					else if (ytemp == y) 
                                                                setMapElement(xtemp, ytemp, mapElementWall);
					else if (ytemp == (y-ylen+1) ) 
                                                                setMapElement(xtemp, ytemp, mapElementWall);
					/** Nastepnie wypełnij podłogą */
					else setMapElement(xtemp, ytemp, mapElementFloor);
				}
			}
               }else if(dir == 1){
                  
			for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
				if (ytemp < 0 || ytemp > ysize) 
                                                        return false;
				for (int xtemp = x; xtemp < (x+xlen); xtemp++){
					if (xtemp < 0 || xtemp > xsize) 
                                                        return false;
					if (getMapElement(xtemp, ytemp) != mapElementUnused) 
                                                        return false;
				}
			}
 
			for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
				for (int xtemp = x; xtemp < (x+xlen); xtemp++){
 
					if (xtemp == x) 
                                                        setMapElement(xtemp, ytemp, mapElementWall);
					else if (xtemp == (x+xlen-1)) 
                                                        setMapElement(xtemp, ytemp, mapElementWall);
					else if (ytemp == (y-ylen/2)) 
                                                        setMapElement(xtemp, ytemp, mapElementWall);
					else if (ytemp == (y+(ylen-1)/2)) 
                                                        setMapElement(xtemp, ytemp, mapElementWall);

					else setMapElement(xtemp, ytemp, mapElementFloor);
				}
			}
               } else if(dir == 2 ){  
		/* Południe */
			for (int ytemp = y; ytemp < (y+ylen); ytemp++){
				if (ytemp < 0 || ytemp > ysize) 
                                                                return false;
				for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
					if (xtemp < 0 || xtemp > xsize) 
                                                                return false;
					if (getMapElement(xtemp, ytemp) != mapElementUnused) 
                                                                return false;
				}
			}
 
			for (int ytemp = y; ytemp < (y+ylen); ytemp++){
				for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
 
					if (xtemp == (x-xlen/2)) 
                                                                setMapElement(xtemp, ytemp, mapElementWall);
					else if (xtemp == (x+(xlen-1)/2)) 
                                                                setMapElement(xtemp, ytemp, mapElementWall);
					else if (ytemp == y) 
                                                                setMapElement(xtemp, ytemp, mapElementWall);
					else if (ytemp == (y+ylen-1)) 
                                                                setMapElement(xtemp, ytemp, mapElementWall);
					else setMapElement(xtemp, ytemp, mapElementFloor);
				}
			}
                }else if(dir == 3){
		/** Zachód */
			for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
				if (ytemp < 0 || ytemp > ysize) 
                                                        return false;
				for (int xtemp = x; xtemp > (x-xlen); xtemp--){
					if (xtemp < 0 || xtemp > xsize) 
                                                        return false;
					if (getMapElement(xtemp, ytemp) != mapElementUnused) 
                                                        return false; 
				}
			}
 
			for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
				for (int xtemp = x; xtemp > (x-xlen); xtemp--){
 
					if (xtemp == x) 
                                                        setMapElement(xtemp, ytemp, mapElementWall);
					else if (xtemp == (x-xlen+1)) 
                                                        setMapElement(xtemp, ytemp, mapElementWall);
					else if (ytemp == (y-ylen/2)) 
                                                        setMapElement(xtemp, ytemp, mapElementWall);
					else if (ytemp == (y+(ylen-1)/2)) 
                                                        setMapElement(xtemp, ytemp, mapElementWall);
 
					else setMapElement(xtemp, ytemp, mapElementFloor);
				}
			}
                }
		return true;
	}
 
 
	/** Metoda testowa wyświetlająca korytarz */
	public void testMapGenerator(){
		for (int y = 0; y < ysize; y++){
			for (int x = 0; x < xsize; x++){
				//System.out.print(getCell(x, y));
				switch(getMapElement(x, y)){
				case mapElementUnused:
					System.out.print(" ");
					break;
				case mapElementWall:
					System.out.print("#");
					break;
				case mapElementFloor:
					System.out.print(".");
					break;
				case mapElementLimit:
					System.out.print("O");
					break;
				case mapElementUnused2:
					System.out.print(" ");
					break;
				case mapElementDoor:
					System.out.print("D");
					break;
				case mapElementUpStairs:
					System.out.print("<");
					break;
				case mapElementDownStairs:
					System.out.print(">");
					break;
				case mapElementChest:
					System.out.print("*");
					break;
                                case mapElementHero:
                                        System.out.print("@");
                                        break;
                                case mapElementMonster:
                                        System.out.print("M");
                                        break;
				}
			}
			if (xsize <= xmax) System.out.println();
		}           
                System.out.println(msgXSize + xsize);
		System.out.println(msgYSize + ysize);
                System.out.println(msgNumRooms + room);
                System.out.println(msgNumMonsters + monster);           
	}
 
	/** 
         * Metoda generująca labirynt 
         * @param inx - wymiar x
         * @param iny - wymiar y
         * @param inobj - liczba obiektow do wygenerowania
         * @param inmonsters - maksymalna liczba potworów do wygenerowania
         * @return Jeżli zwróci true, to generowanie zostało zakończone sukcesem 
         */
	public boolean createMap(int inx, int iny, int inroom, int inmonsters){
		if (inroom <= 0) room = 10;
		else room = inroom;
 
                if (inmonsters <= 0)
                                monstersMax = 1;
                else if (inmonsters >= 10)
                                monstersMax = 10;
                else monstersMax = inmonsters;
                
		/** Dopasuj wielkość mapy do limitów jeżeli podane przez użytkownika wykraczają poza zakresy */
		if (inx < 3) // jeżeli zbyt mały
                            xsize = 3;
		else if (inx > xmax) //jeżeli zbyt duży
                            xsize = xmax;
		else xsize = inx;
 
		if (iny < 3)    
                            ysize = 3;
		else if (iny > ymax) 
                            ysize = ymax;
		else ysize = iny;

                monstersMax = getRand(2, monstersMax+1);
 
		/** Ustal wielkość tablicy, zgodnie z naszymi nowymi wymiarami */
		map = new int[xsize * ysize];
 
		/** Narysuj ściany oraz podłogę - postawowe elementy każdej z map */
		for (int tempy = 0; tempy < ysize; tempy++){
			for (int tempx = 0; tempx < xsize; tempx++){
				/** Tworzenie granic mapy */
                        	if (tempy == 0) setMapElement(tempx, tempy, mapElementLimit);
				else if (tempy == ysize-1) setMapElement(tempx, tempy, mapElementLimit);
                                else if (tempx == 0) setMapElement(tempx, tempy, mapElementLimit);
				else if (tempx == xsize-1) setMapElement(tempx, tempy, mapElementLimit);
				else setMapElement(tempx, tempy, mapElementUnused); /** Te elementy możemy następnie zagospodarować */
			}
		}
 
                /** Utwórz pokój pośrodku mapy, skąd następnie idziemy dalej */
		mapElementFloor(xsize/2, ysize/2, 8, 6, getRand(0,3));
 
		/** Licznik utworzonych obiektów */
		int madeRooms = 1; //+1 gdyż utworzyliśmy pomieszczenie
 
		/** Pętla próbująca rysować sieć korytarzy */
		for (int j = 0; j < 1000; j++){
			/** Sprawdzenie czy osiągnięto zadany limit pomieszczeń */
			if (madeRooms == room){
				break;
			}
 
			/* Zacznij z losową ścianą */
			int x = 0;
			int xmod = 0; // modyfikacja x po sprawdzeniu czy można rysować
			
                        int y = 0;
			int ymod = 0; // modyfikacja y po sprawdzeniu czy można rysować
			int validMapElement = -1;  // domyślnie niepoprawny
                        
                        /** Sprawdzenie czy w pobliżu można rysować */
			for (int k = 0; k < 1000; k++){
				x = getRand(1, xsize-1);
				y = getRand(1, ysize-1);
				validMapElement = -1;
				//System.out.println("tempx: " + newx + "\ttempy: " + newy);
				if (getMapElement(x, y) == mapElementWall){
					/** Sprawdzenie czy w ogóle można wejść kratkę dalej */ 
					if (getMapElement(x, y+1) == mapElementFloor ){
						validMapElement = 0; //
						xmod = 0;
						ymod = -1;
					}
					else if (getMapElement(x-1, y) == mapElementFloor){
						validMapElement = 1; //
						xmod = +1;
						ymod = 0;
					}
					else if (getMapElement(x, y-1) == mapElementFloor){
						validMapElement = 2; //
						xmod = 0;
						ymod = +1;
					}
					else if (getMapElement(x+1, y) == mapElementFloor){
						validMapElement = 3; //
						xmod = -1;
						ymod = 0;
					}
                                            
                                        /** Sprawdzenie, czy obok naszej nowej kratki nie leżą żadne drzwi, aby 
                                         * nie postawić kolejnych drzwi, albo potwora od razu obok nich 
                                        */
	
					if (validMapElement > -1){
						if (getMapElement(x, y+1) == mapElementDoor) 
							validMapElement = -1;
						else if (getMapElement(x-1, y) == mapElementDoor)
							validMapElement = -1;
						else if (getMapElement(x, y-1) == mapElementDoor)
							validMapElement = -1;
						else if (getMapElement(x+1, y) == mapElementDoor)
							validMapElement = -1;
					}
                                       
                                        /** Jeśli wszystko w okolicy jest w porządku, kończymy testowanie */ 
					if (validMapElement > -1) break;
				}
			}
			if (validMapElement > -1){
					if (mapElementFloor((x+xmod), (y+ymod), 10, 6, validMapElement)){
						madeRooms++; /** dodaj pokój do liczby zrobionych obiektów */
                                                /** Wstaw drzwi */
						setMapElement(x, y, mapElementDoor);
						/** Sprzątnij to co jest przed przed drzwiami */ 
						setMapElement((x+xmod), (y+ymod), mapElementFloor);
					}			
			}
		}
		/**
                 * Wypełniamy mapę elementami innymi niż ściany i podłoga - schody, potwory
                */
 
		int x1 = 0;
		int y1 = 0;
		int possibleWays = 0; /** Od ilu kierunków można osiągnąć miejsce losowe */
		int state = 0; /** Zmienna stanu pętli. 0 to schody */

                
		while (state != generatedEnd){
			for (int testing = 0; testing < 1000; testing++){
				x1 = getRand(1, xsize-1);
				y1 = getRand(1, ysize-2); 
 
				possibleWays = 4; //im mniejsza liczba, tym więcej jest dostępnych kierunków
 
				//Sprawdzenie czy można osiągnąć to miejsce
				if (getMapElement(x1, y1+1) == mapElementFloor){
				//północ
					if (getMapElement(x1, y1+1) != mapElementDoor)
					possibleWays--;
				}
				if (getMapElement(x1-1, y1) == mapElementFloor){
				//wschód
					if (getMapElement(x1-1, y1) != mapElementDoor)
					possibleWays--;
				}
				if (getMapElement(x1, y1-1) == mapElementFloor){
				//południe
					if (getMapElement(x1, y1-1) != mapElementDoor)
					possibleWays--;
				}
				if (getMapElement(x1+1, y1) == mapElementFloor){
				//zachód
					if (getMapElement(x1+1, y1) != mapElementDoor)
					possibleWays--;
				}
 
				if (state == generatedRooms){
					if ((possibleWays == 0) && (getMapElement(x1-2, y1)==mapElementFloor)  ){
                                            /** Jeśli stan jest równy 0, mozna wstawić schody do góry */
						setMapElement(x1, y1, mapElementUpStairs);
                                                setMapElement(x1-1, y1, mapElementHero);
						state = generatedUpStairs;  //utworzone schody do góry i obok bohater
						break;
					}
				}
  
                                /** Jeśli stan jest równy 1, mozna wstawić schody na dół */
                                else if (state == generatedUpStairs){
					if (possibleWays == 0){	
                                                setMapElement(x1, y1, mapElementDownStairs);
						state = generatedDownStairs; //utworzone schody w dol
						break;
					}
				}
                                else if (state == generatedDownStairs){
                                       if (possibleWays <= 1){	                        
                                               if((monster < monstersMax) && (getMapElement(x1,y1) == mapElementFloor)) {                                                  
                                                        /**następny wygenerowany potwór */
                                                        ++monster;
                                                        setMapElement(x1, y1, mapElementMonster);       
                                               }                                  
                                               else{
                                                   state = generatedEnd;
                                                   break;
                                               }         			 
					}     
                                } 
			}
		}
		return true;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
	public static void main(String[] args){
		int x = 30;
                int y = 20; 
                int rooms = 10;
                int monsters = 10;
                int[][] mapa; 

		MapGenerator generator = new MapGenerator();
	
		if (generator.createMap(x, y, rooms, monsters) == true){
                                   generator.testMapGenerator(); /** Metoda testowa */
                                    mapa = generator.getMap(); /** Metoda zwracająca mapę 2D */
                                    generator.writeMapToFile();
                }
        
     /*   
        
             Creature potwor = new Creature(3);
             Creature potwor3 = new Creature(3);
             Creature potwor4 = new Creature(2);
             Hero hero = new Hero();
        
             //potwor.getHit(hero.attack());
        
             
             
       //     for (int i = 0; i<4; ++i) System.out.println("Przykladowy atak: " + potwor.getHit(hero.attack()));                 
            
             while( potwor.isAlive() && hero.isAlive() ){
                
                    Thread healing = new Healing(hero);
                    Thread walka = new Fight(potwor, hero);
                    
             }
        
             while( potwor4.isAlive() && hero.isAlive() ){
                
                    Thread healing = new Healing(hero);
                    Thread walka2 = new Fight(potwor4, hero);
                    
             }
             
             while( potwor3.isAlive() && hero.isAlive() ){
                
                    Thread healing = new Healing(hero);
                    Thread walka2 = new Fight(potwor3, hero);
                    
             }
             
             System.out.println(hero.getLife());
             
             
             hero.testcreated();
             
             
             System.out.println();
             /*
           do{
                 System.out.println("Otrzymane obrazenia po ataku bohatera: " + potwor.getHit( hero.attack() ));
                 System.out.println("Życie potwora: " + potwor.getLife());     
                 System.out.println("Otrzymane obrazenia po kontrataku potwora: " + hero.getHit( potwor.attack())  );
                 System.out.println("Życie bohatera: " + hero.getLife());
           }while( potwor.isAlive() && hero.isAlive() );
           
           if(potwor.isAlive() == false){
                    hero.addExperience(potwor.getExperience());
                    System.out.println("dodano doswiadczenie"); 
                    Item[] loot = potwor.getItems(); //system zbierania przedmiotow
                     for(int i=0; i<loot.length; ++i)
                            System.out.println(i +" " +loot[i]);
                 }
           */
           
           
     /*   
        if(isAlive() == false){
                     Item[] loot = getItems();
                     for(int i=0; i<loot.length; ++i)
                            System.out.println(i +" " +loot[i]);
                 }
             */
             
             
             
        
        
        }        
                   
             
}