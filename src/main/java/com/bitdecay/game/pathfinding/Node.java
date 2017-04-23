package com.bitdecay.game.pathfinding;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public Vector2 position;
    public NodeType type;
    public List<NodeConnection> connections;

    public Node(Vector2 position) {
        this(position, new ArrayList<>());
    }

    public Node(Vector2 position, List<NodeConnection> connections) {
        this.position = position;
        this.connections = connections;
    }

    public void connectTo(Node node) {
        NodeConnection to = new NodeConnection(this, node);
        NodeConnection from = new NodeConnection(node, this);

        connections.add(to);
        connections.add(from);

        node.connections.add(to);
        node.connections.add(from);
    }
}
