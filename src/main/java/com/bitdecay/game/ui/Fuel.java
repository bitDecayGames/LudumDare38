package com.bitdecay.game.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;
/**
 * Created by Luke on 4/22/2017.
 */
public class Fuel extends Group {
    public Fuel(Vector2 size){
        super();

        Image fuel = new Image(MyGame.ATLAS.findRegion("uiStuff/gasGauge"));
        Image needle = new Image(MyGame.ATLAS.findRegion("uiStuff/gasNeedle"));


//        float scale = size.y /12 / fuel.getHeight();
//        fuel.scaleBy(scale);
//        fuel.moveBy(size.x - fuel.getWidth(), 0);

        System.out.println(fuel.getImageX()+ " ," +fuel.getImageWidth()+", "+fuel.getWidth());

        needle.setOrigin(needle.getWidth()/2,needle.getWidth()/2);
        needle.moveBy((fuel.getWidth()/2 - needle.getWidth()/2), (fuel.getHeight()/2 - needle.getWidth()/2));
        needle.rotateBy(-120);
        needle.rotateBy(180);
        this.addActor(fuel);
        this.addActor(needle);
    }
}
