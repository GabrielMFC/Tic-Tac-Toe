
package com.gabriel.ttt.game;

public class GameBoard {

    public static final int SIZE = 3;
    public static final float CELL_SIZE = 100;

    public static final int EMPTY = 0;
    public static final int CIRCLE = 1;
    public static final int X = 2;

    private final int[][] board = new int[SIZE][SIZE];

    public boolean placePiece(int row, int col, int player) {
        if (!isValidPosition(row, col)) {
            return false;
        }

        if (board[row][col] != EMPTY) {
            return false;
        }

        board[row][col] = player;
        return true;
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE
            && col >= 0 && col < SIZE;
    }


    public boolean isFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    public void reset() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = EMPTY;
            }
        }
    }

}
