package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.WaypointComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;
import com.bitdecay.game.util.RectangleExt;
import com.bitdecay.game.util.VectorMath;

/**
 * This system is in charge of drawing the waypoints on objects
 */
public class WaypointSystem extends AbstractDrawableSystem {

    private float arrowHeight = 0.5f;
    private float arrowWidth = 0.25f;

    private float visRadius = 5f;

    public WaypointSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {return gob.hasComponents(PositionComponent.class, WaypointComponent.class);}

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        room.shapeRenderer.setProjectionMatrix(camera.combined);
        room.shapeRenderer.begin();
        gobs.forEach(gob -> gob.forEachComponentDo(PositionComponent.class, pos -> gob.forEachComponentDo(WaypointComponent.class, wp -> {
            Vector2 camPos = VectorMath.toVector2(camera.position);
            Vector2 camToPos = pos.toVector2().sub(camPos);
            float halfWidth = camera.viewportWidth / 2;
            float halfHeight = camera.viewportHeight / 2;
            RectangleExt viewPort = new RectangleExt(-halfWidth + camPos.x, -halfHeight + camPos.y, camera.viewportWidth, camera.viewportHeight);
            if (viewPort.contains(pos.toVector2())) drawArrow(pos.toVector2().add(0, 1), new Vector2(0, -1), wp.color);
            else {
                Vector2 intersection = viewPort.intersectionFromCenter(camToPos.cpy().nor().scl(camToPos.len()));
                if (intersection != null) drawArrow(intersection.cpy(), camToPos, wp.color);
            }

        })));
        room.shapeRenderer.end();
    }

    private void drawArrow(Vector2 pos, Vector2 pointingTo, Color color){
        Vector2 norm = pointingTo.cpy().scl(-1).nor();
        Vector2 perp = norm.cpy().rotate(90);
        Vector2 left = norm.cpy().scl(arrowHeight).add(perp.cpy().scl(-arrowWidth * 0.5f)).add(pos);
        Vector2 right = norm.cpy().scl(arrowHeight).add(perp.cpy().scl(arrowWidth * 0.5f)).add(pos);

        room.shapeRenderer.setColor(color);
        room.shapeRenderer.line(pos, left);
        room.shapeRenderer.line(pos, right);
        room.shapeRenderer.line(right, left);
    }
}
