package client.apps.chat.view.comp;

import client.core.DoppioApp;
import client.datatype.BasicController;
import client.dbcontroller.FileModelController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import shared.datatype.PmVerdict;
import shared.model.SinglePm;
import shared.request.*;
import shared.response.CheckProfile;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SinglePmLabelController extends BasicController implements Initializable {

    private SinglePm singlePm;

    @FXML
    private Label profileLabel;

    @FXML
    private Label pmTextLabel;

    @FXML
    void profileLabelClicked(MouseEvent event) {
        getListener().listen(new ExplorerSearchIdRequest(singlePm.getUserId()));
    }

    public SinglePmLabelController(SinglePm singlePm) {
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
        view = new ImageView(new Image("squScreenshot (91).png"));;
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

    //    <?xml version="1.0" encoding="UTF-8"?>
//
//<?import javafx.geometry.Insets?>
//<?import javafx.scene.control.Label?>
//<?import javafx.scene.text.Font?>


//<Label fx:id="label" prefWidth="300.0" style="-fx-background-color: #5ff58c; -fx-background-radius: 10;" text="KosrqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqKosrqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqKosrqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqKosrqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" wrapText="true" xmlns="http://javafx.com/javafx/16" xmlns:fx="http://javafx.com/fxml/1">
//   <font>
//      <Font size="20.0" />
//   </font>
//   <padding>
//      <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
//   </padding>
//</Label>

}
