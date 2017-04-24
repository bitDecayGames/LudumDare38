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
                        aiComp.setPath(createRandomPath(aiComp.currentNode));
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
                        float diffRads = diff.angle() * MathUtils.degreesToRadians;
                        if (diff.len() < 2f) {
                            if (!aiComp.currentPath.hasNext()) {
                                aiComp.setPath(createRandomPath(aiComp.currentNode));
                            }
                            aiComp.currentNode = null;
                        } else {
                            physComp.body.setTransform(physComp.body.getPosition(), diffRads - MathUtils.PI/2);
                            float walkSpeed = 1.1f;
                            physComp.body.applyLinearImpulse(diff.nor().scl(walkSpeed - diff.len()).scl(physComp.body.getMass()), physComp.body.getWorldCenter(), true);
                        }
                    }
                });
            });
        });
    }

    private float normalizedAngle(float in) {
        while (in > MathUtils.PI2) {
            in -= MathUtils.PI2;
        }

        while (in < 0) {
            in += MathUtils.PI2;
        }

        return in;
    }

    private DefaultGraphPath<Node> createRandomPath(Node startNode) {
        DefaultGraphPath<Node> graphPath = new DefaultGraphPath<>();
        ManhattanHeuristic manhattanHeuristic = new ManhattanHeuristic();

        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<>(graph);
        Array<Node> nodes = graph.getNodes();
//        Node start = startNode != null ? startNode : nodes.get(MathUtils.random(0, nodes.size - 1));
        Node start = startNode != null ? startNode : nodes.get(0);
        Node end = nodes.get(MathUtils.random(0, nodes.size - 1));
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
