package fr.ubx.poo.view.image;

public enum ImageResource {
    BANNER_BOMB ("banner_bomb.png"),
    BANNER_RANGE ("banner_range.png"),
    HEART("heart.png"),
    KEY("key.png"),
    DIGIT_0 ("banner_0.jpg"),
    DIGIT_1 ("banner_1.jpg"),
    DIGIT_2 ("banner_2.jpg"),
    DIGIT_3 ("banner_3.jpg"),
    DIGIT_4 ("banner_4.jpg"),
    DIGIT_5 ("banner_5.jpg"),
    DIGIT_6 ("banner_6.jpg"),
    DIGIT_7 ("banner_7.jpg"),
    DIGIT_8 ("banner_8.jpg"),
    DIGIT_9 ("banner_9.jpg"),
    PLAYER_DOWN("player_down.png"),
    PLAYER_LEFT("player_left.png"),
    PLAYER_RIGHT("player_right.png"),
    PLAYER_UP("player_up.png"),
    STONE("stone.png"),
    TREE("tree.png"),
    BOX("box.png"),
	PRINCESS("bomberwoman.png"),
	DOORCLOSED("door_closed.png"),
	DOOROPENED("door_opened.png"),
	BOMBNBINC("bonus_bomb_nb_inc.png"),
	BOMBNBDEC("bonus_bomb_nb_dec.png"),
	BOMBRNGINC("bonus_bomb_range_inc.png"),
	BOMBRNGDEC("bonus_bomb_range_dec.png"),
	MONSTER_DOWN("monster_down.png"),
	MONSTER_LEFT("monster_left.png"),
	MONSTER_RIGHT("monster_right.png"),
	MONSTER_UP("monster_up.png"),
	BOMBSTATE1("bomb1.png"),
	BOMBSTATE2("bomb2.png"),
	BOMBSTATE3("bomb3.png"),
	BOMBSTATE4("bomb4.png"),
	EXPLOSION("explosion.png"),
    ;

    private final String FileName;

    ImageResource(String fileName) {
        this.FileName = fileName;
    }

    public String getFileName() {
        return FileName;
    }
}
