package com.bitdecay.game.component;

import com.bitdecay.game.physics.TireFrictionData;

/**
 * Created by Luke on 4/22/2017.
 */
public class TireFrictionModifierComponent extends AbstractComponent {
    public TireFrictionData modifiedFriction;

    public TireFrictionModifierComponent(TireFrictionData Friction){
        modifiedFriction = Friction;
    }
}
