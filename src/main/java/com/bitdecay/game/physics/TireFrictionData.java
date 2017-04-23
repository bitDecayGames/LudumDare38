package com.bitdecay.game.physics;

/**
 * Created by Monday on 4/23/2017.
 */
public class TireFrictionData {
    public float maxLateralForceWhileInTraction;
    public float maxLateralForceWhileWheelsLocked;
    public float maxVelocityForLockedTiresToExperienceLateralFriction;
    public float weightOnTire;

    public TireFrictionData copy() {
        TireFrictionData newData = new TireFrictionData();
        newData.maxLateralForceWhileInTraction = this.maxLateralForceWhileInTraction;
        newData.maxLateralForceWhileWheelsLocked = this.maxLateralForceWhileWheelsLocked;
        newData.maxVelocityForLockedTiresToExperienceLateralFriction = this.maxVelocityForLockedTiresToExperienceLateralFriction;
        newData.weightOnTire = this.weightOnTire;
        return newData;
    }
}
