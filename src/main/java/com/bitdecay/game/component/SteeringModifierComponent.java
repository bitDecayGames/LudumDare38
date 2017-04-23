package com.bitdecay.game.component;

/**
 * Created by Luke on 4/22/2017.
 */
public class SteeringModifierComponent extends AbstractComponent {
    public TireFrictionComponent.TireFrictionData modifiedFriction;

    public SteeringModifierComponent(TireFrictionComponent.TireFrictionData Friction){
        modifiedFriction = Friction;
    }
}
