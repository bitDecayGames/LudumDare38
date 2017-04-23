package com.bitdecay.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;

public class NodeConnection implements Connection<Node> {
    private Node fromNode;
    private Node toNode;

    public NodeConnection(Node fromNode, Node toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override
    public float getCost() {
        return fromNode.position.dst(toNode.position);
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }
}
