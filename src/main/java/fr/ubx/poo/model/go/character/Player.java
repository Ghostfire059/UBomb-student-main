/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import static fr.ubx.poo.view.image.ImageResource.BOMBNBDEC;
import static fr.ubx.poo.view.image.ImageResource.BOMBNBINC;
import static fr.ubx.poo.view.image.ImageResource.BOMBRNGDEC;
import static fr.ubx.poo.view.image.ImageResource.BOMBRNGINC;
import static fr.ubx.poo.view.image.ImageResource.DOORCLOSED;
import static fr.ubx.poo.view.image.ImageResource.DOOROPENED;
import static fr.ubx.poo.view.image.ImageResource.HEART;
import static fr.ubx.poo.view.image.ImageResource.KEY;
import static fr.ubx.poo.view.image.ImageResource.PRINCESS;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.decor.bomb.*;
import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.view.sprite.SpriteDecor;
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
    	WorldEntity object = game.getWorld().getEntity(nextPos);
    	return nextPos.inside(game.getWorld().dimension) && object.isCrossable();
    }

    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    	Decor decor = game.getWorld().get(nextPos);
    	if(decor instanceof Princess) {
    		this.winner = true;
    	}/*
    	if(decor instanceof Monster) {
    		this.lives = this.lives - 1;
    		if( this.lives == 0) {
    			this.alive = false;
    		}
    	}*/
        if(decor instanceof Heart) {
        	this.lives = this.lives + 1;
        }
        if(decor instanceof Key){
        	this.keys = this.keys + 1;
			System.out.println("key+1");
        }
        if(decor instanceof BombNbInc){
        	this.bombs = this.bombs + 1;
			System.out.println("bomb+1");
        }
        if(decor instanceof BombNbDec){
        	this.bombs = this.bombs - 1;
			System.out.println("bomb-1");
        }
        if(decor instanceof BombRngInc){
        	this.scope = this.scope + 1;
			System.out.println("scope+1");
        }
        if(decor instanceof BombRngDec){
        	if( this.scope > 1 ) {
				this.scope = this.scope - 1;
				System.out.println("scope-1");
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
