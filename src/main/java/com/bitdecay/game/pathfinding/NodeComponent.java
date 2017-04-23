package com.bitdecay.game.pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.AbstractComponent;

public class NodeComponent extends AbstractComponent {
    public Node node;

    public NodeComponent(Vector2 position) {
        this(new Node(position));
    }

    public NodeComponent(Node node) {
        this.node = node;
    }
}
