package com.bitdecay.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.ZoneType;

import java.util.Arrays;

public class Phone extends Group {
    Image phone;

    final WaypointButtonType[] waypointButtons = new WaypointButtonType[] {
        new WaypointButtonType("fix", ZoneType.REPAIR),
        new WaypointButtonType("gas", ZoneType.FUEL),
        new WaypointButtonType("grub", ZoneType.FOOD),
        new WaypointButtonType("pooper", ZoneType.BATHROOM),
    };

    public Phone(Vector2 screenSize) {
        super();

        phone = new Image(getUIRegion("phone"));
        Image phoneGlare = new Image(getUIRegion("phoneGlare"));

        addActor(phone);

        makeWaypointButtons();

        phoneGlare.setTouchable(Touchable.childrenOnly);
        addActor(phoneGlare);

        float scale = screenSize.y / phone.getHeight() - 1;
        scaleBy(scale);
    }

    public boolean getWaypointEnabled(ZoneType type) {
        WaypointButtonType buttonType = Arrays.stream(waypointButtons)
            .filter(bt -> bt.type == type)
            .findFirst()
            .get();

        if (buttonType != null) {
            if (buttonType.button != null) {
                return buttonType.button.isChecked();
            }
        }

        return true;
    }

    private void makeWaypointButtons() {
        float y = phone.getHeight() * 0.15f;
        float x = phone.getWidth() * 0.20f;
        float initX = phone.getWidth() * 0.11f;

        for (int i = 0; i < waypointButtons.length; i++) {
            WaypointButtonType buttonType = waypointButtons[i];
            ImageButton button = makeButton(buttonType.imageName);
            button.setPosition(initX + x * i, y);
            buttonType.button = button;

            addActor(button);
        }
    }

    private ImageButton makeButton(String name) {
        Drawable onImage = getDrawable(name + "On");
        ImageButton imageButton = new ImageButton(getDrawable(name + "Off"), onImage, onImage);
        imageButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return true;
            }
        });
        return imageButton;
    }

    private TextureRegion getUIRegion(String name) {
        return MyGame.ATLAS.findRegion("uiStuff/" + name);
    }

    private Drawable getDrawable(String name) {
        return new TextureRegionDrawable(getUIRegion(name));
    }

    private class WaypointButtonType {
        public String imageName;
        public ZoneType type;
        public ImageButton button;

        public WaypointButtonType(String imageName, ZoneType type) {
            this.imageName = imageName;
            this.type = type;
        }
    }
}
