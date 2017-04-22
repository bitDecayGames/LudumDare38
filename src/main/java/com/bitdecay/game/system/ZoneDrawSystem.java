package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.game.component.ZoneComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

public class ZoneDrawSystem extends AbstractDrawableSystem{
    public ZoneDrawSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {

    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(ZoneComponent.class);
    }
}
