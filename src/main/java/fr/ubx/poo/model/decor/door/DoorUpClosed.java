package fr.ubx.poo.model.decor.door;

import fr.ubx.poo.game.World;
import fr.ubx.poo.model.go.character.Player;

public class DoorUpClosed extends DoorUp{
	
	@Override
	public void crossIt(Player player) {
		World w = player.getGame().getWorld();
		player.loseKeys();
    	w.clear(player.getDirection().nextPosition(player.getPosition()));
		w.changed();
	}
	
}
