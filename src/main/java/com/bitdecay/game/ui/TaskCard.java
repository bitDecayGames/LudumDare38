package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.trait.IUpdate;
import com.bitdecay.game.util.Quest;

public class TaskCard extends ImageButton implements IUpdate {

    private static final Quest defaultQuest = new Quest("", "uiStuff/missions/pokey", 0, null, null, null);

    private Image icon;
    private Label name;
    private Label location;
    private Money reward;
    public Quest quest;

    public TaskCard(){
        super(new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/selected")));
        init(defaultQuest, this);
    }

    public TaskCard(Quest quest) {
        super(
            new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/unselected")),
            new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/selected")),
            new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/selected")));
        init(quest, this);
    }

    private void init(Quest quest, TaskCard thisCard){
        this.quest = quest;
        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(HUD.instance().selectedTask != null && HUD.instance().selectedTask != thisCard) {
                    HUD.instance().selectedTask.toggle();
                    HUD.instance().selectedTask.quest.isActive = false;
                    HUD.instance().selectedTask = thisCard;
                } else if (HUD.instance().selectedTask != null && HUD.instance().selectedTask == thisCard){
                    HUD.instance().selectedTask = null;
                } else {
                    HUD.instance().selectedTask = thisCard;
                }
                quest.isActive = !quest.isActive;
                event.stop();
                return true;
            }
        });
        icon = new Image(quest.getIcon());
        icon.setScale(1.68f);
        addActor(icon);
        name = new Label(quest.personName, new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        name.setAlignment(Align.topLeft);
        name.setFontScale(2.5f);
        name.setPosition(Gdx.graphics.getWidth() * 0.11f, Gdx.graphics.getHeight() * 0.16f);
        addActor(name);
        location = new Label(quest.currentZone().map(z -> z.name).orElse(""), new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        location.setAlignment(Align.topLeft);
        location.setFontScale(2f);
        location.setPosition(Gdx.graphics.getWidth() * 0.11f, Gdx.graphics.getHeight() * 0.11f);
        addActor(location);
        reward = new Money(3f);
        reward.align(Align.right);
        reward.setPosition(Gdx.graphics.getWidth() * 0.28f, Gdx.graphics.getHeight() * 0.04f);
        reward.setMoney(quest.reward);
        addActor(reward);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        update(delta);
    }

    @Override
    public void update(float delta) {
        if (quest != null) {
            icon.setDrawable(new TextureRegionDrawable(quest.getIcon()));
            name.setText(quest.personName);
            location.setText(quest.currentZone().map(z -> z.name).orElse(""));
            reward.setMoney(quest.reward);
        }
    }
}
