package client.apps.chat.view.comp;

import client.config.apps.chat.SimpleTextConfig;
import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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
        SimpleTextConfig simpleTextConfig = new SimpleTextConfig();
        text.setText(pmText);
        text.setFont(new Font(simpleTextConfig.getFontSize()));
        text.setFill(Paint.valueOf(simpleTextConfig.getColor()));
    }
}
