/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;

import fr.ubx.poo.model.decor.Decor;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

public class World {
    private final Map<Position, Decor> grid;
    private final WorldEntity[][] raw;
    public final Dimension dimension;
    private boolean changed = true;
    public final int level;
    private boolean actived;

    public World(WorldEntity[][] raw) {
        this.raw = raw;
        dimension = new Dimension(raw.length, raw[0].length);
        grid = WorldBuilder.build(raw, dimension);
        this.level = 0;
        this.actived = true;
    }
    
    public World(WorldEntity[][] raw, int level) {
        this.raw = raw;
        dimension = new Dimension(raw.length, raw[0].length);
        grid = WorldBuilder.build(raw, dimension);
        this.level = level;
        this.actived = false;
    }

    public Position findPlayer() throws PositionNotFoundException {
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (raw[y][x] == WorldEntity.Player) {
                    return new Position(x, y);
                }
            }
        }
        throw new PositionNotFoundException("Player");
    }
    
    public Position[] findMonsters() throws PositionNotFoundException{
    	Position[] tmp= new Position[100];
    	int cpt=0;
    	for(int x=0; x<this.dimension.width; x++) {
    		for(int y =0; y<this.dimension.height; y++) {
    			if(this.raw[y][x] == WorldEntity.Monster) {
    				tmp[cpt] = new Position(x, y);
    				cpt++;
    			}
    		}
    	}
    	Position[] tabPos = new Position[cpt];
    	for(int i=0; i<cpt; i++) {
    		tabPos[i] = tmp[i];
    	}
    	return tabPos;
    }

    public Decor get(Position position) {
        return grid.get(position);
    }

    public WorldEntity getEntity(Position pos) {
    	return raw[pos.y][pos.x];
    }
    	
    public void set(Position position, Decor decor) {
        grid.put(position, decor);
    }

    public void clear(Position position) {
        grid.remove(position);
    }

    public void forEach(BiConsumer<Position, Decor> fn) {
        grid.forEach(fn);
    }

    public Collection<Decor> values() {
        return grid.values();
    }

    public boolean isInside(Position position) {
        return position.inside(dimension);
    }

    public boolean isEmpty(Position position) {
        return grid.get(position) == null;
    }
    
    public boolean hasChanged() {
    	return this.changed;
    }
    
    public void changed() {
    	this.changed = !this.changed;
    }
    
    public boolean isActived() {
    	return this.actived;
    }
    
    public void actived() {
    	this.actived = !this.actived;
    }
}
