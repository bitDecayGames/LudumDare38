package com.bitdecay.game.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

public class NodeSystem extends AbstractDrawableSystem {
    private final static boolean ENABLE_RENDERING = false;
    private ShapeRenderer renderer;

    public NodeSystem(AbstractRoom room) {
        super(room);

        renderer = new ShapeRenderer();
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(NodeComponent.class);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        if (!ENABLE_RENDERING) {
            return;
        }

        renderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        renderer.setTransformMatrix(spriteBatch.getTransformMatrix());

        gobs.stream().forEach(gob -> gob.forEach(NodeComponent.class, nodeComp -> {
            Node node = nodeComp.node;
            Vector2 nodePos = node.position;

            // Node
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.FOREST);
            renderer.point(nodePos.x, nodePos.y, 0);
            renderer.end();

            // Connections
            renderer.setColor(Color.FIREBRICK);
            node.connections.forEach(connection -> {
                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.line(connection.getFromNode().position, connection.getToNode().position);
                renderer.end();
            });
        }));
    }
}
