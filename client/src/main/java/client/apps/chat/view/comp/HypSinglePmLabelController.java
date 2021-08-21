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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;
import shared.model.SinglePm;
import shared.request.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class HypSinglePmLabelController extends BasicController implements Initializable {

    private SinglePm singlePm;
    private LocalDateTime lastProfileImageUpdate = LocalDateTime.now().minusYears(1);
    private LocalDateTime lastPmImageUpdate = LocalDateTime.now().minusYears(1);

    @FXML
    private Label profileLabel;

    @FXML
    private Label pmTextLabel;

    @FXML
    private TextFlow textFlow;

    @FXML
    private Label pmPicLabel;

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
        textFlow.getChildren().addAll(HyperTextUtility.getHypText(singlePm.getText(), getListener()));
        HypSinglePmLabelConfig config = new HypSinglePmLabelConfig();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem(config.getEditItemText());
        editItem.setOnAction((event) -> {
            openEditDialog();
        });
        MenuItem deleteItem = new MenuItem(config.getDeleteItemText());
        deleteItem.setOnAction((event) -> {
            openDeleteDialog();
        });
        contextMenu.getItems().addAll(editItem, deleteItem);
        pmTextLabel.setContextMenu(contextMenu);

        switch (singlePm.getPmVerdict()) {
            case OFFLINE -> pmTextLabel.setStyle(
                    pmTextLabel.getStyle() + "-fx-background-color: " + config.getOfflineColor() + ";");
            case SENT -> pmTextLabel.setStyle(
                    pmTextLabel.getStyle() + "-fx-background-color: " + config.getSentColor() + ";");
            case NOTSEEN -> pmTextLabel.setStyle(
                    pmTextLabel.getStyle() + "-fx-background-color: " + config.getNotseenColor() + ";");
            case SEEN -> pmTextLabel.setStyle(
                    pmTextLabel.getStyle() + "-fx-background-color: " + config.getSeenColor() + ";");
        }
        profileLabel.setText("");
        Rectangle rectangle = new Rectangle(config.getProfileSize(), config.getProfileSize());
        rectangle.setFill(Paint.valueOf(config.getDefaultProfileColor()));
        profileLabel.setGraphic(rectangle);
    }

    @Override
    public Runnable getUpdateAction() {
        return () -> {
            HypSinglePmLabelConfig config = new HypSinglePmLabelConfig();
            if (FileModelController.isBefore(
                    lastProfileImageUpdate,
                    DoppioApp.getFileModelController().getUsernameDir(),
                    config.getProfilepicsPath() + singlePm.getUserId() + ".jpg")) {
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
                profileLabel.setGraphic(view);

                lastProfileImageUpdate = LocalDateTime.now();
            }
            if (FileModelController.isBefore(
                    lastPmImageUpdate,
                    DoppioApp.getFileModelController().getUsernameDir(),
                    config.getPmpicsPath() + singlePm.getPmId() + ".jpg")) {
                ImageView view;
                File img = new File(DoppioApp.getFileModelController().getPmPicPath(
                        singlePm.getPmId()
                ));
                InputStream isImage = null;
                try {
                    isImage = new FileInputStream(img);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                view = new ImageView(new Image(isImage));
                view.setPreserveRatio(true);
                view.setFitWidth(config.getPmpicfitwidth());
                pmPicLabel.setGraphic(view);

                lastPmImageUpdate = LocalDateTime.now();
            }
        };
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            HypSinglePmLabelConfig config = new HypSinglePmLabelConfig();
            if (FileModelController.canUpdate(config.getProfilepicsPath() + singlePm.getUserId())) {
                getListener().listen(new GetProfilePicRequest(singlePm.getUserId()));
            }
            if (FileModelController.canUpdate(config.getPmpicsPath() + singlePm.getPmId())) {
                getListener().listen(new GetPmPicRequest(singlePm.getPmId()));
            }
        };
    }

    public void openEditDialog() {
        HypSinglePmLabelConfig config = new HypSinglePmLabelConfig();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(config.getEditdialogecontenttext());
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            getListener().listen(new EditPmRequest(singlePm.getPmId(), name));
        });
    }

    public void openDeleteDialog() {
        HypSinglePmLabelConfig config = new HypSinglePmLabelConfig();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(config.getDeletedialogecontenttext());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            getListener().listen(new DeletePmRequest(singlePm.getPmId()));
        }
    }
}
