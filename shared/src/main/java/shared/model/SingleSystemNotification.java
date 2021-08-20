package shared.model;

public class SingleSystemNotification {

    private String text;

    public SingleSystemNotification(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
