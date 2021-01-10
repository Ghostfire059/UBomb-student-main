package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;

public class Character extends GameObject {
	
	protected boolean alive = true;
	Direction direction;
	protected boolean moveRequested = false;

	public Character(Game game, Position position) {
		super(game, position);
	}
	

	public Direction getDirection() {
		return this.direction;
	}
	
	public boolean alive() {
		return this.alive;
	}
	
    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
    }

}
