
package com.gabriel.ttt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameController {

    private final BoardLayout layout;
    private final OrthographicCamera camera;
    private final Vector3 touchPosition = new Vector3();

    public GameController(
            OrthographicCamera camera,
            BoardLayout layout
    ) {
        this.camera = camera;
        this.layout = layout;
    }

    public void update() {

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        touchPosition.set(
            Gdx.input.getX(),
            Gdx.input.getY(),
            0
        );

        camera.unproject(touchPosition);

        float x = touchPosition.x;
        float y = touchPosition.y;

        float boardX = layout.getBoardX();
        float boardY = layout.getBoardY();
        float cellSize = layout.getCellSize();

        float boardWidth = GameBoard.SIZE * cellSize;
        float boardHeight = GameBoard.SIZE * cellSize;

        if (x < boardX || x >= boardX + boardWidth ||
            y < boardY || y >= boardY + boardHeight) {
            return;
        }

        int col = (int) ((x - boardX) / cellSize);
        int row = (int) ((y - boardY) / cellSize);

        onCellClicked(row, col);
    }

    private void onCellClicked(int row, int col) {
        // Futuramente enviaremos essa jogada ao servidor.
    }
}
