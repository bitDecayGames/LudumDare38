package com.bitdecay.game.system;

import com.bitdecay.game.component.GoToHighScoreComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.screen.NameEntryScreen;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.ui.HUD;

/**
 * Created by Monday on 4/24/2017.
 */
public class GoToNameEntryScreenSystem extends AbstractUpdatableSystem {
    public GoToNameEntryScreenSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(GoToHighScoreComponent.class);
    }

    @Override
    public void update(float delta) {
        gobs.forEach(gob -> {
            if (gob.hasComponent(GoToHighScoreComponent.class)) {
                room.gameScreen.game.setScreen(new NameEntryScreen(room.gameScreen.game, (int) HUD.instance().phone.money.moneyValue));
//                room.gameScreen.game.setScreen(new MainMenuScreen(room.gameScreen.game));
            }
        });
    }
}
