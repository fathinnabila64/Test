package com.mytest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

public class Drop implements Screen, InputProcessor, Input.TextInputListener {

    private SpriteBatch batch;
    private Texture bg, player, car, bathroom, kitchen, garden, checkbutton, backbutton, background, back;
    private Sprite sprite;
    private int currentFrame = 1;
    private OrthographicCamera camera;
    private TextureAtlas textureAtlas;
    private Animation animationright, animationleft, animationup, animationdown, animationstatic;
    private float elapsedTime = 0;
    private int screen = 0;
    private int animation = 0;
    private BitmapFont font;
    private  float touchX, touchY;
    private float charX, charY;
    private double waterCon, waterConBath, waterConPorch, waterConKitchen, waterConGarden, waterConPorchSum, waterConBathSum, waterConKitchenSum, waterConGardenSum;
    private double sum=0;
    private Preferences pref;
    private String text;
    private FileHandle file;




    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        background = new Texture(Gdx.files.internal("Bubbles.png"));
        back = new Texture(Gdx.files.internal("Bubbles.png"));
        bg = new Texture(Gdx.files.internal("mapreal.png"));
        car = new Texture(Gdx.files.internal("mapreal car.png"));
        bathroom = new Texture(Gdx.files.internal("mapreal bathroom.png"));
        kitchen = new Texture(Gdx.files.internal("mapreal kitchen.png"));
        garden = new Texture(Gdx.files.internal("mapreal garden.png"));
        checkbutton = new Texture(Gdx.files.internal("check-button.png"));
        backbutton = new Texture(Gdx.files.internal("image.png"));
        player = new Texture(Gdx.files.internal("playergirl2.png"));
        sprite = new Sprite(player);
        charX = 8;
        charY = 464;

        pref =  Gdx.app.getPreferences("DropHighScore");
        pref.putString("Name", Double.toString(waterCon));
        pref.flush();


        textureAtlas = new TextureAtlas(Gdx.files.internal("data2/try.txt"));

        TextureRegion[] stat =  new TextureRegion[1];
        stat[0] = (textureAtlas.findRegion("waklingfront3"));
        animationstatic= new Animation(1/5f, stat);


        TextureRegion[] walkRight =  new TextureRegion[2];
        walkRight[0] = (textureAtlas.findRegion("walkingright"));
        walkRight[1] = (textureAtlas.findRegion("walkingright2"));
        animationright = new Animation(1/5f, walkRight);

        TextureRegion[] walkLeft=  new TextureRegion[2];
        walkLeft[0] = (textureAtlas.findRegion("walkingleft2"));
        walkLeft[1] = (textureAtlas.findRegion("walkingleft"));
        animationleft= new Animation(1/5f, walkLeft);

        TextureRegion[] walkUp =  new TextureRegion[2];
        walkUp[0] = (textureAtlas.findRegion("walkingback"));
        walkUp[1] = (textureAtlas.findRegion("walkingback2"));
        animationup = new Animation(1/5f, walkUp);

        TextureRegion[] walkDown =  new TextureRegion[2];
        walkDown[0] = (textureAtlas.findRegion("walkingfront1"));
        walkDown[1] = (textureAtlas.findRegion("walkingfront2"));
        animationdown = new Animation(1/5f, walkDown);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Gdx.input.setInputProcessor(this);

        if(Gdx.input.justTouched()) {
            Gdx.input.getTextInput(this, "Title", "Default Text", "Name");
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        camera.update();

//		int  screen=-2;
//		batch.draw(back,0,0);
//		animation = 5;

        //sprite.setPosition(posX, posY);
        if(screen  == 0 || screen == -1) {
            batch.draw(background, 0, 0);
            batch.draw(bg, 0, 0);
            font.draw(batch, "Welcome to the game", 0, 465);
            font.draw(batch, "Player", 0, 400);
            font.draw(batch, "Current Usage(L)", 150, 400);
            font.draw(batch, "Previous Usage(L)", 430, 400);
            font.draw(batch, Double.toString(waterCon) + "L", 257, 323);
            batch.draw(backbutton, 570, 1191);
        }
        else if(screen == 1)
        {
            batch.draw(background,0,0);
            batch.draw(car, 0,470);
            batch.draw(backbutton, 570, 1191);
            font.draw(batch, "What Can You Do At Porch?", 0, 465);
            font.draw(batch, "Use bucket to wash the car", 0, 400);
            font.draw(batch, "Use hose to wash the car", 0,350);
            font.draw(batch, "Usage At Porch(L)", 0,300);
            font.draw(batch, Double.toString(waterConPorchSum) + "L", 400,300);
            batch.draw(checkbutton, 400,370);
            batch.draw(checkbutton, 400, 320);
        }
        else if(screen == 2)
        {
            batch.draw(background,0,0);
            batch.draw(bathroom, 0, 470);
            batch.draw(backbutton, 570, 1191);
            font.draw(batch, "What Can You Do At Bathroom?", 0, 465);
            font.draw(batch, "Let the water running while brushing teeth", 0, 400);
            font.draw(batch, "Do not let water running while brushing teeth", 0,350);
            font.draw(batch, "Bath", 0,300);
            font.draw(batch, "Shower", 0,250);
            font.draw(batch, "Full flush toilet", 0,200);
            font.draw(batch, "Half flush toilet", 0,150);
            font.draw(batch, "Usage At Bathroom(L)", 0,100);
            font.draw(batch, Double.toString(waterConBathSum) + "L", 600,100);
            batch.draw(checkbutton, 670,370);
            batch.draw(checkbutton, 670,320);
            batch.draw(checkbutton, 670,260);
            batch.draw(checkbutton, 670,210);
            batch.draw(checkbutton, 670,160);
            batch.draw(checkbutton, 670,110);
        }
        else if(screen == 3)
        {
            batch.draw(background,0,0);
            batch.draw(kitchen, 0, 470);
            batch.draw(backbutton, 570, 1191);
            font.draw(batch, "What Can You Do At Kitchen?", 0, 465);
            font.draw(batch, "Washing dishes by hand", 0, 400);
            font.draw(batch, "Use dishwashers", 0,350);
            font.draw(batch, "Wash fruit/vegetables in a basin", 0,300);
            font.draw(batch, "Wash fruit/vegetables using running water", 0,250);
            font.draw(batch, "Usage At Kitchen(L)", 0,200);
            font.draw(batch, Double.toString(waterConKitchenSum) + "L", 600,200);
            batch.draw(checkbutton, 670,370);
            batch.draw(checkbutton, 670,320);
            batch.draw(checkbutton, 670,260);
            batch.draw(checkbutton, 670,210);
        }
        else if (screen == 4)
        {
            batch.draw(background,0,0);
            batch.draw(garden, 0, 470);
            batch.draw(backbutton, 570, 1191);
            font.draw(batch, "What Can You Do At Garden?", 0, 465);
            font.draw(batch, "Water the plants using hose", 0, 400);
            font.draw(batch, "Water the plants using sprinkler", 0,350);
            font.draw(batch, "Usage At Garden(L)", 0,300);
            font.draw(batch, Double.toString(waterConGardenSum) + "L", 450,300);
            batch.draw(checkbutton, 470,370);
            batch.draw(checkbutton, 470,320);
        }
        else if(screen == 5)
        {
            batch.draw(backbutton, 0,470);
        }

        //sprite.draw(batch);
        //elapsedTime += Gdx.graphics.getDeltaTime();
        if(animation == 0)
        {
            batch.draw(animationstatic.getKeyFrame(elapsedTime, true), charX, charY);
        }
        else if (animation ==1)
        {
            batch.draw(animationright.getKeyFrame(elapsedTime, true), charX, charY);

        }
        else if(animation == 2)
        {
            batch.draw(animationleft.getKeyFrame(elapsedTime, true), charX, charY);
        }
        else if(animation == 3)
        {
            batch.draw(animationup.getKeyFrame(elapsedTime, true), charX, charY);
        }
        else if(animation == 4)
        {
            batch.draw(animationdown.getKeyFrame(elapsedTime, true), charX, charY);
        }


        batch.end();
    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        player.dispose();
        textureAtlas.dispose();
        font.dispose();
        car.dispose();
        bathroom.dispose();
        kitchen.dispose();
        garden.dispose();
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
    public void input(String text) {
        this.text = text;
    }

    @Override
    public void canceled() {
        text = "Canceled";
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, final int button) {

        touchX = screenX - sprite.getWidth()/2;
        touchY = Gdx.graphics.getHeight() - screenY - sprite.getHeight()/2;
        Gdx. app.log("touchx and touchY", touchX + " " + touchY);


        if(touchX > 289 && touchX < 331 && touchY > 562 && touchY < 666) {
            if (screen == 0) {

                int totalframe = (int)(Math.abs(charX - 269) + Math.abs(charY - 609));

                screen =-1;//transition

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        elapsedTime += Gdx.graphics.getDeltaTime();


                        currentFrame++;
                        if (currentFrame > 20) {
                            currentFrame = 1;
                        }

                        if (charX < 269) {
                            charX++;
                            animation = 1;
                        } else if (charY < 609) {
                            charY++;
                            animation = 3;
                        } else if (charX > 269) {
                            charX--;
                            animation = 2;
                        } else if (charY > 609) {
                            charY--;
                            animation = 4;
                        }
                        Gdx.app.log("charX and charY", charX + " " + charY);
                        //Gdx.app.log("ymove and ycount", ymove + " " + ycount);
                        if (charX == 269 && charY == 609) {
                            animation = 0;
                            screen = 1;
                        }
                    }
                }
                        , 0, 1 / 30.0f, totalframe);//delay, interval for every frame, repeat count
            }

        }
        //start the new else if here
        else if(touchX> 434 && touchX < 658 && touchY > 521 && touchY < 711) {
            animation = 2;
            if (screen == 0) {
                int totalframe = (int)(Math.abs(charX - 531) + Math.abs(charY - 584));

                screen = -1;//transition

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        elapsedTime += Gdx.graphics.getDeltaTime();


                        currentFrame++;
                        if (currentFrame > 20) {
                            currentFrame = 1;
                        }

                        if (charX < 531) {
                            charX++;
                            animation = 1;
                        } else if (charY < 584) {
                            charY++;
                            animation = 3;
                        }
                        else if (charX > 531) {
                            charX--;
                            animation = 2;
                        }
                        else if (charY > 584) {
                            charY--;
                            animation = 4;
                        }
                        Gdx.app.log("xmove and xcount", charX+ " " + charY);
                        //Gdx.app.log("ymove and ycount", ymove + " " + ycount);
                        if (charX == 531 && charY == 584) {
                            animation = 0;
                            screen = 2;
                        }
                    }
                }
                        , 0, 1 / 30.0f, totalframe);//delay, interval for every frame, repeat count
            }
        }
        //start the new else if here
        else if(touchX> 434 && touchX < 652 && touchY > 711 && touchY < 899) {
            animation = 3;
            if (screen == 0) {
                int totalframe = (int)(Math.abs(charX - 491) + Math.abs(charY - 778));

                screen = -1;//transition

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        elapsedTime += Gdx.graphics.getDeltaTime();


                        currentFrame++;
                        if (currentFrame > 20) {
                            currentFrame = 1;
                        }

                        if (charX < 491) {
                            charX++;
                            animation = 1;
                        } else if (charY < 778) {
                            charY++;
                            animation = 3;
                        }
                        else if (charX > 491) {
                            charX--;
                            animation = 2;
                        }
                        else if (charY > 778) {
                            charY--;
                            animation = 4;
                        }
                        Gdx.app.log("xmove and xcount", charX+ " " + charY);
                        //Gdx.app.log("ymove and ycount", ymove + " " + ycount);
                        if (charX == 491 && charY == 778) {
                            animation = 0;
                            screen = 3;
                        }
                    }
                }
                        , 0, 1 / 30.0f, totalframe);//delay, interval for every frame, repeat count
            }
        }
        //start the new else if here
        else if(touchX> 21 && touchX < 142 && touchY > 809 && touchY < 923) {
            animation = 4;
            if (screen == 0) {
                int totalframe = (int)(Math.abs(charX - 13) + Math.abs(charY - 879));

                screen = -1;//transition

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        elapsedTime += Gdx.graphics.getDeltaTime();


                        currentFrame++;
                        if (currentFrame > 20) {
                            currentFrame = 1;
                        }

                        if (charX < 13) {
                            charX++;
                            animation = 1;
                        } else if (charY < 879) {
                            charY++;
                            animation = 3;
                        }
                        else if (charX > 13) {
                            charX--;
                            animation = 2;
                        }
                        else if (charY > 879) {
                            charY--;
                            animation = 4;
                        }
                        Gdx.app.log("xmove and xcount", charX+ " " + charY);
                        //Gdx.app.log("ymove and ycount", ymove + " " + ycount);
                        if (charX == 13 && charY == 879) {
                            animation = 0;
                            screen = 4;
                        }
                    }
                }
                        , 0, 1 / 30.0f, totalframe);//delay, interval for every frame, repeat count
            }
        }

        if(screen == 0)
        {
            if(touchX > 553 && touchX < 653 && touchY > 1193 && touchY < 1255)
            {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }

        if (screen == 1)
        {
            if(touchX > 553 && touchX < 653 && touchY > 1193 && touchY < 1255)
            {
//				bg.dispose();
//				screen = 5;
//				animation = 5;
                //sprite.setPosition(8, 464);
                screen = 0;
            }
            else if(touchX > 386 && touchX< 427 && touchY>352 && touchY< 385)
            {
                waterConPorch=11;
            }
            else if(touchX > 386 && touchX< 427 && touchY>301 && touchY< 336)
            {
                waterConPorch=378;
            }

            waterCon = waterConBath + waterConGarden + waterConKitchen + waterConPorchSum;
            for(int i=0; i<1; i++)
            {
                waterConPorchSum = waterConPorchSum + waterConPorch;
            }
            Gdx.app.log("Water", Double.toString(waterCon) + " " + Double.toString(sum));

        }
        else if(screen == 2)
        {
            if(touchX > 553 && touchX < 653 && touchY > 1193 && touchY < 1255)
            {
                screen = 0;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 347 && touchY < 383)
            {
                waterConBath=9;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 302 && touchY < 335)
            {
                waterConBath=1;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 240 && touchY < 277)
            {
                waterConBath=113;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 190 && touchY < 226)
            {
                waterConBath=75;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 139 && touchY < 177)
            {
                waterConBath=9;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 88 && touchY < 126)
            {
                waterConBath=4.5;
            }
            waterCon = waterConBath + waterConGarden + waterConKitchen + waterConPorchSum;
            for(int i=0; i<1; i++)
            {
                waterConBathSum = waterConBathSum + waterConBath;
            }
            Gdx.app.log("Water", Double.toString(waterCon) + " " + Double.toString(sum));
        }
        else if(screen == 3)
        {
            if(touchX > 553 && touchX < 653 && touchY > 1193 && touchY < 1255)
            {
                screen = 0;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 352 && touchY < 386)
            {
                waterConKitchen=10.5;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 302 && touchY < 334)
            {
                waterConKitchen=2.3;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 244 && touchY < 277)
            {
                waterConKitchen=2;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 192 && touchY < 240)
            {
                waterConKitchen=5;
            }
            waterCon = waterConBath + waterConGarden + waterConKitchen + waterConPorchSum;
            for(int i=0; i<1; i++)
            {
                waterConKitchenSum = waterConKitchenSum + waterConKitchen;
            }
            Gdx.app.log("Water", Double.toString(waterCon) + " " + Double.toString(sum));
        }
        else if(screen == 4)
        {
            if(touchX > 553 && touchX < 653 && touchY > 1193 && touchY < 1255)
            {
                screen = 0;
            }
            else if(touchX > 455 && touchX < 497 && touchY > 393 && touchY < 347)
            {
                //waterConGarden=5;
            }
            else if(touchX > 660 && touchX < 698 && touchY > 299 && touchY < 339)
            {
                //waterConGarden=5;
            }
            waterCon = waterConBath + waterConGarden + waterConKitchen + waterConPorchSum;
            for(int i=0; i<1; i++)
            {
                waterConGardenSum = waterConGardenSum + waterConGarden;
            }
            Gdx.app.log("Water", Double.toString(waterCon) + " " + Double.toString(sum));
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
