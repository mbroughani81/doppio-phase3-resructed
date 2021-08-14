package shared.response;

public class NullResponse implements Response {

    private String text;

    @Override
    public void handle(ResponseHandler responseHandler) {
        System.out.println("null response is handele! text is " + text);
    }

    public NullResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
