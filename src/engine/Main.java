package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import locations.Location;
import locations.LocationDictionary;
import locations.LocationLoader;
import input.InputController;
import input.JoystickController;
import items.ItemHandler;
import items.ItemInventory;
import items.ItemLoader;
import entities.Camera;
import exceptions.CameraNotFoundException;
import exceptions.PlayerNotFoundException;
import gameplay.ActionController;
import gameplay.CombatSystem;
import gameplay.MovementController;
import graphics.EffectsController;
import graphics.GUIController;
import graphics.GraphicsController;
import npc.NPCLoader;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.util.ResourceLoader;

import quests.QuestHandler;
import quests.QuestLoader;
import savefiles.SaveGenerator;
import savefiles.SaveLoader;
import sound.SoundController;

public class Main extends BasicGame {
	public static boolean debug = true;
	public static GameContainer game_container;
	public static World world;
	public static int ScreenResX = 640;
	public static int ScreenResY = 360;
	public static int ResX = 640;
	public static int ResY = 360;
	public static boolean debug_mode = false;
	public static Controller controller = null;
	public static boolean FULLSCREEN = false;
	public static boolean VSYNC = false;
	public static boolean SHOWFPS = false;
	
	//public static Font font;
	public static TrueTypeFont ttf;
	public static UnicodeFont uni;
	
	public static Image font_image = null;
	public static AngelCodeFont font = null;
	
	public static int previous_state = States.PAUSED;
	public static int game_state = States.PAUSED;
	
	public static Stack<Integer> gameState = new Stack<Integer>();
	
	public static int input_device = InputController.KEYBOARD;
	
	public Main(String title) {
		super(title);
	}
	
	/**
	 * Function:     main
	 * Description:  Main entry point for the program, initializes Slick2D.
	 */
	public static void main(String[] args) throws SlickException {
		//AppGameContainer app = new AppGameContainer(new Main("Default Engine"));
		AppGameContainer app = new AppGameContainer(new ScalableGame(new Main("Default Engine"), ResX, ResY, true));
		//ResX = ScreenResX;
		//ResY = ScreenResY;
		app.setDisplayMode(ScreenResX, ScreenResY, FULLSCREEN);
		app.setIcon("res/icon/icon.png");
		app.setShowFPS(SHOWFPS);
		app.setVSync(VSYNC);
		app.start();
	}
	
	/**
	 * Function:     render
	 * Description:  Handles the rendering portion of the Slick2D Game Loop.
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//g.setFont(uni);
		if (world.init_anim_synchronizer) {
			g.setFont(font);
			GraphicsController.RenderWorld(world, g);
		} else {
			GraphicsController.RenderTileAnimations(world, g);
			world.init_anim_synchronizer = true;
		}
	}
	
	/**
	 * Function:     init
	 * Description:  Handles the initialization portion of the Slick2D Game Loop,
	 *               ie. Loading images, sounds, etc.
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		/*try {
			font = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/fonts/FreePixel.ttf"));
			font = font.deriveFont(Font.PLAIN , 40.0f);
			uni = new UnicodeFont(font);
			uni.addAsciiGlyphs();
			ColorEffect a = new ColorEffect();
			a.setColor(Color.white);
			uni.getEffects().add(a);
			uni.loadGlyphs();
		} catch (Exception e) {
		}*/
		ItemInventory.Init();
		ItemLoader.LoadItems();
		QuestLoader.LoadQuests();
		LocationLoader.LoadLocations();
		NPCLoader.LoadNPCs();
		font_image = new Image("res/fonts/minecraftia_0.tga");
		font_image.setFilter(Image.FILTER_NEAREST);
		font = new AngelCodeFont("res/fonts/minecraftia.fnt", font_image);
		
		world = new World();
		CombatSystem.battleWorld = new World();
		world.LoadTileDictionary("res/dictionaries/TileDictionary.dict");
		CombatSystem.battleWorld.LoadTileDictionary("res/dictionaries/TileDictionary.dict");
		world.LoadEntityDictionary("res/dictionaries/EntityDictionary.dict");
		CombatSystem.battleWorld.LoadEntityDictionary("res/dictionaries/EntityDictionary.dict");
		world.AddEntity(EntityFactory.CreateEntity(world, EntityDictionary.PLAYER, 288, 32, 32, 32));
		world.AddEntity(EntityFactory.CreateEntity(world, EntityDictionary.CAMERA, ResX / 2, ResY / 2, 32, 32));
		CombatSystem.battleWorld.AddEntity(EntityFactory.CreateEntity(world, EntityDictionary.CAMERA, ResX / 2, ResY / 2, 32, 32));
		
		SaveLoader.LoadSaveFile("save1.save");
		
		EffectsController.Init();
		//world.entity_dictionary.LoadAnimations("res/sprites/player/player.anim");
		
		
		Location loc = LocationDictionary.GetLocationByIndex(0);
		if (loc == null) {
			System.exit(0);
		}
		MapLoader.LoadMap(world, loc.mapfile);
		MapLoader.LoadMap(CombatSystem.battleWorld, loc.battlemapfile);
		world.currentLocationID = loc.id;
		CombatSystem.battleWorld.currentLocationID = loc.id;
		
		InputController.LoadKeyMapping("res/config/keymap.conf");
		String controller_name = JoystickController.LoadKeyMapping("res/config/joymap.conf");
		if (controller_name != null) {
			/* Finding controller */
			for (int i = 0; i < Controllers.getControllerCount(); i++) {
				Controller temp = Controllers.getController(i);
				if (temp.getName().toLowerCase().contains(controller_name)) {
					controller = temp;
					System.out.println(temp.getName());
					break;
				}
			}
		}
		
		//Entity npc = EntityFactory.CreateEntity(world, EntityDictionary.NPC, 384, 160, 32, 32);
		//Menu menu = new Menu("info_panel", "res/gui/info_panel.png", 0, 0, 400, 96);
		//world.AddMenu(menu);
		world.menus = Menu.LoadMenus("res/config/menus.conf");
		CombatSystem.battleWorld.menus = Menu.LoadMenus("res/config/menus.conf");
		//npc.dialog = new ArrayList<String>();
		//npc.dialog.add("Hello World");
		//world.AddEntity(npc);
		//world.AddEntity(EntityFactory.CreateEntity(world, EntityDictionary.ENEMY, 384, 416, 32, 32));
		//world.AddEntity(EntityFactory.CreateEntity(world, EntityDictionary.PLAYER, 288, 32, 32, 32));
		//world.AddEntity(EntityFactory.CreateEntity(world, EntityDictionary.CAMERA, ResX / 2, ResY / 2, 32, 32));
		try {
			int player = world.FindPlayer();
			Camera.Follow(player);
		} catch (PlayerNotFoundException e) {
		}
		SoundController.LoadMusic("res/sounds/Track-03.ogg");
		
		SaveGenerator.SaveGame("save2.save");
		
		game_state = States.RUNNING;
		SetState(States.RUNNING);
		SetState(States.RUNNING);
	}
	
	/**
	 * Function:     update
	 * Description:  Handles input, movement, and actions during the Slick2D Game Loop.
	 */
	@Override
	public void update(GameContainer gc, int fps_scaler) throws SlickException {
		game_container = gc;
		if (!SoundController.IsPlaying()) {
			//SoundController.PlayMusic(true);
		}
		if (GetPreviousState() == States.BATTLE) { // NOTE: may want to replace current state and previous state with a stack (like was done with menus)
			CombatSystem.CleanupBattleWorld();
			RemovePreviousState();
		}
		
		Input input = gc.getInput();
		HashMap<String, Boolean> held_keys = InputController.HandleHeldInput(input, controller);
		HashMap<String, Boolean> pressed_keys = InputController.HandlePressedInput(input, controller);
		MovementController.HandleMovement(world, held_keys, fps_scaler);
		ActionController.HandleEntityAction(world, pressed_keys, held_keys, fps_scaler);
		world.tile_dictionary.UpdateAnimations(fps_scaler);
		world.UpdateEntityAnimations(fps_scaler);
		if (GetState() == States.RUNNING) {
			world.UpdateEntityHitTimers(fps_scaler);
			CombatSystem.UpdateCooldowns(world, fps_scaler);
			CombatSystem.CleanupDeadEntities(world);
			CombatSystem.UpdateSpawnTimers(world, fps_scaler);
			QuestHandler.UpdateQuestStatuses();
		}
		if (GetState() == States.BATTLE) {
			CombatSystem.InitBattleWorld(world);
			CombatSystem.HandleBattleTurns(world);
		}
		EffectsController.UpdateEffects(fps_scaler);
		ItemHandler.CleanUpInventory();
		
		if (debug && input.isKeyPressed(Input.KEY_F1)) {
			debug_mode = !debug_mode;
		}
		if (debug && input.isKeyPressed(Input.KEY_F2)) {
			if (GetState() == States.RUNNING) {
				//Main.previous_state = Main.game_state;
				//Main.game_state = States.BATTLE;
				SetState(States.BATTLE);
			} else if (GetState() == States.BATTLE) {
				//Main.previous_state = Main.game_state;
				//Main.game_state = States.RUNNING;
				SwapCurrentPreviousStates();
			}
			//Location loc = LocationDictionary.GetLocationByID(world.currentLocationID);
			//MapLoader.LoadMap(world, loc.battlemapfile);
		}
		if (debug && input.isKeyPressed(Input.KEY_F3)) {
			System.out.println("Menu Queue:");
			for (int i = 0; i < GUIController.menuQueue.size(); i++) {
				System.out.println(GUIController.menuQueue.get(i));
			}
		}
		if (debug && input.isKeyPressed(Input.KEY_F4)) {
			System.out.println("Game State Queue:");
			for (int i = 0; i < Main.gameState.size(); i++) {
				System.out.println(Main.gameState.get(i));
			}
		}
	}
	
	/*public static void SetState(int state) {
		previous_state = game_state;
		game_state = state;
	}
	
	public static int GetState() {
		return game_state;
	}
	
	public static int GetPreviousState() {
		return previous_state;
	}*/
	
	public static void SetState(int state) {
		gameState.push(state);
	}
	
	public static void SetPreviousState() {
		gameState.pop();
	}
	
	public static void SwapCurrentPreviousStates() {
		int current = gameState.pop();
		int previous = gameState.pop();
		gameState.push(current);
		gameState.push(previous);
	}
	
	public static void RemovePreviousState() {
		int current = gameState.pop();
		int previous = gameState.pop();
		if (gameState.empty()) {
			gameState.push(current);
		}
		gameState.push(current);
	}
	
	public static int GetState() {
		if (gameState.empty()) {
			return States.ERROR;
		}
		return gameState.peek();
	}
	
	public static int GetPreviousState() {
		if (gameState.empty()) {
			return States.ERROR;
		}
		int temp = gameState.pop();
		int val = gameState.peek();
		gameState.push(temp);
		return val;
	}
	
	
	public void keyPressed(int key, char c) {
		if (GetState() == States.MENU && GUIController.GetCurrentMenu() == GUIController.MENU_CONTROLS
				&& GUIController.selectedControlField != -1 && Main.CurrentController() == InputController.KEYBOARD) {
			int menuID = world.FindMenu(GUIController.MENU_CONTROLS);
			Menu temp = world.GetMenu(menuID);
			
			for (int i = 0; i < temp.GetMenuItemCount(); i++) {
				MenuItem item = temp.GetMenuItem(i);
				
				if (i == GUIController.selectedControlField) {
					String keyname = "KEY_" + item.text;
					int key_value = GUIController.GetKeyValue(keyname);
					
					if (GUIController.GetKeyValue(keyname) != null) {
						if (key_value != 28 && !GUIController.tempKeyMap.containsValue(key)) {
							GUIController.tempKeyMap.put(keyname, key);
						}
					}
					break;
				}
			}
		}
	}
	
	public static void SetController(int controller) {
		input_device = controller;
	}
	
	public static int CurrentController() {
		return input_device;
	}
}
