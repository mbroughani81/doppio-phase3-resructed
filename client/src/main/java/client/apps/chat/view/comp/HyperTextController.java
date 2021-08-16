package client.apps.chat.view.comp;

import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HyperTextController extends BasicController implements Initializable {

    private HyperType type;
    private String val;
    private String pmText;


    @FXML
    private Text text;

    @FXML
    void textClicked(MouseEvent event) {
        System.out.println(type.getVal() + ":type " + val + ":val" + " is clicked!");
    }


    public HyperTextController(String pmText, HyperType type, String val) {
        this.pmText = pmText;
        this.type = type;
        this.val = val;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setText(pmText);
    }
    //    private HyperType
}

enum HyperType {
    CHAT("chat"),
    TWEET("tweet"),
    JOINGROUP("join"),
    UNDEFINED("");

    private String val;

    HyperType(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
