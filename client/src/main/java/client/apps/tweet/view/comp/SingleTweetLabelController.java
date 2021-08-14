package client.apps.tweet.view.comp;

import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleTweetLabelController extends BasicController implements Initializable {

    private int tweetId;
    private String text;

    @FXML
    private Label profileLabel;

    @FXML
    private Label tweetTextLabel;

    @FXML
    void profileLabelClicked(MouseEvent event) {
    }

    @FXML
    void tweetTextLabelClicked(MouseEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.TWEETPAGE, tweetId));
    }


    public SingleTweetLabelController(int tweetId, String text) {
        this.tweetId = tweetId;
        this.text = text;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweetTextLabel.setText(text);
        profileLabel.setText("");
        ImageView view = new ImageView(new Image("squScreenshot (91).png"));
        view.setFitWidth(40);
        view.setFitHeight(40);
        profileLabel.setGraphic(view);
    }
}
