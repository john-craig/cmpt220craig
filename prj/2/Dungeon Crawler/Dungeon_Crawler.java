import java.util.Scanner;
import java.util.ArrayList;

public class Dungeon_Crawler{
	public static void main(String[] args) {
		boolean gameOver = false;
		Player player = new Player();
		Level level = new Level();
		
		while(!gameOver){
			player.playerUI();
		}
	}
}

class Player{
	private Scanner input;
	private Hero player;
	private int floor;
	private int[] position;
	
	public Player(){
		input = new Scanner(System.in);
		floor = 0;
		
		System.out.println("What is your name, adventurer?");
		System.out.print("-->");
		
		String name = input.next();
		
		System.out.println("Chose your class:\n\t1. Warrior\n\t2. Rogue\n\t3. Mage");
		System.out.print("-->");
		
		int t = input.nextInt() - 1;
		player = new Hero(t, 1);
		player.setName(name);
	}
	
	public void playerUI(){
		System.out.println("Select an action:\n\t1. Check Equipped\n\t2. Check Inventory\n\t3. Check Status\n\t4. Inspect Room");
		System.out.print("-->");
		choice = input.nextInt();
			
		switch(choice){
			case 1: viewEquipped(); break;
			case 2: viewInventory(); break;
			case 3: viewStatus(); break;
		}
	}
	
	private void viewEquipped(){
		Item[] equipped = player.getEquipped();
		int choice = -1;
		
		while(choice != 0){
			equipped = player.getEquipped();
			
			System.out.println("Select an item, or enter 0 to go back:");
			int n = 0;
			
			for(int i = 0;i<equipped.length;i++){
				if(equipped[i] != null){System.out.println("\t" + (i+1) + ". " + equipped[i]);}
				else{
					System.out.println("\t" + (i+1) + ". Empty");
					n++;
				}
			}
			System.out.print("-->");
			choice = input.nextInt();
			
			if(choice>0){interactItem(equipped[choice-1], 0);}
		}
	}
	
	private void viewInventory(){
		ArrayList<Item> inventory = player.getInventory();
		int choice = -1;
		
		while(choice != 0){
			inventory = player.getInventory();
			
			System.out.println("Select an item, or enter 0 to go back:");
			for(int i = 0;i<inventory.size();i++){
				System.out.println("\t" + (i+1) + ". " + inventory.get(i));
			}
			System.out.println("Gold: " + player.getGold());
			System.out.print("-->");
			choice = input.nextInt();
			
			if(choice>0){interactItem(inventory.get(choice-1), 1);}
		}
	}
	
	private void viewStatus(){
		int[] xp = player.getExperience();
		int[] hp = player.getHP();
		int[] mp = player.getMP();
		
		if(mp[2] > 0){
			System.out.println(player + " with " + hp[0] + "/" + hp[1] + " Health Points, " + mp[0] + "/" + mp[1] + " Mana Points, and a progress of " + xp[0] + "/" + xp[1] + " to the next Level");
		}
		else{
			System.out.println(player + " with " + hp[0] + "/" + hp[1] + " Health Points, and a progress of " + xp[0] + "/" + xp[1] + " to the next Level");
		}
		for(int i=0;i<6;i++){
			System.out.println("\t" + Utility.getStatName(i) + ": " + player.getStat(i));
		}
		System.out.print("-->");
		input.next();
	}
	
	private void interactItem(Item n, int m){
		int choice = -1;
		
		System.out.println(n.getDescription());
		System.out.println("Select an option, or enter 0 to go back:");
			
		if(n.getSlot() >= 0){
			if(m == 0){
				System.out.println("\t1. Unequip Item\n\t2. Drop Item");
				System.out.print("-->");
				choice = input.nextInt();
			
				if(choice == 1){player.unEquip(n);}
				else{player.modifyInventory(1, n);}
			}
			else{
				System.out.println("\t1. Equip Item\n\t2. Drop Item");
				System.out.print("-->");
				choice = input.nextInt();
				
				if(choice == 1){player.setEquipped(n);}
				else{player.modifyInventory(1, n);}
			}
		}
		else if(n.getSlot() == -2){
			System.out.println("\t1. Use Item\n\t2. Drop Item");
			System.out.print("-->");
			choice = input.nextInt();
		}
		else{
			System.out.println("\t1. Drop Item");
			System.out.print("-->");
			choice = input.nextInt();
		}
	}
	
	public boolean getAlive(){return player.getAlive();}
	
	public void killPlayer(){player.damage(200);}
}

class Character{
	protected boolean alive;
	protected int level;
	protected int type;
	protected int gold;
	protected int hp[];
	protected int mp[];
	protected int stats[];
	protected Item[] equipment;
	protected ArrayList<Item> inventory;
	protected ArrayList<Hero> allies;
	protected String name;

	public Character(){
		alive = true;
		hp = new int[3];
		mp = new int[3];
		stats = new int[6];
		equipment = new Item[10];
		inventory = new ArrayList<Item>();
		allies = new ArrayList<Hero>();
	}
	
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	
	public int getLevel(){return level;}
	public void setLeveL(int level){this.level = level;}
	
	public int[] getStats(){return stats;}
	public int getStat(int n){return stats[n];}
	
	public int getGold(){return gold;}
	
	public boolean getAlive(){
		alive = hp[0] > 0;
		return alive;
	}
	
	public int[] getHP(){return hp;}
	public void setHP(int[] n){hp = n;}
	
	public int[] getMP(){return mp;}
	public void setMP(int[] n){mp = n;}
	
	public void heal(int amt){
		hp[0] += amt;
		if(hp[0]>hp[1]){hp[0] = hp[1];}
	}
	public void damage(int amt){
		hp[0] -= amt;
		if(hp[0]<1){alive = false;}
	}
	
	public boolean equipItem(Item x){
		int n = x.getSlot();
		if(n > -1){
			if(equipment[n]!=null){
				inventory.add(equipment[n]);
				equipment[n] = x;
			}
			else{equipment[n] = x;}
			return true;
		}
		else{return false;}
	}
	public Item[] getEquipped(){return equipment;}
	public void setEquipped(Item[] x){equipment = x;}
	public void setEquipped(Item x){
		if(equipment[x.getSlot()] != null){ //This checks if the equipment slot of an item is already occupied, and if it is, adds the item currently there back to the player's inventory
			inventory.add(equipment[x.getSlot()]);
			inventory.remove(x);
			equipment[x.getSlot()] = x;
		}
		else{
			inventory.remove(x);
			equipment[x.getSlot()] = x;
		}
	}
	public void unEquip(Item x){
		inventory.add(x);
		equipment[x.getSlot()] = null;
	}
	
	public ArrayList<Item> getInventory(){return inventory;}
	public void setInventory(ArrayList<Item> n){inventory = n;}
	public void modifyInventory(int n, Item i){
		if(n == 0){inventory.add(i);}
		else{inventory.remove(i);}
	}
	
	public String toString(){return name + " is a Level " + level + " Character";}
}

class Hero extends Character{
	private int[] xp;
	
	public Hero(int type){
		xp = new int[2];
		super.type = type;
		super.level = 1;
		setupHero();
	}
	
	public Hero(int type, int level){
		xp = new int[2];
		super.type = type;
		super.level = level;
		setupHero();
	}
	
	private void setupHero(){
		xp[0] = 0;
		xp[1] = 1000 * level;
		super.name = "(Insert random name here)";
		super.gold = 50 * level;
		setupType();
	}
	
	private void setupType(){
		Item[] startingEquipment = new Item[10];
		
		for(int i=0;i<6;i++){ //This sets each of the character's stats so that the minimum number is 9 and the maximum is 18
			int stat = 6;
			for(int j=0;j<3;j++){stat += Utility.random(4);}
			super.stats[i] = stat;
		}
		
		switch(super.type){
			case 0:
				super.hp[2] = 12; //This sets the "base" HP
				startingEquipment[0] = new Weapon(super.level, 2);
				startingEquipment[2] = new Armor(super.level, 1, 2);
				startingEquipment[4] = new Armor(super.level, 2, 4);
				startingEquipment[6] = new Armor(super.level, 1, 6);
				startingEquipment[7] = new Armor(super.level, 1, 7);
				break;
		
			case 1:
				super.hp[2] = 8; //This sets the "base" HP
				startingEquipment[1] = new Weapon(super.level, 1);
				startingEquipment[2] = new Armor(super.level, 0, 2);
				startingEquipment[4] = new Armor(super.level, 1, 4);
				break;
			
			case 2:
				super.hp[2] = 4; //This sets the "base" HP
				super.mp[2] = 5; //This gives the mage Mana Points
				super.mp[1] = mp[2] * level;
				super.mp[0] = mp[1];
				startingEquipment[8] = new Armor(super.level, -1, 8);
				break;
		}
		super.hp[1] = hp[2] * level;
		super.hp[0] = hp[1];
		this.setEquipped(startingEquipment);
	}

	public int[] getExperience(){return xp;}
	
	public String toString(){
		String output = super.name + " is a Level " + super.level;
		
		switch(super.type){
			case 0: output = output + " Warrior"; break;
			case 1: output = output + " Rogue"; break;
			case 2: output = output + " Mage"; break;
		}
		
		return output;
	}
}

class Nonplayer extends Character{
	
	public Nonplayer(int type){
		super.type = type;
	}
}

class Enemy extends Character{
	
	public Enemy(){
		
	}
}

class Item{
	protected String name;
	protected int tier;
	protected int slot; 
	//Slot 0 and 1 are for handheld items; Slot 2 is for Boots, Slot 3 is for Leg Armor, Slot 4 is for Chest Armor,
	//Slot 5 is for Shoulder Armor, Slot 6 is for Gauntlets, Slot 7 is for Helmets, Slot 8 is for Robes, Slot 9 is Misc.
	protected int value;

	public Item(){
		tier = 1;
		setupItem(tier);
		name = "Item";
	}
	
	public Item(int tier){
		this.tier = tier;
		setupItem(tier);
		name = "Item";
	}
	
	public Item(int tier, String name){
		this.tier = tier;
		setupItem(tier);
		this.name = name;
	}
	
	private void setupItem(int tier){
		value = (tier * 20) + (tier * Utility.random(5));
	}

	public int getSlot(){return slot;}
	public int getValue(){return value;}
	
	public String toString(){return name;}
	public String getDescription() {return name + "is a tier " + tier + " item worth " + value;}
}

class Weapon extends Item{
	protected boolean twoHanded;
	protected int type;
	protected int damage;
	protected int range;
	
	public Weapon(int tier){
		super(tier);
		this.type = Utility.random(5);
		setupWeapon();
		setupName();
	}
	
	public Weapon(int tier, int type){
		super(tier);
		this.type = type;
		setupWeapon();
		setupName();
	}
	
	private void setupWeapon(){
		switch(type){
			case 0: //Primary-handed melee weapons
				super.slot = 0;
				twoHanded = false;
				damage = (tier * 15) + (Utility.random(5) * tier);
				range = 5;
				break;
			case 1: //Offhanded melee weapons
				super.slot = 1;
				twoHanded = false;
				damage = (tier * 10) + (Utility.random(5) * tier);
				range = 5;
				break;
			case 2: //Two-handed melee weapons
				super.slot = 0;
				twoHanded = true;
				damage = (tier * 20) + (Utility.random(10) * tier);
				range = 5;
				break;
			case 3: //One-handed ranged weapons
				super.slot = 0;
				twoHanded = false;
				damage = (tier * 5) + (Utility.random(5) * tier);
				range = 15;
				break;
			case 4: //Two-handed ranged weapons
				super.slot = 0;
				twoHanded = true;
				damage = (tier * 10) + (Utility.random(5) * tier);
				range = 25;
				break;
		}
	}

	private void setupName(){super.name = Utility.getItemName(0,type);}
	public String getDescription(){return super.name + " is a tier " + tier + " weapon worth " + super.value + " which deals " + damage + " damage and has a range of " + range;}
	
}

class Armor extends Item{
	protected int type;
	protected int dodge;
	protected int defense;
	
	public Armor(int tier){
		super(tier);
		type = Utility.random(3);
		super.slot = Utility.random(7) + 1;
		setupArmor();
		setupName();
	}
	
	public Armor(int tier, int type){
		super(tier);
		this.type = type;
		super.slot = Utility.random(7) + 1;
		setupArmor();
		setupName();
	}
	public Armor(int tier, int type, int slot){
		super(tier);
		this.type = type;
		super.slot = slot;
		setupArmor();
		setupName();
	}
	
	private void setupArmor(){
		switch(type){
			case -1: //No armor
				dodge = 0;
				defense = 0;
				break;
			case 0: //Light Armor
				dodge = (tier * 10) + (tier * Utility.random(5));
				defense = tier;
				break;
			case 1: //Medium Armor
				dodge = (tier * 5) + Utility.random(5);
				defense = (tier * 5) + Utility.random(5) ;
				break;
			case 2: //Heavy Armor
				dodge = tier;
				defense = (tier * 10) + (tier * Utility.random(5));
				break;
		}
	}

	private void setupName(){super.name = Utility.getItemName(1,slot-2);}
	public String getDescription(){return super.name + " is a tier " + tier + " piece of armor worth " + super.value + " with a dodge rating of " + dodge + " and a defense rating of " + defense;}
}

class Spellbook extends Item{
	
}

class Consumable extends Item{
	
}

class Level{
	private Floor[] floors;

	public Level(){
		floors = new Floor[5];
		setupFloor();
	}
	
	private void setupFloor(){
		int[] prevExit;

		floors[0] = new Floor();
		for(int i = 1;i<5;i++){
			prevExit = floors[i-1].getExit();
			
			if(Utility.random(4) < 3){floors[i] = new Floor(prevExit[0],prevExit[1], 0);}
			else{floors[i] = new Floor(prevExit[0],prevExit[1], 1);}
		}
		prevExit = floors[4].getExit();
		floors[4] = new Floor(prevExit[0],prevExit[1],2);
	}
	
	public Floor[] getFloors(){return floors;}
}

class Floor{
	private int type; //0 indicates a Hostile Floor with enemies, 1 indicates a Friendly Floor with villagers; 2 indicates a Boss Floor
	private Room[][] rooms;
	private int[][] layout;
	private int[] start;
	private int[] exit;
	private int amtOfRooms;

	public Floor(){
		type = 0;
		rooms = new Room[5][5];
		start = new int[2];
		start[0] = Utility.random(5) - 1;
		start[1] = Utility.random(5) - 1;
		exit = new int[2];
		layout = generateLayout(start[0],start[1]);
		setupLayout(0);
	}

	public Floor(int x, int y, int n){
		type = n;
		start = new int[2];
		start[0] = x;
		start[1] = y;
		exit = new int[2];
		rooms = new Room[5][5];
		layout = generateLayout(x,y);
		setupLayout(n);
	}

	private int[][] generateLayout(int x, int y){ //This method generates the layout for the dungeon's rooms
		amtOfRooms = Utility.random(3,5) * Utility.random(3,5); //This decides how many rooms there will be in the dungeon
		int[][]  layout = new int[5][5];

		for(int i  = 0;i<layout.length;i++){
			for(int j = 0;j<layout[0].length;j++){layout[i][j] = 0;}
		}
		layout[x][y] = -1;

		int newX = -1;
		int newY = -1;

		for(int i = 0;i<amtOfRooms;i++){
			boolean valid = false;

			while(!valid){ //This while loop makes sure that the next position for a room will be adjacent to a previous room, and not in the same position as another room
				newX = Utility.random(5) - 1;		
				newY = Utility.random(5) - 1;
				boolean[] valids = new boolean[4];

				if(newX - 1 >= 0){valids[0] = layout[newX-1][newY] != 0;}
				if(newX + 1 < 5){valids[1] = layout[newX+1][newY] != 0;}
				if(newY - 1 >= 0){valids[2] = layout[newX][newY-1] != 0;}
				if(newY + 1 < 5){valids[3] = layout[newX][newY+1] != 0;}
				
				if(i < (i/2)){
					valid = valids[0] || valids[1] || valids[2] || valids[3];
				}
				else{
					valid = (valids[0] && !valids[1] && !valids[2] && !valids[3]) || (!valids[0] && valids[1] && !valids[2] && !valids[3]) || (!valids[0] && !valids[1] && valids[2] && !valids[3]) || (!valids[0] && !valids[1] && !valids[2] && valids[3]);	
				}
			}
			if(layout[newX][newY] == 0){
				layout[newX][newY] = 1;
			}
		}

		layout[newX][newY] = 2; //This makes it so that the last room is set as "2" instead of "1"; this designates it as the exit

		return layout;
	}

	private void setupLayout(int n){
		int lastRoom = 2;
		if(n == 2){lastRoom = 3;}
		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				switch(layout[i][j]){
					case -1: rooms[i][j] = new Room(-1); break;
					case 0: break;
					case 1: if(n == 1){rooms[i][j] = new Room(1);} else{rooms[i][j] = new Room(0);} break;
					case 2: rooms[i][j] = new Room(lastRoom); exit[0] = i; exit[1] = j; break;
				}
				rooms[i][j].setPos(i,j);
				rooms[i][j].setLayout(layout);
			}
		}
	}

	int getType(){return type;}
	int[] getStart(){return start;}
	int[] getExit(){return exit;}
	int[][] getLayout(){return layout;}
}

class Room{
	private int type; //-1 indicates a Start Room, which is always safe and always empty; 0 indicates a Hostile Room; 1 indicates a Friendly Room; 2 indicates an Exit Room; 3 indicates a Boss Room
	private int subtype;
	private int[] position;
	private String description;
	private ArrayList<Character> characters;
	
	public Room(int n){
		type = n;
		positioni = new int[2];
		availableDirections = new boolean[4];
		characters = new ArrayList<Character>();
		setupRoom();
	}
	
	private void setupRoom(){
		subtype = 0;
		
		switch(type){
			case 0: subtype = Utility.random(3) - 1; break;
			case 1: subtype = Utility.random(3) - 1; break;
		}
		
		description = Utility.getRoomDesc(type + 1, subtype);
	}

	public void setPos(int x, int y){
		position[0] = x;
		position[1] = y;
	}
	
	public int getType(){return type;}
}

class Utility{//The Utility class largely exists for the sole purpose of storing miscellaneas data which would be cumbersome to actually store in the other classes themselves
	private static String[] statName = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
	private static String[][] characterTypes = {
		{"Warrior","Rogue","Mage"},
		{"Villager","Merchant"},
		{"Giant Spider","Goblin","Skeleton"}
	};
	private static String[][] itemTypes = {
		{"Sword","Knife","Claymore","Crossbow","Longbow"},
		{"Boots","Greaves","Chestplate","Pauldrons","Gauntlets","Helmet","Robe"},
		{""}
	};
	private static String[][] roomDescriptions = {
		{"Empty Room Starting Room"},
		{"Empty Room","Room filled with Enemies","Room with a Trap"},
		{"Empty Room","Merchant Room","Tavern Room"},
		{"Empty Exit Room"},
		{"Boss Room"}
	};
	
	public static int random(int max){
		int num = (int)(Math.random() * max) + 1;
		return num;
	}

	public static int random(int min, int max){
		int num = (int)(Math.random() * (min - max)) + 1 + min;
		return num;
	}
	
	public static String getCharacterName(int a, int b){return characterTypes[a][b];}
	public static String getItemName(int a, int b){return itemTypes[a][b];}
	public static String getRoomDesc(int a, int b){return roomDescriptions[a][b];}
	public static String getStatName(int a){return statName[a];}
}