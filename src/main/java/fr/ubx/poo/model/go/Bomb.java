package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.decor.Box;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.decor.Heart;
import fr.ubx.poo.model.decor.Stone;
import fr.ubx.poo.model.decor.Tree;
import fr.ubx.poo.model.decor.door.DoorDown;
import fr.ubx.poo.model.decor.door.DoorUpClosed;
import fr.ubx.poo.model.decor.door.DoorUpOpened;
import fr.ubx.poo.model.decor.bomb.BombNbDec;
import fr.ubx.poo.model.decor.bomb.BombNbInc;
import fr.ubx.poo.model.decor.bomb.BombRngDec;
import fr.ubx.poo.model.decor.bomb.BombRngInc;
import fr.ubx.poo.game.Direction;

public class Bomb extends GameObject{
	private long creation_time;
	private int scope;
	
	public Bomb(Game game, Position position, long now) {
		super(game, position);
		this.creation_time = now;
		this.scope = game.getPlayer().getScope();
		//game.getPlayer().loseBomb();
	}
	
	public long getCreationTime() {
		return this.getCreationTime();
	}
	
	public void explode() {
		World w = this.game.getWorld();
		Position[] explosionTab = new Position[this.scope];
		Direction[] dirTab = {Direction.N, Direction.E, Direction.S, Direction.W};
		Position pos = this.getPosition();
		
		for(Direction d : dirTab) {
			Position nextPos = d.nextPosition(pos);
			for(int i=0; i<this.scope; i++) {
				Decor object = w.get(nextPos);
				if(object instanceof Stone || object instanceof Tree) {
					break;
				}
				if(object instanceof DoorDown || object instanceof DoorUpClosed || object instanceof DoorUpOpened) {
					break;
				}
				else {
					w.clear(nextPos);
					//w.set(nextPos, new Explosion());
					w.changed();
				}
				if(object instanceof Box || object instanceof BombNbDec || object instanceof BombNbInc || object instanceof BombRngDec || object instanceof BombRngInc || object instanceof Heart) {
					break;
				}
			}
		}
		//game.getPlayer().winBomb();
	}
	
	public void update(long now) {
		
	}
	
}
