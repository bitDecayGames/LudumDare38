package com.bitdecay.game.ai;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.pathfinding.ManhattanHeuristic;
import com.bitdecay.game.pathfinding.Node;
import com.bitdecay.game.pathfinding.NodeGraph;
import com.bitdecay.game.pathfinding.NodeType;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;

public class AIControlSystem extends AbstractUpdatableSystem {

    NodeGraph graph;

    public AIControlSystem(AbstractRoom room, NodeGraph graph) {
        super(room);

        this.graph = graph;
    }

    @Override
    public void update(float delta) {
        gobs.forEach(gob -> {
            gob.forEachComponentDo(AIControlComponent.class, aiComp -> {
                gob.forEachComponentDo(PhysicsComponent.class, physComp -> {
                    // Generate paths and nodes.
                    if (aiComp.currentPath == null) {
                        aiComp.setPath(createRandomPath());
                    }

                    if (aiComp.currentNode == null) {
                        if (aiComp.currentPath.hasNext()) {
                            aiComp.currentNode = aiComp.currentPath.next();
                            aiComp.currentNode.type = NodeType.ROAD;
                        } else {
                            aiComp.currentPath = null;
                        }
                    } else {
                        Vector2 targetPos = aiComp.currentNode.position.cpy();
                        Vector2 currentPos = physComp.body.getPosition().cpy();

                        Vector2 diff = targetPos.sub(currentPos);

                        if (diff.len() < 0.5f) {
                            aiComp.currentNode = null;
                        } else {
                            physComp.body.applyLinearImpulse(diff, physComp.body.getWorldCenter(), true);
                        }
                    }
                });
            });
        });
    }

    private DefaultGraphPath<Node> createRandomPath() {
        DefaultGraphPath<Node> graphPath = new DefaultGraphPath<>();
        ManhattanHeuristic manhattanHeuristic = new ManhattanHeuristic();

        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<>(graph);
        Array<Node> nodes = graph.getNodes();
        Node start = nodes.get(MathUtils.random(0, nodes.size));
        Node end = nodes.get(MathUtils.random(0, nodes.size));
        pathFinder.searchNodePath(start, end, manhattanHeuristic, graphPath);

        graphPath.iterator().forEachRemaining(node -> node.type = NodeType.SIDEWALK);

        return graphPath;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                AIControlComponent.class,
                PhysicsComponent.class

        );
    }
}
