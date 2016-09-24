package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * This component marks an object as despawnable.  If the object falls off the screen, the system will kill the object.
 */
public class DespawnableComponent extends AbstractComponent {

    public DespawnableComponent(MyGameObject obj){super(obj);}
}
