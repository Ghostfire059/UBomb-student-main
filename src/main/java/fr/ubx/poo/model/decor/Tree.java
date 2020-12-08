/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

import fr.ubx.poo.model.go.character.Player;

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
    public String toString() {
        return "Tree";
    }
}
