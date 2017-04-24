package com.bitdecay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.InputHelper;

public class NameEntryScreen implements Screen {

    private MyGame game;
    private Stage stage = new Stage();

    private TextField text;
    private Image background;
    private Skin skin;
    private int score = 0;

    public NameEntryScreen(MyGame game, int score) {
        this.game = game;
        this.score = score;

        skin = new Skin();
        skin.add("pixel", MyGame.ASSET_MANAGER.get("font/acknowtt.ttf"), BitmapFont.class);
        skin.load(Gdx.files.classpath(Launcher.conf.getString("highscore.skin")));

        background = new Image(new Texture(Gdx.files.classpath(Launcher.conf.getString("menu.titleBackground"))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        text = new TextField("", skin);
        text.setMessageText("Enter your name here");
        text.setWidth(Gdx.graphics.getWidth());
        text.setFocusTraversal(false);
        text.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')){
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        text.setAlignment(Align.center);
        text.setPosition(0, Gdx.graphics.getHeight() * 0.5f);
        text.setMaxLength(20);

        Label l = new Label("Then press [ENTER]", skin);
        l.setWidth(Gdx.graphics.getWidth());
        l.setFontScale(8);
        l.setColor(Color.BLACK);
        l.setAlignment(Align.center);
        l.setPosition(0, Gdx.graphics.getHeight() * 0.1f);


        stage.addActor(background);
        stage.addActor(text);
        stage.addActor(l);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // TODO: maybe play music here if the music from the intro is not still playing

        // animate the main menu when entering
        Action fadeIn = Actions.sequence(Actions.alpha(0),
                Actions.delay(0.25f),
                Actions.fadeIn(2.5f));
        stage.addAction(fadeIn);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (InputHelper.isKeyJustPressed(Input.Keys.ENTER)) gotoHighscores(text.getText());
    }

    private void gotoHighscores(String text) { game.setScreen(new HighscoresScreen(game, text, score)); }

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
