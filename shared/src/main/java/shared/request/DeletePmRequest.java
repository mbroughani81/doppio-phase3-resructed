package shared.request;

import shared.response.Response;

public class DeletePmRequest extends Request {

    private int pmId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.deletePm(this);
    }

    public DeletePmRequest(int pmId) {
        this.pmId = pmId;
    }

    public int getPmId() {
        return pmId;
    }

    public void setPmId(int pmId) {
        this.pmId = pmId;
    }
}
