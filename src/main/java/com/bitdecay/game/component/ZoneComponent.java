package com.bitdecay.game.component;

public class ZoneComponent extends AbstractComponent {
    public boolean active = false;
    private Runnable func;

    public ZoneComponent(Runnable func){
        this.func = func;
    }

    public void execute(){
        func.run();
    }
}
