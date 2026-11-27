package com.gabriel.ttt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import com.gabriel.ttt.game.GameBoard;
import com.gabriel.ttt.game.GameController;
import com.gabriel.ttt.game.GameUI;
import com.gabriel.ttt.game.ShapeDrawer;
import com.gabriel.ttt.game.BoardLayout;
import com.gabriel.ttt.rest.GameClient;

import java.io.IOException;

public class Main extends ApplicationAdapter {

    private OrthographicCamera camera;

    private GameBoard board;
    private GameController controller;
    private ShapeDrawer drawer;
    private GameUI gameUI;
    private BoardLayout layout;

    private GameClient gameClient;

    @Override
    public void create() {

        camera = new OrthographicCamera();

        camera.setToOrtho(
            false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        camera.update();

        layout = new BoardLayout(camera);

        board = new GameBoard();

        gameClient = new GameClient();

        controller = new GameController(
            camera,
            layout,
            board,
            gameClient
        );

        gameClient.setMessageListener(controller::handleServerMessage);

        drawer = new ShapeDrawer(layout);

        gameUI = new GameUI(controller);

        drawer.setProjectionMatrix(camera.combined);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(gameUI.getStage());

        Gdx.input.setInputProcessor(inputMultiplexer);

        try {
            gameClient.connect("192.168.0.104", 8080);
        } catch (IOException e) {
            System.err.println("Não foi possível conectar ao servidor.");
            e.printStackTrace();
        }
    }

    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false, width, height);
        camera.update();

        layout.update(camera);

        drawer.setProjectionMatrix(camera.combined);

        gameUI.getStage().getViewport().update(width, height, true);
    }

    @Override
    public void render() {

        ScreenUtils.clear(Color.BLACK);

        controller.update();

        drawer.render(board);

        gameUI.update();
        gameUI.render();
    }

    @Override
    public void dispose() {

        if (gameClient != null) {
            gameClient.disconnect();
        }

        drawer.dispose();
        gameUI.dispose();
    }
}
