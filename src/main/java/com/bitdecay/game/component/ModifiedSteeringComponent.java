package com.bitdecay.game.component;

import com.bitdecay.game.physics.TireFrictionData;

/**
 * Created by Luke on 4/23/2017.
 */
public class ModifiedSteeringComponent extends AbstractComponent  {
    public TireFrictionData modifiedFriction;

    public ModifiedSteeringComponent(TireFrictionData Friction){
        modifiedFriction = Friction;
    }
}
