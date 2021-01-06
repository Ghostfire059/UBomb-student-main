package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.World;
import fr.ubx.poo.model.go.character.Player;

public class Heart extends Decor{
	
	@Override
	public boolean isCrossable() {
		return true;
	}
	
	@Override
	public boolean isTaken() {
		return true;
	}
	
	@Override
	public void crossIt(Player player) {
		World w = player.getGame().getWorld();
		player.addLife();
    	w.clear(player.getPosition());
		w.changed();
	}
}
