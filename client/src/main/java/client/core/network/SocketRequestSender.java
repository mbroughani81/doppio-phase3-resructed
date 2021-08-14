package client.core.network;

import client.core.DoppioApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.gson.Deserializer;
import shared.gson.LocalDateTimeDeserializer;
import shared.gson.LocalDateTimeSerializer;
import shared.gson.Serializer;
import shared.model.AuthToken;
import shared.model.SessionModel;
import shared.request.ChangeNameRequest;
import shared.request.LoginRequest;
import shared.request.Request;
import shared.response.DisconnectResponse;
import shared.response.LoginResponse;
import shared.response.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketRequestSender implements RequestSender {
    private final Socket socket;
    private final PrintWriter output;
    private final Scanner input;
    private final Gson gson;

    private boolean isDead = false;

    public SocketRequestSender(Socket socket) throws IOException {
        this.socket = socket;
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new Scanner(socket.getInputStream());
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Response.class, new Deserializer<>())
                .registerTypeAdapter(Request.class, new Serializer<>())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
    }

    @Override
    public Response send(Request request) {
        SessionModel session = DoppioApp.getSessionModelController().getSession();
        AuthToken authToken = ((session == null) ? null : session.getAuthToken());
        request.setAuthToken(authToken);


        String requestString = gson.toJson(request, Request.class);
        output.println(requestString);

        try {
            String responseString = input.nextLine();
            return gson.fromJson(responseString, Response.class);
        } catch (NoSuchElementException e) {
            this.isDead = true;
            return new DisconnectResponse();
        }
    }

    @Override
    public void close() {

    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
