package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.model.*;
import shared.datatype.PmVerdict;
import shared.model.ChatModel;
import shared.model.SinglePm;
import shared.model.SingleTweet;
import shared.model.SingleUser;
import shared.datatype.ChatType;
import shared.request.*;

import java.util.LinkedList;

public class MessageController extends AbstractController {
    public int newPrivateChat(NewPrivateChatRequest request) {
        int id1 = context.Users.get(request.getUser1Id()).getMessageDataId();
        int id2 = context.Users.get(request.getUser2Id()).getMessageDataId();
        if (hasPrivateChat(id1, id2) != -1) {
            return hasPrivateChat(id1, id2);
        }
        String username1 = context.Users.get(id1).getUsername();
        String username2 = context.Users.get(id2).getUsername();
        MessageData messageData1 = context.MessageDatas.get(id1);
        MessageData messageData2 = context.MessageDatas.get(id2);
        Chat chat1 = new Chat(request.getUser1Id(), ChatType.PRIVATE);
        chat1.getMemberIds().add(request.getUser1Id());
        chat1.getMemberIds().add(request.getUser2Id());
        Chat chat2 = new Chat(request.getUser2Id(), ChatType.PRIVATE);
        chat2.getMemberIds().add(request.getUser1Id());
        chat2.getMemberIds().add(request.getUser2Id());
        chat1.setChatName("PV with " + username2);
        chat2.setChatName("PV with " + username1);
        int chat1Id = context.Chats.add(chat1);
        int chat2Id = context.Chats.add(chat2);
        chat1.setId(chat1Id);
        chat2.setId(chat2Id);
        chat1.setParentChatId(chat1Id);
        chat2.setParentChatId(chat1Id);
        context.Chats.update(chat1);
        context.Chats.update(chat2);
        messageData1.getChatIds().add(chat1Id);
        messageData2.getChatIds().add(chat2Id);
        context.MessageDatas.update(messageData1);
        context.MessageDatas.update(messageData2);

        LogManager.getLogger(MessageController.class).info("new private chat created with parent id : " + chat1Id);
        return chat1Id;
    }

    public void newGroupChat(NewGroupRequest newGroupRequest) {
        int messageDataId = context.Users.get(newGroupRequest.getOwnerId()).getMessageDataId();
        MessageData messageData = context.MessageDatas.get(messageDataId);
        Chat chat = new Chat(newGroupRequest.getOwnerId(), ChatType.GROUP);
        chat.setChatName(newGroupRequest.getGroupname());
        chat.getMemberIds().add(newGroupRequest.getOwnerId());
        for (SingleUser user : newGroupRequest.getSingleUsers()) {
            chat.getMemberIds().add(user.getUserId());
        }
        int parentId = context.Chats.add(chat);
        chat.setParentChatId(parentId);
        chat.setId(parentId);
        context.Chats.update(chat);
        messageData.getChatIds().add(chat.getId());
        context.MessageDatas.update(messageData);
        for (SingleUser user : newGroupRequest.getSingleUsers()) {
            if (user.getUserId() == newGroupRequest.getOwnerId()) {
                continue;
            }
            messageData = context.MessageDatas.get(messageDataId);
            chat = new Chat(user.getUserId(), ChatType.GROUP);
            chat.setChatName(newGroupRequest.getGroupname());
            chat.getMemberIds().add(newGroupRequest.getOwnerId());
            for (SingleUser userr : newGroupRequest.getSingleUsers()) {
                chat.getMemberIds().add(userr.getUserId());
            }
            chat.setParentChatId(parentId);
            context.Chats.add(chat);
            messageData.getChatIds().add(chat.getId());
            context.MessageDatas.update(messageData);
        }
        LogManager.getLogger(MessageController.class).info("new group chat created with parent id : " + parentId);
    }

    public int sendNewPm(NewPmRequest newPmRequest) {
        AuthController authController = new AuthController();
        User user = authController.getUserWithAuthToken(newPmRequest.getAuthToken());
        Pm pm = new Pm(user.getId(), PmVerdict.SENT, newPmRequest.getText());
        int id = context.Pms.add(pm);

        Chat eventChat = context.Chats.get(newPmRequest.getChatId());
        int parentChatId = eventChat.getParentChatId();

        for (Chat chat : context.Chats.all()) {
            int userId1 = eventChat.getOwnerId();
            int userId2 = chat.getOwnerId();
            BlockList blockList1 = context.BlockLists.get(context.Users.get(userId1).getBlockListId());
            BlockList blockList2 = context.BlockLists.get(context.Users.get(userId2).getBlockListId());
            if (blockList1.getList().contains(userId2) || blockList2.getList().contains(userId1))
                continue;
            if (chat.getParentChatId() == parentChatId) {
                LinkedList<Integer> pmIds = chat.getPmIds();
                pmIds.add(id);
                chat.setPmIds(pmIds);
                if (chat.getId() != newPmRequest.getChatId())
                    chat.setUnreadCount(chat.getUnreadCount() + 1);
                context.Chats.update(chat);
            }
        }
        FileController fileController = new FileController();
        if (newPmRequest.getImageString() != null)
            fileController.updatePm(id, newPmRequest.getImageString());

        LogManager.getLogger(MessageController.class).info("new pm created with id : " + id);
        return id;
    }

    public void sendNewScheduledPm(NewScheduledPmRequest newScheduledPmRequest) {
        AuthController authController = new AuthController();
        User user = authController.getUserWithAuthToken(newScheduledPmRequest.getAuthToken());
        ScheduledPm scheduledPm = new ScheduledPm(
                user.getId(),
                PmVerdict.SENT,
                newScheduledPmRequest.getText(),
                newScheduledPmRequest.getDate()
        );
        context.ScheduledPms.add(scheduledPm);
    }

    public void editPm(EditPmRequest editPmRequest) {
        AuthController authController = new AuthController();
        User user = authController.getUserWithAuthToken(editPmRequest.getAuthToken());
        Pm pm = context.Pms.get(editPmRequest.getPmId());
        if (user.getId() != pm.getUserId()) {
            return;
        }
        pm.setText(editPmRequest.getText());
        context.Pms.update(pm);
    }

    public void deletePm(DeletePmRequest deletePmRequest) {
        AuthController authController = new AuthController();
        User user = authController.getUserWithAuthToken(deletePmRequest.getAuthToken());
        Pm pm = context.Pms.get(deletePmRequest.getPmId());
        if (user.getId() != pm.getUserId()) {
            return;
        }
        pm.setText("(This is deleted)");
        context.Pms.update(pm);
    }

    public void reportSpam(NewReportSpamRequest newReportSpamRequest) {
        AuthController authController = new AuthController();
        User user = authController.getUserWithAuthToken(newReportSpamRequest.getAuthToken());
        ReportedTweetList reportedTweetList = context.ReportedTweetLists.get(user.getReportedTweetListId());
        if (reportedTweetList.getTweetIds().contains(newReportSpamRequest.getTweetId()))
            return;
        Tweet tweet = context.Tweets.get(newReportSpamRequest.getTweetId());
        tweet.setSpamCounter(tweet.getSpamCounter() + 1);
        context.Tweets.update(tweet);
        reportedTweetList.getTweetIds().add(newReportSpamRequest.getTweetId());
        context.ReportedTweetLists.update(reportedTweetList);
    }

    public void saveTweetInSavedMessage(SaveTweetInSavedMessageRequest saveTweetInSavedMessageRequest) {
        AuthController authController = new AuthController();
        FileController fileController = new FileController();
        TweetController tweetController = new TweetController();
        int userId = authController.getUserWithAuthToken(saveTweetInSavedMessageRequest.getAuthToken()).getId();
        int savedMessageChatId = hasSavedMessage(userId);
        Tweet tweet = tweetController.getTweet(saveTweetInSavedMessageRequest.getTweetId());
        sendNewPm(new NewPmRequest(
                savedMessageChatId,
                "Forwarded from tweet : \n" + tweet.getText(),
                fileController.getTweetString(tweet.getId())
        ));
    }

    public boolean isMemberOfChat(int userId, int chatId) {
        Chat chat = context.Chats.get(chatId);
        return chat.getMemberIds().contains(userId);
    }

    public static ChatModel getErrorChatModel(int chatId) {
        LinkedList<SinglePm> pms = new LinkedList<>();
        pms.add(new SinglePm(-1, -1, PmVerdict.SEEN, "You don't have access to this chat"));
        return new ChatModel(chatId, pms);
    }

    public LinkedList<Chat> getChats(int userId) {
        LinkedList<Chat> chats = new LinkedList<>();
        for (Chat chat : context.Chats.all()) {
            if (chat.getOwnerId() == userId)
                chats.add(chat);
        }
        return chats;
    }

    public LinkedList<Pm> getPms(int chatId) {
        LinkedList<Pm> pms = new LinkedList<>();
        for (int pmId : context.Chats.get(chatId).getPmIds()) {
            pms.add(context.Pms.get(pmId));
        }
        return pms;
    }

    public LinkedList<Chat> getPrivateChats(int userId) {
        LinkedList<Chat> chats = new LinkedList<>();
        for (Chat chat : context.Chats.all()) {
            if (chat.getOwnerId() == userId && chat.getChatType() == ChatType.PRIVATE)
                chats.add(chat);
        }
        return chats;
    }

    public static LinkedList<SinglePm> convertToSinglePm(LinkedList<Pm> pms) {
        LinkedList<SinglePm> singlePms = new LinkedList<>();
        for (Pm pm : pms) {
            singlePms.add(convertToSinglePm(pm));
        }
        return singlePms;
    }

    public static SinglePm convertToSinglePm(Pm pm) {
        return new SinglePm(
                pm.getId(),
                pm.getUserId(),
                pm.getPmVerdict(),
                pm.getText()
        );
    }

    private int hasPrivateChat(int followerId, int followedId) {
        for (Chat chat : getPrivateChats(followerId)) {
            if (chat.getChatType() == ChatType.PRIVATE && chat.getMemberIds().contains(followedId))
                return chat.getId();
        }
        return -1;
    }

    private int hasSavedMessage(int userId) {
        for (Chat chat : getPrivateChats(userId)) {
            if (chat.getChatType() == ChatType.PRIVATE && chat.getMemberIds().size() == 1)
                return chat.getId();
        }
        return -1;
    }
}
