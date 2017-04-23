package com.bitdecay.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class ManhattanHeuristic implements Heuristic<Node> {

    public ManhattanHeuristic() {

    }

    @Override
    public float estimate(Node node, Node endNode) {
        // TODO Might be able to just do Vector2.dst() here instead.
        return Math.abs(endNode.position.x - node.position.x) + Math.abs(endNode.position.y - node.position.y);
    }
}
