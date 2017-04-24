package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.MyGame;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class Phone extends Group {

    protected Logger log = LogManager.getLogger(this.getClass());

    public TaskList tasks;
    public WaypointList waypoints;
    public Money money;

    private boolean isDown = true;

    public Phone(Vector2 screenSize) {
        super();

        Image phone = new Image(MyGame.ATLAS.findRegion("uiStuff/phone"));
        addActor(phone);

        tasks = new TaskList();
        tasks.align(Align.top);
        tasks.setPosition(Gdx.graphics.getWidth() * 0.17f, Gdx.graphics.getHeight() * 0.90f);
        addActor(tasks);

        money = new Money(3);
        money.setPosition(Gdx.graphics.getWidth() * 0.17f, Gdx.graphics.getHeight() * 0.95f);
        addActor(money);

        waypoints = new WaypointList();
        waypoints.setPosition(Gdx.graphics.getWidth() * 0.17f, Gdx.graphics.getHeight() * 0.22f);
        addActor(waypoints);

        Image phoneGlare = new Image(MyGame.ATLAS.findRegion("uiStuff/phoneGlare"));
        phoneGlare.setTouchable(Touchable.childrenOnly);
        addActor(phoneGlare);

        float scale = screenSize.y / phone.getHeight() - 1;
        scaleBy(scale);

        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return true;
            }
        });
    }

    public void moveUp(){
        MoveToAction move = moveTo(getX(), Gdx.graphics.getHeight());
        move.setDuration(0.25f);
        addAction(move);
        isDown = false;
    }

    public void moveDown(){
        MoveToAction move = moveTo(getX(), Gdx.graphics.getHeight() * 0.125f);
        move.setDuration(0.25f);
        addAction(move);
        isDown = true;
    }

    public void toggle() {
        if (isDown) moveUp();
        else moveDown();
    }
}
