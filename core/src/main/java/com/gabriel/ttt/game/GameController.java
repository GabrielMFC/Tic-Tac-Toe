
package com.gabriel.ttt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameController {

    private final GameBoard board;
    private final OrthographicCamera camera;

    private final Vector3 touchPosition = new Vector3();

    private int currentPlayer = GameBoard.CIRCLE;

    public GameController(
        GameBoard board,
        OrthographicCamera camera
    ) {
        this.board = board;
        this.camera = camera;
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

        if (x < GameBoard.BOARD_X ||
            x >= GameBoard.BOARD_X + GameBoard.SIZE * GameBoard.CELL_SIZE ||
            y < GameBoard.BOARD_Y ||
            y >= GameBoard.BOARD_Y + GameBoard.SIZE * GameBoard.CELL_SIZE) {
            return;
        }

        int col = (int) ((x - GameBoard.BOARD_X) / GameBoard.CELL_SIZE);
        int row = (int) ((y - GameBoard.BOARD_Y) / GameBoard.CELL_SIZE);

        if (board.placePiece(row, col, currentPlayer)) {
            currentPlayer = currentPlayer == GameBoard.CIRCLE
                ? GameBoard.X
                : GameBoard.CIRCLE;
        }
    }

    public GameBoard getBoard() {
        return board;
    }
}
