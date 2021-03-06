package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.trait.IShapeDraw;
import com.typesafe.config.Config;

/**
 * This component, when added to a game object, will draw a circle at the current position of the game object.
 */
public class DebugCircleComponent extends AbstractComponent implements IShapeDraw {
    public final Color color;
    public final float radius;

    private DebugCircleComponent(Color color, float radius){
        this.color = color.cpy();
        this.radius = radius;
    }

    public DebugCircleComponent(Config conf) {
        this.radius = (float) conf.getDouble("radius");
        Config colorConf = conf.getConfig("color");
        this.color = new Color(
                (float) colorConf.getDouble("r"),
                (float) colorConf.getDouble("g"),
                (float) colorConf.getDouble("b"),
                (float) colorConf.getDouble("a")
        );
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 pos) {
        shapeRenderer.setColor(color);
        shapeRenderer.circle(pos.x, pos.y, radius);
    }
}
