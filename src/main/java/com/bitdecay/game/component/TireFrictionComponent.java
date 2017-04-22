package com.bitdecay.game.component;

/**
 * Created by Monday on 4/22/2017.
 */
public class TireFrictionComponent extends AbstractComponent {
    public float maxForce;
    public float driftingMaxForce;
    public float lockedTireGripVelocity;
    public boolean tireLocked = false;

    public TireFrictionComponent(float rollingMaxForce, float driftingMaxForce, float lockedTireGripVelocity){
        this.maxForce = rollingMaxForce;
        this.driftingMaxForce = driftingMaxForce;
        this.lockedTireGripVelocity = lockedTireGripVelocity;
    }
}
