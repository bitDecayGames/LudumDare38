package com.bitdecay.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.utils.Array;
import com.bitdecay.game.component.*;
import com.bitdecay.game.component.money.MoneyComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.ui.HUD;

/**
 * Created by Monday on 4/24/2017.
 */
public class RespawnGameOverSystem extends AbstractUpdatableSystem {
    private float timeWithoutPlayer = 0;

    private Array<MyGameObject> playerCars;

    public RespawnGameOverSystem(AbstractRoom room, Array<MyGameObject> playerCars) {
        super(room);
        this.playerCars = playerCars;
    }

    @Override
    public void update(float delta) {
        if (gobs.size() <= 0) {
            timeWithoutPlayer += delta;
        }

        if (timeWithoutPlayer >= 2) {
            timeWithoutPlayer = 0;

            if (playerCars.size > 0) {
                MyGameObject nextLife = playerCars.get(MathUtils.random(0, playerCars.size - 1));
                createPlayerCar(nextLife);
                playerCars.removeValue(nextLife, true);
            } else {
                HUD.instance().gameOver.setVisible(true);
                Gdx.input.setInputProcessor(null);

            }
        }
    }

    private void createPlayerCar(MyGameObject car) {
        float maxSpeed = 30;
        float acceleration = 10;

        // Add camera and other stats
        car.addComponent(new CameraFollowComponent());
        car.addComponent(new PlayerControlComponent("HitMetal"));
        car.addComponent(new HungerComponent(40, 0.5f));
        car.addComponent(new PoopooComponent(60, 0.5f));
        car.addComponent(new MoneyComponent(100));
        car.addComponent(new PlayerBodyComponent());

        PhysicsComponent p = car.getComponent(PhysicsComponent.class).get();
        for (JointEdge jointEdge : p.body.getJointList()) {
            MyGameObject tire = (MyGameObject) jointEdge.other.getUserData();
            tire.addComponent(new PlayerTireComponent());
            if (tire.hasComponent(RevoluteJointComponent.class)) {
                tire.addComponent(new DriveTireComponent(maxSpeed, acceleration));
                tire.addComponent(new SteerableComponent(MathUtils.PI / 6));
            }
        }
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                PlayerControlComponent.class
        );
    }
}
