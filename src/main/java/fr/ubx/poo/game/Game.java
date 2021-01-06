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
import java.util.Properties;

import fr.ubx.poo.model.go.Bomb;
import fr.ubx.poo.model.go.character.*;

public class Game {

    private final World[] tabWorld;
    private final Player player;
    private Monster[] monsters;
    private final String worldPath;
    public int initPlayerLives;

    public Game(String worldPath) {
        tabWorld = new World[1];    //initialiser les différents niveaux
        for(int i=0; i<1; i++) {
        	tabWorld[i] = new WorldStatic();   //new World( raw , i);
        }
        this.worldPath = worldPath;
        loadConfig(worldPath);
        Position positionPlayer = null;
        Position[] positionsMonsters = null;
        try {
            positionPlayer = tabWorld[0].findPlayer();    //niveau 0 findPlayer, niveau sup position de DoorDown
            player = new Player(this, positionPlayer);    //niveau inf position de DoorUpOpened
            positionsMonsters = tabWorld[0].findMonsters();
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
        } catch (IOException ex) {
            System.err.println("Error loading configuration");
        }
    }
    
    public World getWorld() {
    	for(World t : tabWorld) {
    		if( t.isActived() ) {
    			return t;
    		}
    	}
    	return tabWorld[0];
    }

    public World getWorld(int level) {
        return tabWorld[level];
    }

    public Player getPlayer() {
        return this.player;
    }

    public Monster[] getMonsters() {
    	return this.monsters;
    }

}
