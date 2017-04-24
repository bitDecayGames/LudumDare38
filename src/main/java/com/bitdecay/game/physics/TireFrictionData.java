package com.bitdecay.game.physics;

/**
 * Created by Monday on 4/23/2017.
 */
public class TireFrictionData {
    public float maxLateralForceWhileInTraction;
    public float maxLateralForceWhileWheelsLocked;
    public float weightOnTire;
    public float driftingLateralForceScalar;

    public TireFrictionData copy() {
        TireFrictionData newData = new TireFrictionData();
        newData.maxLateralForceWhileInTraction = this.maxLateralForceWhileInTraction;
        newData.maxLateralForceWhileWheelsLocked = this.maxLateralForceWhileWheelsLocked;
        newData.driftingLateralForceScalar = this.driftingLateralForceScalar;
        newData.weightOnTire = this.weightOnTire;
        return newData;
    }
}
