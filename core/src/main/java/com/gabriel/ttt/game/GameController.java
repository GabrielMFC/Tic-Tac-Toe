
package com.gabriel.ttt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameController {

    private final BoardLayout layout;
    private final GameBoard board;
    private final OrthographicCamera camera;
    private final Vector3 touchPosition = new Vector3();

    private int currentPlayer = GameBoard.CIRCLE;
    private int winner = GameBoard.EMPTY;
    private boolean gameOver = false;

    public GameController(GameBoard board, OrthographicCamera camera,
                          BoardLayout layout) {
        this.board = board;
        this.camera = camera;
        this.layout = layout;
    }

    public void update() {
        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPosition);

        if (gameOver) {
            return;
        }

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

        if (board.placePiece(row, col, currentPlayer)) {
            checkWinner();

            if (!gameOver) {
                if (board.isFull()) {
                    gameOver = true;
                } else {
                    currentPlayer = currentPlayer == GameBoard.CIRCLE
                        ? GameBoard.X
                        : GameBoard.CIRCLE;
                }
            }
        }
    }

    private void checkWinner() {

        // Verifica linhas e colunas.
        for (int i = 0; i < GameBoard.SIZE; i++) {

            if (board.getCell(i, 0) != GameBoard.EMPTY &&
                board.getCell(i, 0) == board.getCell(i, 1) &&
                board.getCell(i, 1) == board.getCell(i, 2)) {

                winner = board.getCell(i, 0);
                gameOver = true;
                return;
            }

            if (board.getCell(0, i) != GameBoard.EMPTY &&
                board.getCell(0, i) == board.getCell(1, i) &&
                board.getCell(1, i) == board.getCell(2, i)) {

                winner = board.getCell(0, i);
                gameOver = true;
                return;
            }
        }

        // Diagonal principal.
        if (board.getCell(0, 0) != GameBoard.EMPTY &&
            board.getCell(0, 0) == board.getCell(1, 1) &&
            board.getCell(1, 1) == board.getCell(2, 2)) {

            winner = board.getCell(0, 0);
            gameOver = true;
            return;
        }

        // Diagonal secundária.
        if (board.getCell(0, 2) != GameBoard.EMPTY &&
            board.getCell(0, 2) == board.getCell(1, 1) &&
            board.getCell(1, 1) == board.getCell(2, 0)) {

            winner = board.getCell(0, 2);
            gameOver = true;
        }
    }

    public void restart() {
        board.reset();

        currentPlayer = GameBoard.CIRCLE;
        winner = GameBoard.EMPTY;
        gameOver = false;
    }

    public int getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isDraw() {
        return gameOver && winner == GameBoard.EMPTY;
    }

    public GameBoard getBoard() {
        return board;
    }
}
