package shared.request;

import shared.response.Response;

public class GetPmPicRequest extends Request {

    private int pmId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getPmPic(this);
    }

    public GetPmPicRequest(int pmId) {
        this.pmId = pmId;
    }

    public int getPmId() {
        return pmId;
    }

    public void setPmId(int pmId) {
        this.pmId = pmId;
    }
}
