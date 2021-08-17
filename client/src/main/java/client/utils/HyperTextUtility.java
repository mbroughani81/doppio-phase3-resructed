package client.utils;

import client.apps.chat.view.comp.HypSinglePmLabelController;
import client.apps.chat.view.comp.HyperTextController;
import client.apps.chat.view.comp.SimpleTextController;
import client.datatype.HyperType;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.LinkedList;

public class HyperTextUtility {

    public static LinkedList<Text> getHypText(String text) {
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

    public static HyperType getHypeType(String text) {
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

    public static String getHypeVal(String text) {
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

    public static Text getHypeText(String text, HyperType hyperType, String val) {
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

    public static Text getSimpleText(String text) {
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
