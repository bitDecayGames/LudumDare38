package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;
import com.bitdecay.game.util.RectangleExt;
import com.bitdecay.game.util.VectorMath;

/**
 * This system is in charge of drawing the waypoints on objects
 */
public class WaypointSystem extends AbstractDrawableSystem {

    private AnimatedImageComponent arrowSpin;
    private StaticImageComponent arrowStatic;
    private float scale = 0.03f;
    private float width = 26 * scale;
    private float height = 29 * scale;
    private float originX = width * 0.5f;
    private float originY = height * 0f;

    public WaypointSystem(AbstractRoom room) {
        super(room);
        arrowSpin = new AnimatedImageComponent("uiStuff/arrow", 0.1f);
        arrowStatic = new StaticImageComponent("uiStuff/arrow/0");
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {return gob.hasComponents(PositionComponent.class, WaypointComponent.class);}

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        arrowSpin.update(1/60f);
        spriteBatch.begin();
        gobs.forEach(gob -> gob.forEachComponentDo(PositionComponent.class, pos -> gob.forEachComponentDo(WaypointComponent.class, wp -> {
            Vector2 camPos = VectorMath.toVector2(camera.position);
            Vector2 camToPos = pos.toVector2().sub(camPos);
            float modWidth = camera.viewportWidth * 0.9f;
            float modHeight = camera.viewportHeight * 0.9f;
            float halfWidth = modWidth / 2;
            float halfHeight = modHeight / 2;
            RectangleExt viewPort = new RectangleExt(-halfWidth + camPos.x, -halfHeight + camPos.y, modWidth, modHeight);
            if (viewPort.contains(pos.toVector2())) {
                drawArrow(pos.toVector2(), new Vector2(0, -1), wp.color, arrowSpin);
            } else {
                Vector2 intersection = viewPort.intersectionFromCenter(camToPos.cpy());
                if (intersection != null) drawArrow(intersection.cpy(), camToPos, wp.color, arrowStatic);
            }
        })));
        spriteBatch.end();
    }

    private void drawArrow(Vector2 pos, Vector2 pointingTo, Color color, DrawableComponent arrow){
        float rotation = VectorMath.angleInDegrees(new Vector2(0, -1), pointingTo.cpy().nor());
        room.spriteBatch.draw(arrow.image(), pos.x - originX, pos.y - originY, originX, originY, width, height, 1, 1, rotation);
    }
}
