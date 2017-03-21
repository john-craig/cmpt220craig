import java.util.Scanner;
import java.util.ArrayList;

public class Dungeon_Crawler{
	public static void main(String[] args) {
		boolean gameOver = false;
		Player player = new Player();
		
		while(!gameOver){
			player.playerUI();
			
			player.killPlayer();
			gameOver = !player.getAlive();
		}
	}
}

class Player{
	private Scanner input;
	private Character player;
	private int[] position;

	public Player(){
		input = new Scanner(System.in);
		int t = getSelection(-1);
		player = new Hero(t, 1);
	}
	
	public void playerUI(){
		int a = getSelection(0);
		int b = getSelection(a+1);
		if(b == 0){playerUI();}
		else{
			switch(a){
				case 1: break;
				case 2: break;
			}
		}
	}
	
	private int getSelection(int i){
		switch(i){
				case -1: System.out.println("Chose your class:\n\t1. Warrior\n\t2. Rogue\n\t3. Mage"); break;
				case 0: System.out.println("What will you do?\n\t1. Check Equipped\n\t2. Check Inventory\n\t3. Check Status\n\t4. Inspect Room"); break;
				case 1: viewEquipped(); break;
				case 2: viewInventory(); break;
				case 3: break;
				case 4: break;
		}
		System.out.print("-->");
		return input.nextInt() - 1;
	}
	
	private void viewEquipped(){
		Item[] equipped = player.getEquipped();
		System.out.println("Select an item, or enter 0 to go back:");
		for(int i = 0;i<equipped.length;i++){
			System.out.println("\t" + (i+1) + ". " + equipped[i]);
		}
	}
	
	private void viewInventory(){
		ArrayList<Item> inventory = player.getInventory();
		System.out.println("Select an item, or enter 0 to go back:");
		for(int i = 0;i<inventory.size();i++){
			System.out.println("\t" + (i+1) + ". " + inventory.get(i));
		}
	}
	
	private void interactItem(){
		
	}
	
	public boolean getAlive(){return player.getAlive();}
	
	public void killPlayer(){player.damage(200);}
}

class Character{
	protected boolean alive;
	protected int level;
	protected int type;
	protected int hp[];
	protected int mp[];
	protected int stats[];
	protected Item[] equipment;
	protected ArrayList<Item> inventory;

	public Character(){
		alive = true;
		hp = new int[3];
		mp = new int[3];
		stats = new int[6];
		equipment = new Item[10];
		inventory = new ArrayList<Item>();
	}
	
	public boolean getAlive(){
		alive = hp[0] > 0;
		return alive;
	}
	
	public int[] getHP(){return hp;}
	public void setHP(int[] n){hp = n;}
	
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
	
	public ArrayList<Item> getInventory(){return inventory;}
	public void setInventory(ArrayList<Item> n){inventory = n;}
	public void modifyInventory(int n, Item i){
		if(n == 0){inventory.add(i);}
		else{inventory.remove(i);}
	}
}

class Hero extends Character{
	
	public Hero(int type){
		super.type = type;
		super.level = 1;
		setupType();
	}
	
	public Hero(int type, int level){
		super.type = type;
		super.level = level;
		setupType();
	}
	
	private void setupType(){
		switch(super.type){
			case 0:
				super.hp[2] = 12; //This sets the "base" HP
				this.modifyInventory(0, new Weapon(super.level, 2));
				this.modifyInventory(0, new Armor(super.level, 1, 2));
				this.modifyInventory(0, new Armor(super.level, 2, 4));
				this.modifyInventory(0, new Armor(super.level, 1, 6));
				this.modifyInventory(0, new Armor(super.level, 1, 7));
				break;
		
			case 1:
				super.hp[2] = 8; //This sets the "base" HP
				this.modifyInventory(0, new Weapon(super.level, 1));
				this.modifyInventory(0, new Armor(super.level, 0, 2));
				this.modifyInventory(0, new Armor(super.level, 1, 4));
				break;
			
			case 2:
				super.hp[2] = 4; //This sets the "base" HP
				super.mp[2] = 5; //This gives the mage Mana Points
				super.mp[1] = mp[2] * level;
				super.mp[0] = mp[1];
				this.modifyInventory(0, new Armor(super.level, -1, 8));
				break;
		}

		super.hp[1] = hp[2] * level;
		super.hp[0] = hp[1];
	}
}

class Nonplayer extends Character{
	
	public Nonplayer(int type){
		super.type = type;
	}
}

//class Enemy extends Character{}

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
		int[] prevExit;

		floors[0] = new Floor();
		for(int i = 1;i<5;i++){
			prevExit = floors[i-1].getExit();
			floors[i] = new Floor(prevExit[0],prevExit[1]);
		}
		prevExit = floors[4].getExit();
		floors[4] = new Floor(prevExit[0],prevExit[1],1);
	}
	
	public Floor[] getFloors(){return floors;}
}

class Floor{
	private int type;
	private Room[][] rooms;
	private int[][] layout;
	private int[] start;
	private int[] exit;

	public Floor(){
		type = 0;
		rooms = new Room[5][5];
		start = new int[2];
		start[0] = Utility.random(5) - 1;
		start[1] = Utility.random(5) - 1;
		exit = new int[2];
		layout = Floor.generateLayout(start[0],start[1]);
		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				if(layout[i][j] == 1){rooms[i][j] = new Room();}
				else if(layout[i][j] == 2){
					rooms[i][j] = new Room(1);
					exit[0] = i;
					exit[1] = j;
				}
			}
		}
	}

	public Floor(int x, int y){
		type = 0;
		rooms = new Room[5][5];
		start = new int[2];
		start[0] = x;
		start[1] = y;
		exit = new int[2];
		layout = Floor.generateLayout(x,y);

		for(int i = 0;i<5;i++){
			for(int j=0;j<5;j++){
				if(layout[i][j] == 1){rooms[i][j] = new Room();}
				else if(layout[i][j] == 2){
					rooms[i][j] = new Room(1);
					exit[0] = i;
					exit[1] = j;
				}
			}
		}
	}

	public Floor(int x, int y, int n){
		type = 1;
		start = new int[2];
		start[0] = x;
		start[1] = y;
		exit = new int[2];
		rooms = new Room[5][5];
		layout = Floor.generateLayout(x,y);

		for(int i = 0;i<5;i++){
			for(int j=0;j<5;j++){
				if(layout[i][j] == 1){rooms[i][j] = new Room();}
				else if(layout[i][j] == 2){
					rooms[i][j] = new Room(2);
					exit[0] = i;
					exit[1] = j;
				}
			}
		}
	}

	public static int[][] generateLayout(int x, int y){ //This method generates the layout for the dungeon's rooms
		int num = Utility.random(3,5) * Utility.random(3,5); //This decides how many rooms there will be in the dungeon
		int[][]  layout = new int[5][5];

		for(int i  = 0;i<layout.length;i++){
			for(int j = 0;j<layout[0].length;j++){layout[i][j] = 0;}
		}
		layout[x][y] = 1;

		int newX = -1;
		int newY = -1;

		for(int i = 0;i<num;i++){
			boolean valid = false;

			while(!valid){ //This while loop makes sure that the next position for a room will be adjacent to a previous room, and not in the same position as another room
				newX = Utility.random(5) - 1;		
				newY = Utility.random(5) - 1;
				boolean[] valids = new boolean[4];

				if(newX - 1 >= 0){valids[0] = (layout[newX-1][newY]>0) ? true : false;}
				if(newX + 1 < 5){valids[1] = (layout[newX+1][newY]>0) ? true : false;}
				if(newY - 1 >= 0){valids[2] = (layout[newX][newY-1]>0) ? true : false;}
				if(newY + 1 < 5){valids[3] = (layout[newX][newY+1]>0) ? true : false;}

				valid = valids[0] || valids[1] || valids[2] || valids[3];
			}
			if(layout[newX][newY] == 0){
				layout[newX][newY] = 1;
			}
		}

		layout[newX][newY] = 2; //This makes it so that the last room is set as "2" instead of "1"; this designates it as the exit

		return layout;
	}

	int getType(){return type;}
	int[] getStart(){return start;}
	int[] getExit(){return exit;}
	int[][] getLayout(){return layout;}
}

class Room{
	private int type;

	public Room(){
		type = Utility.random(5);
	}
	public Room(int n){
		type = (n * -1) + 1;
	}

}

class Utility{
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
}