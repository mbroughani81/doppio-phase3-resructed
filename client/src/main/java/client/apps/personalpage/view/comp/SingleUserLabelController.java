package client.apps.personalpage.view.comp;

import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import shared.request.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SingleUserLabelController extends BasicController implements Initializable {

    private int userId;
    private String type;

    @FXML
    private Label profileLabel;

    @FXML
    void profileLabelClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            getListener().listen(new ExplorerSearchIdRequest(userId));
        }
    }

    public SingleUserLabelController(int userId, String type) {
        this.userId = userId;
        this.type = type;
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
            Rectangle rectangle = new Rectangle(40, 40);
            rectangle.setFill(Paint.valueOf("#fa7e5c"));
            profileLabel.setGraphic(rectangle);
        }
        // ask for image to load in furthur openings
        getListener().listen(new GetProfilePicRequest(userId));

        //
        ContextMenu contextMenu = new ContextMenu();
        MenuItem unblockItem = new MenuItem("unblock");
        unblockItem.setOnAction((event) -> {
            getListener().listen(new NewUnblockRequest(userId));
        });
        if (type.equals("blacklist")) {
            contextMenu.getItems().add(unblockItem);
        }
        profileLabel.setContextMenu(contextMenu);
    }
}
