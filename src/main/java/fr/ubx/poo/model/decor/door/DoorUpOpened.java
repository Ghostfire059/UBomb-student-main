package fr.ubx.poo.model.decor.door;

import fr.ubx.poo.game.PositionNotFoundException;
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
	public void crossIt(Player player) {
		player.getGame().levelUp();
		try {
			player.setPosition(player.getGame().getWorld().findDoorDown());
		}  catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
	}
}
