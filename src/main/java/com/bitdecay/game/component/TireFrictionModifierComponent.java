package com.bitdecay.game.component;

/**
 * Created by Luke on 4/22/2017.
 */
public class TireFrictionModifierComponent extends AbstractComponent {
    public TireFrictionComponent.TireFrictionData modifiedFriction;

    public TireFrictionModifierComponent(TireFrictionComponent.TireFrictionData Friction){
        modifiedFriction = Friction;
    }
}