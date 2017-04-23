package com.bitdecay.game.component;

import com.bitdecay.game.trait.IUpdate;
import com.bitdecay.game.util.ZoneType;

/**
 * Use this component to mark an object on or off screen with a little indicator
 */
public class WaypointComponent extends AbstractComponent implements IUpdate {
    public AnimatedImageComponent animated = null;
    public StaticImageComponent staticImage = null;
    public ZoneType zoneType = null;
    public boolean rotates = false;

    public WaypointComponent(ZoneType zoneType){
        this.zoneType = zoneType;

        switch(zoneType) {
            case BATHROOM:
                animated = new AnimatedImageComponent("uiStuff/pooperOn", 0.1f);
                staticImage = new StaticImageComponent("uiStuff/pooperOn");
                break;
            case FOOD:
                animated = new AnimatedImageComponent("uiStuff/grubOn", 0.1f);
                staticImage = new StaticImageComponent("uiStuff/grubOn");
                break;
            case REPAIR:
                animated = new AnimatedImageComponent("uiStuff/fixOn", 0.1f);
                staticImage = new StaticImageComponent("uiStuff/fixOn");
                break;
            case FUEL:
                animated = new AnimatedImageComponent("uiStuff/gasOn", 0.1f);
                staticImage = new StaticImageComponent("uiStuff/gasOn");
                break;
            case OBJECTIVE:
                animated = new AnimatedImageComponent("uiStuff/arrow", 0.1f);
                staticImage = new StaticImageComponent("uiStuff/arrow/0");
                rotates = true;
                break;
            default:
                animated = new AnimatedImageComponent("uiStuff/arrow", 0.1f);
                staticImage = new StaticImageComponent("uiStuff/arrow/0");
                rotates = true;
                break;
        }
    }

    @Override
    public void update(float delta) {
        if (animated != null) animated.update(delta);
    }
}
