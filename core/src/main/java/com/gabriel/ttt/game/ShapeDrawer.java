
package com.gabriel.ttt.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class ShapeDrawer {

    private static final float SYMBOL_SIZE = 45f;
    private static final float CIRCLE_RADIUS = 45f;

    private final BoardLayout layout;
    private final ShapeRenderer shape;

    public ShapeDrawer(BoardLayout layout) {
        this.layout = layout;
        shape = new ShapeRenderer();
    }

    public void setProjectionMatrix(Matrix4 matrix) {
        shape.setProjectionMatrix(matrix);
    }

    public void render(GameBoard board) {
        float cellSize = layout.getCellSize();

        float boardWidth = GameBoard.SIZE * cellSize;
        float boardHeight = GameBoard.SIZE * cellSize;

        float boardX = layout.getBoardX();
        float boardY = layout.getBoardY();

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);

        renderBoard(boardX, boardY, boardWidth, boardHeight);

        for (int row = 0; row < GameBoard.SIZE; row++) {
            for (int col = 0; col < GameBoard.SIZE; col++) {

                float x = boardX + col * cellSize + cellSize / 2f;
                float y = boardY + row * cellSize + cellSize / 2f;

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

    private void renderBoard(float boardX, float boardY,
                             float boardWidth, float boardHeight) {

        float cellSize = layout.getCellSize();

        for (int i = 1; i < GameBoard.SIZE; i++) {

            float x = boardX + i * cellSize;
            float y = boardY + i * cellSize;

            // Linhas verticais
            shape.line(
                x, boardY,
                x, boardY + boardHeight
            );

            // Linhas horizontais
            shape.line(
                boardX, y,
                boardX + boardWidth, y
            );
        }
    }

    private void renderCircle(float x, float y) {
        shape.circle(x, y, CIRCLE_RADIUS, 64);
    }

    private void renderX(float x, float y) {
        shape.line(
            x - SYMBOL_SIZE, y - SYMBOL_SIZE,
            x + SYMBOL_SIZE, y + SYMBOL_SIZE
        );

        shape.line(
            x + SYMBOL_SIZE, y - SYMBOL_SIZE,
            x - SYMBOL_SIZE, y + SYMBOL_SIZE
        );
    }

    public void dispose() {
        shape.dispose();
    }
}
