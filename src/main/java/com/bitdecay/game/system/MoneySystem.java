package com.bitdecay.game.system;

import com.bitdecay.game.component.money.MoneyComponent;
import com.bitdecay.game.component.money.MoneyDiffComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.ui.UIElements;

public class MoneySystem extends AbstractForEachUpdatableSystem {
    private UIElements uiElements;

    public MoneySystem(AbstractRoom room, UIElements uiElements) {
        super(room);
        this.uiElements = uiElements;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                MoneyComponent.class,
                MoneyDiffComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        MoneyComponent moneyComp = gob.getComponent(MoneyComponent.class).get();

        gob.forEachComponentDo(MoneyDiffComponent.class, moneyDiff -> moneyComp.diffMoney(moneyDiff));

        uiElements.hud.phone.setMoney(moneyComp.getMoney());
    }
}
