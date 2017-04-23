package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

public class FollowComponent extends AbstractComponent{
    public MyGameObject target;

    public FollowComponent(MyGameObject target) {
        this.target = target;
    }
}
