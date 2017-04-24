package com.bitdecay.game.ai;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.bitdecay.game.component.AbstractComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.pathfinding.Node;

import java.util.Iterator;

public class AIControlComponent extends AbstractComponent {
    public AIDriveDirection driveDirection = AIDriveDirection.NONE;
    public AITurnDirection turnDirection = AITurnDirection.NONE;

    public Iterator<Node> currentPath;
    public Node currentNode;

    public static AIControlComponent get(MyGameObject gob) {
        return gob.hasComponent(AIControlComponent.class) ? gob.getComponent(AIControlComponent.class).get() : null;
    }

    public AIControlComponent() {

    }

    public void setPath(DefaultGraphPath<Node> path) {
        currentPath = path.iterator();
    }

    public boolean left() {
        return turnDirection == AITurnDirection.LEFT;
    }

    public boolean right() {
        return turnDirection == AITurnDirection.RIGHT;
    }

    public boolean up() {
        return driveDirection == AIDriveDirection.FORWARD;
    }

    public boolean down() {
        return driveDirection == AIDriveDirection.SLOW ||
            driveDirection == AIDriveDirection.REVERSE;
    }
}
