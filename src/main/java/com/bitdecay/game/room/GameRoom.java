package com.bitdecay.game.room;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.jump.level.Level;

/**
 * Created by Monday on 4/21/2017.
 */
public class GameRoom extends AbstractRoom {

    World world;
    private GameScreen gameScreen;

    public GameRoom(GameScreen gameScreen) {
        super(gameScreen, new Level());
        world = new World(Vector2.Zero, false);
        this.gameScreen = gameScreen;
    }
}
