package com.bitdecay.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Phone extends Actor {

    private ShapeRenderer renderer;

    public Phone (Vector2 screenSize) {
        renderer = new ShapeRenderer();

        float height = screenSize.y;
        float width = height / 2;

        setBounds(screenSize.x / 2 - width / 2, 0, width, height);
        setVisible(false);
    }

    public void draw (Batch batch, float parentAlpha) {
        batch.end();

        drawBase(batch);

        batch.begin();
    }

    private void drawBase(Batch batch) {
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(getX(), getY(), 0);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        renderer.rect(0, 0, getWidth(), getHeight());
        renderer.end();
    }
}
