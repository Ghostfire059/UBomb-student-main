/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Sprite {
    private final ColorAdjust effect = new ColorAdjust();

    public static final int size = 40;
    private final Pane layer;
	private ImageView imageView;
    private Image image;
    public boolean invincible = false;

    public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public Sprite(Pane layer, Image image) {
        this.layer = layer;
        this.image = image;
    }

    public final void setImage(Image image) {
        if (this.image == null || this.image != image) {
            this.image = image;
        }
    }

    public ImageView getImageView() {
		return imageView;
	}

	public abstract void updateImage();

    public abstract Position getPosition();

    public final void render() {
        if (imageView != null) {
            remove();
        }
        updateImage();
        imageView = new ImageView(this.image);
        imageView.setX(getPosition().x * size);
        imageView.setY(getPosition().y * size);
        if ( this.invincible ) {
        	effect.setBrightness(0.6);
        	imageView.setEffect(effect);
        }
        layer.getChildren().add(imageView);
    }

    public final void remove() {
        layer.getChildren().remove(imageView);
        imageView = null;
    }
}
