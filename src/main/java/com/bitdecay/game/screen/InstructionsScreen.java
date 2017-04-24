package com.bitdecay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.InputHelper;

/**
 * This is the instructions screen.
 */
public class InstructionsScreen implements Screen {

    private MyGame game;
    private Stage stage = new Stage();

    private Image background;
    private Image body;
    private Image car;
    private Image zone;
    private Image phone;
    private Image restaurant;
    private Image bathroom;
    private Image autoShop;
    private Image gasStation;
    private Image objCar;
    private Image person;
    private Image objZone;

    private Label controls;
    private Container controlsContainer;

    private Label explainConcerns;
    private Container concernsContainer;

    private Label explainSolutions;
    private Container solutionsContainer;

    private Label explainObjectivbes;
    private Container objectivesContainer;

    private Label lblTitle;
    private Label lblBack;
    private Label lblCredits;

    public InstructionsScreen(MyGame game) {
        this.game = game;

        Skin skin = new Skin(Gdx.files.classpath(Launcher.conf.getString("instructions.skin")));

        background = new Image(MyGame.ATLAS.findRegion("background"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        lblTitle = new Label(" Instructions", skin);
        lblTitle.setFontScale(5);
        lblTitle.setFillParent(true);
        lblTitle.setAlignment(Align.top, Align.center);
        lblTitle.setColor(Color.SKY);

        lblBack = new Label("Press esc/B to go back", skin);
        lblBack.setFontScale(2);
        lblBack.setFillParent(true);
        lblBack.setAlignment(Align.bottom, Align.right);
        lblBack.setColor(Color.GRAY);

        // ///////////////////////////////////////////////
        // Add new sections to the credits here
        // ///////////////////////////////////////////////
        StringBuilder creditsStr = new StringBuilder();

        lblCredits = new Label(creditsStr.toString(),
                skin);
        lblCredits.setFontScale(Launcher.conf.getInt("instructions.fontSize"));
        lblCredits.setBounds(0, -Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lblCredits.setAlignment(Align.center, Align.bottom);
        lblCredits.setColor(Color.WHITE);

        controls = new Label("WSAD to control the car.\nSPACE for the e-brake.\n" +
                "Tab opens your phone\nQ and E select objectives.", skin);
        controls.setFontScale(3);
        controls.setFillParent(true);
        controls.setAlignment(Align.center);
        controls.setColor(Color.WHITE);
        controlsContainer = new Container(controls);
        controlsContainer.setPosition(Gdx.graphics.getWidth() * 0.52f, Gdx.graphics.getHeight() * 0.87f);

        phone = new Image(MyGame.ATLAS.findRegion("uiStuff/phone"));
        phone.setScale(0.2f, 0.2f);
        phone.setPosition(controlsContainer.getX() * 0.02f, controlsContainer.getY() * 0.76f);


        body = new Image(MyGame.ATLAS.findRegion("uiStuff/foodBio"));
        body.setScale(0.2f, 0.2f);
        body.setPosition(Gdx.graphics.getWidth() * 0.53f, Gdx.graphics.getHeight() * 0.65f);

        explainConcerns = new Label("Keep your stomach full,\nyour intestines empty,\nyour car repaired and\nfueled up.", skin);
        explainConcerns.setFontScale(3);
        explainConcerns.setFillParent(true);
        explainConcerns.setAlignment(Align.center);
        explainConcerns.setColor(Color.WHITE);
        concernsContainer = new Container(explainConcerns);
        concernsContainer.setPosition(Gdx.graphics.getWidth() * 0.96f, Gdx.graphics.getHeight() * 0.87f);

        explainSolutions = new Label("Drive to a restaurant to eat,\nbathroom to poop,\nauto-shop to repair,\nand gas station to fuel up.", skin);
        explainSolutions.setFontScale(3);
        explainSolutions.setFillParent(true);
        explainSolutions.setAlignment(Align.center);
        explainSolutions.setColor(Color.WHITE);
        solutionsContainer = new Container(explainSolutions);
        solutionsContainer.setPosition(Gdx.graphics.getWidth() * 0.75f, Gdx.graphics.getHeight() * 0.63f);

        car = new Image(MyGame.ATLAS.findRegion("player/taxi/taxi"));
        car.setPosition(Gdx.graphics.getWidth() * 0.20f, Gdx.graphics.getHeight() * 0.41f);

        zone = new Image(MyGame.ATLAS.findRegion("uiStuff/missions/rectangleTarget/0-1"));
        zone.setPosition(Gdx.graphics.getWidth() * 0.17f, Gdx.graphics.getHeight() * 0.33f);

        restaurant = new Image(MyGame.ATLAS.findRegion("uiStuff/grubOn"));
        restaurant.setPosition(Gdx.graphics.getWidth() * 0.77f, Gdx.graphics.getHeight() * 0.55f);
        bathroom = new Image(MyGame.ATLAS.findRegion("uiStuff/pooperOn"));
        bathroom.setPosition(Gdx.graphics.getWidth() * 0.83f, Gdx.graphics.getHeight() * 0.55f);
        autoShop = new Image(MyGame.ATLAS.findRegion("uiStuff/fixOn"));
        autoShop.setPosition(Gdx.graphics.getWidth() * 0.77f, Gdx.graphics.getHeight() * 0.44f);
        gasStation = new Image(MyGame.ATLAS.findRegion("uiStuff/gasOn"));
        gasStation.setPosition(Gdx.graphics.getWidth() * 0.83f, Gdx.graphics.getHeight() * 0.44f);

        explainObjectivbes = new Label("Drive into a person's blue zone to pick them up.\nThen drive to the blue zone they want you to go.", skin);
        explainObjectivbes.setFontScale(3);
        explainObjectivbes.setFillParent(true);
        explainObjectivbes.setAlignment(Align.center);
        explainObjectivbes.setColor(Color.WHITE);
        objectivesContainer = new Container(explainObjectivbes);
        objectivesContainer.setPosition(Gdx.graphics.getWidth() * 0.87f, Gdx.graphics.getHeight() * 0.36f);

        objCar = new Image(MyGame.ATLAS.findRegion("player/taxi/taxi"));
        objCar.setRotation(270);
        objCar.setPosition(Gdx.graphics.getWidth() * 0.44f, Gdx.graphics.getHeight() * 0.21f);

        person = new Image(MyGame.ATLAS.findRegion("person/walk/0"));
        person.setPosition(Gdx.graphics.getWidth() * 0.57f, Gdx.graphics.getHeight() * 0.145f);

        objZone = new Image(MyGame.ATLAS.findRegion("uiStuff/missions/circleTarget/0"));
        objZone.setScale(0.35f, 0.35f);
        objZone.setPosition(Gdx.graphics.getWidth() * 0.52f, Gdx.graphics.getHeight() * 0.05f);

        stage.addActor(background);
        stage.addActor(lblTitle);
        stage.addActor(lblBack);
        stage.addActor(lblCredits);
        stage.addActor(body);
        stage.addActor(controlsContainer);
        stage.addActor(phone);
        stage.addActor(concernsContainer);
        stage.addActor(solutionsContainer);
        stage.addActor(zone);
        stage.addActor(car);
        stage.addActor(restaurant);
        stage.addActor(bathroom);
        stage.addActor(autoShop);
        stage.addActor(gasStation);
        stage.addActor(objectivesContainer);
        stage.addActor(objZone);
        stage.addActor(objCar);
        stage.addActor(person);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
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
