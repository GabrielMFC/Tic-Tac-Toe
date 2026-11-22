package com.gabriel.ttt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shape;
    private OrthographicCamera camera;

    @Override
    public void create() {
        shape = new ShapeRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        shape.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();

        shape.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float gridSize = 300f;
        float thickness = 5f;

        float x = (screenWidth - gridSize) / 2f;
        float y = (screenHeight - gridSize) / 2f;

        ScreenUtils.clear(Color.BLACK);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Verticais
        shape.rect(x + 100 - thickness / 2, y, thickness, gridSize);
        shape.rect(x + 200 - thickness / 2, y, thickness, gridSize);

        // Horizontais
        shape.rect(x, y + 100 - thickness / 2, gridSize, thickness);
        shape.rect(x, y + 200 - thickness / 2, gridSize, thickness);

        shape.end();
    }

    @Override
    public void dispose() {
        shape.dispose();
    }
}
