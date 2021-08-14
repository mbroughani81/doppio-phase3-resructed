package client.db;

import shared.model.ChatModel;
import shared.model.MessageDataModel;
import shared.model.SessionModel;

public class Context {

    public DBSet<SessionModel> SessionModels = new SessionModelDB();
    public DBSet<MessageDataModel> MessageDataModels = new MessageDataModelDB();
    public DBSet<ChatModel> ChatModels = new ChatModelDB();

    public void clearDB(String usernameDir) {
        SessionModels.clear(usernameDir);
        MessageDataModels.clear(usernameDir);
        ChatModels.clear(usernameDir);
    }
}
