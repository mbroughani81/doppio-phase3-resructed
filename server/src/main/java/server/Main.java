package server;

import org.apache.logging.log4j.LogManager;
import server.controller.SocketManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        LogManager.getLogger(Main.class).trace("Server Started");

        SocketManager socketManager = new SocketManager();
        socketManager.start();
    }
}
