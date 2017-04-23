package com.bitdecay.game.component.money;

import com.bitdecay.game.component.AbstractComponent;
import com.bitdecay.game.gameobject.MyGameObject;

public class MoneyComponent extends AbstractComponent {
    private float bitcoins;

    public static boolean payForService(MyGameObject gameObj, float value) {
        return false;

//        gobs.forEach(gob -> gob.forEachComponentDo(MoneyComponent.class, moneyComp -> {
//            if (canPay) {
//                return;
//            }
//
//            if (moneyComp.getMoney() >= value) {
//                gob.addComponent(new MoneyDiffComponent(-value));
//            }
//        }));
    }

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
