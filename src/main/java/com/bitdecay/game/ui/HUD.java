package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;

public class HUD extends Group {
    private static HUD I;
    public static HUD instance(){
        return I;
    }

    public Phone phone;
    public Body body;
    public Fuel fuel;
    public Tray tray;

    public TaskCard selectedTask = null;

    public HUD() {
        super();
        I = this;
        Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Image bezel = new Image(MyGame.ATLAS.findRegion("uiStuff/bezel"));
        bezel.setPosition(0, screenSize.y);
        bezel.setScale(0.5f);
        addActor(bezel);

        phone = new Phone(screenSize);
        phone.setPosition(screenSize.x * .675f, 0);
        phone.moveDown();
        addActor(phone);

        body = new Body(screenSize);
        body.setPosition(screenSize.x * 0.01f, screenSize.y * 1.02f);
        body.setScale(0.20f);
        addActor(body);

        fuel = new Fuel();
        fuel.setPosition(screenSize.x * 0.09f, screenSize.y);
        fuel.setScale(0.60f);
        addActor(fuel);

        tray = new Tray(phone.tasks);
        tray.setPosition(screenSize.x * 0.2f, screenSize.y * 1.01f);
        tray.showTray();
        addActor(tray);

        setBounds(0, -screenSize.y, screenSize.x, screenSize.y);
    }
}
