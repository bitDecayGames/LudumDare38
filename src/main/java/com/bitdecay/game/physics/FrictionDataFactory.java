package com.bitdecay.game.physics;

/**
 * Created by Monday on 4/23/2017.
 */
public class FrictionDataFactory {
    public static TireFrictionData getStreetFriction() {
        TireFrictionData streetData = new TireFrictionData();
        streetData.maxLateralForceWhileInTraction = 8;
        streetData.maxLateralForceWhileWheelsLocked = .5f;
        streetData.driftingLateralForceScalar = .05f;
        return streetData;
    }

    public static TireFrictionData getGrassFriction() {
        TireFrictionData grassFriction = new TireFrictionData();
        grassFriction.maxLateralForceWhileInTraction = 2;
        grassFriction.maxLateralForceWhileWheelsLocked = .2f;
        return grassFriction;
    }

    public static TireFrictionData getCartFriction() {
        TireFrictionData cartFriction = new TireFrictionData();
        cartFriction.maxLateralForceWhileInTraction = .1f;
        cartFriction.maxLateralForceWhileWheelsLocked = .1f;
        return cartFriction;
    }
}
