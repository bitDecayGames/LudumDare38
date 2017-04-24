package com.bitdecay.game.component;

import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.trait.IInitializable;
import com.bitdecay.game.trait.IRemovable;

/**
 * Created by Luke on 4/22/2017.
 */
public class HealthComponent extends AbstractComponent {

    public float maxHealth;
    public float currentHealth;

    public HealthComponent(float max, float current){
        maxHealth = max;
        currentHealth = current;
    }

    public HealthComponent(float max){
        maxHealth = max;
        currentHealth = max;
    }

    public boolean isDead(){
        return currentHealth <= 0;
    }

    public HealthComponent set(float currentHealth){
        this.currentHealth = currentHealth;
        return this;
    }
}
