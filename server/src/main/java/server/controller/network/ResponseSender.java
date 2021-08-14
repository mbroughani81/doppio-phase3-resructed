package server.controller.network;

import shared.request.Request;
import shared.response.Response;

public interface ResponseSender {
    Request getRequest();
    void sendResponse(Response response);
    void close();
}