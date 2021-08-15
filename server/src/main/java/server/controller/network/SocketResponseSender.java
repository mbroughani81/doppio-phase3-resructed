package server.controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import shared.gson.Deserializer;
import shared.gson.LocalDateTimeDeserializer;
import shared.gson.LocalDateTimeSerializer;
import shared.gson.Serializer;
import shared.request.Request;
import shared.response.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class SocketResponseSender implements ResponseSender {
    private final Socket socket;
    private final PrintWriter output;
    private final Scanner input;
    private final Gson gson;

    public SocketResponseSender(Socket socket) throws IOException {
        this.socket = socket;
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new Scanner(socket.getInputStream());
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Request.class, new Deserializer<>())
                .registerTypeAdapter(Response.class, new Serializer<>())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
    }

    @Override
    public Request getRequest() {
        try {
            String requestString = input.nextLine();
            return gson.fromJson(requestString, Request.class);
        } catch (Exception e) {
            LogManager.getLogger(SocketResponseSender.class).error("error with getting requests");
        }
        return null;
    }

    @Override
    public void sendResponse(Response response) {
        output.println(gson.toJson(response, Response.class));
    }

    @Override
    public void close() {
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
