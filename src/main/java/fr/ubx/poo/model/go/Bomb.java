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
	private Position[] eTab;
	private boolean exploded = false;
	
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
		
		//fill explosionTab with the positions that can be exploded
		for(Direction d : dirTab) {
			Position nextPos = d.nextPosition(pos);
			for(int i=0; i<this.scope; i++) {
				Decor object = w.get(nextPos);
				if(object==null || object.isExplodable()) {
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
		
		Monster[] mTab = game.getMonsters();
		//use explosion tab to:
		for(Position p:explosionTab) {
			if(p!=null) {
				//create explosion's Decor
				w.clear(p);
				w.set(p, new Explosion());
				//inflict damage to the player
				if(p.equals(game.getPlayer().getPosition())) {
					game.getPlayer().loseLife();
				}
				//kill monster
				for(Monster m:mTab) {
					if(p.equals(m.getPosition())) {
						m.die();
					}
				}
			}
		}
		//switch world's state to display the new sprites c.f. world.update();
		w.changed();
		this.eTab=explosionTab;
		this.exploded = true;
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
		//explode if hasn't yet
		if(delta>=4 && delta<5) {
			this.state = 0;
			if(!this.exploded) {
				this.explode();				
			}
		}
		//clear explosion's sprites
		if(delta>=5 && delta<6) {
			this.state = -1;
			game.getPlayer().addBombs();
			World w = this.game.getWorld();
			for(Position p:this.eTab) {
				if(p!=null) {
					w.clear(p);
				}
			}
			w.changed();
		}
	}
	
}
