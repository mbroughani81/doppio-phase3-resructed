package shared.request;

import shared.datatype.LastSeenPrivacy;
import shared.response.Response;

public class ChangeLastseenprivacyRequest extends Request {

    private LastSeenPrivacy lastSeenPrivacy;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changeLastseen(this);
    }

    public ChangeLastseenprivacyRequest(LastSeenPrivacy lastSeenPrivacy) {
        this.lastSeenPrivacy = lastSeenPrivacy;
    }

    public LastSeenPrivacy getLastSeenPrivacy() {
        return lastSeenPrivacy;
    }

    public void setLastSeenPrivacy(LastSeenPrivacy lastSeenPrivacy) {
        this.lastSeenPrivacy = lastSeenPrivacy;
    }
}
