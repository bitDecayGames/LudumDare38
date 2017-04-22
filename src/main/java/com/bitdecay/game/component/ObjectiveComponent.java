package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

public class ObjectiveComponent extends AbstractComponent{
    public int time = 0;
    public int reward = 0;
    public MyGameObject target;

    public ObjectiveComponent(int time, int reward, MyGameObject target){
        this.time = time;
        this.reward = reward;
        this.target = target;
    }
}
