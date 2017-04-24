package com.bitdecay.game.component;

/**
 * Created by Monday on 4/23/2017.
 */
public class FollowOtherPositionComponent extends AbstractComponent {
    public PositionComponent pos;

    public FollowOtherPositionComponent(PositionComponent pos) {
        this.pos = pos;
    }
}
