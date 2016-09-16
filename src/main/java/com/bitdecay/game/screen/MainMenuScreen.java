package com.bitdecay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.InputHelper;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen implements Screen {

    private MyGame game;
    private Stage stage = new Stage();

    private Table menu;
    private List<MenuOption> menuOptions = new ArrayList<>();
    private Image background;
    private Skin skin;

    private int menuSelection;

    public MainMenuScreen(MyGame game) {
        this.game = game;

        menuSelection = 0;

        skin = new Skin(Gdx.files.classpath("skin/skin.json"));

        background = new Image(new Texture(Gdx.files.classpath(Launcher.conf.getString("menu.titleBackground"))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        menu = new Table();
        menu.setFillParent(true);
        menu.add(buildNewMenuOption("Start", this::gotoGame)).height(60).padBottom(20).padTop(150).row();
        menu.add(buildNewMenuOption("Credits", this::gotoCredits)).height(60).padBottom(20).row();
        menu.add(buildNewMenuOption("Quit", this::exitGame)).height(60).padBottom(20).row();
        menu.align(Align.center);
        menu.padLeft(300);
        menu.padBottom(200);

        stage.addActor(background);
        stage.addActor(menu);

        Gdx.input.setInputProcessor(stage);
    }

    private Label buildNewMenuOption(String itemText, Runnable runnable){
        Label l = new Label(itemText, skin);
        l.setFontScale(8);
        l.setColor(Color.BLACK);
        l.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                runnable.run();
            }
        });
        menuOptions.add(new MenuOption(itemText, runnable, l));
        return l;
    }

    @Override
    public void show() {
        // SoundLibrary.loopMusic("ambientIntro");
        // animate the main menu when entering
        Action fadeIn = Actions.sequence(Actions.alpha(0),
                Actions.delay(0.25f),
                Actions.fadeIn(2.5f));
        background.addAction(fadeIn);
        menu.addAction(fadeIn);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        update();
    }


    public void update(){
        if (InputHelper.isKeyJustPressed(Input.Keys.UP, Input.Keys.LEFT)) updateMenuSelection(1);
        else if (InputHelper.isKeyJustPressed(Input.Keys.DOWN, Input.Keys.RIGHT)) updateMenuSelection(-1);
        else if (InputHelper.isKeyJustPressed(Input.Keys.ENTER, Input.Keys.SPACE)) makeSelection();
    }

    private void gotoGame() {
        game.setScreen(new GameScreen(game));
    }

    private void gotoCredits() {
        game.setScreen(new CreditsScreen(game));
    }

    private void exitGame() {
        Gdx.app.exit();
    }

    private void makeSelection(){

    }

    private void updateMenuSelection(int change) {
        menuSelection += change;
        if (menuSelection >= menuOptions.size()) menuSelection = 0;
        else if (menuSelection < 0) menuSelection = menuOptions.size() - 1;
        for (int i = 0; i < menuOptions.size(); i++){
            if (menuSelection == i) menuOptions.get(i).label.setColor(Color.GREEN);
            else menuOptions.get(i).label.setColor(Color.BLACK);
        }
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

    private class MenuOption{
        public String text;
        public Runnable runnable;
        public Label label;
        public MenuOption(String text, Runnable runnable, Label label){
            this.text = text;
            this.runnable = runnable;
            this.label = label;
        }
    }
}
