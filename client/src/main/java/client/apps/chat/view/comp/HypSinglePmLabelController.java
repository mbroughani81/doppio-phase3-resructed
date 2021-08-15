package client.apps.chat.view.comp;

import client.datatype.BasicController;
import client.dbcontroller.FileModelController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import shared.model.SinglePm;
import shared.request.ExplorerSearchIdRequest;
import shared.request.GetProfilePicRequest;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
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
//        pmTextLabel.setText(singlePm.getUserId() + " :\n" + singlePm.getText());
        textFlow.getChildren().clear();
        textFlow.getChildren().addAll(HypSinglePmLabelController.getHypText(singlePm.getText()));

        switch (singlePm.getPmVerdict()) {
            case OFFLINE -> pmTextLabel.setStyle(pmTextLabel.getStyle() + "-fx-background-color: #f54949;");
            case SENT -> pmTextLabel.setStyle(pmTextLabel.getStyle() + "-fx-background-color: #ed4ca2;");
            case NOTSEEN -> pmTextLabel.setStyle(pmTextLabel.getStyle() + "-fx-background-color: #6b5ffa;");
            case SEEN -> pmTextLabel.setStyle(pmTextLabel.getStyle() + "-fx-background-color: #4ced54;");
        }
        profileLabel.setText("");
        ImageView view;
        view = new ImageView(new Image("iliya1.png"));
        ;
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

    private static LinkedList<Text> getHypText(String text) {
        LinkedList<Text> texts = new LinkedList<>();
//        texts.add(getHypeText(HyperType.TWEET, "@tweetid:10"));
//        texts.add(getSimpleText("salam"));
//        texts.add(getSimpleText("salam"));
//        texts.add(getSimpleText("salam"));
//        texts.add(getHypeText(HyperType.TWEET, "@tweetid:10"));
//        texts.add(getSimpleText("salam"));
//        texts.add(getHypeText(HyperType.TWEET, "@tweetid:10"));
//        texts.add(getSimpleText("salam"));
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
                    texts.add(getHypeText(HyperType.TWEET, cur));
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
            texts.add(getHypeText(HyperType.TWEET, cur));
        } else {
            texts.add(getSimpleText(cur));
        }
//        System.out.println(texts.size() + " is size!");
        return texts;
    }

    private static Text getHypeText(HyperType hyperType, String text) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HypSinglePmLabelController.class.getResource("hypertext.fxml"));
        fxmlLoader.setController(new HyperTextController(text));
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
