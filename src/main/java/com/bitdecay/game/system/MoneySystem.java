package com.bitdecay.game.system;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bitdecay.game.component.money.MoneyComponent;
import com.bitdecay.game.component.money.MoneyDiffComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.ui.PointBurstEffect;
import com.bitdecay.game.ui.UIElements;

public class MoneySystem extends AbstractForEachUpdatableSystem {
    private UIElements uiElements;
    private Stage stage;

    public MoneySystem(AbstractRoom room, UIElements uiElements, Stage stage) {
        super(room);
        this.uiElements = uiElements;
        this.stage = stage;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                MoneyComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        MoneyComponent moneyComp = gob.getComponent(MoneyComponent.class).get();

        gob.forEachComponentDo(MoneyDiffComponent.class, moneyDiff -> {
            moneyComp.diffMoney(moneyDiff);

            stage.addActor(PointBurstEffect.money(moneyDiff.toString()));
        });

        gob.removeComponent(MoneyDiffComponent.class);

        uiElements.hud.phone.setMoney(moneyComp.getMoney());
    }
}
