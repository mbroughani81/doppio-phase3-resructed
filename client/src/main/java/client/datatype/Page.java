package client.datatype;

public class Page {
    private View view;
    private int id;

    public Page(View view, int id) {
        this.view = view;
        this.id = id;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
