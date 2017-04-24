package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.component.PersonComponent;
import com.bitdecay.game.component.RemoveNowComponent;
import com.bitdecay.game.component.WaypointComponent;
import com.bitdecay.game.component.ZoneComponent;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.ui.HUD;
import com.bitdecay.game.util.ObjectiveZone;
import com.bitdecay.game.util.Quest;
import com.bitdecay.game.util.Tuple;
import com.bitdecay.game.util.ZoneType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ObjectiveSystem extends AbstractUpdatableSystem{

    private List<Quest> quests = new ArrayList<>();

    List<Tuple<MyGameObject, Quest>> objectives = new ArrayList<>();

    final private int MAXOBJECTIVES = 3;
    private int currentObjectives = 0;
    private int peopleInTheWorld = 0;

    private Vector2 zone1 = new Vector2(5, 30);
    private Vector2 zone2 = new Vector2(15, 30);
    private Vector2 zone3 = new Vector2(-5, 30);

    private List<Vector2> zoneCoordsList = new ArrayList<>();

    public ObjectiveSystem(AbstractRoom room) {
        super(room);
//        HUD.instance().phone.tasks = objectives;
        zoneCoordsList.add(zone1);
        zoneCoordsList.add(zone2);
        zoneCoordsList.add(zone3);

        quests.addAll(Launcher.conf.getConfigList("quest.list").stream().map(questConf -> {
            String personName = questConf.getString("personName");
            String icon = questConf.getString("icon");
            float reward = (float) questConf.getDouble("reward");
            List<ObjectiveZone> zones = questConf.getConfigList("targetZones").stream().map(zoneConf -> {
                String name = zoneConf.getString("name");
                Vector2 position = new Vector2((float) zoneConf.getDouble("position.x"), (float) zoneConf.getDouble("position.x"));
                String flavorText = zoneConf.getString("flavorText");
                float timer = (float) zoneConf.getDouble("timer");
                return new ObjectiveZone(name, position, flavorText, timer);
            }).collect(Collectors.toList());
            return new Quest(personName, icon, reward, zones, null, null);
        }).collect(Collectors.toList()));
    }

    @Override
    public void update(float delta){
        peopleInTheWorld = (int) gobs.stream().filter(gob -> gob.hasComponent(PersonComponent.class)).count();

        if(currentObjectives < MAXOBJECTIVES && peopleInTheWorld >= 3){
            for(; currentObjectives < MAXOBJECTIVES; currentObjectives ++){
                createObjective();
            }
        }

        gobs.forEach(gob -> gob.getComponent(WaypointComponent.class).ifPresent(w -> {
            if (w.quest != null && w.quest.failed){
                gob.addComponent(new RemoveNowComponent());
            }
        }));
        HUD.instance().phone.tasks.removeFailedQuests();
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(PersonComponent.class) || gob.hasComponent(ZoneComponent.class);
    }

    private void createObjective(){
        if (quests.size() > 0) {
            Random randomizer = new Random();
            List<MyGameObject> currentHoomans = objectives.stream().map(tup -> tup.x).collect(Collectors.toList());

            List<MyGameObject> hoomanGobs = gobs.stream().filter(gob -> gob.hasComponent(PersonComponent.class) &&
                    !currentHoomans.contains(gob)).collect(Collectors.toList());
            MyGameObject targetHooman = hoomanGobs.get(randomizer.nextInt(hoomanGobs.size()));

            int questIndex = randomizer.nextInt(quests.size());
            Quest quest = quests.get(questIndex).copy((q, o) -> {
                if (q.currentZone().isPresent()) {
                    ObjectiveZone curZone = q.currentZone().get();
                    MyGameObject nextZone = GameObjectFactory.createZone(curZone.position.x, curZone.position.y, 15, 15, 0, ZoneType.OBJECTIVE, (obj) -> {
                        log.info("Remove old zone");
                        q.removeCurrentZone();
                        q.onZoneTrigger.accept(q, obj);
                    });
                    nextZone.getFreshComponent(WaypointComponent.class).ifPresent(wp -> wp.quest = q);
                    log.info("Add new zone");
                    room.addGob(nextZone);
                } else q.onCompletion.accept(q, o);
            }, (q, o) -> {
                log.info("End of quest: {}", q.personName);
                HUD.instance().phone.tasks.removeQuest(q);
            });
            log.info("Got quest: {}", quest);
            quests.remove(questIndex);
            log.info("Remaining Quests: {}", quests);

            MyGameObject humanZone = GameObjectFactory.createZone(targetHooman, 10000, 10000, 5, 5, 0, ZoneType.OBJECTIVE, (gameObj) -> {
                quest.started = true;
                quest.onZoneTrigger.accept(quest, gameObj);
            });
            humanZone.getFreshComponent(WaypointComponent.class).ifPresent(wp -> {
                wp.quest = quest;
                log.info("Set quest to zone!");
            });
            room.addGob(humanZone);
            HUD.instance().phone.tasks.addQuest(quest);

            objectives.add(new Tuple<>(targetHooman, quest));
        }
    }
}
