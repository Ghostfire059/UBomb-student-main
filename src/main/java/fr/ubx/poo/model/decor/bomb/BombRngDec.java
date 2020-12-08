package fr.ubx.poo.model.decor.bomb;

import fr.ubx.poo.game.World;
import fr.ubx.poo.model.go.character.Player;

public class BombRngDec extends BombRng{

	@Override
	public void crossIt(Player player) {
		if( player.getScope() > 1 ) {
			player.loseScope();
			World w = player.getGame().getWorld();
			w.clear(player.getPosition());
			w.changed();
    	}
	}
}
