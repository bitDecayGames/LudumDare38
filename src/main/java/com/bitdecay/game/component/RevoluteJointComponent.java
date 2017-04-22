package com.bitdecay.game.component;

import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

/**
 * Created by Monday on 4/22/2017.
 */
public class RevoluteJointComponent extends AbstractComponent {
    public RevoluteJoint join;

    public RevoluteJointComponent(RevoluteJoint joint){
        this.join = joint;
    }
}
