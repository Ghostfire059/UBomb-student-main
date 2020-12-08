package fr.ubx.poo.model.decor;

import fr.ubx.poo.model.go.character.Player;

public class Princess extends Decor{
	
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
		player.winner();
	}
}
