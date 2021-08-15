package client.apps.chat.view.comp;

import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HyperTextController extends BasicController implements Initializable {

    private String pmText;

    @FXML
    private Text text;

    @FXML
    void textClicked(MouseEvent event) {
        System.out.println(pmText + " is clicked!");
    }


    public HyperTextController(String pmText) {
        this.pmText = pmText;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setText(pmText);
    }
    //    private HyperType
}

enum HyperType {
    CHAT,
    TWEET,
    JOINGROUP;
}
