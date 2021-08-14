package shared.response;

public class CheckConnectionResponse implements Response {
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.socketConnectionInUp(this);
    }
}
