package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.bitdecay.game.util.Quest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Optional;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class Tray extends Group {
    protected Logger log = LogManager.getLogger(this.getClass());

    public TrayCard taskCard;
    public ConversationDialog dialog;
    private TaskList taskList;
    private boolean isHidden = false;

    public Tray(TaskList taskList) {
        super();

        this.taskList = taskList;
        Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Group taskCardGroup = new Group();
        taskCard = new TrayCard();
        taskCard.setPosition(0, 0);
        taskCardGroup.addActor(taskCard);
        taskCardGroup.setScale(0.7f);
        addActor(taskCardGroup);

        dialog = new ConversationDialog();
        dialog.setPosition(screenSize.x * 0.2125f, 0);
        addActor(dialog);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Optional<Quest> curQuest = taskList.currentQuest();
        if (curQuest.isPresent()){
            Quest q = curQuest.get();
            if (q != taskCard.quest) {
                taskCard.quest = q;
                showTray();
            }
        } else if (!isHidden) hideTray();
    }

    public void showTray(){
        log.info("Show tray");
        MoveToAction move = moveTo(getX(), Gdx.graphics.getHeight() * 1.01f);
        move.setDuration(0.25f);
        addAction(Actions.sequence(Actions.run(()-> {
            if(taskCard.quest != null && taskCard.quest.currentZone().isPresent()) {
                taskCard.quest.currentZone().ifPresent(zone -> {
                    dialog.setPersonName(taskCard.quest.personName);
                    dialog.setText(zone.flavorText);
                });
            } else {
                dialog.setPersonName(null);
                dialog.setText("");
            }
        }), move));
        isHidden = false;
    }

    public void hideTray(){
        log.info("Hide tray");
        MoveToAction move = moveTo(getX(), 0);
        move.setDuration(0.25f);
        addAction(Actions.sequence(move, Actions.run(()-> taskCard.quest = null)));
        isHidden = true;
    }

    public void toggle(){
        if (isHidden) showTray();
        else hideTray();
    }
}
