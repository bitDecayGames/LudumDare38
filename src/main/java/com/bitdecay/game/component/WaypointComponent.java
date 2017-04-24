package com.bitdecay.game.component;

import com.bitdecay.game.trait.IUpdate;
import com.bitdecay.game.util.Quest;
import com.bitdecay.game.util.ZoneType;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Use this component to mark an object on or off screen with a little indicator
 */
public class WaypointComponent extends AbstractComponent implements IUpdate {

    protected Logger log = LogManager.getLogger(this.getClass());

    public AnimatedImageComponent animated = null;
    public StaticImageComponent staticImage = null;
    public ZoneType zoneType = null;
    public boolean rotates = false;
    public Quest quest = null;

    public WaypointComponent(Quest quest){
        this.quest = quest;
        animated = new AnimatedImageComponent("uiStuff/arrow", 0.1f);
        staticImage = new StaticImageComponent("uiStuff/arrow/0");
        rotates = true;
    }

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

    public boolean isQuestActive(){
        return quest == null || quest.isActive;
    }

    @Override
    public void update(float delta) {
        if (animated != null) animated.update(delta);
    }
}
