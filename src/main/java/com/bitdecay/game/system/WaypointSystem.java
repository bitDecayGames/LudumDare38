package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.DrawableComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.WaypointComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;
import com.bitdecay.game.ui.HUD;
import com.bitdecay.game.util.RectangleExt;
import com.bitdecay.game.util.VectorMath;

/**
 * This system is in charge of drawing the waypoints on objects
 */
public class WaypointSystem extends AbstractDrawableSystem {

    private float size = 1f;
    private float originX = size * 0.5f;
    private float originY = 0f;

    private float size_XL = 2f;
    private float originX_XL = size_XL * 0.5f;
    private float originY_XL = 0f;

    public WaypointSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {return gob.hasComponents(PositionComponent.class, WaypointComponent.class);}

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.begin();
        gobs.forEach(gob -> gob.forEachComponentDo(PositionComponent.class, pos -> gob.forEachComponentDo(WaypointComponent.class, wp -> {
            if (HUD.instance().phone.waypoints.isWaypointOn(wp.zoneType)) {
                Vector2 camPos = VectorMath.toVector2(camera.position);
                Vector2 camToPos = pos.toVector2().sub(camPos);
                float modWidth = camera.viewportWidth * 0.9f;
                float modHeight = camera.viewportHeight * 0.9f;
                float halfWidth = modWidth / 2;
                float halfHeight = modHeight / 2;
                RectangleExt viewPort = new RectangleExt(-halfWidth + camPos.x, -halfHeight + camPos.y, modWidth, modHeight);
                if (viewPort.contains(pos.toVector2())) {
                    drawArrow(pos.toVector2(), new Vector2(0, -1), wp.animated, wp.rotates, size_XL, originX_XL, originY_XL);
                } else {
                    Vector2 intersection = viewPort.intersectionFromCenter(camToPos.cpy());
                    if (intersection != null) drawArrow(intersection.cpy(), camToPos, wp.staticImage, wp.rotates, size, originX, originY);
                }
            }
        })));
        spriteBatch.end();
    }

    private void drawArrow(Vector2 pos, Vector2 pointingTo, DrawableComponent arrow, boolean rotates, float size, float originX, float originY){
        float rotation = rotates ? VectorMath.angleInDegrees(new Vector2(0, -1), pointingTo.cpy().nor()) : 0;
        room.spriteBatch.draw(arrow.image(), pos.x - originX, pos.y - originY, originX, originY, size, size, 1, 1, rotation);
    }
}
