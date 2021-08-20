package shared.request;

import shared.datatype.Privacy;
import shared.response.Response;

public class ChangePrivacyRequest extends Request {

    private Privacy privacy;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changePrivacy(this);
    }

    public ChangePrivacyRequest(Privacy privacy) {
        this.privacy = privacy;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }
}
