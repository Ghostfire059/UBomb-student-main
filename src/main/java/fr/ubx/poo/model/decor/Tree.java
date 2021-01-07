/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

public class Tree extends Decor {
	
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
	public boolean isMonsterAccessible() {
		return false;
	}
	
    @Override
    public String toString() {
        return "Tree";
    }
}
