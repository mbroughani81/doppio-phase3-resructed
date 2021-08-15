package client.apps.chat.view.comp;

import client.datatype.BasicController;
import client.dbcontroller.FileModelController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import shared.model.SinglePm;
import shared.request.ExplorerSearchIdRequest;
import shared.request.GetProfilePicRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class HypSinglePmLabelController extends BasicController implements Initializable {

    private SinglePm singlePm;

    @FXML
    private Label profileLabel;

    @FXML
    private Label pmTextLabel;

    @FXML
    void profileLabelClicked(MouseEvent event) {
        getListener().listen(new ExplorerSearchIdRequest(singlePm.getUserId()));
    }


    public HypSinglePmLabelController(SinglePm singlePm) {
        this.singlePm = singlePm;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pmTextLabel.setText(singlePm.getUserId() + " :\n" + singlePm.getText());
        switch (singlePm.getPmVerdict()) {
            case OFFLINE -> pmTextLabel.setStyle(pmTextLabel.getStyle() + "-fx-background-color: #f54949;");
            case SENT -> pmTextLabel.setStyle(pmTextLabel.getStyle() + "-fx-background-color: #ed4ca2;");
            case NOTSEEN -> pmTextLabel.setStyle(pmTextLabel.getStyle() + "-fx-background-color: #6b5ffa;");
            case SEEN -> pmTextLabel.setStyle(pmTextLabel.getStyle() + "-fx-background-color: #4ced54;");
        }
        profileLabel.setText("");
        ImageView view;
        view = new ImageView(new Image("iliya1.png"));;
        view.setFitWidth(40);
        view.setFitHeight(40);
        profileLabel.setGraphic(view);
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
//            getListener().listen(new NullRequest("i should have asked for new profile"));
            if (FileModelController.canUpdate("profilepics/" + singlePm.getUserId() + ".jpg")) {
                getListener().listen(new GetProfilePicRequest(singlePm.getUserId()));
            }
        };
    }
}
