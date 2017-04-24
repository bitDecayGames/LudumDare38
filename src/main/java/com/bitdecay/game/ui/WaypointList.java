package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.ZoneType;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;

public class WaypointList extends Table {

    protected Logger log = LogManager.getLogger(this.getClass());

    private float padding = Gdx.graphics.getWidth() * 0.01f;

    private Map<ZoneType, ImageButton> btnData = new HashMap<>();

    public WaypointList() {
        add(makeButton("fix", ZoneType.REPAIR)).pad(0, 0, 0, padding);
        add(makeButton("gas", ZoneType.FUEL)).pad(0, 0, 0, padding);
        add(makeButton("grub", ZoneType.FOOD)).pad(0, 0, 0, padding);
        add(makeButton("pooper", ZoneType.BATHROOM)).row();
    }

    public boolean isWaypointOn(ZoneType zoneType){
        if (btnData.containsKey(zoneType)) return btnData.get(zoneType).isChecked();
        else return zoneType == ZoneType.OBJECTIVE;
    }

    private ImageButton makeButton(String name, ZoneType zoneType){
        Drawable onImage = new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/" + name + "On"));
        Drawable offImage = new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/" + name + "Off"));
        ImageButton btn = new ImageButton(offImage, onImage, onImage);
        btn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return true;
            }
        });
        btn.toggle();
        btnData.put(zoneType, btn);
        return btn;
    }
}
