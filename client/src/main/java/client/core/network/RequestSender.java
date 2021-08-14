package client.core.network;

import shared.request.Request;
import shared.response.Response;

public interface RequestSender {
    Response send(Request request);
    void close();
}
