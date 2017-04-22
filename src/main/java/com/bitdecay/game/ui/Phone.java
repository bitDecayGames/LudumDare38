package com.bitdecay.game.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;

public class Phone extends Group {
    public Phone(Vector2 screenSize) {
        super();

        Image phone = new Image(MyGame.ATLAS.findRegion("uiStuff/phone"));
        Image phoneGlare = new Image(MyGame.ATLAS.findRegion("uiStuff/phoneGlare"));

        addActor(phone);
        addActor(phoneGlare);

        float scale = screenSize.y / phone.getHeight() - 1;
        scaleBy(scale);
    }
}
