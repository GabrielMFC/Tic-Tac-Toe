package com.gabriel.ttt.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameUI {

    private final Stage stage;
    private final GameController controller;

    private final Skin skin;
    private final BitmapFont font;
    private final Texture buttonTexture;

    private final Label statusLabel;
    private final TextButton restartButton;

    public GameUI(GameController controller) {

        this.controller = controller;

        stage = new Stage(new ScreenViewport());

        font = new BitmapFont();
        buttonTexture = createButtonTexture();

        skin = new Skin();

        TextureRegionDrawable background =
            new TextureRegionDrawable(new TextureRegion(buttonTexture));

        TextButton.TextButtonStyle buttonStyle =
            new TextButton.TextButtonStyle();

        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.up = background.tint(Color.valueOf("353535"));
        buttonStyle.down = background.tint(Color.valueOf("606060"));

        skin.add("default", buttonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        skin.add("default", labelStyle);

        statusLabel = new Label("Connecting...", skin);
        restartButton = new TextButton("RESTART", skin);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.restart();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.bottom().center();

        table.add(statusLabel).padBottom(12);
        table.row();

        table.add(restartButton)
            .size(160, 55)
            .padBottom(30);

        restartButton.setVisible(false);

        stage.addActor(table);
    }

    private Texture createButtonTexture() {

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return texture;
    }

    public void update() {

        statusLabel.setText(controller.getStatus());

        restartButton.setVisible(controller.isGameOver());
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
        buttonTexture.dispose();
    }
}
