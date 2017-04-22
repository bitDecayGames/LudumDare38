package com.bitdecay.game.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;

public class Body extends Group {
    public Body(Vector2 size) {
        super();

        Image body = new Image(MyGame.ATLAS.findRegion("uiStuff/foodBio"));

        float scale = size.y / body.getHeight() - 1.5f;
        body.scaleBy(scale);
        body.moveBy(size.x - body.getWidth(), 0);

        this.addActor(body);
    }
}
