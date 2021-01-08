/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.engine;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.view.sprite.Sprite;
import fr.ubx.poo.view.sprite.SpriteFactory;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.decor.door.DoorUpClosed;
import fr.ubx.poo.model.go.Bomb;
import fr.ubx.poo.model.go.character.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final String windowTitle;
    private final Game game;
    private final Player player;
    private Monster monsters[][];
    private final List<Sprite> sprites = new ArrayList<>();
    private StatusBar statusBar;
    private Pane layer;
    private Input input;
    private Stage stage;
    private Sprite spritePlayer;
    private Sprite spriteMonsters[][];
    private Sprite spriteBomb[][];

    public GameEngine(final String windowTitle, Game game, final Stage stage) {
        this.windowTitle = windowTitle;
        this.game = game;
        this.player = game.getPlayer();
        this.monsters = game.getMonsters();
        this.spriteMonsters = new Sprite[this.game.getNbrLevels()][];
        initialize(stage, game);
       	int nbrBombMax = 3+this.game.getWorld().findNbrBombMax();     	
        this.spriteBomb = new Sprite[this.game.getNbrLevels()][nbrBombMax];
        buildAndSetGameLoop();
    }

    private void initialize(Stage stage, Game game) {
        this.stage = stage;
        Group root = new Group();
        layer = new Pane();

        int height = game.getWorld().dimension.height;
        int width = game.getWorld().dimension.width;
        int sceneWidth = width * Sprite.size;
        int sceneHeight = height * Sprite.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        input = new Input(scene);
        root.getChildren().add(layer);
        statusBar = new StatusBar(root, sceneWidth, sceneHeight, game);
        // Create decor sprites
        game.getWorld().forEach( (pos,d) -> sprites.add(SpriteFactory.createDecor(layer, pos, d)));
        //Create go sprites
        spritePlayer = SpriteFactory.createPlayer(layer, player);
        int nbMonsters = monsters[this.game.getIndice()].length;
        this.spriteMonsters[this.game.getIndice()] = new Sprite[nbMonsters];
        for(int i=0; i<nbMonsters; i++) {
        	if(monsters[this.game.getIndice()][i]!=null) {
        		this.spriteMonsters[this.game.getIndice()][i] = SpriteFactory.createMonster(layer, monsters[this.game.getIndice()][i]);        		
        	}
        }
    }

    protected final void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {
                // Check keyboard actions
            	processInput(now);

                // Do actions
                update(now);
                
                // Graphic update
                render();
                statusBar.update(game);
            }
        };
    }

    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        }
        if (input.isMoveDown()) {
            player.requestMove(Direction.S);
        }
        if (input.isMoveLeft()) {
            player.requestMove(Direction.W);
        }
        if (input.isMoveRight()) {
            player.requestMove(Direction.E);
        }
        if (input.isMoveUp()) {
            player.requestMove(Direction.N);
        }
        if (input.isKey()) {
        	Position nextPos = player.getDirection().nextPosition(player.getPosition());
        	Decor object = game.getWorld().get(nextPos);
        	if (object instanceof DoorUpClosed) {
        		object.crossIt(player);
        	}
        }
        if (input.isBomb()){
        	player.requestBomb();
        	if(player.bombRequested()) {
        		player.setBomb(now);
            	Bomb[] bombs = player.getTabBombs();
                for(int i=0; i<this.spriteBomb[this.game.getIndice()].length; i++) {
                	Bomb bomb = bombs[i];
                	if(bomb!=null) {
                    	this.spriteBomb[this.game.getIndice()][i] = SpriteFactory.createBomb(layer, bomb);
                	}
                }
        	}
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }


    private void update(long now) {    	
    	int indice = this.game.getIndice();
    	Monster[] tabMonsters = this.monsters[indice];
    	for(int i=0; i< tabMonsters.length; i++) {
    		if(tabMonsters[i]!=null) {
    			tabMonsters[i].update(now);
    			if(!tabMonsters[i].alive()) {
    				this.spriteMonsters[indice][i].remove();
        			this.spriteMonsters[indice][i]=null;
        			this.monsters[indice][i]=null;
    			}
    		}
    	}
        
    	Bomb[] tabBombs = this.player.getTabBombs();
    	for(int i=0; i<tabBombs.length; i++) {
    		Bomb b = tabBombs[i];
    		if(b!=null) {
    			b.update(now);
    			if(b.getState()==0) {  
    				if(this.spriteBomb[this.game.getIndice()][i]!=null) {
    					this.spriteBomb[this.game.getIndice()][i].remove();
    					this.spriteBomb[this.game.getIndice()][i]=null;
    				}    					
    			}
    			if(b.getState()==-1) {
    				b=null;
    				tabBombs[i]=null;    				
    			}
    		}
    	}

    	int activeLevel = game.getIndice();
    	player.update(now);
    	int newActiveLevel = game.getIndice();
    	if(activeLevel!=newActiveLevel) {
    		initialize(stage, game);
    	}
    	
        if (player.isAlive() == false) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }
        if (player.isWinner()) {
            gameLoop.stop();
            showMessage("Gagné", Color.BLUE);
        }
        
        if(game.getWorld().hasChanged()) {
        	sprites.forEach(Sprite::remove);
        	sprites.clear();
        	initialize(stage, game);
        	game.getWorld().changed();
        }
    }

    private void render() {
        for(Sprite m : this.spriteMonsters[this.game.getIndice()]) {
        	if(m!=null) {        		
        		m.render();
        	}
        }
        
        for(Sprite b : this.spriteBomb[this.game.getIndice()]) {
        	if(b!=null) {
        		b.render();
        	}
        }
        sprites.forEach(Sprite::render);
        // last rendering to have player in the foreground
        spritePlayer.render();        
    }

    public void start() {
        gameLoop.start();
    }
}
