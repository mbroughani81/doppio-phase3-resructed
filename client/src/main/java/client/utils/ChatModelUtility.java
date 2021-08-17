package client.utils;

import shared.model.ChatModel;
import shared.model.SinglePm;

public class ChatModelUtility {
    public static boolean isChatModelChanged(ChatModel chatModel1, ChatModel chatModel2) {
        if (chatModel1 == null)
            return true;
        if (chatModel1.getPms().size() != chatModel2.getPms().size()) {
            return true;
        }
        for (int i = 0; i < chatModel1.getPms().size(); i++) {
            SinglePm pm1 = chatModel1.getPms().get(i);
            SinglePm pm2 = chatModel2.getPms().get(i);
            if (!SinglePmUtility.isSinglePmChange(pm1, pm2))
                return true;
        }
        return false;
    }
}


