package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.decor.Explosion;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.game.Direction;

public class Bomb extends GameObject{
	private static final long SEC = 1000000000;
	private long creation_time;
	private int scope;
	private int state = 4;
	private Position[] eTab;
	private boolean exploded = false;
	private int levelIndice;
	
	public Bomb(Game game, Position position, long now) {
		super(game, position);
		this.creation_time = now;
		this.scope = game.getPlayer().getScope();
		this.levelIndice = this.game.getIndice();
	}
	
	public long getCreationTime() {
		return this.getCreationTime();
	}
	
	public int getState() {
		return this.state;
	}
	
	public void explode(long now) {
		World w = this.game.getTabWorld()[this.levelIndice];
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
		
		Monster[] mTab = game.getMonsters()[levelIndice];
		Bomb[] bTab = game.getPlayer().getTabBombs();
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
					if(m!=null) {
						if(p.equals(m.getPosition())) {
							m.die();
						}						
					}
				}
				for(Bomb b:bTab) {
					if(b!=null) {
						if(p.equals(b.getPosition())) {
							b.creation_time = now-4*SEC;
						}
					}
				}
			}
		}
		//switch world's state to display the new sprites c.f. world.update();
		if(!w.hasChanged()) {			
			w.changed();
		}
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
				this.explode(now);				
				game.getPlayer().addBombs();
			}
		}
		//clear explosion's sprites
		if(delta>=5) {
			this.state = -1;
			World w = this.game.getTabWorld()[this.levelIndice];
			for(Position p:this.eTab) {
				if(p!=null) {
					w.clear(p);
				}
			}
			if(!w.hasChanged()) {			
				w.changed();
			}
		}
	}
	
	public int getLevelIndice() {
		return this.levelIndice;
	}
	
	public Position[] getExplosionTab() {
		return this.eTab;
	}
	
}
