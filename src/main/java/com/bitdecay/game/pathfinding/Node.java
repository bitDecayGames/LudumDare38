package com.bitdecay.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Node implements IndexedNode<Node> {
    private int index;

    public Vector2 position;
    public NodeType type;
    public Array<Connection<Node>> connections;

    public Node(Vector2 position, int index) {
        this.position = position;
        this.index = index;
        this.connections = new Array<>();
    }

    public void connectTo(Node node) {
        // TODO From connection may be needed for 2 direction graph.
        NodeConnection to = new NodeConnection(this, node);
        NodeConnection from = new NodeConnection(node, this);

        connections.add(to);
        connections.add(from);

        node.connections.add(to);
        node.connections.add(from);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Array getConnections() {
        return connections;
    }
}
