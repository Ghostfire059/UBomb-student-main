/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.view.sprite;

import static fr.ubx.poo.view.image.ImageResource.*;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.decor.bomb.*;
import fr.ubx.poo.model.decor.door.*;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.layout.Pane;


public final class SpriteFactory {

    public static Sprite createDecor(Pane layer, Position position, Decor decor) {
        ImageFactory factory = ImageFactory.getInstance();
        if (decor instanceof Stone)
            return new SpriteDecor(layer, factory.get(STONE), position);
        if (decor instanceof Tree)
            return new SpriteDecor(layer, factory.get(TREE), position);
        if(decor instanceof Box)
        	return new SpriteDecor(layer, factory.get(BOX), position);
        if(decor instanceof Princess)
        	return new SpriteDecor(layer, factory.get(PRINCESS), position);
        if(decor instanceof Heart)
        	return new SpriteDecor(layer, factory.get(HEART), position);
        if(decor instanceof Key)
        	return new SpriteDecor(layer, factory.get(KEY), position);
        if(decor instanceof DoorDown)
        	return new SpriteDecor(layer, factory.get(DOOROPENED), position);
        if(decor instanceof DoorUpClosed)
        	return new SpriteDecor(layer, factory.get(DOORCLOSED), position);
        if(decor instanceof DoorUpOpened)
        	return new SpriteDecor(layer, factory.get(DOOROPENED), position);
        if(decor instanceof BombNbInc)
        	return new SpriteDecor(layer, factory.get(BOMBNBINC), position);
        if(decor instanceof BombNbDec)
        	return new SpriteDecor(layer, factory.get(BOMBNBDEC), position);
        if(decor instanceof BombRngInc)
        	return new SpriteDecor(layer, factory.get(BOMBRNGINC), position);
        if(decor instanceof BombRngDec)
        	return new SpriteDecor(layer, factory.get(BOMBRNGDEC), position);
        return null;
    }

    public static Sprite createPlayer(Pane layer, Player player) {
        return new SpritePlayer(layer, player);
    }
}
