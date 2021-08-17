package client.apps.tweet.view;

import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import shared.request.NewTweetRequest;
import shared.util.ImageSerializer;

import java.awt.*;
import java.io.File;

public class NewTweetRootController extends BasicController {

    private int mainTweetId;
    private File selectedFile;

    @FXML
    private TextArea tweetTextArea;

    @FXML
    private Button imageButton;

    @FXML
    private Button sendButton;

    @FXML
    void sendButtonClicked(ActionEvent event) {
        getListener().listen(new NewTweetRequest(
                mainTweetId,
                tweetTextArea.getText(),
                (selectedFile != null) ? ImageSerializer.encodeFileToBase64Binary(selectedFile) : null
        ));
        ViewSwitcher.getInstance().switchTo(new Page(View.PERSONALPAGE, -1));
    }

    @FXML
    void imageButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose tweet pic");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("image", "*.png")
        );
        selectedFile = fileChooser.showOpenDialog(imageButton.getScene().getWindow());
    }

    public int getMainTweetId() {
        return mainTweetId;
    }

    public void setMainTweetId(int mainTweetId) {
        this.mainTweetId = mainTweetId;
    }
}
