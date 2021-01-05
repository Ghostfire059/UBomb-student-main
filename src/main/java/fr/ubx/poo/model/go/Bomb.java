package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.decor.Box;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.decor.Explosion;
import fr.ubx.poo.model.decor.Heart;
import fr.ubx.poo.model.decor.Stone;
import fr.ubx.poo.model.decor.Tree;
import fr.ubx.poo.model.decor.door.DoorDown;
import fr.ubx.poo.model.decor.door.DoorUpClosed;
import fr.ubx.poo.model.decor.door.DoorUpOpened;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.decor.bomb.BombNbDec;
import fr.ubx.poo.model.decor.bomb.BombNbInc;
import fr.ubx.poo.model.decor.bomb.BombRngDec;
import fr.ubx.poo.model.decor.bomb.BombRngInc;
import fr.ubx.poo.game.Direction;

public class Bomb extends GameObject{
	private static final long SEC = 1000000000;
	private long creation_time;
	private int scope;
	private int state = 4;
	
	public Bomb(Game game, Position position, long now) {
		super(game, position);
		this.creation_time = now;
		this.scope = game.getPlayer().getScope();
	}
	
	public long getCreationTime() {
		return this.getCreationTime();
	}
	
	public int getState() {
		return this.state;
	}
	
	public void explode() {
		World w = this.game.getWorld();
		Direction[] dirTab = {Direction.N, Direction.E, Direction.S, Direction.W};
		Position[] explosionTab = new Position[this.scope*dirTab.length];
		Position pos = this.getPosition();
		
		for(Direction d : dirTab) {
			Position nextPos = d.nextPosition(pos);
			for(int i=0; i<this.scope; i++) {
				Decor object = w.get(nextPos);
				if(object==null || object instanceof Box || object instanceof Heart || object instanceof BombNbInc || object instanceof BombNbDec || object instanceof BombRngInc || object instanceof BombRngDec) {
					//rempli explosionTab
					int tmp = 0;
					while(tmp<explosionTab.length && explosionTab[tmp]!=null) {
						tmp++;
					}
					explosionTab[tmp] = new Position(nextPos);
					nextPos = d.nextPosition(nextPos);
				}
			}
		}
		//use explosionTab
		//retirer sprites explosion après les avoir afficher
		Monster[] mTab = game.getMonsters();
		for(Position p:explosionTab) {
			if(p!=null) {
				w.clear(p);
				w.set(p, new Explosion());
				if(p.equals(game.getPlayer().getPosition())) {
					game.getPlayer().loseLife();
				}
				for(Monster m:mTab) {
					if(p.equals(m.getPosition())) {
						m.die();
					}
				}
			}
		}
		game.getPlayer().addBombs();
		System.out.println("KABOOM");
		w.changed();
	}
	
	public void update(long now) {
		long delta = (now/SEC)-(this.creation_time/SEC);
		if(delta>=1 && delta<2) {
			this.state = 3;
		}
		if (delta>=2 && delta<3) {
			this.state = 2;
		}
		if (delta>=3 && delta<4) {
			this.state = 1;
		}
		if(delta>=4) {
			this.state = 0;
			this.explode();
		}
	}
	
}
