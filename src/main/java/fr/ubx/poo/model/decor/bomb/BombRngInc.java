package fr.ubx.poo.model.decor.bomb;

import fr.ubx.poo.game.World;
import fr.ubx.poo.model.go.character.Player;

public class BombRngInc extends BombRng{

	@Override
	public void crossIt(Player player) {
		World w = player.getGame().getWorld();
    	player.addScope();
    	w.clear(player.getPosition());
		w.changed();
	}
}
