package com.bitdecay.game.system;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bitdecay.game.component.money.MoneyComponent;
import com.bitdecay.game.component.money.MoneyDiffComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.ui.HUD;
import com.bitdecay.game.ui.pointBurst.PointBurstFactory;

public class MoneySystem extends AbstractForEachUpdatableSystem {
    private Stage stage;

    public MoneySystem(AbstractRoom room, Stage stage) {
        super(room);
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

            stage.addActor(PointBurstFactory.money(moneyDiff.diff));
        });

        gob.removeComponent(MoneyDiffComponent.class);

        HUD.instance().phone.money.setMoney(moneyComp.getMoney());
    }
}
