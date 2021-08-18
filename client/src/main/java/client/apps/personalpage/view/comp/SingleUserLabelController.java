package client.apps.personalpage.view.comp;

import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import shared.request.ExplorerSearchIdRequest;
import shared.request.ExplorerSearchRequest;
import shared.request.GetProfilePicRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SingleUserLabelController extends BasicController implements Initializable {

    private int userId;

    @FXML
    private Label profileLabel;

    @FXML
    void profileLabelClicked(MouseEvent event) {
        getListener().listen(new ExplorerSearchIdRequest(userId));
    }

    public SingleUserLabelController(int userId) {
        this.userId = userId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profileLabel.setText("");
        if (DoppioApp.getFileModelController().profileExists(userId)) {
            ImageView view;
            File img = new File(DoppioApp.getFileModelController().getProfilePicPath(
                    userId
            ));
            InputStream isImage = null;
            try {
                isImage = new FileInputStream(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            view = new ImageView(new Image(isImage));
            view.setFitWidth(40);
            view.setFitHeight(40);
            profileLabel.setGraphic(view);
        } else {
            ImageView view;
            view = new ImageView(new Image("iliya1.png"));
            view.setFitWidth(40);
            view.setFitHeight(40);
            profileLabel.setGraphic(view);
        }
        // ask for image to load in furthur openings
        getListener().listen(new GetProfilePicRequest(userId));
    }
}
