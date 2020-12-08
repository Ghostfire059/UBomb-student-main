package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.go.GameObject;

public class Monster extends GameObject implements Movable{
	
	private boolean alive = true;
	Direction direction;
	private boolean moveRequested = false;
	private long prev_move = 0;

	public Monster(Game game, Position position) {
		super(game, position);
		this.direction = Direction.S;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public boolean alive() {
		return this.alive;
	}
	public void die() {
		this.alive = false;
	}
	
	public void hit(Player p) {
		p.loseLife();
	}
	
	public void requestMove(Direction direction) {
		if(direction!=this.direction) {
			this.direction = direction;
		}
		this.moveRequested = true;
	}

	@Override
	public boolean canMove(Direction direction) {
		Position nextPos = direction.nextPosition(getPosition());
		Decor object = game.getWorld().get(nextPos);
		
		return nextPos.inside(game.getWorld().dimension) && (object == null || object.isCrossable());
	}

	@Override
	public void doMove(Direction direction) {
		Position nextPos = direction.nextPosition(getPosition());
		setPosition(nextPos);
		if(this.getPosition().equals(game.getPlayer().getPosition())) {
			this.hit(game.getPlayer());
		}
	}
	
	public void update(long now) {
		if(now-this.prev_move>1500000000) {
			Direction d = Direction.random();
			requestMove(d);
			if(this.moveRequested) {
				if(this.canMove(this.direction)) {
					this.doMove(this.direction);
				}
			}
			moveRequested = false;
			this.prev_move = now;
		}
	}

}
