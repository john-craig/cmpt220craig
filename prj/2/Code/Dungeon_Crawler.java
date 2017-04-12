import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;

public class Dungeon_Crawler extends Application{
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override public void start(Stage primaryStage){
		Scene scene = new Scene(Utility.setupTilePane(new Image("Images/background_tile.png"), 16, 24), 1200, 800);
		scene.setOnKeyPressed(e ->{
			if(e.getCode() == KeyCode.ESCAPE){primaryStage.close();}
		});
		
		Game game = new Game(scene);
		
		primaryStage.setTitle("Dungeon Crawler");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

class Game{
	private Scene scene;
	private Pane rootPane;
	private Level level;
	private Floor currentFloor;
	private Room currentRoom;
	private int[] currentPosition;
	private int currentFloorNumber;
	private Player player;
	private boolean gameOver;
	
	public Game(Scene scene){
		this.scene = scene;
		rootPane = new Pane();
		gameOver = false;
		level = new Level();
		currentFloor = level.getFloor(0);
		currentFloorNumber = 0;
		currentPosition = currentFloor.getStart();
		currentRoom = currentFloor.getRoom(currentPosition);
		player = new Player();
		this.setScene(0);
	}
	
	private void setupGame(){
		player.setRoomPosition(currentPosition);
		player.setRoom(currentFloor.getRoom(currentPosition[0], currentPosition[1]));
		this.setScene(1);
	}	
	
	private void progressGame(){
		currentFloor = level.getFloor(currentFloorNumber);
		currentRoom = currentFloor.getRoom(currentPosition);
		this.setScene(2);
	}
	
	private void setScene(int n){
		Pane defaultPane = new Pane();
		
		Image image = new Image("Images/background_tile.png");
		Image image2 = new Image("Images/background_tile2.png");
		
		TilePane backgroundTop = Utility.setupTilePane(image, 3, 24);
		backgroundTop.relocate(0,0);
		
		TilePane backgroundMid = Utility.setupTilePane(image2, 10, 24);
		backgroundMid.relocate(0, 150);
		
		TilePane backgroundBottom = Utility.setupTilePane(image, 3, 24);
		backgroundBottom.relocate(0, 650);
		
		defaultPane.getChildren().addAll(backgroundTop, backgroundMid, backgroundBottom);
		
		switch(n){
			case 0: 
				defaultPane.getChildren().add(setupStart());
				break;
			case 1:
				defaultPane.getChildren().add(setupSelection());
				break;
			case 2:
				defaultPane.getChildren().add(setupMainScreen());
				
				scene.setOnKeyPressed(e -> {
					player.getCharacter().moveCharacter(e.getCode());
					travel(e.getCode());
				});
				break;
			case 3:
				defaultPane.getChildren().add(setupViewEquipment());
				break;
			case 4:
				defaultPane.getChildren().add(setupViewInventory());
				
				break;
			case 5:
				defaultPane.getChildren().add(setupViewStatus());
				break;
			case 6:
				defaultPane.getChildren().add(setupViewMap());
				break;
			case 7:
				
		}
	
		rootPane = defaultPane;
		scene.setRoot(rootPane);
	}
	
	private Pane setupStart(){
		Pane pane = new Pane();
		
		ImageView title = Utility.setupImage("Images/title.png");
		title.relocate(200, 200);
		title.setFitWidth(800);
		
		StackPane button1 = Utility.setupButton("Start Game");
		button1.relocate(550, 700);
		button1.setOnMouseReleased(e -> {setupGame();});
		
		pane.getChildren().addAll(button1, title);
		return pane;
	}
	
	private Pane setupSelection(){
		Pane pane = new Pane();

		for(int i = 0;i<3;i++){
			ImageView scroll = Utility.setupImage("Images/Scroll.png");
			scroll.setFitWidth(100);
			scroll.setX(400 + (i * 150));
			scroll.setY(258);
			pane.getChildren().add(scroll);
		}
		
		StackPane button1 = Utility.setupButton("Warrior");
		button1.relocate(400, 450);
		button1.setOnMouseReleased(e -> {
			player.setCharacter(new Hero(0, 1));
			progressGame();
		});
		
		StackPane button2 = Utility.setupButton("Rogue");
		button2.setOnMouseReleased(e -> {
			player.setCharacter(new Hero(1, 1));
			progressGame();
		});
		button2.relocate(550, 450);
		
		StackPane button3 = Utility.setupButton("Mage");
		button3.setOnMouseReleased(e -> {
			player.setCharacter(new Hero(2, 1));
			progressGame();
		});
		button3.relocate(700, 450);
				
		ImageView warriorImage = Utility.setupImage("Images/Warrior.png");
		warriorImage.setFitWidth(100);
		warriorImage.setX(400);
		warriorImage.setY(250);
		
		ImageView rogueImage = Utility.setupImage("Images/Rogue.png");
		rogueImage.setFitWidth(100);
		rogueImage.setX(550);
		rogueImage.setY(250);
		
		ImageView mageImage = Utility.setupImage("Images/Mage.png");
		mageImage.setFitWidth(100);
		mageImage.setX(700);
		mageImage.setY(250);
		
		pane.getChildren().addAll(warriorImage, button1, rogueImage, button2, mageImage, button3);
		return pane;
	}
	
	private Pane setupMainScreen(){
		Pane pane = new Pane();
		ArrayList<Character> characters = currentRoom.getCharacters();
		
		StackPane button1 = Utility.setupButton("Check\n Equipment");
		button1.setOnMouseReleased(e -> {setScene(3);});
		button1.relocate(250, 700);
		
		StackPane button2 = Utility.setupButton("Check\n Inventory");
		button2.setOnMouseReleased(e -> {setScene(4);});
		button2.relocate(450, 700);
		
		StackPane button3 = Utility.setupButton("Check Status");
		button3.setOnMouseReleased(e -> {setScene(5);});
		button3.relocate(650, 700);
		
		StackPane button4 = Utility.setupButton("Check Map");
		button4.setOnMouseReleased(e -> {setScene(6);});
		button4.relocate(850, 700);
		
		Pane setting = new Pane();
		setting.getChildren().add(currentRoom.getBackground());
		setting.relocate(200, 150);
		
		pane.getChildren().addAll(button1, button2, button3, button4, setting, player.getCharacter().getActor());
		for(int i=0;i<characters.size();i++){
			pane.getChildren().add(characters.get(i).getActor());
		}
		return pane;
	}
	
	private Pane setupViewEquipment(){
		Pane pane = new Pane();
		Item[] equipped = player.getCharacter().getEquipped();
		
		for(int i=0;i<2;i++){
			ImageView image = Utility.setupImage("Images/inventory_panel.png");
			image.relocate(200 + (i * 400), 150);
			pane.getChildren().add(image);
		}
		
		Pane equipmentList = new Pane();
		equipmentList.relocate(675, 200);
		
		for(int i=0;i<2;i++){
			StackPane category;
			if(i>0){category = Utility.setupButton("Armor");}
			else{category = Utility.setupButton("Weapons");}
			category.relocate(i * 150, 0);
			equipmentList.getChildren().add(category);
		}
		
		Pane weapons = new Pane();
		weapons.relocate(0, 75);
		Pane armor = new Pane();
		armor.relocate(150, 75);
		for(int i=0;i<equipped.length;i++){
			if(equipped[i] != null){
				if(i<2){
					Weapon temp = (Weapon)equipped[i];
					Text itemInfo = new Text(5, weapons.getChildren().size() * 50, temp.toString() + "\n\tDamage: " + temp.getDamage() + "\n\tRange: " + temp.getRange());
					itemInfo.setWrappingWidth(100);
					weapons.getChildren().add(itemInfo);
				}
				else{
					Armor temp = (Armor)equipped[i];
					Text itemInfo = new Text(5, armor.getChildren().size() * 50, temp.toString() + "\n\tDefense: " + temp.getDefense() + "\n\tDodge: " + temp.getDodge());
					itemInfo.setWrappingWidth(100);
					armor.getChildren().add(itemInfo);
				}
			}
		}
		equipmentList.getChildren().addAll(weapons, armor);
		
		Pane equipmentModel = player.viewEquipped();
		equipmentModel.relocate(300, 200);
		
		StackPane button1 = Utility.setupButton("Back");
		button1.relocate(550, 700);
		button1.setOnMouseReleased(e -> {setScene(2);});
		
		pane.getChildren().addAll(equipmentModel, equipmentList, button1);
		return pane;
	}
	
	private Pane setupViewInventory(){
		Pane pane = new Pane();
		
		for(int i=0;i<2;i++){
			ImageView image = Utility.setupImage("Images/inventory_panel.png");
			image.relocate(200 + (i * 400), 150);
			pane.getChildren().add(image);
		}
		
		ArrayList<Item> inventory = player.getCharacter().getInventory();
		TilePane inventoryPanel = new TilePane();
		inventoryPanel.setPrefColumns(4);
		inventoryPanel.relocate(200, 150);
		
		for(int i=0;i<inventory.size();i++){
			Pane temp = new Pane();
			
			ImageView itemSprite = Utility.setupImage(inventory.get(i).getImage());
			itemSprite.setFitWidth(40);
			itemSprite.relocate(5, 5);
			
			Text itemName = new Text(45, 25, inventory.get(i).toString());
			itemName.setTextAlignment(TextAlignment.CENTER);
			itemName.setWrappingWidth(50);
			itemName.setFont(new Font(10));
			
			temp.getChildren().addAll(Utility.setupImage("Images/button_background.png"), itemSprite, itemName);
			inventoryPanel.getChildren().add(temp);
		}
		
		inventoryPanel.setOnMouseReleased(e -> {
			int selected = ((int)e.getX() / 100) + (((int)e.getY() / 50) * 8);
			
			if(selected < inventory.size()){
				pane.getChildren().remove(pane.getChildren().size() - 1);
				
				Item item = inventory.get(selected);
				Pane itemPanel = item.getItemPanel();
				itemPanel.relocate(600, 150);
				
				if(itemPanel.getChildren().size() > 3){
					itemPanel.getChildren().get(3).setOnMouseReleased(d -> {
						player.getCharacter().equipItem(item);
						player.getCharacter().modifyInventory(1, item);
						setScene(4);
					});
				}
				
				pane.getChildren().add(itemPanel);
			}
		});
		
		Pane itemPanel = new Pane();
		
		StackPane button1 = Utility.setupButton("Back");
		button1.relocate(550, 700);
		button1.setOnMouseReleased(e -> {setScene(2);});
		
		pane.getChildren().addAll(button1, inventoryPanel, itemPanel);
		return pane;
	}
	
	private Pane setupViewStatus(){
		Pane pane = new Pane();
		
		ImageView image = Utility.setupImage("Images/inventory_panel.png");
		image.relocate(400, 150);
		
		ImageView mainPanel = Utility.setupImage("Images/button_background.png");
		mainPanel.setPreserveRatio(false);
		mainPanel.setFitWidth(350);
		mainPanel.setFitHeight(200);
		mainPanel.relocate(425, 175);
		
		Pane statBlock = new Pane();
		statBlock.relocate(500, 400);
		int[] stats = player.getCharacter().getStats();
		
		for(int i=0;i<6;i++){
			ImageView statBar = Utility.setupImage("Images/button_background.png");
			statBar.setPreserveRatio(false);
			statBar.setFitHeight(25);
			statBar.setFitWidth(200);
			statBar.setY(25 * i);
			
			Rectangle statColor = new Rectangle(0, (i * 25), 200, 25);
			statColor.setFill(Utility.getStatColor(i));
			
			Text statText = new Text(0, (i * 25) + 18, Utility.getStatName(i) + ": " + stats[i]);
			statText.setTextAlignment(TextAlignment.CENTER);
			statText.setWrappingWidth(200);
			
			statBlock.getChildren().addAll(statBar, statColor, statText);
		}
		
		StackPane button1 = Utility.setupButton("Back");
		button1.relocate(550, 700);
		button1.setOnMouseReleased(e -> {setScene(2);});
		
		pane.getChildren().addAll(image, statBlock, mainPanel, button1);
		return pane;
	}
	
	private Pane setupViewMap(){
		Pane pane = new Pane();
		
		Pane map = new Pane();
		ImageView currentLocation = Utility.setupImage("Images/Location_Indicator.png");
		currentLocation.setFitWidth(40);
		currentLocation.setX((currentPosition[0] * 80) + 20);
		currentLocation.setY((currentPosition[1] * 80) + 20);
		map.getChildren().addAll(currentFloor.getMap(), currentLocation);
		map.relocate(400, 200);
		
		ImageView mapBorder = Utility.setupImage("Images/Map_Border.png");
		mapBorder.setFitWidth(400);
		mapBorder.relocate(400, 200);
		
		StackPane button1 = Utility.setupButton("Back");
		button1.relocate(550, 700);
		button1.setOnMouseReleased(e -> {setScene(2);});
		
		pane.getChildren().addAll(button1, map, mapBorder);
		return pane;
	}
	
	private void travel(KeyCode k){
		int[] temp = player.getCharacter().getScreenPosition();
		int[] newScreenPosition = player.getCharacter().getScreenPosition();
		boolean[] available = currentRoom.getAvailableDirections();
		 
		if(k == KeyCode.E){
			if(temp[0] > Utility.getDoorPositionX(0) + 200 && temp[0] < Utility.getDoorPositionX(0) + 276){
				if(available[0] && temp[1] < Utility.getDoorPositionY(0) + 246){
					currentPosition[1] -= 1;
					newScreenPosition[1] += 290;
					progressGame();
				}
				else if(available[2] && temp[1] + player.getCharacter().getActor().getFitHeight() > Utility.getDoorPositionY(2) - 20){
					currentPosition[1] += 1;
					newScreenPosition[1] -= 290;
					progressGame();
				}
			}
			else if(temp[1] > Utility.getDoorPositionY(1) + 150 && temp[1] < Utility.getDoorPositionY(1) + 226){
				if(available[1] && temp[0] < Utility.getDoorPositionX(1) + 296){
					currentPosition[0] -= 1;
					newScreenPosition[0] += 620;
					progressGame();
				}
				else if(available[3] && temp[0] + player.getCharacter().getActor().getFitWidth() > Utility.getDoorPositionX(3) + 180){
					currentPosition[0] += 1;
					newScreenPosition[0] -= 620;
					progressGame();
				}
			}
			
			if(currentRoom.getType() > 1){
				if(temp[0] > 540 && temp[0] < 640 && temp[1] > 350 && temp[1] < 450){
					descend();
				}
			}
		}
		
		player.getCharacter().setScreenPosition(newScreenPosition);
	}

	private void descend(){
		if(currentFloorNumber == 4){
			level = new Level();
			currentFloorNumber = 0;
			currentFloor = level.getFloor(currentFloorNumber);
			currentPosition = currentFloor.getStart();
			progressGame();
		}
		else{
			currentFloorNumber += 1;
			currentFloor = level.getFloor(currentFloorNumber);
			currentPosition = currentFloor.getStart();
			progressGame();
		}
	}
}

class Player{
	private Hero player;
	private int floor;
	private int[] roomPosition;
	private Room currentRoom;
	
	public Player(){
		floor = 0;
		roomPosition = new int[2];
		String name = "Bob";
	}
	
	public Pane viewEquipped(){
		Pane output = new Pane();
		Item[] equipment = player.getEquipped();
		
		ImageView model = new ImageView();
		switch(player.getType()){
			case 0:
				model = Utility.setupImage(new Image("Images/Warrior_Base.png"));
				break;
			case 1:
				model = Utility.setupImage(new Image("Images/Rogue_Base.png"));
				break;
			case 2:
				model = Utility.setupImage(new Image("Images/Mage_Base.png"));
				break;
		}
		model.setFitWidth(200);
		output.getChildren().add(model);
		
		for(int i = 0;i<equipment.length;i++){
			if(equipment[i] != null){
				ImageView item = Utility.setupImage(equipment[i].getImage());
				item.setFitWidth(200);
				output.getChildren().add(item);
			}
		}
		
		return output;
	}
	
	public int getFloor(){return floor;}
	public void setFloor(int n){floor = n;}
	
	public int[] getRoomPosition(){return roomPosition;}
	public void setRoomPosition(int[] n){roomPosition = n;}
	
	public void setRoom(Room room){currentRoom = room;}
	
	public Character getCharacter(){return player;}
	public void setCharacter(Hero hero){
		player = hero;
		player.setAI(false);
	}
}

class Character{
	protected boolean alive;
	protected boolean isAI;
	protected int level;
	protected int type;
	protected int gold;
	protected int[] hp;
	protected int[] mp;
	protected int[] stats;
	protected Item[] equipment;
	protected ArrayList<Item> inventory;
	protected ArrayList<Hero> allies;
	protected int[] screenPosition;
	protected int[] spriteDimensions;
	protected ImageView spriteActor;
	protected Image spriteSheet;
	protected String name;

	public Character(){
		alive = true;
		isAI = true;
		hp = new int[3];
		mp = new int[3];
		stats = new int[6];
		equipment = new Item[10];
		spriteDimensions = new int[2];
		screenPosition = new int[2];
		screenPosition[0] = 600;
		screenPosition[1] = 400;
		spriteActor = new ImageView();
		spriteActor.relocate(screenPosition[0],screenPosition[1]);
		inventory = new ArrayList<Item>();
		allies = new ArrayList<Hero>();
	}
	
	public void moveCharacter(KeyCode k){
		double startTime = System.currentTimeMillis();
		EventHandler<ActionEvent> walking = e-> {
			int currentCycle = ((int)(System.currentTimeMillis() - startTime)/100) - 1;
			if(currentCycle == 2 || currentCycle == 4){currentCycle = 0;}
			if(currentCycle == 3){currentCycle = 2;}
			int viewX = spriteDimensions[0] * currentCycle;
			int viewY = 0;
			int viewWidth = spriteDimensions[0];
			int viewHeight = spriteDimensions[1];
			
			switch(k){
				case UP: case W:
					if(screenPosition[1] > 233){
						screenPosition[1] -= 15;
						spriteActor.setViewport(new Rectangle2D(viewX + (spriteDimensions[0] * 3), viewY, viewWidth, viewHeight));
					}
					break;
				case DOWN: case S:
					if(screenPosition[1] < (567 - (spriteDimensions[1] * 1.5))){
						screenPosition[1] += 15;
						spriteActor.setViewport(new Rectangle2D(viewX, viewY, viewWidth, viewHeight));
					}
					break;
				case LEFT: case A:
					if(screenPosition[0] > 283){
						screenPosition[0] -= 15;
						spriteActor.setViewport(new Rectangle2D(viewX, viewY + spriteDimensions[1], viewWidth, viewHeight));
					}
					break;
				case RIGHT: case D:
					if(screenPosition[0] < (917 - (spriteDimensions[0] * 1.5))){
						screenPosition[0] += 15;
						spriteActor.setViewport(new Rectangle2D(viewX + (spriteDimensions[0] * 3), viewY + spriteDimensions[1], viewWidth, viewHeight));
					}
					break;
			
			}
			spriteActor.relocate(screenPosition[0], screenPosition[1]);
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(100), walking));
		animation.setCycleCount(5);
		animation.play();
	}
	
	public boolean getAI(){return isAI;}
	public void setAI(boolean n){isAI = n;}
	
	public int[] getScreenPosition(){return screenPosition;}
	public void setScreenPosition(int[] n){screenPosition = n;}
	
	public ImageView getActor(){return spriteActor;}
	
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	
	public int getLevel(){return level;}
	public void setLeveL(int level){this.level = level;}
	
	public int[] getStats(){return stats;}
	public int getStat(int n){return stats[n];}
	
	public int getType(){return type;}
	
	public int getGold(){return gold;}
	
	public boolean getAlive(){
		alive = hp[0] > 0;
		return alive;
	}
	
	public void kill(){hp[0] = 0;}
	
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
		Item[] startingEquipment = new Item[10];
		xp[0] = 0;
		xp[1] = 1000 * level;
		super.name = "(Insert random name here)";
		super.gold = 50 * level;
		
		for(int i=0;i<6;i++){ //This sets each of the character's stats so that the minimum number is 9 and the maximum is 18
			int stat = 6;
			for(int j=0;j<3;j++){stat += Utility.random(4);}
			super.stats[i] = stat;
		}
		
		switch(super.type){
			case 0:
				super.spriteSheet = new Image("Images/Warrior.png");
				super.hp[2] = 12; //This sets the "base" HP
				startingEquipment[0] = new Weapon(super.level, 2);
				startingEquipment[2] = new Armor(super.level, 1, 2);
				startingEquipment[4] = new Armor(super.level, 2, 4);
				startingEquipment[6] = new Armor(super.level, 1, 6);
				startingEquipment[7] = new Armor(super.level, 1, 7);
				break;
		
			case 1:
				super.spriteSheet = new Image("Images/Rogue.png");
				super.hp[2] = 8; //This sets the "base" HP
				startingEquipment[1] = new Weapon(super.level, 1);
				startingEquipment[2] = new Armor(super.level, 0, 2);
				startingEquipment[4] = new Armor(super.level, 1, 4);
				break;
			
			case 2:
				super.spriteDimensions[0] = 20;
				super.spriteDimensions[1] = 57;
				super.spriteSheet = new Image("Images/Sprites/Mage_Walk_Sprites.png");
				super.spriteActor.setImage(spriteSheet);
				super.spriteActor.setViewport(new Rectangle2D(super.spriteDimensions[0] * 0, super.spriteDimensions[1] * 0, super.spriteDimensions[0], super.spriteDimensions[1]));
				super.hp[2] = 4; //This sets the "base" HP
				super.mp[2] = 5; //This gives the mage Mana Points
				super.mp[1] = mp[2] * level;
				super.mp[0] = mp[1];
				startingEquipment[8] = new Armor(super.level, -1, 8);
				super.inventory.add(new Weapon(super.level, 2));
				super.inventory.add(new Armor(super.level, 1, 4));
				super.inventory.add(new Weapon(super.level, 1));
				break;
		}
		super.spriteActor.setPreserveRatio(true);
		super.spriteActor.setFitWidth(30);
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

class Neutral extends Character{
	
	public Neutral(int type){
		super.type = type;
	}
}

class Enemy extends Character{
	public Enemy(int type){
		super.type = type;
		super.level = 1;
		setupEnemy();
	}
	
	public Enemy(int type, int level){
		super.type = type;
		super.level = level;
		setupEnemy();
	}
	
	private void setupEnemy(){
		for(int i=0;i<6;i++){
			int stat = 3;
			for(int j=0;j<3;j++){stat += Utility.random(4);}
			super.stats[i] = stat;
		}
		
		super.screenPosition[0] = 280 + Utility.random(645);
		super.screenPosition[1] = 230 + Utility.random(345);
		
		super.spriteDimensions[0] = 20;
		super.spriteDimensions[1] = 57;
		
		super.spriteSheet = new Image("Images/Sprites/Mage_Walk_Sprites.png");
		super.spriteActor.setImage(spriteSheet);
		super.spriteActor.setViewport(new Rectangle2D(super.spriteDimensions[0] * 0, super.spriteDimensions[1] * 0, super.spriteDimensions[0], super.spriteDimensions[1]));
		super.spriteActor.setPreserveRatio(true);
		super.spriteActor.setFitWidth(30);
		super.spriteActor.relocate(super.screenPosition[0], super.screenPosition[1]);
	}
}

class Item{
	protected String name;
	protected int tier;
	protected int slot; 
	//Slot 0 and 1 are for handheld items; Slot 2 is for Boots, Slot 3 is for Leg Armor, Slot 4 is for Chest Armor,
	//Slot 5 is for Shoulder Armor, Slot 6 is for Gauntlets, Slot 7 is for Helmets, Slot 8 is for Robes, Slot 9 is Misc.
	protected int value;
	protected Image image;

	public Item(){
		tier = 1;
		setupItem(tier);
		name = "Item";
		image = new Image("Images/Blank.png");
	}
	
	public Item(int tier){
		this.tier = tier;
		setupItem(tier);
		name = "Item";
		image = new Image("Images/Blank.png");
	}
	
	public Item(int tier, String name){
		this.tier = tier;
		setupItem(tier);
		this.name = name;
		image = new Image("Images/Blank.png");
	}
	
	private void setupItem(int tier){
		value = (tier * 20) + (tier * Utility.random(5));
	}

	public int getSlot(){return slot;}
	public int getValue(){return value;}
	
	public Image getImage(){return image;}
	
	public Pane getItemPanel(){
		Pane output = new Pane();
		
		Text itemTitle = new Text(75, 75, toString());
		itemTitle.setTextAlignment(TextAlignment.CENTER);
		itemTitle.setWrappingWidth(250);
		itemTitle.setFont(new Font(25));
		
		Text itemDesc = new Text(50, 225, getDescription());
		itemDesc.setWrappingWidth(300);
		itemDesc.setFont(new Font(10));
		
		ImageView itemSprite = Utility.setupImage(image);
		itemSprite.setFitWidth(100);
		itemSprite.relocate(150, 100);
		
		output.getChildren().addAll(itemTitle, itemDesc, itemSprite);
		return output;
	}
	
	public String toString(){return name;}
	public String getDescription() {return name + "is a tier " + tier + " item worth " + value;}
}

class Weapon extends Item{
	private boolean twoHanded;
	private int type;
	private int damage;
	private int range;
	private Image model;
	
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
	
	@Override public Pane getItemPanel(){
		Pane output = super.getItemPanel();
		
		StackPane equipButton = Utility.setupButton("Equip");
		equipButton.relocate(150, 350);
		
		output.getChildren().add(equipButton);
		return output;
	}
	
	public int getDamage(){return damage;}
	public int getRange(){return range;}
	public String getDescription(){return super.name + " is a tier " + tier + " weapon worth " + super.value + " which deals " + damage + " damage and has a range of " + range;}
	
}

class Armor extends Item{
	private int type;
	private int dodge;
	private int defense;
	
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
	
	@Override public Pane getItemPanel(){
		Pane output = super.getItemPanel();
		
		StackPane equipButton = Utility.setupButton("Equip");
		equipButton.relocate(150, 350);
		
		output.getChildren().add(equipButton);
		return output;
	}
	
	public int getDodge(){return dodge;}
	public int getDefense(){return defense;}
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
	
	public Floor getFloor(int n){return floors[n];}
	public Floor[] getFloors(){return floors;}
}

class Floor{
	private int type; //0 indicates a Hostile Floor with enemies, 1 indicates a Friendly Floor with villagers; 2 indicates a Boss Floor
	private Room[][] rooms;
	private int[][] layout;
	private int[] start;
	private int[] exit;
	private int amtOfRooms;
	private TilePane map;

	public Floor(){
		type = 0;
		rooms = new Room[5][5];
		start = new int[2];
		start[0] = Utility.random(5) - 1;
		start[1] = Utility.random(5) - 1;
		exit = new int[2];
		map = new TilePane();
		layout = generateLayout(start[0],start[1]);
		setupLayout(0);
		setupMap();
	}

	public Floor(int x, int y, int n){
		type = n;
		start = new int[2];
		start[0] = x;
		start[1] = y;
		exit = new int[2];
		rooms = new Room[5][5];
		map = new TilePane();
		layout = generateLayout(x,y);
		setupLayout(n);
		setupMap();
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
					case -1: rooms[i][j] = new Room(-1, i, j, layout); break;
					case 0: rooms[i][j] = new Room(0, i, j, layout); break;
					case 1: if(n == 1){rooms[i][j] = new Room(1, i, j, layout);} else{rooms[i][j] = new Room(0, i, j, layout);} break;
					case 2: rooms[i][j] = new Room(lastRoom, i, j, layout); exit[0] = i; exit[1] = j; break;
				}
			}
		}
	}
	
	private void setupMap(){
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				Pane temp = new Pane();
				int[] n = {0, 1, 0, -1};
				int[] m = {0, 1, 2, 1};
				boolean[] available = rooms[j][i].getAvailableDirections();
				temp.getChildren().add(Utility.setupImage("Images/Map_Background.png"));
				
				if(layout[j][i] != 0){
					for(int k=0;k<4;k++){
						if(available[k]){
							ImageView temp4 = Utility.setupImage("Images/background_tile3.png");
							temp4.setFitWidth(20);
							temp4.setViewport(new Rectangle2D(20, 10, 10, 20));
							temp4.setX(30 + (n[k] * -20));
							temp4.setY(m[k] * 20);
							temp4.setRotate(k * 90);
							temp.getChildren().add(temp4);
						}
					}
					
					ImageView temp2 = Utility.setupImage("Images/background_tile3.png");
					temp2.setFitWidth(40);
					temp2.setX(20);
					temp2.setY(20);
					temp.getChildren().add(temp2);
				}
				
				if(layout[j][i] == -1){
					Rectangle temp3 = new Rectangle(20, 20, 40, 40);
					temp3.setFill(Color.rgb(10, 10, 255, 0.5));
					temp.getChildren().add(temp3);
				}
				
				if(layout[j][i] == 2){
					Rectangle temp3 = new Rectangle(20, 20, 40, 40);
					temp3.setFill(Color.rgb(255, 10, 10, 0.5));
					temp.getChildren().add(temp3);
				}
				
				map.getChildren().add(temp);
			}
		}
	}
	
	public Room getRoom(int... n){return rooms[n[0]][n[1]];}
	
	public int getType(){return type;}
	public int[] getStart(){return start;}
	public int[] getExit(){return exit;}
	public int[][] getLayout(){return layout;}
	public TilePane getMap(){return map;}
}

class Room{
	private int type; //-1 indicates a Start Room, which is always safe and always empty; 0 indicates a Hostile Room; 1 indicates a Friendly Room; 2 indicates an Exit Room; 3 indicates a Boss Room
	private int subtype;
	private int[] position;
	private int[][] layout;
	private ArrayList<Character> characters;
	private Pane background;
	private boolean[] availableDirections; //0 is North, 1 is West, 2 is South, 3 is East
	
	public Room(int n, int x, int y, int[][] layout){
		type = n;
		position = new int[2];
		position[0] = x;
		position[1] = y;
		this.layout = layout;
		characters = new ArrayList<Character>();
		background = new Pane();
		availableDirections = new boolean[4];
		setupRoom();
	}
	
	private void setupRoom(){
		subtype = 0;
		
		switch(type){
			case 0: subtype = Utility.random(3) - 1; break;
			case 1: subtype = Utility.random(3) - 1; break;
		}
		
		if(position[0] > 0){availableDirections[1] = layout[position[0] - 1][position[1]] != 0;}
		
		if(position[0] < 4){availableDirections[3] = layout[position[0] + 1][position[1]] != 0;}
		
		if(position[1] > 0){availableDirections[0] = layout[position[0]][position[1] - 1] != 0;}
		
		if(position[1] < 4){availableDirections[2] = layout[position[0]][position[1] + 1] != 0;}
		
		background.getChildren().add(Utility.setupImage("Images/Room.png"));
		
		for(int i=0;i<4;i++){
			if(availableDirections[i]){
				ImageView temp = Utility.setupImage("Images/Door.png");
				temp.setRotate(360 - (i * 90));
				temp.relocate(Utility.getDoorPositionX(i), Utility.getDoorPositionY(i));
				background.getChildren().add(temp);
			}
		}
		
		if(type > 1){
			ImageView temp = Utility.setupImage("Images/Trap_Door.png");
			temp.relocate(375, 225);
			background.getChildren().add(temp);
		}
	
		setupCharacters();
	}
	
	private void setupCharacters(){
		switch(type){
			case 0:
				for(int i=0;i<(1 + Utility.random(2));i++){characters.add(new Enemy(1));}
				break;
			case 1:
				
				break;
		}
	}
	
	public ArrayList<Character> getCharacters(){return characters;}
	public int getType(){return type;}
	public boolean[] getAvailableDirections(){return availableDirections;}
	public Pane getBackground(){return background;}
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
	private static int[][] doorPositions = {
		{352, 0},
		{0, 202},
		{352, 424},
		{724, 202},
	};
	
	private static Color[] statColors = {Color.rgb(250, 10, 10, 0.5), Color.rgb(250, 125, 10, 0.5), Color.rgb(250, 250, 10, 0.5), Color.rgb(10, 250, 10, 0.5), Color.rgb(10, 10, 250, 0.5), Color.rgb(250, 10, 250, 0.5)};
	
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
	public static String getStatName(int a){return statName[a];}
	public static int[] getDoorPosition(int a){
		int[] output = new int[2];
		output[0] = doorPositions[a][0];
		output[1] = doorPositions[a][1];
		return output;
	}
	public static int getDoorPositionX(int a){
			int[] temp = Utility.getDoorPosition(a);
			return temp[0];
	}
	public static int getDoorPositionY(int a){
			int[] temp = Utility.getDoorPosition(a);
			return temp[1];
	}
	public static Color getStatColor(int a){return statColors[a];}
	
	public static TilePane setupTilePane(Image image, int height, int width){
		TilePane output = new TilePane();
		output.setPrefWidth(width * image.getWidth());
		output.setPrefHeight(height * image.getHeight());
		
		for(int i = 0;i<(height * width);i++){
			ImageView bgImage = new ImageView();
			bgImage.setImage(image);
			bgImage.setCache(true);
			bgImage.setSmooth(false);
			output.getChildren().add(bgImage);
		}
		
		return output;
	}
	
	public static StackPane setupButton(String bLabel){
		StackPane output = new StackPane();
		
		Text text = new Text(bLabel);
		text.setTextAlignment(TextAlignment.CENTER);
		
		Image image = new Image("Images/button_background.png");
		ImageView buttonBackground = new ImageView();
		buttonBackground.setImage(image);
		
		output.getChildren().addAll(buttonBackground, text);
		
		return output;
	}

	public static ImageView setupImage(String directory){
		ImageView output = new ImageView();
		output.setImage(new Image(directory));
		output.setCache(true);
		output.setSmooth(false);
		output.setPreserveRatio(true);
		return output;
	}
	
	public static ImageView setupImage(Image image){
		ImageView output = new ImageView();
		output.setImage(image);
		output.setCache(true);
		output.setSmooth(false);
		output.setPreserveRatio(true);
		return output;
	}
}