package com.bitdecay.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Node implements IndexedNode<Node> {
    // Must be unique among all nodes.
    private int index;

    public Vector2 position;
    public NodeType type = NodeType.NONE;
    public Array<Connection<Node>> connections;

    public Node(Vector2 position, int index) {
        this.position = position;
        this.index = index;
        this.connections = new Array<>();
    }

    public void connectTo(Node node) {
        NodeConnection to = new NodeConnection(this, node);
        NodeConnection from = new NodeConnection(node, this);

        connections.add(to);
//        connections.add(from);

//        node.connections.add(to);
//        node.connections.add(from);
    }

    public void disconnectFromAllNodes() {
        connections.forEach(conn -> {
            removeConnection(conn.getToNode(), conn.getFromNode(), conn);
            removeConnection(conn.getFromNode(), conn.getToNode(), conn);
        });

        connections.clear();
    }

    private void removeConnection(Node localNode, Node remoteNode, Connection<Node> conn) {
        if (localNode == this) {
            remoteNode.connections.removeValue(conn, true);
        }
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void setIndex(int value) {
        index = value;
    }

    @Override
    public Array getConnections() {
        return connections;
    }
}
