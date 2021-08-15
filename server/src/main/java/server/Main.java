package server;

import org.apache.logging.log4j.LogManager;
import server.controller.SocketManager;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args) throws IOException {
        LogManager.getLogger(Main.class).info("server started  " + Instant.now());

        SocketManager socketManager = new SocketManager();
        socketManager.start();
    }
}
