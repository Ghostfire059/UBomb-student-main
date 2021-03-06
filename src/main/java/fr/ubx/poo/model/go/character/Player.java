/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.go.Bomb;
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
    private boolean winner = false;
    private boolean invincible = false;
    private long timer = 0;
    private boolean bombRequested = false;
    private Bomb tabBombs[];

    public Player(Game game, Position position, int nbrBombMax) {
        super(game, position);
        this.direction = Direction.S;
        this.lives = game.getInitPlayerLives();
        this.tabBombs = new Bomb[nbrBombMax];
        this.bombs = game.getInitPlayerBombs();
    }
    
    public Game getGame() {
        return super.game;
    }
    
    public int getLives() {
        return lives;
    }
    public void addLife() {
    	this.lives = this.lives + 1;
    }
    public void loseLife() {
    	if(this.lives > 0 && !invincible) {
    		this.lives -= 1;
    		this.invincible = true;
    	}
    }
    
    public int getKeys() {
        return keys;
    }
    public void addKeys() {
    	this.keys = this.keys + 1;
    }
    public void loseKeys() {
    	this.keys -= 1;
    }
    
    public int getBombs() {
        return bombs;
    }
    
    public Bomb[] getTabBombs() {
    	return this.tabBombs;
    }
    
    public void addBombs() {
    	this.bombs = this.bombs + 1;
    }
    public void loseBombs() {
    	this.bombs = this.bombs - 1;
    }
    
    public int getScope() {
    	return scope;
    }
    public void addScope() {
    	this.scope = this.scope + 1;
    }
    public void loseScope() {
		this.scope = this.scope - 1;
    }

    public Direction getDirection() {
        return direction;
    }
    
    public boolean bombRequested() {
    	return this.bombRequested;
    }
    
    public void requestBomb() {
    	if(this.getPosition().inside(this.game.getWorld().dimension) && this.bombs>0) {
    		bombRequested = true;
    	}
    	else {
    		bombRequested = false;
    	}
    }
    
    public void setBomb(long now) {
    	if(this.getPosition().inside(this.game.getWorld().dimension)) {
    		int tmp=0;
    		while(this.tabBombs[tmp]!=null && tmp<this.tabBombs.length) {
    			tmp++;
    		}
    		this.tabBombs[tmp]=new Bomb(this.game, this.getPosition(), now);
    	}
    	this.loseBombs();
    }

    public void winner() {
    	this.winner = true;
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
    		for(Monster m:this.game.getMonsters()[game.getIndice()]) {
    			if(m!=null) {
    				if(m.getPosition().equals(furtherPos)) {
    					return false;
    				}
    			}
    		}
    		return furtherPos.inside(game.getWorld().dimension) && (game.getWorld().get(furtherPos) == null );
    	}
    	return nextPos.inside(game.getWorld().dimension) && (object == null || object.isCrossable()) ;
    }

    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
		World w = game.getWorld();
    	Decor decor = w.get(nextPos);
		if( decor != null) {
			if( decor.isTaken() ) {
	    		decor.crossIt(this);
	    	}
		}
    	
    	for(Monster m : game.getMonsters()[game.getIndice()]) {
    		if(m!=null) {    			
    			if(this.getPosition().equals(m.getPosition())) {
    				this.loseLife();
    			}
    		}
    	}
 
    }

    public void update(long now) {
    	if(this.lives<=0) {
    		this.alive = false;
    	}
    	if( this.invincible && this.timer == 0 && this.alive) {
    		this.timer = now;
    	}
    	if( this.invincible && now-this.timer >= 2000000000) {
    		this.invincible = false;
    		this.timer = 0;
    	}
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
    
    public boolean isInvincible() {
        return invincible;
    }

}
