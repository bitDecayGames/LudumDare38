package com.bitdecay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.Highscore;
import com.bitdecay.game.util.InputHelper;
import com.bitdecay.game.util.ServerCommunication;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the generic credits screen.  Almost everything in the credits is populated from the /resources/conf/credits.conf file.  The only reason you should be making changes to this file is to adjust the position or maxSpeed of the text.
 */
public class HighscoresScreen implements Screen {

    private static String SPACE_AFTER_TITLE = "\n\n\n";
    private static String SPACE_AFTER_NAME = "\n\n";

    private MyGame game;
    private Stage stage = new Stage();
    private List<Highscore> highscores = new ArrayList<>();

    private Image background;
    private Label lblTitle;
    private Label lblBack;
    private Label lblScores;

    public HighscoresScreen(MyGame game, String name, int score) {
        this.game = game;

        if (name != null && !name.equalsIgnoreCase("")) ServerCommunication.instance().sendHighscore(new Highscore(name, score));
        highscores = ServerCommunication.instance().getHighscores();

        Skin skin = new Skin(Gdx.files.classpath(Launcher.conf.getString("menu.skin")));

        background = new Image(new Texture(Gdx.files.classpath(Launcher.conf.getString("highscore.background"))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        lblTitle = new Label("Highscores", skin);
        lblTitle.setFontScale(10);
        lblTitle.setFillParent(true);
        lblTitle.setAlignment(Align.top, Align.left);
        lblTitle.setColor(Color.SKY);

        lblBack = new Label("Press esc/B to go back", skin);
        lblBack.setFontScale(2);
        lblBack.setFillParent(true);
        lblBack.setAlignment(Align.bottom, Align.right);
        lblBack.setColor(Color.GRAY);

        // ///////////////////////////////////////////////
        // Add new sections to the credits here
        // ///////////////////////////////////////////////
        StringBuilder s = new StringBuilder();
        s.append(SPACE_AFTER_TITLE);
        highscores.forEach(h -> s.append(h.pretty()).append(SPACE_AFTER_NAME));
        s.append(SPACE_AFTER_TITLE);

        lblScores = new Label(s.toString(),
                skin);
        lblScores.setFontScale(Launcher.conf.getInt("highscore.fontSize"));
        lblScores.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lblScores.setAlignment(Align.center, Align.bottom);
        lblScores.setColor(Color.WHITE);


        stage.addActor(background);
        stage.addActor(lblTitle);
        stage.addActor(lblBack);
        stage.addActor(lblScores);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        lblScores.addAction(Actions.sequence(
                Actions.moveTo(0, 0),
                Actions.moveBy(0, 20, 1),
                Actions.moveBy(0, -20, 1),
                Actions.moveBy(0, 20, 1),
                Actions.moveBy(0, -20, 1),
                Actions.moveBy(0, 20, 1),
                Actions.moveBy(0, -20, 1),
                Actions.run(this::nextScreen)
        ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (InputHelper.isKeyJustPressed(Input.Keys.SPACE, Input.Keys.ESCAPE, Input.Keys.B, Input.Keys.BACKSPACE)) nextScreen();
    }

    public void nextScreen(){
        game.setScreen(new MainMenuScreen(game));
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
