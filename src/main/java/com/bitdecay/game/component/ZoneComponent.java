package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

import java.util.function.Consumer;

public class ZoneComponent extends AbstractComponent {
    public boolean canDeactivate = true;
    public boolean active = false;
    private Consumer<MyGameObject> func;

    public ZoneComponent(Consumer<MyGameObject> func){
        this.func = func;
    }

    public void execute(MyGameObject gameObj){
        func.accept(gameObj);
    }
}
