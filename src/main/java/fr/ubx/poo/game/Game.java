/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import fr.ubx.poo.model.go.character.*;

public class Game {

    private final World world;      //World[] tabWorld;
    private final Player player;
    private Monster[] monsters;
    private final String worldPath;
    public int initPlayerLives;

    public Game(String worldPath) {
        world = new WorldStatic();    //initialiser les différents niveaux
        this.worldPath = worldPath;
        loadConfig(worldPath);
        Position positionPlayer = null;
        Position[] positionsMonsters = null;
        try {
            positionPlayer = world.findPlayer();    //niveau 0 findPlayer, niveau sup position de DoorDown
            player = new Player(this, positionPlayer);    //niveau inf position de DoorUpOpened
            positionsMonsters = world.findMonsters();
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
        return world;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Monster[] getMonsters() {
    	return this.monsters;
    }

}
