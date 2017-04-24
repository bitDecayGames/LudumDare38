package com.bitdecay.game.ai;

import com.bitdecay.game.component.AbstractComponent;
import com.bitdecay.game.gameobject.MyGameObject;

public class AIControlComponent extends AbstractComponent {
    private AIDriveDirection driveDirection = AIDriveDirection.FORWARD;
    private AITurnDirection turnDirection = AITurnDirection.RIGHT;

    public static AIControlComponent get(MyGameObject gob) {
        return gob.hasComponent(AIControlComponent.class) ? gob.getComponent(AIControlComponent.class).get() : null;
    }

    public AIControlComponent() {

    }

    public boolean left() {
        return turnDirection == AITurnDirection.LEFT;
    }

    public boolean right() {
        return turnDirection == AITurnDirection.RIGHT;
    }

    public boolean up() {
        return driveDirection == AIDriveDirection.FORWARD;
    }

    public boolean down() {
        return driveDirection == AIDriveDirection.SLOW ||
            driveDirection == AIDriveDirection.REVERSE;
    }

    public AIDriveDirection getDriveDirection() {
        return driveDirection;
    }

    public AITurnDirection getTurnDirection() {
        return turnDirection;
    }
}
