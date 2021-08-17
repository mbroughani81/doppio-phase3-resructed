package client.apps.chat.view.comp;

import client.config.apps.chat.HypSinglePmLabelConfig;
import client.core.DoppioApp;
import client.datatype.BasicController;
import client.dbcontroller.FileModelController;
import client.utils.HyperTextUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import shared.model.SinglePm;
import shared.request.DeletePmRequest;
import shared.request.EditPmRequest;
import shared.request.ExplorerSearchIdRequest;
import shared.request.GetProfilePicRequest;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class HypSinglePmLabelController extends BasicController implements Initializable {

    private SinglePm singlePm;
    private LocalDateTime lastImageUpdate = LocalDateTime.now().minusYears(1);

    @FXML
    private Label profileLabel;

    @FXML
    private Label pmTextLabel;

    @FXML
    private TextFlow textFlow;

    @FXML
    void profileLabelClicked(MouseEvent event) {
        getListener().listen(new ExplorerSearchIdRequest(singlePm.getUserId()));
    }

    public HypSinglePmLabelController(SinglePm singlePm) {
        this.singlePm = singlePm;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFlow.getChildren().clear();
        textFlow.getChildren().addAll(HyperTextUtility.getHypText(singlePm.getText()));
        HypSinglePmLabelConfig hypSinglePmLabelConfig = new HypSinglePmLabelConfig();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit pm");
        editItem.setOnAction((event) -> {
            System.out.println("edit clickec" + singlePm.getText());
            openEditDialog();
        });
        MenuItem deleteItem = new MenuItem("Delete pm");
        deleteItem.setOnAction((event) -> {
            System.out.println("delete clicked" + singlePm.getText());
            openDeleteDialog();
        });
        contextMenu.getItems().addAll(editItem, deleteItem);
        pmTextLabel.setContextMenu(contextMenu);

        switch (singlePm.getPmVerdict()) {
            case OFFLINE -> pmTextLabel.setStyle(
                    pmTextLabel.getStyle() + "-fx-background-color: " + hypSinglePmLabelConfig.getOfflineColor() + ";");
            case SENT -> pmTextLabel.setStyle(
                    pmTextLabel.getStyle() + "-fx-background-color: " + hypSinglePmLabelConfig.getSentColor() + ";");
            case NOTSEEN -> pmTextLabel.setStyle(
                    pmTextLabel.getStyle() + "-fx-background-color: " + hypSinglePmLabelConfig.getNotseenColor() + ";");
            case SEEN -> pmTextLabel.setStyle(
                    pmTextLabel.getStyle() + "-fx-background-color: " + hypSinglePmLabelConfig.getSeenColor() + ";");
        }
        profileLabel.setText("");
        ImageView view;
        view = new ImageView(new Image("iliya1.png"));
        view.setFitWidth(hypSinglePmLabelConfig.getProfileSize());
        view.setFitHeight(hypSinglePmLabelConfig.getProfileSize());
        profileLabel.setGraphic(view);
    }

    @Override
    public Runnable getUpdateAction() {
        return () -> {
            if (FileModelController.isBefore(
                    lastImageUpdate,
                    DoppioApp.getFileModelController().getUsernameDir(),
                    "profilepics/" + singlePm.getUserId() + ".jpg")) {
                HypSinglePmLabelConfig hypSinglePmLabelConfig = new HypSinglePmLabelConfig();
                ImageView view;
                File img = new File(DoppioApp.getFileModelController().getProfilePicPath(
                        singlePm.getUserId()
                ));
                InputStream isImage = null;
                try {
                    isImage = new FileInputStream(img);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                view = new ImageView(new Image(isImage));
                view.setFitWidth(hypSinglePmLabelConfig.getProfileSize());
                view.setFitHeight(hypSinglePmLabelConfig.getProfileSize());
                System.out.println("profile of user " + singlePm.getUserId() + " is changed");
                profileLabel.setGraphic(view);

                lastImageUpdate = LocalDateTime.now();
            }
        };
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            if (FileModelController.canUpdate("profilepics/" + singlePm.getUserId() + ".jpg")) {
                getListener().listen(new GetProfilePicRequest(singlePm.getUserId()));
            }
        };
    }

    public void openEditDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Editing pm");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new pm : ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            System.out.println("edit completed");
            getListener().listen(new EditPmRequest(singlePm.getPmId(), name));
        });
    }

    public void openDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting pm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete pm?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("delete completed");
            getListener().listen(new DeletePmRequest(singlePm.getPmId()));
        }
    }



}
