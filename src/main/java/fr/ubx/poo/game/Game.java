/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;


import static fr.ubx.poo.game.WorldEntity.BombNumberDec;
import static fr.ubx.poo.game.WorldEntity.BombNumberInc;
import static fr.ubx.poo.game.WorldEntity.BombRangeDec;
import static fr.ubx.poo.game.WorldEntity.BombRangeInc;
import static fr.ubx.poo.game.WorldEntity.Box;
import static fr.ubx.poo.game.WorldEntity.DoorNextClosed;
import static fr.ubx.poo.game.WorldEntity.Empty;
import static fr.ubx.poo.game.WorldEntity.Heart;
import static fr.ubx.poo.game.WorldEntity.Key;
import static fr.ubx.poo.game.WorldEntity.Monster;
import static fr.ubx.poo.game.WorldEntity.Player;
import static fr.ubx.poo.game.WorldEntity.Princess;
import static fr.ubx.poo.game.WorldEntity.Stone;
import static fr.ubx.poo.game.WorldEntity.Tree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Properties;

import fr.ubx.poo.model.go.Bomb;
import fr.ubx.poo.model.go.character.*;

public class Game {

    private final World[] tabWorld;
    private final Player player;
    private Monster[] monsters;
    private final String worldPath;
    private int initPlayerLives;
    private int nbrLevels;
    private int indiceWorld;
    private int predIndiceWorld;

    public Game(String worldPath) {
        this.worldPath = worldPath;
        loadConfig(worldPath);
        tabWorld = new World[nbrLevels];
    	indiceWorld = 0;
        WorldEntity[][] level1 = parse(worldPath+"/level1.txt");
        tabWorld[0] = new World( level1 , 1);
        WorldEntity[][] level2 = parse(worldPath+"/level2.txt");
        tabWorld[1] = new World( level2 , 2);
        WorldEntity[][] level3 = parse(worldPath+"/level3.txt");
        tabWorld[2] = new World( level3 , 3);
        Position positionPlayer = null;
        Position[] positionsMonsters = null;
        try {
    		positionPlayer = tabWorld[0].findPlayer();
            player = new Player(this, positionPlayer); 
            positionsMonsters = tabWorld[indiceWorld].findMonsters();
            int nbMonsters = positionsMonsters.length;
        	monsters = new Monster[nbMonsters];
            for(int i=0; i<nbMonsters; i++) {
            	monsters[i] = new Monster(this, positionsMonsters[i]);
            }
            
        } catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public int getInitPlayerLives() {
        return initPlayerLives;
    }

    private void loadConfig(String path) {
        try (InputStream input = new FileInputStream(new File(path, "config.properties"))) {
            Properties prop = new Properties();
            // load the configuration file
            prop.load(input);
            initPlayerLives = Integer.parseInt(prop.getProperty("lives", "3"));
            nbrLevels = Integer.parseInt(prop.getProperty("levels","3"));
        } catch (IOException ex) {
            System.err.println("Error loading configuration");
        }
    }
    
    private WorldEntity[][] parse(String worldPath) {
    	try (InputStream input = new FileInputStream(worldPath)){
    		byte[] byteArray = input.readAllBytes();
    		input.close();
    		int lineLength = 0;
    		int nbrLines = 0;
    		for(byte b:byteArray) {
    			if((char)b!='\n') {
    				lineLength++;
    			}
    			else {
    				lineLength++;
    				nbrLines++;
    			}
    		}
    		lineLength/=nbrLines;
    		WorldEntity world[][] = new WorldEntity[nbrLines][lineLength-1];
    		for(int i=0; i<nbrLines; i++) {
    			for(int j=0; j<lineLength; j++) {
    				char c = (char)byteArray[i*lineLength+j];
    				if(c == '\n') {
    					break;
    				}
    				if(c == WorldEntity.BombNumberDec.getCode())
    					world[i][j] = WorldEntity.BombNumberDec;
    				if(c == WorldEntity.BombNumberInc.getCode())
    					world[i][j] = WorldEntity.BombNumberInc;
    				if(c == WorldEntity.BombRangeDec.getCode())
    					world[i][j] = WorldEntity.BombRangeDec;
    				if(c == WorldEntity.BombRangeInc.getCode())
    					world[i][j] = WorldEntity.BombRangeInc;
    				if(c == WorldEntity.Box.getCode())
    					world[i][j] = WorldEntity.Box;
    				if(c == WorldEntity.DoorNextClosed.getCode())
    					world[i][j] = WorldEntity.DoorNextClosed;
    				if(c == WorldEntity.DoorNextOpened.getCode())
    					world[i][j] = WorldEntity.DoorNextOpened;
    				if(c == WorldEntity.DoorPrevOpened.getCode())
    					world[i][j] = WorldEntity.DoorPrevOpened;
    				if(c == WorldEntity.Empty.getCode())
    					world[i][j] = WorldEntity.Empty;
    				if(c == WorldEntity.Heart.getCode())
    					world[i][j] = WorldEntity.Heart;
    				if(c == WorldEntity.Key.getCode())
    					world[i][j] = WorldEntity.Key;
    				if(c == WorldEntity.Monster.getCode())
    					world[i][j] = WorldEntity.Monster;
    				if(c == WorldEntity.Player.getCode())
    					world[i][j] = WorldEntity.Player;
    				if(c == WorldEntity.Princess.getCode())
    					world[i][j] = WorldEntity.Princess;
    				if(c == WorldEntity.Stone.getCode())
    					world[i][j] = WorldEntity.Stone;
    				if(c == WorldEntity.Tree.getCode())
    					world[i][j] = WorldEntity.Tree;
    			}
    		}
    		return world;
    	}
    	catch (IOException e){
    		System.err.println("Error loading world");
    	}
		return null;
    }

    public World getWorld() {
    	return tabWorld[indiceWorld];
    }
    
    public World getPredWorld() {
    	return tabWorld[predIndiceWorld];
    }

    public Player getPlayer() {
        return this.player;
    }

    public Monster[] getMonsters() {
    	return this.monsters;
    }
    
    public void levelDown() {
    	this.predIndiceWorld = indiceWorld;
    	this.indiceWorld = indiceWorld - 1;
    }
    
    public void levelUp() {
    	this.predIndiceWorld = indiceWorld;
    	this.indiceWorld = indiceWorld + 1;
    }

}
