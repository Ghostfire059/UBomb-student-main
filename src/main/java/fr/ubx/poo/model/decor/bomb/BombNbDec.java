package fr.ubx.poo.model.decor.bomb;

import fr.ubx.poo.game.World;
import fr.ubx.poo.model.go.character.Player;

public class BombNbDec extends BombNb{
	
	@Override
	public void crossIt(Player player) {
		World w = player.getGame().getWorld();
    	player.loseBombs();
    	w.clear(player.getPosition());
		w.changed();
		}
}
