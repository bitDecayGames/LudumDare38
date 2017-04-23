package com.bitdecay.game.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;

public class Money extends Group {
    public Money(Vector2 screenSize) {
        Image money = new Image(MyGame.ATLAS.findRegion("uiStuff/btc-512"));

        addActor(money);
    }

    public void setMoney(float value) {

    }
}
