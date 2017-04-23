package com.bitdecay.game.component.money;

import com.bitdecay.game.component.AbstractComponent;

public class MoneyComponent extends AbstractComponent {
    private float bitcoins;

    public MoneyComponent(float startingMoney) {
        bitcoins = startingMoney;
    }

    public float getMoney() {
        return bitcoins;
    }

    public void diffMoney(MoneyDiffComponent comp) {
        bitcoins += comp.diff;
    }
}
