package client.apps.tweet.view.comp;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleTweetLabel extends Label implements Initializable {
    public SingleTweetLabel(String text) {
        super(text);
        this.setFont(Font.font(20));
        this.setWrapText(true);
        this.setPrefWidth(600);
        BackgroundFill backgroundFill =
                new BackgroundFill(
                        Color.valueOf("#deff70"),
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
