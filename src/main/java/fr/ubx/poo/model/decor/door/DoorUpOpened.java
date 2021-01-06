package fr.ubx.poo.model.decor.door;

import fr.ubx.poo.model.go.character.Player;

public class DoorUpOpened extends DoorUp{
	
	@Override
	public boolean isCrossable() {
		return true;
	}
	
	@Override
	public boolean isExplodable() {
		return false;
	}

	@Override
	public void crossIt(Player player) {}
}
