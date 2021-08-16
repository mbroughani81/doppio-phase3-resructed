package shared.model;

import shared.datatype.PmVerdict;

public class SinglePm {

    private int pmId;
    private int userId;
    private PmVerdict pmVerdict;
    private String text;

    public SinglePm(int pmId, int userId, PmVerdict pmVerdict, String text) {
        this.pmId = pmId;
        this.userId = userId;
        this.pmVerdict = pmVerdict;
        this.text = text;
    }

    public int getPmId() {
        return pmId;
    }

    public void setPmId(int pmId) {
        this.pmId = pmId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PmVerdict getPmVerdict() {
        return pmVerdict;
    }

    public void setPmVerdict(PmVerdict pmVerdict) {
        this.pmVerdict = pmVerdict;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
