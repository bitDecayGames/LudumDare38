package com.bitdecay.game.component;

import com.bitdecay.game.component.money.MoneyComponent;
import com.bitdecay.game.component.money.MoneyDiffComponent;
import com.bitdecay.game.gameobject.MyGameObject;

import java.util.function.Consumer;

public class ZoneComponent extends AbstractComponent {
    public boolean canDeactivate = true;
    public boolean active = false;
    public boolean strict = true;
    private Consumer<MyGameObject> func;
    float cost;

    public ZoneComponent(float cost, Consumer<MyGameObject> func){
        this.func = func;
        this.cost = cost;
    }

    public void execute(MyGameObject gameObj){
        if (payForService(gameObj)) {
            func.accept(gameObj);
        }
    }

    private boolean payForService(MyGameObject gameObj) {
        return gameObj.getComponentStream(MoneyComponent.class)
            .findFirst()
            .map(moneyComp -> {
                if (moneyComp.getMoney() >= cost) {
                    gameObj.addComponent(new MoneyDiffComponent(-cost));
                    return true;
                } else {
                    return false;
                }
            })
            .orElse(false);
    }
}
