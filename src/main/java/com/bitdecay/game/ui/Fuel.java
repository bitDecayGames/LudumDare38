package com.bitdecay.game.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;

/**
 * Created by Luke on 4/22/2017.
 */
public class Fuel extends Group {

    private final float emptyRotation = 60;
    private final float rotationRange = 180;
    private final Image needle;
    private final Image fuel;
    private final TextureRegionDrawable normalGauge;
    private final TextureRegionDrawable lowGasGauge;

    public Fuel(){
        super();

        normalGauge = new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/gasGauge"));
        lowGasGauge = new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/lowGasGauge"));
        fuel = new Image(normalGauge);
        needle = new Image(MyGame.ATLAS.findRegion("uiStuff/gasNeedle"));

//        float scale = size.y /12 / fuel.getHeight();
//        fuel.scaleBy(scale);
//        fuel.moveBy(size.x - fuel.getWidth(), 0);

        needle.setOrigin(needle.getWidth()/2, needle.getWidth()/2);
        needle.moveBy((fuel.getWidth()/2 - needle.getWidth()/2), (fuel.getHeight()/2 - needle.getWidth()/2));
        needle.setRotation(emptyRotation);
        this.addActor(fuel);
        this.addActor(needle);
    }

    public void setPercent(float percent) {
        needle.setRotation(60 - rotationRange*percent);

        if (percent <= .15f) {
            fuel.setDrawable(lowGasGauge);
        } else {
            fuel.setDrawable(normalGauge);
        }
    }
}
