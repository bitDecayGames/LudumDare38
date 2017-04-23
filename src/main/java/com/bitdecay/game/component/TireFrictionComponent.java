package com.bitdecay.game.component;

import com.bitdecay.game.physics.TireFrictionData;

/**
 * Created by Monday on 4/22/2017.
 */
public class TireFrictionComponent extends AbstractComponent {
    public final TireFrictionData data;
    public boolean tireLocked = false;

    public TireFrictionComponent(TireFrictionData data){
        this.data = data.copy();
    }
}
