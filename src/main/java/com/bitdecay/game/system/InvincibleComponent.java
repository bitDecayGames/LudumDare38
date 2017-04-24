package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.Color;
import com.bitdecay.game.component.AbstractComponent;
import com.bitdecay.game.component.TimerComponent;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Luke on 4/23/2017.
 */
public class InvincibleComponent extends AbstractComponent{
        public Color origColor;
    public InvincibleComponent(Color original){
        origColor = original;
    }
}
