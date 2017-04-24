package com.bitdecay.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class NodeGraph implements IndexedGraph<Node> {
    private Array<Node> nodes;

    public NodeGraph(int width, int height) {
        nodes = new Array<>();

        // Create nodes.
        int nodeIdx = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node n = new Node(new Vector2(x * 2, y * 2), nodeIdx);
                nodes.add(n);
                nodeIdx++;
            }
        }

        // Connect nodes.
        for (int i = 0; i < nodes.size; i++) {
            // Node above available.
            int aboveTargetIdx = i + width;
            if (aboveTargetIdx < nodes.size) {
                connectIndexedNodes(i, aboveTargetIdx);
            }
            // Node to the right available.
            int rightTargetIdx = i + 1;
            if (rightTargetIdx < nodes.size && rightTargetIdx % width != 0) {
                connectIndexedNodes(i, rightTargetIdx);
            }
            // Node above and to the right available
            int aboveRightTargetIdx = aboveTargetIdx + 1;
            if (aboveRightTargetIdx < nodes.size && aboveRightTargetIdx % width != 0) {
                connectIndexedNodes(i, aboveRightTargetIdx);
            }
            // Node above and to the left available.
            int aboveLeftTargetIdx = aboveTargetIdx - 1;
            if (aboveLeftTargetIdx < nodes.size && aboveTargetIdx % width != 0) {
                connectIndexedNodes(i, aboveLeftTargetIdx);
            }
        }
    }

    private void connectIndexedNodes(int currentIdx, int targetIdx) {
        Node currentNode = nodes.get(currentIdx);
        Node targetNode = nodes.get(targetIdx);
        currentNode.connectTo(targetNode);
    }

    public Array<Node> getNodes() {
        return nodes;
    }

    public Node removeNode(Node node) {
        return nodes.removeIndex(node.getIndex());
    }

    @Override
    public Array<Connection<Node>> getConnections(Node node) {
        return node.connections;
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }
}
