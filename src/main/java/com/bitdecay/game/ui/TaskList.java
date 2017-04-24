package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.Quest;
import com.bitdecay.game.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskList extends Group {
    private float padding = Gdx.graphics.getHeight() * 0.22f;

    private List<Tuple<Quest, ImageButton>> quests = new ArrayList<>();

    private Vector2 first = new Vector2(0, Gdx.graphics.getHeight() * -0.18f);
    private Vector2 second = new Vector2(first.x, first.y - padding);
    private Vector2 third = new Vector2(first.x, first.y - padding * 2);

    public void addQuest(Quest quest){
        ImageButton btn = listItem(quest);
        addActor(btn);
        if (quests.size() == 0) btn.setPosition(first.x, first.y);
        else if (quests.size() == 1) btn.setPosition(second.x, second.y);
        else if (quests.size() == 2) btn.setPosition(third.x, third.y);
        else btn.setPosition(Gdx.graphics.getWidth(), 0);
        quests.add(new Tuple<>(quest, btn));
    }

    public boolean removeQuest(Quest quest){
        for (int i = 0; i < quests.size(); i++){
            Tuple<Quest, ImageButton> tup = quests.get(i);
            if (quest == tup.x) {
                removeActor(tup.y);
                quests.remove(i);
                for (int k = 0; k < quests.size(); k++){
                    tup = quests.get(k);
                    if (k == 0) tup.y.setPosition(first.x, first.y);
                    else if (k == 1) tup.y.setPosition(second.x, second.y);
                    else if (k == 2) tup.y.setPosition(third.x, third.y);
                    else tup.y.setPosition(Gdx.graphics.getWidth(), 0);
                }
                return true;
            }
        }
        return false;
    }

    public Optional<Quest> currentQuest(){
        for (Tuple<Quest, ImageButton> quest : quests){
            if (quest.x.isActive) return Optional.of(quest.x);
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
            if (q.x.started) q.x.currentZone().ifPresent(z -> {
                z.update(delta);
                if (z.isOutOfTime()){
                    q.x.failed = true;
                }
            });
        });
    }

    public void removeFailedQuests(){
        quests.stream().collect(Collectors.toList()).forEach(q -> {
            if (q.x.failed) removeQuest(q.x);
        });
    }
}
