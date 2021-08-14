package shared.response;

public class DisconnectResponse implements Response {
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.socketConnectionIsDown(this);
    }
}
