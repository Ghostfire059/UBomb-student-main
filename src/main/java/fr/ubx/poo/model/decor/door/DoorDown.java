package fr.ubx.poo.model.decor.door;

import fr.ubx.poo.game.PositionNotFoundException;
import fr.ubx.poo.model.go.character.Player;

public class DoorDown extends Door{
	
	@Override
	public boolean isCrossable() {
		return true;
	}
	
	@Override
	public boolean isExplodable() {
		return false;
	}
	
	@Override
	public void crossIt(Player player) {
		player.getGame().levelDown();
	}
	
}
