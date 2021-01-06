package fr.ubx.poo.model.decor.door;

import fr.ubx.poo.model.decor.Decor;

public class Door extends Decor{

	@Override
	public boolean isMonsterAccessible() {
		return false;
	}
}
