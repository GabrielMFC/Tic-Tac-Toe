
package com.gabriel.ttt.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class ShapeDrawer {

    private final ShapeRenderer shape;

    public ShapeDrawer() {
        shape = new ShapeRenderer();
    }

    public void setProjectionMatrix(Matrix4 matrix) {
        shape.setProjectionMatrix(matrix);
    }

    public void render(GameBoard board) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);

        renderBoard();

        for (int row = 0; row < GameBoard.SIZE; row++) {
            for (int col = 0; col < GameBoard.SIZE; col++) {

                float x = GameBoard.BOARD_X
                    + col * GameBoard.CELL_SIZE
                    + GameBoard.CELL_SIZE / 2;

                float y = GameBoard.BOARD_Y
                    + row * GameBoard.CELL_SIZE
                    + GameBoard.CELL_SIZE / 2;

                int cell = board.getCell(row, col);

                if (cell == GameBoard.CIRCLE) {
                    renderCircle(x, y);
                } else if (cell == GameBoard.X) {
                    renderX(x, y);
                }
            }
        }

        shape.end();
    }

    private void renderBoard() {
        for (int i = 1; i < GameBoard.SIZE; i++) {

            float position = GameBoard.BOARD_X
                + i * GameBoard.CELL_SIZE;

            shape.line(
                position,
                GameBoard.BOARD_Y,
                position,
                GameBoard.BOARD_Y + GameBoard.SIZE * GameBoard.CELL_SIZE
            );

            position = GameBoard.BOARD_Y
                + i * GameBoard.CELL_SIZE;

            shape.line(
                GameBoard.BOARD_X,
                position,
                GameBoard.BOARD_X + GameBoard.SIZE * GameBoard.CELL_SIZE,
                position
            );
        }
    }

    private void renderCircle(float x, float y) {
        shape.circle(x, y, 35);
    }

    private void renderX(float x, float y) {
        shape.line(x - 35, y - 35, x + 35, y + 35);
        shape.line(x + 35, y - 35, x - 35, y + 35);
    }

    public void dispose() {
        shape.dispose();
    }
}
