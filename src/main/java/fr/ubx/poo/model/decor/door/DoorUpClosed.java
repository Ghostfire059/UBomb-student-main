package fr.ubx.poo.model.decor.door;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.go.character.Player;

public class DoorUpClosed extends DoorUp{

	@Override
	public boolean isExplodable() {
		return false;
	}

	@Override
	public void crossIt(Player player) {
		World w = player.getGame().getWorld();
		player.loseKeys();
		Position pos = player.getDirection().nextPosition(player.getPosition());
    	w.clear(pos);
    	w.set(pos, new DoorUpOpened() );
		w.changed();
	}
	
}
