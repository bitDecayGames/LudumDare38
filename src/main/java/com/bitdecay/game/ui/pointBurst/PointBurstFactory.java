package com.bitdecay.game.ui.pointBurst;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;

public class PointBurstFactory {
    public static PointBurstEffect money(float diff) {
        Image moneyIcon = new Image(MyGame.ATLAS.findRegion("uiStuff/btc-512"));
        moneyIcon.setScale(0.075f);

        Image diffIcon;
        if (diff > 0) {
            diffIcon = new Image(MyGame.ATLAS.findRegion("uiStuff/plus"));
        } else {
            diffIcon = new Image(MyGame.ATLAS.findRegion("uiStuff/minus"));
        }
        diffIcon.setScale(0.5f);

        return new PointBurstEffect(moneyIcon, diffIcon, Float.toString(Math.abs(diff)));
    }
}
