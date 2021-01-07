package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.go.character.Player;

public class Box extends Decor{
	
	@Override
	public boolean isTaken() {
		return true;
	}
	
	@Override
	public void crossIt(Player player) {
		World w = player.getGame().getWorld();
		Position furtherPos = player.getDirection().nextPosition(player.getPosition());
		w.clear(player.getPosition());
		w.set(furtherPos, new Box());
		w.changed();
	}
	
	@Override
	public boolean isMonsterAccessible() {
		return false;
	}
	
	public String toString() {
		return "Box";
	}
}
