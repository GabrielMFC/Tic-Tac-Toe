
package com.gabriel.ttt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gabriel.ttt.game.GameBoard;
import com.gabriel.ttt.game.GameController;
import com.gabriel.ttt.game.ShapeDrawer;

public class Main extends ApplicationAdapter {

    private OrthographicCamera camera;

    private GameBoard board;
    private GameController controller;
    private ShapeDrawer drawer;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(
            false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );
        camera.update();

        board = new GameBoard();
        controller = new GameController(board, camera);
        drawer = new ShapeDrawer();

        drawer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();

        drawer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

        controller.update();
        drawer.render(board);
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }
}
