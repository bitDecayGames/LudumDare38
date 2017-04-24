package com.bitdecay.game.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.Objective;

public class TaskCard extends Group {

    public TaskCard(Objective obj) {
        Image background = new Image(MyGame.ATLAS.findRegion("uiStuff/missions/selected"));
        addActor(background);
        Image icon = new Image(MyGame.ATLAS.findRegion(obj.icon));
        icon.setScale(1.68f);
        addActor(icon);
    }
}
