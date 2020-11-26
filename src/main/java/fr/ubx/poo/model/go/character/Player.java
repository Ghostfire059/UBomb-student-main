/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.decor.bomb.*;
import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.game.Game;

public class Player extends GameObject implements Movable {

    private boolean alive = true;
    Direction direction;
    private boolean moveRequested = false;
    private int lives = 3;
    private int keys = 0;
    private int bombs = 3;
    private int scope = 1;
    private boolean winner;

    public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.S;
        this.lives = game.getInitPlayerLives();
    }

    public int getLives() {
        return lives;
    }
    public void loseLife() {
    	if(this.lives>0) {
    		this.lives-=1;
    	}
    }
    
    public int getKeys() {
        return keys;
    }
    
    public int getBombs() {
        return bombs;
    }
    
    public int getScope() {
    	return scope;
    }

    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
    }

    @Override
    public boolean canMove(Direction direction) {
    	Position nextPos = direction.nextPosition(getPosition());
    	Decor object = game.getWorld().get(nextPos);
    	
    	if(object instanceof Box) {
    		Position furtherPos = direction.nextPosition(nextPos);
    		Decor furtherObject = game.getWorld().get(furtherPos);
    		return furtherPos.inside(game.getWorld().dimension) && !(furtherObject instanceof Decor);
    	}
    	return nextPos.inside(game.getWorld().dimension) && !(object instanceof Tree || object instanceof Stone) ;
    }

    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    	Decor decor = game.getWorld().get(nextPos);
    	
    	if(decor instanceof Box) {
    		World w = game.getWorld();
    		Position furtherPos = direction.nextPosition(nextPos);
    		w.clear(nextPos);
    		w.set(furtherPos, new Box());
    		w.changed();
    	}
    	
    	if(this.game.getWorld().getEntity(nextPos)==WorldEntity.Monster) {
    		this.loseLife();
    		if(this.lives==0) {
    			this.alive = false;
    		}
    		System.out.println(this.lives);
    	}
    	
    	if(decor instanceof Princess) {
    		this.winner = true;
    	}
        if(decor instanceof Heart) {
        	this.lives = this.lives + 1;
        }
        if(decor instanceof Key){
        	this.keys = this.keys + 1;
        }
        if(decor instanceof BombNbInc){
        	this.bombs = this.bombs + 1;
        }
        if(decor instanceof BombNbDec){
        	this.bombs = this.bombs - 1;
        }
        if(decor instanceof BombRngInc){
        	this.scope = this.scope + 1;
        }
        if(decor instanceof BombRngDec){
        	if( this.scope > 1 ) {
				this.scope = this.scope - 1;
			}
        }
    }

    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isAlive() {
        return alive;
    }

}
