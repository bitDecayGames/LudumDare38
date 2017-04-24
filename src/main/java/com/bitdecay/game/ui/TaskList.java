package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.Quest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaskList extends Table {
    private float padding = Gdx.graphics.getHeight() * 0.03f;

    private Map<Quest, Cell> quests = new HashMap<>();

    public void addQuest(Quest quest){
        Cell c = add(listItem(quest));
        quests.put(quest, c);
        c.pad(0, 0, padding, 0).row();
    }

    public boolean removeQuest(Quest quest){
        Cell cell = quests.get(quest);
        quests.remove(quest);
        cell.getActor().remove();
        return getCells().removeValue(cell, true);
    }

    public Optional<Quest> currentQuest(){
        for (Quest quest : quests.keySet()){
            if (quest.isActive) return Optional.of(quest);
        }
        return Optional.empty();
    }

    private ImageButton listItem(Quest quest){
        ImageButton btn = new ImageButton(new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/unselected")));
        btn.addActor(new TaskCard(quest));
        return btn;
    }
}
