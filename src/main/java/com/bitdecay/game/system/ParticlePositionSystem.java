package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.ParticleFXComponent;
import com.bitdecay.game.component.ParticlePosition;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Monday on 4/23/2017.
 */
public class ParticlePositionSystem extends AbstractForEachUpdatableSystem {

    public ParticlePositionSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(PositionComponent.class, base -> {
            gob.forEachComponentDo(RotationComponent.class, rot -> {
                gob.forEachComponentDo(ParticlePosition.class, pos -> {
                    Vector2 resolvedVec = new Vector2(base.x, base.y);
                    Vector2 offsetVec = new Vector2(pos.xOffset, pos.yOffset);
                    resolvedVec.add(offsetVec.rotateRad(rot.rotation));
                    pos.x = resolvedVec.x;
                    pos.y = resolvedVec.y;
                });
            });
        });
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                PositionComponent.class,
                ParticleFXComponent.class,
                ParticlePosition.class,
                RotationComponent.class
        );
    }
}