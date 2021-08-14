package client;

import client.core.DoppioApp;
import javafx.application.Application;
import org.apache.logging.log4j.LogManager;

public class Main {
    public static void main(String[] args) {
        LogManager.getLogger(Main.class).trace("Client started");

        Application.launch(DoppioApp.class, args);
    }
}
