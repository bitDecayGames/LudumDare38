package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.Quest;

import java.util.HashMap;
import java.util.Map;

public class TaskList extends Table {
    private float padding = Gdx.graphics.getHeight() * 0.03f;

    private Map<Quest, Cell> quests = new HashMap<>();

    public void addQuest(Quest quest){
        Cell c = add(makeButton(quest));
        quests.put(quest, c);
        c.pad(0, 0, padding, 0).row();
    }

    public boolean removeQuest(Quest quest){
        Cell cell = quests.get(quest);
        quests.remove(quest);
        cell.getActor().remove();
        return getCells().removeValue(cell, true);
    }

    private ImageButton makeButton(Quest quest){
        Drawable onImage = new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/selected"));
        Drawable offImage = new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/selected"));
        ImageButton btn = new ImageButton(offImage, onImage, onImage);
        btn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return true;
            }
        });
        btn.addActor(new TaskCard(quest));
        return btn;
    }
}
