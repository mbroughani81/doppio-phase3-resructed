package shared.response;

public class GetPmPicResponse implements Response {

    private int pmId;
    private String imageString;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updatePmPic(this);
    }

    public GetPmPicResponse(int pmId, String imageString) {
        this.pmId = pmId;
        this.imageString = imageString;
    }

    public int getPmId() {
        return pmId;
    }

    public void setPmId(int pmId) {
        this.pmId = pmId;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
