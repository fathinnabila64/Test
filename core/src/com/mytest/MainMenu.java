package com.mytest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by abdulrahimhajisabel on 17/04/16.
 */
public class MainMenu implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private Label heading;
    private Skin skin;
    private BitmapFont white,black;
    private TextureAtlas atlas;

    @Override
    public void show() {
        stage = new Stage();

        atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //create font
        white = new BitmapFont(Gdx.files.external("font/font.fnt"));
        black = new BitmapFont(Gdx.files.external("font/font.fnt"));

        //create buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX =1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.exit();

            }
        });
        buttonExit.pad(20);

        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Drop());
            }
        });
        buttonPlay.pad(20);


        //create heading
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.GREEN);//change font and put the color as clear

        heading = new Label("Water Conservation", headingStyle);
       // heading.setFontScale(3);

        //put it together
        table.add(heading);
        table.getCell(heading).spaceBottom(50);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(20);
        table.row();
        table.add(buttonExit);
       // table.debug();
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        FileHandle handle = Gdx.files.external("data/myfile.txt");
        handle.writeString("My god, it's full of stars", false);

        boolean bool2;
        bool2 = Gdx.files.external("myfile.txt").exists();
        boolean isDirectory = Gdx.files.external("data/").isDirectory();
        Gdx.app.log("Boolean", Boolean.toString(bool2) +" " + Boolean.toString(isDirectory));

        Gdx.app.log("Path", Gdx.files.getLocalStoragePath());

        boolean bool1;
        bool1 = Gdx.files.isLocalStorageAvailable();
        String s1;
        s1 = Boolean.toString(bool1);
        Gdx.app.log("Boolean", s1);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //stage.setDebugAll(true);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
        table.setClip(true);
        table.setSize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
