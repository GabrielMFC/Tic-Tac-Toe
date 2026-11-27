package com.gabriel.ttt.rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class GameClient {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private Thread listenerThread;

    private volatile boolean connected = false;

    private Consumer<String> messageListener;

    public void setMessageListener(Consumer<String> listener) {
        this.messageListener = listener;
    }

    public void connect(String host, int port) throws IOException {

        if (connected) {
            return;
        }

        socket = new Socket(host, port);

        reader = new BufferedReader(
            new InputStreamReader(
                socket.getInputStream(),
                StandardCharsets.UTF_8
            )
        );

        writer = new BufferedWriter(
            new OutputStreamWriter(
                socket.getOutputStream(),
                StandardCharsets.UTF_8
            )
        );

        connected = true;

        listenerThread = new Thread(this::listen);
        listenerThread.setName("GameClient-Listener");
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public synchronized void send(String message) throws IOException {

        if (!connected) {
            throw new IOException("Cliente não está conectado.");
        }

        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private void listen() {

        try {
            String message;

            while (connected && (message = reader.readLine()) != null) {

                Consumer<String> listener = messageListener;

                if (listener != null) {
                    listener.accept(message);
                } else {
                    System.out.println("Servidor: " + message);
                }
            }

        } catch (IOException e) {
            if (connected) {
                System.err.println(
                    "Erro na conexão: " + e.getMessage()
                );
            }
        } finally {
            disconnect();
        }
    }

    public synchronized void disconnect() {

        connected = false;

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println(
                "Erro ao fechar conexão: " + e.getMessage()
            );
        }
    }

    public boolean isConnected() {
        return connected
            && socket != null
            && socket.isConnected()
            && !socket.isClosed();
    }
}
