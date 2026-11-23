
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

public class Main extends ApplicationAdapter {

    private OrthographicCamera camera;

    private GameBoard board;
    private GameController controller;
    private ShapeDrawer drawer;
    private GameUI gameUI;
    private BoardLayout layout;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );
        camera.update();
        layout = new BoardLayout(camera);

        board = new GameBoard();
        controller = new GameController(board, camera, layout);
        drawer = new ShapeDrawer(layout);
        gameUI = new GameUI(controller);

        drawer.setProjectionMatrix(camera.combined);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gameUI.getStage());

        Gdx.input.setInputProcessor(inputMultiplexer);
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
        drawer.dispose();
        gameUI.dispose();
    }
}
