package com.bitdecay.game.pathfinding;

import com.bitdecay.game.component.AbstractComponent;

public class NodeComponent extends AbstractComponent {
    public Node node;

    public NodeComponent(Node node) {
        this.node = node;
    }
}
