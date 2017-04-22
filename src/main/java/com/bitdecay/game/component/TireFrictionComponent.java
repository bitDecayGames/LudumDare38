package com.bitdecay.game.component;

/**
 * Created by Monday on 4/22/2017.
 */
public class TireFrictionComponent extends AbstractComponent {
    public final TireFrictionData data;
    public boolean tireLocked = false;

    public TireFrictionComponent(TireFrictionData data){
        this.data = data.copy();
    }

    public static class TireFrictionData {
        public float rollingMaxForce;
        public float driftingMaxForce;
        public float lockedTireGripVelocity;
        public float weightOnTire;

        public TireFrictionData copy() {
            TireFrictionData newData = new TireFrictionData();
            newData.rollingMaxForce = this.rollingMaxForce;
            newData.driftingMaxForce = this.driftingMaxForce;
            newData.lockedTireGripVelocity = this.lockedTireGripVelocity;
            newData.weightOnTire = this.weightOnTire;
            return newData;
        }
    }
}
