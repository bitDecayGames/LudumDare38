package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;

public class TaskList extends Table {
    private float padding = Gdx.graphics.getHeight() * 0.03f;

    public TaskList() {
        add(makeButton()).pad(0, 0, padding, 0).row();
        add(makeButton()).pad(0, 0, padding, 0).row();
        add(makeButton()).row();
    }

    private ImageButton makeButton(){
        Drawable onImage = new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/pokey"));
        Drawable offImage = new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/missions/selected"));
        ImageButton btn = new ImageButton(offImage, onImage, onImage);
        btn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return true;
            }
        });
        return btn;
    }
}
