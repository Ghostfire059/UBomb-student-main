/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

public class Stone extends Decor {
	
	@Override
	public boolean isCrossable() {
		return false;
	}

	@Override
	public boolean isTaken() {
		return false;
	}
	
	@Override
	public boolean isExplodable() {
		return false;
	}
	
    @Override
    public String toString() {
        return "Stone";
    }
}
