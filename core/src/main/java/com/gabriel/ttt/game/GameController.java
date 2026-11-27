package com.gabriel.ttt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import com.gabriel.ttt.rest.GameClient;

import java.io.IOException;

public class GameController {

    private final BoardLayout layout;
    private final OrthographicCamera camera;
    private final GameBoard board;
    private final GameClient gameClient;

    private final Vector3 touchPosition = new Vector3();

    private int myPlayer = GameBoard.EMPTY;
    private int currentPlayer = GameBoard.CIRCLE;
    private int winner = GameBoard.EMPTY;

    private boolean gameOver = false;
    private boolean gameStarted = false;

    private String status = "Connecting...";

    public GameController(
            OrthographicCamera camera,
            BoardLayout layout,
            GameBoard board,
            GameClient gameClient
    ) {
        this.camera = camera;
        this.layout = layout;
        this.board = board;
        this.gameClient = gameClient;
    }

    public void update() {

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        if (!isMyTurn()) {
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

        sendMove(row, col);
    }

    private void sendMove(int row, int col) {

        try {
            gameClient.send("MOVE|" + row + "|" + col);
        } catch (IOException e) {
            status = "Connection error";
            System.err.println("Erro ao enviar jogada: " + e.getMessage());
        }
    }

    public void handleServerMessage(String message) {

        // As mensagens chegam pela thread de rede.
        // A atualização do jogo deve ocorrer na thread do LibGDX.
        Gdx.app.postRunnable(() -> processServerMessage(message));
    }

    private void processServerMessage(String message) {

        if (message.startsWith("WELCOME|")) {

            String[] parts = message.split("\\|");
            myPlayer = Integer.parseInt(parts[1]);

            status = myPlayer == GameBoard.CIRCLE
                ? "You are Circle"
                : "You are X";

        } else if ("WAITING".equals(message)) {

            gameStarted = false;
            status = "Waiting for opponent...";

        } else if ("START".equals(message)) {

            gameStarted = true;
            status = "Game started!";

        } else if (message.startsWith("STATE|")) {

            applyState(message);

        } else if (message.startsWith("ERROR|")) {

            status = "Server: " + message.substring(6);

        } else if ("FULL".equals(message)) {

            status = "Server is full";

        } else {
            System.out.println("Servidor: " + message);
        }
    }

    private void applyState(String message) {

        String[] parts = message.split("\\|");

        if (parts.length != 5) {
            System.err.println("Estado inválido recebido: " + message);
            return;
        }

        try {
            currentPlayer = Integer.parseInt(parts[1]);
            winner = Integer.parseInt(parts[2]);
            gameOver = Boolean.parseBoolean(parts[3]);

            String[] cells = parts[4].split(",");

            if (cells.length != GameBoard.SIZE * GameBoard.SIZE) {
                return;
            }

            int index = 0;

            for (int row = 0; row < GameBoard.SIZE; row++) {
                for (int col = 0; col < GameBoard.SIZE; col++) {
                    board.setCell(
                        row,
                        col,
                        Integer.parseInt(cells[index++])
                    );
                }
            }

            if (gameOver) {
                if (winner == GameBoard.EMPTY) {
                    status = "It's a draw!";
                } else if (winner == myPlayer) {
                    status = "You win!";
                } else {
                    status = "You lose!";
                }
            } else if (currentPlayer == myPlayer) {
                status = "Your turn";
            } else {
                status = "Opponent's turn";
            }

        } catch (NumberFormatException e) {
            System.err.println("Erro ao interpretar estado: " + message);
        }
    }

    public void restart() {

        try {
            gameClient.send("RESTART");
        } catch (IOException e) {
            status = "Connection error";
        }
    }

    public boolean isMyTurn() {
        return gameStarted
            && !gameOver
            && myPlayer != GameBoard.EMPTY
            && currentPlayer == myPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getWinner() {
        return winner;
    }

    public String getStatus() {
        return status;
    }

    public GameBoard getBoard() {
        return board;
    }
}
