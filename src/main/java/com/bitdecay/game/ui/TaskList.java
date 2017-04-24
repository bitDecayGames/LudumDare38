package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.bitdecay.game.util.InputHelper;
import com.bitdecay.game.util.Quest;
import com.bitdecay.game.util.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskList extends Group {
    private Logger log = LogManager.getLogger(this.getClass());

    private float padding = Gdx.graphics.getHeight() * 0.22f;

    private List<Tuple<Quest, ImageButton>> quests = new ArrayList<>();

    private Vector2 first = new Vector2(0, Gdx.graphics.getHeight() * -0.18f);
    private Vector2 second = new Vector2(first.x, first.y - padding);
    private Vector2 third = new Vector2(first.x, first.y - padding * 2);

    private int currentSelection = 0;

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
//        ImageButton btn = new ImageButton(new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/unselected")));
        TaskCard task = new TaskCard(quest);
//        btn.addActor(task);
//        return btn;
        return task;
    }

    private void moveSelectionUp(){
        if (quests.size() == 1 || currentSelection == -1) selectWithKey(0);
        else if (quests.size() > 1 && currentSelection > 0) selectWithKey(currentSelection - 1);
    }

    private void moveSelectionDown(){
        if (quests.size() == 1 || currentSelection == -1) selectWithKey(0);
        else if (quests.size() > 1 && currentSelection < quests.size() - 1) selectWithKey(currentSelection + 1);
    }

    private void selectWithKey(int index){
        log.info("Select with key {}", index);
        if (quests.size() > 0) {
            InputEvent e = new InputEvent();
            e.setType(InputEvent.Type.touchDown);
            quests.get(index).y.fire(e);
            InputEvent f = new InputEvent();
            f.setType(InputEvent.Type.touchUp);
            quests.get(index).y.fire(f);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateStartedQuests(delta);

        if (InputHelper.isKeyJustPressed(Input.Keys.E)) moveSelectionDown();
        else if (InputHelper.isKeyJustPressed(Input.Keys.Q)) moveSelectionUp();

        boolean found = false;
        for (int i = 0; i < quests.size(); i++){
            if (quests.get(i).x.isActive) {
                currentSelection = i;
                found = true;
                break;
            }
        }
        if (!found) currentSelection = -1;
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
