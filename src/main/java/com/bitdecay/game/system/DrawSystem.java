package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

/**
 * The draw system is one of the few systems that extends AbstractSystem directly.  The reason for this is that there is a call to spritebatch begin and end within the process method.  You will notice however that the forEach method is called in between the begin and end calls.  When you extend the AbstractForEachUpdatableSystem, that forEach call isn't necessary because it happens behind the scenes in the AbstractForEachUpdatableSystem.process method.
 */
public class DrawSystem extends AbstractDrawableSystem {
    public DrawSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(DrawableComponent.class, PositionComponent.class, SizeComponent.class);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        gobs.stream().sorted((a, b) -> {
            int aOrder = a.getComponent(DrawOrderComponent.class).map(o -> o.order).orElseGet(() -> 0);
            int bOrder = b.getComponent(DrawOrderComponent.class).map(o -> o.order).orElseGet(() -> 0);
            return aOrder - bOrder;
        }).forEach(gob -> gob.forEach(DrawableComponent.class, drawable -> gob.forEach(PositionComponent.class, pos -> gob.forEach(SizeComponent.class, size -> {
            if (drawable.isVisible) {
                if (drawable instanceof ParticleFXComponent) {
                    maybeDrawParticle(spriteBatch, gob, (ParticleFXComponent) drawable);
                } else {
                    float rotation = gob.getComponent(RotationComponent.class).map(RotationComponent::toDegrees).orElseGet(() -> 0f);
                    Vector2 origin = gob.getComponent(OriginComponent.class).map(OriginComponent::toVector2).orElseGet(() -> new Vector2(0, 0)).scl(size.w, size.h);
                    spriteBatch.setColor(drawable.color);
                    spriteBatch.draw(drawable.image(), pos.x - origin.x, pos.y - origin.y, origin.x, origin.y, size.w, size.h, drawable.scaleX, drawable.scaleY, rotation);
                }
            }
        }))));
        spriteBatch.end();
    }

    private void maybeDrawParticle(SpriteBatch spriteBatch, MyGameObject gob, ParticleFXComponent particle) {
        if (gob.hasComponent(DrawInDrawSystemComponent.class)) {
            particle.effect.draw(spriteBatch);
        }
    }
}
