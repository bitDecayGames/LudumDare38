package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.bitdecay.game.util.Quest;
import org.apache.log4j.Logger;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class Tray extends Group {
    private Logger log = Logger.getLogger(Tray.class);

    public TaskCard taskCard;
    public ConversationDialog dialog;
    private boolean isHidden = false;

    public Tray() {
        super();

        Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        taskCard = new TaskCard(new Quest("Pokey Dickson", "uiStuff/missions/pokey", 0, null, null));
        taskCard.setPosition(0, 0);
        taskCard.setScale(0.7f);
        addActor(taskCard);

        dialog = new ConversationDialog();
        dialog.setPosition(screenSize.x * 0.2125f, 0);
        addActor(dialog);
    }

    public void showTray(){
        MoveToAction move = moveTo(getX(), Gdx.graphics.getHeight() * 1.01f);
        move.setDuration(0.25f);
        addAction(move);
        isHidden = false;
    }

    public void hideTray(){
        MoveToAction move = moveTo(getX(), 0);
        move.setDuration(0.25f);
        addAction(move);
        isHidden = true;
    }

    public void toggle(){
        if (isHidden) showTray();
        else hideTray();
    }
}
