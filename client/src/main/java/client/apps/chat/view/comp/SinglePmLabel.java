package client.apps.chat.view.comp;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class SinglePmLabel extends Label implements Initializable {

    public SinglePmLabel(String text) {
        super(text);
        this.setFont(Font.font(20));
        this.setWrapText(true);
        this.setPrefWidth(300);
        BackgroundFill backgroundFill =
                new BackgroundFill(
                        Color.valueOf("#5ff58c"),
                        new CornerRadii(10),
                        null
                );
        this.setBackground(new Background(backgroundFill));
        this.setPadding(new Insets(5));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
