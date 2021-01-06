package fr.ubx.poo.model.go.character;

import java.util.Random;

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
		
		return nextPos.inside(game.getWorld().dimension) && (object == null || object.isMonsterAccessible());
	}

	@Override
	public void doMove(Direction direction) {
		Position nextPos = direction.nextPosition(getPosition());
		setPosition(nextPos);
		if(this.getPosition().equals(game.getPlayer().getPosition())) {
			this.hit(game.getPlayer());
		}
	}
	
	private int[][] tradGrid(){
		int n = game.getWorld().dimension.height;
		int m = game.getWorld().dimension.width;
		int[][] tab = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				Decor decor = game.getWorld().get(new Position(i,j));
				if( decor == null || decor.isCrossable() ) {
					tab[i][j] = 0;
				}else {
					tab[i][j] = 1;
				}
			}
		}
		return tab;
	}
	
	private Direction chooseDirection() {
		int level = game.getWorld().level;
		if (level < 3) { //aléatoire
			return Direction.random();
		}
		if (level < 5) { //dirigé vers player
			Position posP = game.getPlayer().getPosition();
			Position posM = this.getPosition();
			if (posM.x == posP.x) {
				if (posM.y > posP.y) {
					return Direction.N;
				}
				return Direction.S;
			}
			if (posM.y == posP.y) {
				if (posM.x > posP.x) {
					return Direction.W;
				}
				return Direction.E;
			}
			int rand = new Random().nextInt(2);
			if (posP.y < posM.y && posP.x < posM.x) {
				Direction[] array = {Direction.N, Direction.W};
				return array[rand];
			}
			if (posP.y < posM.y && posP.x > posM.x) {
				Direction[] array = {Direction.S, Direction.W};
				return array[rand];
			}
			if (posP.y > posM.y && posP.x < posM.x) {
				Direction[] array = {Direction.N, Direction.E};
				return array[rand];
			}
			if (posP.y > posM.y && posP.x > posM.x) {
				Direction[] array = {Direction.S, Direction.E};
				return array[rand];
			}
		}
		//le plus court chemin vers le perso
		//int[][] tab = tradGrid();
		//Position posP = game.getPlayer().getPosition();
		//Position posM = this.getPosition();
		//Trouver un chemin de 0 allant de posM à posP
		return Direction.N;
	}
	
	public void update(long now) {
		long interv = 1500000000;
		if(game.getWorld().level == 1) interv = 1450000000;
		if(game.getWorld().level == 2) interv = 1400000000;
		if(game.getWorld().level == 4) interv = 1350000000;
		if(game.getWorld().level == 6) interv = 1300000000;
		if(game.getWorld().level == 8) interv = 1200000000;
		if(now-this.prev_move > interv) {
			requestMove( chooseDirection() );
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
