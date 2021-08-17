package client.apps.chat.view.comp;

import client.config.apps.chat.HypSinglePmLabelConfig;
import client.datatype.BasicController;
import client.dbcontroller.FileModelController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import shared.model.SinglePm;
import shared.request.DeletePmRequest;
import shared.request.EditPmRequest;
import shared.request.ExplorerSearchIdRequest;
import shared.request.GetProfilePicRequest;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class HypSinglePmLabelController extends BasicController implements Initializable {

    private SinglePm singlePm;

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
        textFlow.getChildren().addAll(HypSinglePmLabelController.getHypText(singlePm.getText()));
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

    private static LinkedList<Text> getHypText(String text) {
        LinkedList<Text> texts = new LinkedList<>();
        boolean isWordStarted = false;
        boolean isHyperStarted = false;
        String cur = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '@' && !isWordStarted) {
                if (cur.length() != 0) {
                    texts.add(getSimpleText(cur));
                }
                cur = "";
                isHyperStarted = true;
                isWordStarted = true;
                cur += c;
                continue;
            }
            if (c == ' ') {
                if (isHyperStarted) {
                    texts.add(getHypeText(cur, getHypeType(cur), getHypeVal(cur)));
                    cur = "";
                }
                isHyperStarted = false;
                isWordStarted = false;
                cur += c;
                continue;
            }
            isWordStarted = true;
            cur += c;
        }
        if (isHyperStarted) {
            texts.add(getHypeText(cur, getHypeType(cur), getHypeVal(cur)));
        } else {
            texts.add(getSimpleText(cur));
        }
        return texts;
    }

    private static HyperType getHypeType(String text) {
        text = text.substring(1, text.length());
        text = text.toLowerCase();
        text += "######";
        HyperType[] types = {
                HyperType.TWEET,
                HyperType.JOINGROUP,
                HyperType.CHAT,
        };
        // chat : is a group that already joined
        // tweet : checks tweeet
        // user :
        // join : joins
        for (int i = 0; i < types.length; i++) {
            if (types[i].getVal().equals(text.substring(0, types[i].getVal().length()))) {
                return types[i];
            }
        }
        return HyperType.UNDEFINED;
    }

    private static String getHypeVal(String text) {
        text = text.substring(1, text.length());
        text = text.toLowerCase();
        String src = text;
        text += "######";
        HyperType[] types = {
                HyperType.TWEET,
                HyperType.JOINGROUP,
                HyperType.CHAT,
        };
        // chat : is a group that already joined
        // tweet : checks tweeet
        // user :
        // join : joins
        for (int i = 0; i < types.length; i++) {
            if (types[i].getVal().equals(text.substring(0, types[i].getVal().length()))) {
                return src.substring(types[i].getVal().length(), src.length());
            }
        }
        return src;
    }

    private static Text getHypeText(String text, HyperType hyperType, String val) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HypSinglePmLabelController.class.getResource("hypertext.fxml"));
        fxmlLoader.setController(new HyperTextController(text, hyperType, val));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Text getSimpleText(String text) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HypSinglePmLabelController.class.getResource("simpletext.fxml"));
        fxmlLoader.setController(new SimpleTextController(text));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
