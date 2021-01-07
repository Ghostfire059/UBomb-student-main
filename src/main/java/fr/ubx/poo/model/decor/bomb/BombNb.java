package fr.ubx.poo.model.decor.bomb;

import fr.ubx.poo.model.decor.Decor;

public class BombNb extends Decor{
	
	@Override
	public boolean isCrossable() {
		return true;
	}
	
	@Override
	public boolean isTaken() {
		return true;
	}
	
}
