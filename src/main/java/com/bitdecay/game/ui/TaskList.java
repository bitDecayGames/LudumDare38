package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.Quest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskList extends Table {
    private float padding = Gdx.graphics.getHeight() * 0.03f;

    private List<Quest> quests = new ArrayList<>();

    public void addQuest(Quest quest){
        Cell c = add(listItem(quest));
        c.pad(0, 0, padding, 0).row();
        quests.add(quest);
    }

    public boolean removeQuest(Quest quest){
        int row = quests.indexOf(quest);
        quests.remove(quest);
        return removeActor(getChildren().get(row));
    }

    public Optional<Quest> currentQuest(){
        for (Quest quest : quests){
            if (quest.isActive) return Optional.of(quest);
        }
        return Optional.empty();
    }

    private ImageButton listItem(Quest quest){
        ImageButton btn = new ImageButton(new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/unselected")));
        TaskCard task = new TaskCard(quest);
        btn.addActor(task);
        return btn;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateStartedQuests(delta);
    }

    public void updateStartedQuests(float delta){
        quests.forEach(q -> {
            if (q.started) q.currentZone().ifPresent(z -> {
                z.update(delta);
                if (z.isOutOfTime()){
                    q.failed = true;
                }
            });
        });
    }

    public void removeFailedQuests(){
        quests.stream().collect(Collectors.toList()).forEach(q -> {
            if (q.failed) removeQuest(q);
        });
    }
}
