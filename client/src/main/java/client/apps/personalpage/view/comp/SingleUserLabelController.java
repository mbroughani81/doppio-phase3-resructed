package client.apps.personalpage.view.comp;

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
        ImageView view = new ImageView(new Image("squScreenshot (91).png"));
        view.setFitWidth(50);
        view.setFitHeight(50);
        profileLabel.setGraphic(view);
    }
}
