package com.bitdecay.game.component.money;

import com.bitdecay.game.component.AbstractComponent;

public class MoneyDiffComponent extends AbstractComponent {
    public float diff;

    public MoneyDiffComponent(float diff) {
        this.diff = diff;
    }

    @Override
    public String toString() {
        return Float.toString(diff);
    }
}
