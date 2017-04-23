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
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjects;
import com.bitdecay.game.system.PhysicsSystem;
import com.bitdecay.game.trait.IUpdate;
import com.bitdecay.game.util.Tuple;
import com.bitdecay.game.util.ZoneType;

import java.util.Arrays;
import java.util.List;

public class Phone extends Group implements IUpdate{
    public List<Tuple<MyGameObject, MyGameObject>> objectives;
    public MyGameObject selectedObjective;
    public PhysicsSystem phys;
    public MyGameObjects gobs;
    boolean objectiveFinished = false;

    Image phone;
    Money money;

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
//        makeObjectiveButtons();

        money = new Money(screenSize);
        addActor(money);

        phoneGlare.setTouchable(Touchable.childrenOnly);
        addActor(phoneGlare);

        float scale = screenSize.y / phone.getHeight() - 1;
        scaleBy(scale);
    }

    public void setMoney(float value) {
        money.setMoney(value);
    }

    public boolean getWaypointEnabled(ZoneType type) {
        WaypointButtonType buttonType = Arrays.stream(waypointButtons)
            .filter(bt -> bt.type == type)
            .findFirst().orElse(null);

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

    private void makeObjectiveButtons(){
        float y = phone.getHeight() * 0.7f;
        float initY = phone.getWidth() * 0.11f;
        float x = phone.getWidth() * 0.20f;

//        for (int i = 0; i < objectives.size(); i++){
//            ImageButton button = makeObjectiveButton(objectives.get(i).x);
//            button.setPosition(x, initY + y);
//            addActor(button);
//        }
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

    private ImageButton makeObjectiveButton(MyGameObject hooman) {
        ImageButton imageButton = new ImageButton(new TextureRegionDrawable(MyGame.ATLAS.findRegion("target")));
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

    @Override
    public void update(float delta) {
        if(objectiveFinished){
            makeWaypointButtons();
            objectiveFinished = false;
        }
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

    private class DisplayObjective {
        public String imageName;

        public DisplayObjective(String imageName){
            this.imageName = imageName;
        }
    }
}
