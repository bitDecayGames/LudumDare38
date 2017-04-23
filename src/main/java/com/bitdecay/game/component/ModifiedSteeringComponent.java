package com.bitdecay.game.component;

/**
 * Created by Luke on 4/23/2017.
 */
public class ModifiedSteeringComponent extends AbstractComponent  {
    public TireFrictionComponent.TireFrictionData modifiedFriction;

    public ModifiedSteeringComponent(TireFrictionComponent.TireFrictionData Friction){
        modifiedFriction = Friction;
    }
}
