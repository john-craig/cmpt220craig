import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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
			if(e.getCode() == KeyCode.ESCAPE){
				primaryStage.close();
				System.exit(0);
			}
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
		
		scene.setOnKeyPressed(e -> {});
		
		Image image = new Image("Images/background_tile.png");
		Image image2 = new Image("Images/background_tile2.png");
		
		TilePane backgroundTop = Utility.setupTilePane(image, 3, 24);
		backgroundTop.relocate(0,0);
		
		TilePane backgroundMid = Utility.setupTilePane(image2, 10, 24);
		backgroundMid.relocate(0, 150);
		
		TilePane backgroundBottom = Utility.setupTilePane(image, 3, 24);
		backgroundBottom.relocate(0, 650);
		
		defaultPane.getChildren().addAll(backgroundTop, backgroundMid, backgroundBottom);
		
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), e-> {
			currentRoom.setPlayerPosition(player.getCharacter().getScreenPosition());
			currentRoom.animateCharacters();
			if(currentRoom.inProximity()){setScene(7);}
		}));
		animation.setCycleCount(1);
		animation.setOnFinished(e ->{
			if(!currentRoom.inProximity() && rootPane == defaultPane){animation.play();}
		});
		
		switch(n){
			case 0: 
				defaultPane.getChildren().add(setupStart());
				break;
			case 1:
				defaultPane.getChildren().add(setupSelection());
				break;
			case 2:
				defaultPane.getChildren().add(setupMainScreen());
				currentRoom.setPlayerPosition(player.getCharacter().getScreenPosition());
				
				scene.setOnKeyPressed(e -> {
					player.getCharacter().moveCharacter(e.getCode());
					travel(e.getCode());
				});
				
				animation.play();
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
				defaultPane.getChildren().add(setupBattle());
				break;
			case 8:
				defaultPane.getChildren().add(setupEnd());
				break;
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
			Pane category = new Pane();
			ImageView banner = Utility.setupImage("Images/Banner.png");
			banner.setPreserveRatio(false);
			banner.setFitWidth(100);
			banner.setFitHeight(25);
			
			Text text;
			if(i>0){text = new Text("Armor");}
			else{
				if(player.getCharacter().getType() == 2){text = new Text("Spell");}
				else{text = new Text("Weapon");}
			}
			text.setTextAlignment(TextAlignment.CENTER);
			text.setWrappingWidth(100);
			text.setY(15);
			
			category.getChildren().addAll(banner, text);
			category.relocate(i * 150, 25);
			equipmentList.getChildren().add(category);
		}
		
		Pane offensives = new Pane();
		offensives.relocate(0, 75);
		Pane armor = new Pane();
		armor.relocate(150, 75);
		for(int i=0;i<equipped.length;i++){
			if(equipped[i] != null){
				if(i<2){
					Text itemInfo = new Text(5, offensives.getChildren().size() * 50, "");
					itemInfo.setWrappingWidth(100);
					
					if(equipped[i] instanceof Weapon){
						Weapon temp = (Weapon)equipped[i];
						itemInfo.setText(temp.toString() + "\n\tDamage: " + temp.getDamage() + "\n\tRange: " + temp.getRange());
					}
					else{
						Spellbook temp = (Spellbook)equipped[i];
						itemInfo.setText(temp.toString() + "\n\tType: " + Utility.getSpellType(temp.getType()) + "\n\tMana Cost: " + temp.getMPCost());
					}
					offensives.getChildren().add(itemInfo);
				}
				else{
					Armor temp = (Armor)equipped[i];
					Text itemInfo = new Text(5, armor.getChildren().size() * 50, temp.toString() + "\n\tDefense: " + temp.getDefense() + "\n\tDodge: " + temp.getDodge());
					itemInfo.setWrappingWidth(100);
					armor.getChildren().add(itemInfo);
				}
			}
		}
		equipmentList.getChildren().addAll(offensives, armor);
		
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
			
			ImageView itemSprite = Utility.setupImage(inventory.get(i).getSmallImage());
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
				
				if(item instanceof Weapon || item instanceof Armor){
					itemPanel.getChildren().get(3).setOnMouseReleased(d -> {
						if(player.getCharacter().equipItem(item)){
							player.getCharacter().modifyInventory(1, item);
							setScene(4);
						}
					});
				}
				else if(item instanceof Spellbook){
					itemPanel.getChildren().get(3).setOnMouseReleased(d -> {
						Spellbook temp = (Spellbook)item;
						temp.setSlot(0);
						
						if(player.getCharacter().equipItem(temp)){
							player.getCharacter().modifyInventory(1, item);
							setScene(4);
						}
					});
					itemPanel.getChildren().get(4).setOnMouseReleased(d -> {
						Spellbook temp = (Spellbook)item;
						temp.setSlot(1);
						
						if(player.getCharacter().equipItem(temp)){
							player.getCharacter().modifyInventory(1, item);
							setScene(4);
						}
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
		
		ImageView background = Utility.setupImage("Images/inventory_panel.png");
		background.relocate(400, 150);
		
		Pane mainPanel = player.viewInfo();
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
		
		pane.getChildren().addAll(background, statBlock, mainPanel, button1);
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
	
	private Pane setupBattle(){
		Pane pane = new Pane();
		
		ImageView baseBackground = Utility.setupImage(currentRoom.getBackgroundImage());
		baseBackground.setViewport(new Rectangle2D(200, 250, 400, 250));
		baseBackground.setFitWidth(800);
		baseBackground.relocate(200, 150);
		
		StackPane button1 = Utility.setupButton("Continue");
		button1.relocate(550, 700);
		button1.setOnMouseReleased(e -> {
			if(player.getCharacter().getAlive()){setScene(2);}
			else{setScene(8);}
		});
		
		Battle battle = new Battle(player.getCharacter(), currentRoom.getCharacters());
		
		pane.getChildren().addAll(baseBackground, button1, battle.getPane());
		
		return pane;
	}
	
	private Pane setupEnd(){
		Pane pane = new Pane();
		
		Text text = new Text("YOU\nLOSE");
		text.setFont(new Font(400));
		text.setWrappingWidth(800);
		text.relocate(200, 200);
		text.setTextAlignment(TextAlignment.CENTER);
		
		StackPane button1 = Utility.setupButton("Exit Game");
		button1.relocate(550, 700);
		button1.setOnMouseReleased(e -> {System.exit(0);});
		
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
		
		for(int i = equipment.length-1;i>=0;i--){
			if(equipment[i] != null){
				ImageView item = Utility.setupImage(equipment[i].getLargeImage());
				item.setFitWidth(200);
				output.getChildren().add(item);
			}
		}
		
		return output;
	}
	
	public Pane viewInfo(){
		Pane pane = new Pane();
		
		ImageView mainPanelBackground = Utility.setupImage("Images/button_background.png");
		mainPanelBackground.setPreserveRatio(false);
		mainPanelBackground.setFitWidth(350);
		mainPanelBackground.setFitHeight(200);
		
		ImageView banner = Utility.setupImage("Images/Banner.png");
		banner.setFitHeight(60);
		banner.relocate(75, 10);
		
		Text name = new Text(0, 50, player.getName());
		name.setTextAlignment(TextAlignment.CENTER);
		name.setWrappingWidth(350);
		name.setFont(new Font(40));
		
		pane.getChildren().addAll(mainPanelBackground, banner, name);
		
		String[] text = null;
		if(player.getType() == 2){
			text = new String[3];
			text[0] = "Lvl. " + player.getLevel() + " " + Utility.getCharacterName(0, player.getType());
			text[1] = "HP: " + player.getHP()[0] + "/" + player.getHP()[1];
			text[2] = "MP: " + player.getMP()[0] + "/" + player.getMP()[1];
		}
		else{
			text = new String[2];
			text[0] = "Lvl. " + player.getLevel() + " " + Utility.getCharacterName(0, player.getType());
			text[1] = "HP: " + player.getHP()[0] + "/" + player.getHP()[1];
		}
		
		for(int i=0;i<text.length;i++){
			Text textLine = new Text(0, 100 + (i * 40), text[i]);
			textLine.setTextAlignment(TextAlignment.CENTER);
			textLine.setWrappingWidth(350);
			textLine.setFont(new Font(20));
			pane.getChildren().add(textLine);
		}
		
		return pane;
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
		player.setName(Utility.getPlayerName());
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
	protected ImageView largeActor;
	protected ImageView spriteActor;
	protected Image largeSprite;
	protected Image deadSprite;
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
		largeActor = new ImageView();
		inventory = new ArrayList<Item>();
		allies = new ArrayList<Hero>();
	}
	
	public void moveCharacter(Utility.Direction d){
		double startTime = System.currentTimeMillis();
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(250), e-> {
			int currentCycle = ((int)(System.currentTimeMillis() - startTime)/250) - 1;
			if(currentCycle == 2 || currentCycle == 4){currentCycle = 0;}
			if(currentCycle == 3){currentCycle = 2;}
			int viewX = spriteDimensions[0] * currentCycle;
			int viewY = 0;
			int viewWidth = spriteDimensions[0];
			int viewHeight = spriteDimensions[1];
			
			switch(d){
				case NORTH:
					if(screenPosition[1] > 233){
						screenPosition[1] -= 10;
						spriteActor.setViewport(new Rectangle2D(viewX + (spriteDimensions[0] * 3), viewY, viewWidth, viewHeight));
					}
					break;
				case SOUTH:
					if(screenPosition[1] < (567 - (spriteDimensions[1] * 1.5))){
						screenPosition[1] += 10;
						spriteActor.setViewport(new Rectangle2D(viewX, viewY, viewWidth, viewHeight));
					}
					break;
				case WEST:
					if(screenPosition[0] > 283){
						screenPosition[0] -= 10;
						spriteActor.setViewport(new Rectangle2D(viewX, viewY + spriteDimensions[1], viewWidth, viewHeight));
					}
					break;
				case EAST:
					if(screenPosition[0] < (917 - (spriteDimensions[0] * 1.5))){
						screenPosition[0] += 10;
						spriteActor.setViewport(new Rectangle2D(viewX + (spriteDimensions[0] * 3), viewY + spriteDimensions[1], viewWidth, viewHeight));
					}
					break;
			
			}
			spriteActor.relocate(screenPosition[0], screenPosition[1]);
		}));
		animation.setCycleCount(5);
		animation.play();
	}
	
	public void moveCharacter(KeyCode k){
		double startTime = System.currentTimeMillis();
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(100), e-> {
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
						screenPosition[1] -= 10;
						spriteActor.setViewport(new Rectangle2D(viewX + (spriteDimensions[0] * 3), viewY, viewWidth, viewHeight));
					}
					break;
				case DOWN: case S:
					if(screenPosition[1] < (567 - (spriteDimensions[1] * 1.5))){
						screenPosition[1] += 10;
						spriteActor.setViewport(new Rectangle2D(viewX, viewY, viewWidth, viewHeight));
					}
					break;
				case LEFT: case A:
					if(screenPosition[0] > 283){
						screenPosition[0] -= 10;
						spriteActor.setViewport(new Rectangle2D(viewX, viewY + spriteDimensions[1], viewWidth, viewHeight));
					}
					break;
				case RIGHT: case D:
					if(screenPosition[0] < (917 - (spriteDimensions[0] * 1.5))){
						screenPosition[0] += 10;
						spriteActor.setViewport(new Rectangle2D(viewX + (spriteDimensions[0] * 3), viewY + spriteDimensions[1], viewWidth, viewHeight));
					}
					break;
			
			}
			spriteActor.relocate(screenPosition[0], screenPosition[1]);
		}));
		animation.setCycleCount(5);
		animation.play();
	}
	
	public ImageView getResetActor(){
		ImageView output = Utility.setupImage(spriteSheet);
		output.setViewport(new Rectangle2D(0, 0, spriteDimensions[0], spriteDimensions[1]));
		return output;
	}
	
	public boolean getAI(){return isAI;}
	public void setAI(boolean n){isAI = n;}
	
	public int[] getScreenPosition(){return screenPosition;}
	public void setScreenPosition(int[] n){screenPosition = n;}
	
	public ImageView getActor(){return spriteActor;}
	public ImageView getLargeActor(){return largeActor;}
	
	public int[] getSpriteDimensions(){return spriteDimensions;}
	
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	
	public int getLevel(){return level;}
	public void setLeveL(int level){this.level = level;}
	
	public int[] getStats(){return stats;}
	public int getStat(int n){return stats[n];}
	
	public void setStats(int[] n){stats = n;}
	public void setStat(int n, int m){stats[n] = m;}
	
	public int getType(){return type;}
	
	public int getGold(){return gold;}
	
	public int computeArmorClass(){
		double dodgeSum = 0;
		double pieces = 0;
		
		for(int i=2;i<equipment.length;i++){
			if(equipment[i] != null){
				Armor temp = (Armor)equipment[i];
				dodgeSum += temp.getDodge();
				pieces++;
			}
		}
		
		return (int)(dodgeSum / pieces);
	}
	public int computeDamageReduction(){
		double defenseSum = 0;
		double pieces = 0;
		
		for(int i=2;i<equipment.length;i++){
			if(equipment[i] != null){
				Armor temp = (Armor)equipment[i];
				defenseSum += temp.getDefense();
				pieces++;
			}
		}
		
		return (int)(defenseSum / pieces);
	}
	
	public boolean getAlive(){
		alive = hp[0] > 0;
		return alive;
	}
	
	public void kill(){hp[0] = 0;}
	
	public int[] getHP(){return hp;}
	public int getCurrentHP(){return hp[0];}
	public int getMaxHP(){return hp[1];}
	public void setHP(int[] n){hp = n;}
	
	public int[] getMP(){return mp;}
	public void setMP(int[] n){mp = n;}
	public boolean reduceMP(int n){
		if(mp[1] - n < 0){return false;}
		else{mp[1] -= n; return true;}
	}
	
	public void heal(int amt){
		hp[0] += amt;
		if(hp[0]>hp[1]){hp[0] = hp[1];}
	}
	public void damage(int amt){
		if(amt > 0){
			hp[0] -= amt;
			if(hp[0]<1){alive = false;}
		}
		if(!alive){
			largeActor = Utility.setupImage(deadSprite);
			largeActor.setFitWidth(spriteDimensions[1] * 4);
			largeActor.relocate(0, spriteDimensions[0] * 6);
			spriteActor = Utility.setupImage(deadSprite);
			spriteActor.setFitWidth(spriteDimensions[1]);
		}
	}
	
	public boolean equipItem(Item x){
		int n = x.getSlot();
		if(n > -1){
			if(n == 1 && x instanceof Weapon && equipment[0] != null){
				Weapon temp = (Weapon)equipment[0];
				if(!temp.isTwoHanded()){return false;}
			}
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
	public Item getEquippedItem(int n){return equipment[n];}
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
				super.largeSprite = new Image("Images/Sprites/Warrior_Large_Sprite.png");
				super.deadSprite = new Image("Images/Sprites/Warrior_Dead_Sprite.png");
				super.spriteDimensions[0] = 30;
				super.spriteDimensions[1] = 60;
				super.spriteSheet = new Image("Images/Sprites/Warrior_Walk_Sprites.png");
				super.hp[2] = 50; //This sets the "base" HP
				startingEquipment[0] = new Weapon(super.level, 2);
				startingEquipment[2] = new Armor(super.level, 1, 2);
				startingEquipment[4] = new Armor(super.level, 2, 4);
				startingEquipment[6] = new Armor(super.level, 1, 6);
				super.inventory.add(new Weapon(super.level, 2));
				super.inventory.add(new Armor(super.level, 2, 4));
				break;
		
			case 1:
				super.largeSprite = new Image("Images/Sprites/Rogue_Large_Sprite.png");
				super.deadSprite = new Image("Images/Sprites/Rogue_Dead_Sprite.png");
				super.spriteDimensions[0] = 34;
				super.spriteDimensions[1] = 58;
				super.spriteSheet = new Image("Images/Sprites/Rogue_Walk_Sprites.png");
				super.hp[2] = 40; //This sets the "base" HP
				startingEquipment[0] = new Weapon(super.level, 0);
				startingEquipment[1] = new Weapon(super.level, 1);
				startingEquipment[2] = new Armor(super.level, 0, 2);
				startingEquipment[4] = new Armor(super.level, 0, 4);
				super.inventory.add(new Weapon(super.level, 1));
				super.inventory.add(new Armor(super.level, 0, 4));
				break;
			
			case 2:
				super.largeSprite = new Image("Images/Sprites/Mage_Large_Sprite.png");
				super.deadSprite = new Image("Images/Sprites/Mage_Dead_Sprite.png");
				super.spriteDimensions[0] = 20;
				super.spriteDimensions[1] = 57;
				super.spriteSheet = new Image("Images/Sprites/Mage_Walk_Sprites.png");
				super.hp[2] = 30; //This sets the "base" HP
				super.mp[2] = 30; //This gives the mage Mana Points
				super.mp[1] = mp[2] * level;
				super.mp[0] = mp[1];
				startingEquipment[0] = new Spellbook(super.level, 0);
				startingEquipment[1] = new Spellbook(super.level);
				startingEquipment[8] = new Armor(super.level, -1, 8);
				super.inventory.add(new Spellbook(super.level));
				super.inventory.add(new Armor(super.level, -1, 8));
				break;
		}
		super.spriteActor.setImage(spriteSheet);
		super.spriteActor.setViewport(new Rectangle2D(super.spriteDimensions[0] * 0, super.spriteDimensions[1] * 0, super.spriteDimensions[0], super.spriteDimensions[1]));
		super.spriteActor.setPreserveRatio(true);
		super.spriteActor.setFitWidth(spriteDimensions[0] * 1.5);
		super.largeActor = Utility.setupImage(largeSprite);
		super.largeActor.setFitWidth(spriteDimensions[0] * 3);
		
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
		super.name = "Tester";
		super.type = type;
		super.level = 1;
		setupEnemy();
	}
	
	public Enemy(int type, int level){
		super.name = "Tester";
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
		
		super.equipment[0] = new Weapon(super.level, 1);
		
		super.hp[2] = 20;
		super.hp[1] = hp[2] * level;
		super.hp[0] = hp[1];
		
		super.spriteDimensions[0] = 20;
		super.spriteDimensions[1] = 57;
		
		super.screenPosition[0] = 280 + Utility.random(645 - super.spriteDimensions[0]);
		super.screenPosition[1] = 230 + Utility.random(345 - super.spriteDimensions[1]);
		
		super.spriteSheet = new Image("Images/Sprites/Mage_Walk_Sprites.png");
		super.spriteActor.setImage(spriteSheet);
		super.spriteActor.setViewport(new Rectangle2D(super.spriteDimensions[0] * 0, super.spriteDimensions[1] * 0, super.spriteDimensions[0], super.spriteDimensions[1]));
		super.spriteActor.setPreserveRatio(true);
		super.spriteActor.setFitWidth(30);
		super.spriteActor.relocate(super.screenPosition[0], super.screenPosition[1]);
		
		super.largeSprite = new Image("Images/Sprites/Mage_Large_Sprite.png");
		super.deadSprite = new Image("Images/Sprites/Mage_Dead_Sprite.png");
		super.largeActor = Utility.setupImage(largeSprite);
		super.largeActor.setFitWidth(spriteDimensions[0] * 3);
	}
}

class Battle{
	Pane[] combatantPanes;
	Character[] combatants;
	Character player;
	int amtCombatants;
	int playerPosition;
	boolean playersTurn;
	boolean battleOver;
	
	Battle(Character player, ArrayList<Character> opponents){
		combatantPanes = new Pane[8];
		combatants = new Character[8];
		playersTurn = true;
		battleOver = false;
		
		this.player = player;
		playerPosition = Utility.random(4 - 1);
		combatants[playerPosition] = player;
		
		for(int i=0;i<opponents.size();i++){
			combatants[4 + i] = opponents.get(i);
		}
		
		amtCombatants = opponents.size() + 1;
		setupCombatantPanes();
	}
	
	Battle(Character player, ArrayList<Character> friendlies, ArrayList<Character> opponents){
		combatantPanes = new Pane[8];
		combatants = new Character[8];
		playersTurn = true;
		battleOver = false;
		
		this.player = player;
		playerPosition = Utility.random(4 - 1);
		combatants[playerPosition] = player;
		
		for(int i=0;i<friendlies.size();i++){
			combatants[i] = friendlies.get(i);
		}
		combatants[friendlies.size()] = player;
		
		for(int i=0;i<opponents.size();i++){
			combatants[4 + i] = opponents.get(i);
		}
		
		amtCombatants = opponents.size() + friendlies.size() + 1;
		setupCombatantPanes();
	}

	private void setupCombatantPanes(){
		for(int i=0;i<combatants.length;i++){
			if(combatants[i] != null){
				Character temp = combatants[i];
				int[] tempDimensions = temp.getSpriteDimensions();
				Pane tempPane = new Pane();
				tempPane.relocate(200 + (i * 100), 575 - (tempDimensions[1] * 4));
				
				ImageView tempActor = temp.getLargeActor();
				tempActor.setFitWidth(tempDimensions[0] * 4);
				
				ImageView tempHealthBar = Utility.setupImage("Images/Health_Bar.png");
				tempHealthBar.relocate((tempDimensions[0] * 2) - 38, (tempDimensions[1] * 4) - 15);
				tempHealthBar.setPreserveRatio(false);
				tempHealthBar.setFitWidth(75);
				
				Rectangle health = new Rectangle((tempDimensions[0] * 2) - 36, (tempDimensions[1] * 4) - 12, (temp.getCurrentHP() / temp.getMaxHP()) * 72, 9);
				health.setFill(Color.rgb(255, 10, 10));
				
				tempPane.getChildren().addAll(tempActor, tempHealthBar, health);
				combatantPanes[i] = tempPane;
			}
		}
	}
	
	public void movePosition(boolean direction, int charPosition){
		if(charPosition > 3){
			if(!((!direction && charPosition == 4) || (direction && charPosition == 7))){
				if(direction){
					Character temp = combatants[charPosition + 1];
					combatants[charPosition + 1] = combatants[charPosition];
					combatants[charPosition] = temp;
				}
				else{
					Character temp = combatants[charPosition - 1];
					combatants[charPosition - 1] = combatants[charPosition];
					combatants[charPosition] = temp;
				}
			}
		}
		else{
			if(!((!direction && charPosition == 0) || (direction && charPosition == 3))){
				if(direction){
					Character temp = combatants[charPosition + 1];
					combatants[charPosition + 1] = combatants[charPosition];
					combatants[charPosition] = temp;
				}
				else{
					Character temp = combatants[charPosition - 1];
					combatants[charPosition - 1] = combatants[charPosition];
					combatants[charPosition] = temp;
				}
			}
		}
	}
	
	public boolean attack(int n, boolean type, int attackerSlot){
		boolean output;
		Character attacker = combatants[attackerSlot];
		int defenderSlot = -1;
		int modifyingStat =  -1;
		int damage = -1;
		int range = -1;
		int swiftness = -1;
		int distance = -1;
		
		if(attackerSlot < 4){
			for(int i=0;i<4;i++){
				if(combatants[4 + i] != null && combatants[4 + i].getAlive()){defenderSlot = (4 + i); break;}
			}
		}
		else{
			for(int i=0;i<4;i++){
				if(combatants[3 - i] != null && combatants[3 - i].getAlive()){defenderSlot = (3 - i); break;}
			}
		}
		
		if(type){swiftness = 0;} 
		else{swiftness = attacker.getStat(1);} //If an attack's "type" is false, it is a Light attack, and thus it is swifter and has a better chance of hitting
		
		distance = Math.abs(defenderSlot - attackerSlot) * 5;
		
		if(attacker.getEquippedItem(n) instanceof Weapon){
			Weapon weapon = (Weapon)attacker.getEquippedItem(n);
			range = weapon.getRange();
			
			if(weapon.isTwoHanded() && !type){ //If an attack's "type" is true, it is a Heavy attack, and thus it does full damage with a Two-Handed weapon
				damage = weapon.getDamage() / 2;
			}
			else{damage = weapon.getDamage();}
			
			if(weapon.getType() > 2){
				modifyingStat = 1;
			}
			else{
				modifyingStat = 0;
			}
		}
		else{
			modifyingStat = 4;
			
			Spellbook spell = (Spellbook)attacker.getEquippedItem(n);
			
			if(!attacker.reduceMP(spell.getMPCost())){return false;}
			else{
				if(defenderSlot > 3){
					Character[] temp = spell.spellEffect(combatants[4], combatants[5], combatants[6], combatants[7]);
					for(int i=0;i<temp.length;i++){combatants[4 + i] = temp[i];}
				}
				else{
					Character[] temp = spell.spellEffect(combatants[3], combatants[2], combatants[1], combatants[0]);
					for(int i=0;i<temp.length;i++){combatants[3 - i] = temp[i];}
				}
			}
		}
		
		if(Utility.random(100) + swiftness + attacker.getStat(modifyingStat) > 50 + combatants[defenderSlot].computeArmorClass() + Math.abs(distance - range)){
			combatants[defenderSlot].damage(damage - combatants[defenderSlot].computeDamageReduction());
			Rectangle tempHealth = (Rectangle)combatantPanes[defenderSlot].getChildren().get(2);
			tempHealth.setWidth((double)combatants[defenderSlot].getCurrentHP() / (double)combatants[defenderSlot].getMaxHP() * 72.0);
			combatantPanes[defenderSlot].getChildren().set(2, tempHealth);
			
			output = true;
		}
		else{output = false;;}
		
		if(!combatants[defenderSlot].getAlive()){cleanupDead();}
		
		return output;
	}
	
	public int getCombatantPosition(Character character){
		int output = -1;
		
		for(int i=0;i<8;i++){
			if(combatants[i] == character){
				output = i;
				break;
			}
		}
		
		return output;
	}
	
	public void cleanupDead(){
		for(int i=0;i<combatants.length;i++){
			if(combatants[i] != null && !combatants[i].getAlive()){
				if(i > 3){
					for(int j=0;i<4;i++){movePosition(false, i);}
					combatantPanes[i].getChildren().set(0, combatants[i].getLargeActor());
				}
				else{
					for(int j=0;i<4;i++){movePosition(true, i);}
					combatantPanes[i].getChildren().set(0, combatants[i].getLargeActor());
				}
				amtCombatants--;
			}
		}
	}
	
	public Pane getPane(){
		Pane pane = new Pane();
		
		TilePane background = Utility.setupTilePane(new Image("Images/background_tile.png"), 3, 6);
		background.relocate(450, 650);
		
		ImageView banner = Utility.setupImage("Images/Banner.png");
		banner.setPreserveRatio(false);
		banner.setFitHeight(100);
		banner.setFitWidth(300);
		banner.relocate(450, 25);
		
		Text bannerText = new Text(450, 75, "Your turn!");
		bannerText.setWrappingWidth(300);
		bannerText.setFont(new Font(20));
		bannerText.setTextAlignment(TextAlignment.CENTER);
		
		StackPane button1 = new StackPane();
		StackPane button2 = new StackPane();
		StackPane button3 = new StackPane();
		StackPane button4 = new StackPane();
		
		switch(player.getType()){
			case 0:
				button1 = Utility.setupButton("Heavy Attack");
				button1.setOnMouseReleased(e -> {
					if(playersTurn){
						if(attack(0, true, playerPosition)){
							bannerText.setText("You landed your attack!");
						}
						else{bannerText.setText("You missed your attack!");}
					}
				});
				
				button2 = Utility.setupButton("Light Attack");
				button2.setOnMouseReleased(e -> {
					if(playersTurn){
						int n = 0;
						if(player.getEquippedItem(1) != null){n = 1;}
						if(attack(n, false, playerPosition)){
							bannerText.setText("You landed your attack!");
						}
						else{bannerText.setText("You missed your attack!");}
					}
				});
				break;
			case 1:
				button1 = Utility.setupButton("Double Attack");
				button1.setOnMouseReleased(e -> {
					if(playersTurn){
						int n = 0;
						attack(0, true, playerPosition);
						if(player.getEquippedItem(1) != null){n = 1;}
						if(attack(1, true, playerPosition)){
							bannerText.setText("You landed your attack!");
						}
						else{bannerText.setText("You missed your attack!");}
					}
				});
				
				button2 = Utility.setupButton("Light Attack");
				button2.setOnMouseReleased(e -> {
					if(playersTurn){
						int n = 0;
						if(player.getEquippedItem(1) != null){n = 1;}
						if(attack(n, false, playerPosition)){bannerText.setText("You landed your attack!");}
						else{bannerText.setText("You missed your attack!");}
					}
				});
				break;
			case 2:
				button1 = Utility.setupButton("Spell 1");
				button1.setOnMouseReleased(e -> {
					
				});
				
				button2 = Utility.setupButton("Spell 2");
				button2.setOnMouseReleased(e -> {
					
				});
				break;
		}
		
		button3 = Utility.setupButton("Move Forwards");
		button3.setOnMouseReleased(e -> {
			if(playersTurn){
				movePosition(true, playerPosition);
				combatantPanes[playerPosition].relocate(200 + (getCombatantPosition(player) * 100), 575 - (player.getSpriteDimensions()[1] * 4));
				playerPosition++;
			}
		});
		
		button4 = Utility.setupButton("Move Backwards");
		button4.setOnMouseReleased(e -> {
			if(playersTurn){
				movePosition(false, playerPosition);
				combatantPanes[playerPosition].relocate(200 + (getCombatantPosition(player) * 100), 575 - (player.getSpriteDimensions()[1] * 4));
				playerPosition--;
			}
		});
		
		button1.relocate(491, 666);
		button2.relocate(609, 666);
		button3.relocate(609, 734);
		button4.relocate(491, 734);
		
		double startTime = System.currentTimeMillis();
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), e-> {
			int currentCycle = (((int)(System.currentTimeMillis() - startTime)/1000) - 1) % (amtCombatants * 2);
			int currentCombatant = (currentCycle / 2) + 3;
			playersTurn = false;
			
			if(currentCycle > 1){
				if(currentCycle % 2 == 0){bannerText.setText(combatants[currentCombatant].getName() + "'s turn!");}
				else{
					if(attack(0, false, currentCombatant)){bannerText.setText(combatants[currentCombatant].getName() + " landed its attack!");}
					else{bannerText.setText(combatants[currentCombatant].getName() + " missed its attack!");}
				}
			}
		}));
		animation.setCycleCount(amtCombatants * 2);
		animation.setOnFinished(e -> {
			boolean allAlliesDead = false;
			boolean allEnemiesDead = false;
			
			for(int i=0;i<4;i++){
				if(combatants[3 - i] != null && combatants[3 - i].getAlive()){
					allAlliesDead = false;
					break;
				}
				else{allAlliesDead = true;}
			}
			for(int i=0;i<4;i++){
				if(combatants[i + 3] != null && combatants[i + 3].getAlive()){
					allEnemiesDead = false;
					break;
				}
				else{allEnemiesDead = true;}
			}
			
			battleOver = allAlliesDead || allEnemiesDead;
			if(battleOver){
				for(int i=0;i<5;i++){pane.getChildren().remove(0);}
				bannerText.setText("Battle over!");
			}
			else{
				playersTurn = true;
				bannerText.setText("Your turn!");
			}
		});
		
		pane.getChildren().addAll(background, button1, button2, button3, button4, banner, bannerText);
		for(int i=0;i<combatantPanes.length;i++){
			if(combatantPanes[i] != null){pane.getChildren().add(combatantPanes[i]);}
		}
		pane.setOnMouseReleased(e -> {
			if(playersTurn){animation.play();}
		});
		return pane;
	}
}

class Item{
	protected String name;
	protected int tier;
	protected int slot; 
	//Slot 0 and 1 are for handheld items; Slot 2 is for Boots, Slot 3 is for Leg Armor, Slot 4 is for Chest Armor,
	//Slot 5 is for Shoulder Armor, Slot 6 is for Gauntlets, Slot 7 is for Helmets, Slot 8 is for Robes, Slot 9 is Misc.
	protected int value;
	protected Image smallImage;
	protected Image largeImage;

	public Item(){
		tier = 1;
		setupItem(tier);
		name = "Item";
		smallImage = new Image("Images/Blank.png");
	}
	
	public Item(int tier){
		this.tier = tier;
		setupItem(tier);
		name = "Item";
		smallImage = new Image("Images/Blank.png");
	}
	
	public Item(int tier, String name){
		this.tier = tier;
		setupItem(tier);
		this.name = name;
		smallImage = new Image("Images/Blank.png");
	}
	
	private void setupItem(int tier){
		value = (tier * 20) + (tier * Utility.random(5));
	}

	public int getSlot(){return slot;}
	public int getValue(){return value;}
	
	public Image getSmallImage(){return smallImage;}
	public Image getLargeImage(){return largeImage;}
	
	public Pane getItemPanel(){
		Pane output = new Pane();
		
		Text itemTitle = new Text(75, 75, toString());
		itemTitle.setTextAlignment(TextAlignment.CENTER);
		itemTitle.setWrappingWidth(250);
		itemTitle.setFont(new Font(25));
		
		Text itemDesc = new Text(50, 225, getDescription());
		itemDesc.setWrappingWidth(300);
		itemDesc.setFont(new Font(10));
		
		ImageView itemSprite = Utility.setupImage(smallImage);
		itemSprite.setFitWidth(100);
		itemSprite.relocate(150, 100);
		
		output.getChildren().addAll(itemTitle, itemDesc, itemSprite);
		return output;
	}
	
	public String toString(){return name;}
	public String getDescription() {return name + " is a tier " + tier + " item worth " + value;}
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
				super.smallImage = new Image("Images/Items/Small/Knife.png");
				super.largeImage = new Image("Images/Items/Large/Knife.png");
				break;
			case 1: //Offhanded melee weapons
				super.slot = 1;
				twoHanded = false;
				damage = (tier * 10) + (Utility.random(5) * tier);
				range = 5;
				super.smallImage = new Image("Images/Items/Small/Dagger.png");
				super.largeImage = new Image("Images/Items/Large/Dagger.png");
				break;
			case 2: //Two-handed melee weapons
				super.slot = 0;
				twoHanded = true;
				damage = (tier * 20) + (Utility.random(10) * tier);
				range = 5;
				super.smallImage = new Image("Images/Items/Small/Claymore.png");
				super.largeImage = new Image("Images/Items/Large/Claymore.png");
				break;
			case 3: //One-handed ranged weapons
				super.slot = 0;
				twoHanded = false;
				damage = (tier * 5) + (Utility.random(5) * tier);
				range = 15;
				super.smallImage = new Image("Images/Items/Small/");
				super.largeImage = new Image("Images/Items/Large/");
				break;
			case 4: //Two-handed ranged weapons
				super.slot = 0;
				twoHanded = true;
				damage = (tier * 10) + (Utility.random(5) * tier);
				range = 25;
				super.smallImage = new Image("Images/Items/Small/Crossbow.png");
				super.largeImage = new Image("Images/Items/Large/Crossbow.png");
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
	
	public int getType(){return type;}
	public int getDamage(){return damage;}
	public int getRange(){return range;}
	public boolean isTwoHanded(){return twoHanded;}
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
		setupName();
		setupImages();
		setupArmor();
	}
	
	public Armor(int tier, int type){
		super(tier);
		this.type = type;
		super.slot = Utility.random(7) + 1;
		setupName();
		setupImages();
		setupArmor();
	}
	
	public Armor(int tier, int type, int slot){
		super(tier);
		this.type = type;
		super.slot = slot;
		setupName();
		setupImages();
		setupArmor();
	}
	
	private void setupName(){super.name = Utility.getItemName(1,slot-2);}
	
	private void setupImages(){
		String directory = "";
		
		switch(slot){
			case 2:
				if(type == 0){directory += "Boots_" + Utility.random(2) + ".png";}
				else{directory += "Heavy_Boots_1.png";}
				break;
			case 3:
				directory += "Greaves_" + Utility.random(2) + ".png";
				break;
			case 4:
				if(type == 0){directory += "Cuirass_" + Utility.random(2) + ".png";}
				else{directory += "Chestplate_" + Utility.random(2) + ".png";}
				break;
			case 6:
				directory += "Gauntlets_" + Utility.random(2) + ".png";
				break;
			case 8:
				directory += "Robe_" + Utility.random(3) + ".png";
				break;
		}
		
		super.largeImage = new Image("Images/Items/Large/" + directory);
		super.smallImage = new Image("Images/Items/Small/" + directory);
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
	private int type;
	private int mpCost;
	
	Spellbook(int tier){
		super.tier = tier;
		type = Utility.random(3) - 1;
		setupSpellbook();
	}
	
	Spellbook(int tier, int type){
		super.tier = tier;
		this.type = type;
		setupSpellbook();
	}
	
	private void setupSpellbook(){
		super.name = "Spellbook";
		super.slot = 0;
		mpCost = tier * 5;
	}
	
	public Character[] spellEffect(Character... victims){
		Character[] output = victims;
		
		switch(type){
			case 0:
				for(int i=0;i<victims.length;i++){output[i] = areaOfEffect(victims[i]);}
				break;
			case 1:
				output[0] = damageOverTime(victims[0]);
				break;
			case 2:
				output[0] = debuff(victims[0]);
				break;
		}
		
		return output;
	}
	
	private Character areaOfEffect(Character target){
		target.damage(tier * 5);
		return target;
	}
	
	private Character damageOverTime(Character target){
		Timeline dmgOverTime = new Timeline(new KeyFrame(Duration.millis(1000), e-> {
			target.damage(tier * 5);
		}));
		dmgOverTime.setCycleCount(5);
		dmgOverTime.play();
		
		return target;
	}
	
	private Character debuff(Character target){
		int[] stats = target.getStats();
		
		for(int i=0;i<stats.length;i++){
			stats[i] -= tier;
		}
		
		target.setStats(stats);
		return target;
	}
	
	@Override public Pane getItemPanel(){
		Pane output = super.getItemPanel();
		
		StackPane button1 = Utility.setupButton("Spell\nSlot 1");
		button1.relocate(75, 350);
		
		StackPane button2 = Utility.setupButton("Spell\nSlot 2");
		button2.relocate(225, 350);
		
		output.getChildren().addAll(button1, button2);
		return output;
	}

	public int getMPCost(){return mpCost;}
	public int getType(){return type;}
	public void setSlot(int n){super.slot = n;}
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
	private int[] playerPosition;
	private int[] position;
	private int[][] layout;
	private ArrayList<Character> characters;
	private Image backgroundImage;
	private Pane background;
	private boolean[] availableDirections; //0 is North, 1 is West, 2 is South, 3 is East
	
	public Room(int n, int x, int y, int[][] layout){
		type = n;
		playerPosition = new int[2];
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
		
		backgroundImage = new Image("Images/Room.png");
		background.getChildren().add(Utility.setupImage(backgroundImage));
		
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
				for(int i=0;i<Utility.random(2);i++){characters.add(new Enemy(1));}
				break;
			case 1:
				
				break;
		}
	}
	
	public void animateCharacters(){
		for(int i=0;i<characters.size();i++){
			Character temp = characters.get(i);
			if(temp.getAlive()){
				int[] tempPosition = temp.getScreenPosition();
				int xDifference = tempPosition[0] - playerPosition[0];
				int yDifference = tempPosition[1] - playerPosition[1];
			
				if(Math.abs(xDifference) > Math.abs(yDifference)){
					if(xDifference > 0){temp.moveCharacter(Utility.Direction.WEST);}
					else{temp.moveCharacter(Utility.Direction.EAST);}
				}
				else{
					if(yDifference > 0){temp.moveCharacter(Utility.Direction.NORTH);}
					else{temp.moveCharacter(Utility.Direction.SOUTH);}
				}
			}
		}
	}
	
	public boolean inProximity(){
		for(int i=0;i<characters.size();i++){
			int[] tempPosition = characters.get(i).getScreenPosition();
			
			if(tempPosition[0] > playerPosition[0] - 25 && tempPosition[0] < playerPosition[0] + 25 && tempPosition[1] > playerPosition[1] - 25 && tempPosition[1] < playerPosition[1] + 25){
				return true;
			}
		}
		return false;
	}
	
	public void setPlayerPosition(int[] n){playerPosition = n;}
	public ArrayList<Character> getCharacters(){return characters;}
	public int getType(){return type;}
	public boolean[] getAvailableDirections(){return availableDirections;}
	public Pane getBackground(){return background;}
	public Image getBackgroundImage(){return backgroundImage;}
}

class Utility{//The Utility class largely exists for the sole purpose of storing miscellaneas data which would be cumbersome to actually store in the other classes themselves
	private static String[] playerNames = {"Harman", "Newland", "Lawson", "Bronson", "Millard", "M'ckenzie", "Fernly", "Mallory", "Abagail", "Arlene"};
	private static String[] statName = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
	private static String[] spellTypes = {"AoE", "DOT", "Debuff"};
	private static String[][] characterTypes = {
		{"Warrior","Rogue","Mage"},
		{"Villager","Merchant"},
		{"Giant Spider","Goblin","Skeleton"}
	};
	private static String[][] itemTypes = {
		{"Knife","Dagger","Claymore","Crossbow","Longbow"},
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
	
	public enum Direction{NORTH, WEST, SOUTH, EAST}
	
	public static int random(int max){
		int num = (int)(Math.random() * max) + 1;
		return num;
	}

	public static int random(int min, int max){
		int num = (int)(Math.random() * (min - max)) + 1 + min;
		return num;
	}
	
	public static String getPlayerName(){return playerNames[Utility.random(10) - 1];}
	public static String getCharacterName(int a, int b){return characterTypes[a][b];}
	public static String getItemName(int a, int b){return itemTypes[a][b];}
	public static String getStatName(int a){return statName[a];}
	public static String getSpellType(int a){return spellTypes[a];}
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
		output.setSmooth(true);
		output.setPreserveRatio(true);
		return output;
	}
	
	public static ImageView setupImage(Image image){
		ImageView output = new ImageView();
		output.setImage(image);
		output.setCache(true);
		output.setSmooth(true);
		output.setPreserveRatio(true);
		return output;
	}
}