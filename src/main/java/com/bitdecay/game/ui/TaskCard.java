package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.trait.IUpdate;
import com.bitdecay.game.util.Quest;

public class TaskCard extends Group implements IUpdate {

    private Label name;
    private Label location;
    private Money reward;
    public Quest quest;

    public TaskCard(Quest quest) {
        this.quest = quest;
        Image background = new Image(MyGame.ATLAS.findRegion("uiStuff/missions/selected"));
        addActor(background);
        Image icon = new Image(MyGame.ATLAS.findRegion(quest.icon));
        icon.setScale(1.68f);
        addActor(icon);
        name = new Label(quest.personName, new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        name.setAlignment(Align.topLeft);
        name.setFontScale(2.5f);
        name.setPosition(Gdx.graphics.getWidth() * 0.1f, Gdx.graphics.getHeight() * 0.155f);
        addActor(name);
        location = new Label(quest.currentZone().map(z -> z.name).orElse(""), new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        location.setAlignment(Align.topLeft);
        location.setFontScale(2f);
        location.setPosition(Gdx.graphics.getWidth() * 0.1f, Gdx.graphics.getHeight() * 0.105f);
        addActor(location);
        reward = new Money(3f);
        reward.align(Align.right);
        reward.setPosition(Gdx.graphics.getWidth() * 0.28f, Gdx.graphics.getHeight() * 0.04f);
        reward.setMoney(quest.reward);
        addActor(reward);
    }

    @Override
    public void update(float delta) {
        location.setText(quest.currentZone().map(z -> z.name).orElse(""));
    }
}
