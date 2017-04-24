package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.util.ZoneType;

import java.util.function.Consumer;

public class ZoneComponent extends AbstractComponent {
    public boolean canDeactivate = true;
    public boolean active = false;
    public boolean strict = true;
    public ZoneType type = null;
    private Consumer<MyGameObject> func;
    public String sound;

    public ZoneComponent(Consumer<MyGameObject> func){
        this.func = func;
    }

    public void execute(MyGameObject gameObj){
        func.accept(gameObj);
    }
}
