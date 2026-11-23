
package com.gabriel.ttt.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class BoardLayout {

    private static final float CELL_SIZE = 100f;

    private float boardX;
    private float boardY;

    public BoardLayout(OrthographicCamera camera) {
        update(camera);
    }

    public void update(OrthographicCamera camera) {
        float boardWidth = GameBoard.SIZE * CELL_SIZE;
        float boardHeight = GameBoard.SIZE * CELL_SIZE;

        boardX = (camera.viewportWidth - boardWidth) / 2f;
        boardY = (camera.viewportHeight - boardHeight) / 2f;
    }

    public float getBoardX() {
        return boardX;
    }

    public float getBoardY() {
        return boardY;
    }

    public float getCellSize() {
        return CELL_SIZE;
    }
}
