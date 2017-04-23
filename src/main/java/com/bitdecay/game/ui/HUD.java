package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

public class HUD extends Group {
    private static HUD I;
    public static HUD instance(){
        return I;
    }

    public Phone phone;
    public Body body;
    public Fuel fuel;

    public HUD() {
        super();
        I = this;
        Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        phone = new Phone(screenSize);
        phone.setPosition(screenSize.x * .675f, 0);
        phone.moveDown();
        addActor(phone);

        body = new Body(screenSize);
        body.setPosition(0, screenSize.y * 1.05f);
        body.setScale(0.3f);
        addActor(body);

        fuel = new Fuel();
        fuel.setPosition(screenSize.x * 0.1f, screenSize.y);
        addActor(fuel);

        setBounds(0, -screenSize.y, screenSize.x, screenSize.y);
    }
}
