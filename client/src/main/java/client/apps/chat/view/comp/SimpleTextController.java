package client.apps.chat.view.comp;

import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SimpleTextController extends BasicController implements Initializable {

    private String pmText;

    @FXML
    private Text text;

    public SimpleTextController(String pmText) {
        this.pmText = pmText;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setText(pmText);
    }
}
