package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.MyGame;

public class ConversationDialog extends Group {

    private Label title;
    private Label text;

    public ConversationDialog() {
        Image img = new Image(MyGame.ATLAS.findRegion("uiStuff/missions/selected"));
        img.setScaleX(0.7f);
        img.setScaleY(0.6f);
        addActor(img);
        title = new Label("Jason Voorhees Says:", new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        title.setFontScale(1.9f);
        title.setPosition(Gdx.graphics.getWidth() * 0.005f, Gdx.graphics.getHeight() * 0.107f);
        addActor(title);
        text = new Label("this is a test, \nonly a test", new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        text.setFontScale(1.5f);
        text.setPosition(Gdx.graphics.getWidth() * 0.005f, Gdx.graphics.getHeight() * 0.075f);
        text.setAlignment(Align.topLeft);
        addActor(text);

    }

    public void setPersonName(String str) {
        if (str != null) title.setText(str + " Says:");
        else title.setText("");
    }

    public void setText(String str) {
        if (str != null) text.setText(str);
        else text.setText("");
    }
}
