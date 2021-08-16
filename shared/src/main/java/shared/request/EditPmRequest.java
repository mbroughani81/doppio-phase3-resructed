package shared.request;

import shared.response.Response;

public class EditPmRequest extends Request {

    private int pmId;
    private String text;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.editPm(this);
    }

    public EditPmRequest(int pmId, String text) {
        this.pmId = pmId;
        this.text = text;
    }

    public int getPmId() {
        return pmId;
    }

    public void setPmId(int pmId) {
        this.pmId = pmId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
