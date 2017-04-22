package com.bitdecay.game.component;

import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.trait.IInitializable;
import com.bitdecay.game.trait.IRemovable;

/**
 * Created by Luke on 4/22/2017.
 */
public class HealthComponent extends AbstractComponent {

    public int maxHealth;
    public int currentHealth;

    public HealthComponent(int max, int current){
        maxHealth = max;
        currentHealth = current;
    }

    public HealthComponent(int max){
        maxHealth = max;
        currentHealth = max;
    }

    public boolean notDead(){
        return currentHealth > 0;
    }

    public HealthComponent set(int currentHealth){
        this.currentHealth = currentHealth;
        return this;
    }
}
