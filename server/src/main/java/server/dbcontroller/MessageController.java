package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.config.dbcontrollerConfig.MessageControllerConfig;
import server.model.*;
import shared.datatype.PmVerdict;
import shared.model.ChatModel;
import shared.model.SinglePm;
import shared.model.SingleTweet;
import shared.model.SingleUser;
import shared.datatype.ChatType;
import shared.request.*;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class MessageController extends AbstractController {
    public int newPrivateChat(NewPrivateChatRequest request) {
        MessageControllerConfig config = new MessageControllerConfig();
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
        chat1.setChatName(config.getPvChatnameText() + username2);
        chat2.setChatName(config.getPvChatnameText() + username1);
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
            User u = context.Users.get(user.getUserId());
            messageData = context.MessageDatas.get(u.getMessageDataId());
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

    public void joinGroup(JoinGroupRequest joinGroupRequest) {
        AuthController authController = new AuthController();
        User user = authController.getUserWithAuthToken(joinGroupRequest.getAuthToken());
        Chat requestChat = context.Chats.get(joinGroupRequest.getChatId());
        if (requestChat == null || requestChat.getParentChatId() != requestChat.getId())
            return;
        if (requestChat.getMemberIds().contains(user.getId()))
            return;
        int messageDataId = context.Users.get(user.getId()).getMessageDataId();
        MessageData messageData = context.MessageDatas.get(messageDataId);
        Chat chat = new Chat(user.getId(), ChatType.GROUP);
        chat.setChatName(requestChat.getChatName());
        chat.getMemberIds().add(user.getId());
        for (int userId : requestChat.getMemberIds()) {
            chat.getMemberIds().add(userId);
        }
        int chatId = context.Chats.add(chat);
        chat.setParentChatId(requestChat.getParentChatId());
        chat.setId(chatId);
        context.Chats.update(chat);
        messageData.getChatIds().add(chatId);
        context.MessageDatas.update(messageData);

        LinkedList<Chat> allChats = context.Chats.all();
        for (int i = 0; i < allChats.size(); i++) {
            Chat chat1 = allChats.get(i);
            if (chat1.getParentChatId() == requestChat.getParentChatId() &&
                    chat1.getId() != chatId) {
                chat1.getMemberIds().add(user.getId());
                context.Chats.update(chat1);
            }
        }
    }

    public void leaveGroup(LeaveGroupRequest leaveGroupRequest) {
        AuthController authController = new AuthController();
        User user = authController.getUserWithAuthToken(leaveGroupRequest.getAuthToken());
        Chat requestChat = context.Chats.get(leaveGroupRequest.getChatId());

        int parentId = requestChat.getParentChatId();
        MessageData messageData = context.MessageDatas.get(user.getMessageDataId());
        messageData.getChatIds().remove((Object) requestChat.getId());
        context.MessageDatas.update(messageData);
        // we can delete the chat form database now.
        context.Chats.remove(requestChat.getId());
        //
        int newParentId = -1;
        LinkedList<Chat> allChats = context.Chats.all();
        for (int i = 0; i < allChats.size(); i++) {
            Chat chat1 = allChats.get(i);
            if (chat1.getParentChatId() == parentId && chat1.getId() != requestChat.getId()) {
                newParentId = chat1.getId();
                chat1.getMemberIds().remove((Object) user.getId());
                context.Chats.update(chat1);
            }
        }
        for (int i = 0; i < allChats.size(); i++) {
            Chat chat1 = allChats.get(i);
            if (chat1.getParentChatId() == parentId && chat1.getId() != requestChat.getId()) {
                chat1.setParentChatId(newParentId);
                context.Chats.update(chat1);
            }
        }
    }

    public int sendNewPm(NewPmRequest newPmRequest, int userId) {
        AuthController authController = new AuthController();
//        User user = authController.getUserWithAuthToken(newPmRequest.getAuthToken());
        User user = authController.getUser(userId);
        Pm pm = new Pm(user.getId(), newPmRequest.getText());
        int id = context.Pms.add(pm);

        Chat eventChat = context.Chats.get(newPmRequest.getChatId());
        int parentChatId = eventChat.getParentChatId();

        for (Chat chat : context.Chats.all()) {
            int userId1 = eventChat.getOwnerId();
            int userId2 = chat.getOwnerId();
            BlockList blockList1 = context.BlockLists.get(context.Users.get(userId1).getBlockListId());
            BlockList blockList2 = context.BlockLists.get(context.Users.get(userId2).getBlockListId());
            if (chat.getParentChatId() != parentChatId)
                continue;
            if (eventChat.getChatType() == ChatType.PRIVATE &&
                    (blockList1.getList().contains(userId2) || blockList2.getList().contains(userId1)))
                continue;
            LinkedList<Integer> pmIds = chat.getPmIds();
            pmIds.add(id);
            chat.setPmIds(pmIds);
            chat.setUnreadCount(chat.getUnreadCount() + 1);
            context.Chats.update(chat);
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
                newScheduledPmRequest.getChatId(),
                newScheduledPmRequest.getText(),
                newScheduledPmRequest.getImageString(),
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
        MessageControllerConfig config = new MessageControllerConfig();
        User user = authController.getUserWithAuthToken(deletePmRequest.getAuthToken());
        Pm pm = context.Pms.get(deletePmRequest.getPmId());
        if (user.getId() != pm.getUserId()) {
            return;
        }
        pm.setText(config.getDeletedPmText());
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
        MessageControllerConfig config = new MessageControllerConfig();
        int userId = authController.getUserWithAuthToken(saveTweetInSavedMessageRequest.getAuthToken()).getId();
        int savedMessageChatId = hasSavedMessage(userId);
        Tweet tweet = tweetController.getTweet(saveTweetInSavedMessageRequest.getTweetId());
        sendNewPm(new NewPmRequest(
                savedMessageChatId,
                config.getForwardedTweetText() + tweet.getText(),
                fileController.getTweetString(tweet.getId())
        ), userId);
    }

    public void updateReadCount(int chatId) {
        Chat chat1 = context.Chats.get(chatId);
        if (chat1.getChatType() == ChatType.GROUP ||
                chat1.getMemberIds().size() == 1)
            return;
        int otherMemberId;
        if (chat1.getOwnerId() != chat1.getMemberIds().get(0))
            otherMemberId = chat1.getMemberIds().get(0);
        else
            otherMemberId = chat1.getMemberIds().get(1);
        int pairChatId = -1;
        for (Chat chat : getPrivateChats(otherMemberId)) {
            if (chat.getParentChatId() == chat1.getParentChatId()) {
                pairChatId = chat.getId();
                break;
            }
        }
        Chat pairChat1 = context.Chats.get(pairChatId);
        pairChat1.setReadPmCount(pairChat1.getPmIds().size());
        context.Chats.update(pairChat1);
    }

    public void updateIgnoredCount(int userId) {
        for (Chat chat : getPrivateChats(userId))
            updateSingleIgnoredCount(chat.getId());
    }

    private void updateSingleIgnoredCount(int chatId) {
        Chat chat1 = context.Chats.get(chatId);
        if (chat1.getChatType() == ChatType.GROUP ||
                chat1.getMemberIds().size() == 1)
            return;
        int otherMemberId;
        if (chat1.getOwnerId() != chat1.getMemberIds().get(0))
            otherMemberId = chat1.getMemberIds().get(0);
        else
            otherMemberId = chat1.getMemberIds().get(1);
        int pairChatId = -1;
        for (Chat chat : getPrivateChats(otherMemberId)) {
            if (chat.getParentChatId() == chat1.getParentChatId()) {
                pairChatId = chat.getId();
                break;
            }
        }
        Chat pairChat1 = context.Chats.get(pairChatId);
//        System.out.println("i have found pair " + pairChatId);
        pairChat1.setIgnoredPmCount(pairChat1.getPmIds().size());
        context.Chats.update(pairChat1);
    }

    public void updateUnreadCount(int chatId) {
        Chat chat = context.Chats.get(chatId);
        chat.setUnreadCount(0);
        context.Chats.update(chat);
    }

    public void handleScheduledPms() {
        LinkedList<ScheduledPm> allScheduledPms = context.ScheduledPms.all();
        for (ScheduledPm scheduledPm : allScheduledPms) {
            if (!scheduledPm.isSent() && LocalDateTime.now().isAfter(scheduledPm.getDate())) {
                sendNewPm(
                        new NewPmRequest(scheduledPm.getChatId(), scheduledPm.getText(), scheduledPm.getImageString()),
                        scheduledPm.getUserId()
                );
                scheduledPm.setSent(true);
                context.ScheduledPms.update(scheduledPm);
            }
        }
    }

    public boolean isOwnerOfChat(int userId, int chatId) {
        Chat chat = context.Chats.get(chatId);
        if (chat == null)
            return false;
        return chat.getOwnerId() == userId;
    }

    public static ChatModel getErrorChatModel(int chatId) {
        LinkedList<SinglePm> pms = new LinkedList<>();
        MessageControllerConfig config = new MessageControllerConfig();
        pms.add(new SinglePm(-1, -1, PmVerdict.SEEN, config.getErrorChatModelText()));
        return new ChatModel(chatId, ChatType.PRIVATE, pms);
    }

    public LinkedList<Chat> getChats(int userId) {
        User user = context.Users.get(userId);
        LinkedList<Chat> chats = new LinkedList<>();
        for (int chatId : context.MessageDatas.get(user.getMessageDataId()).getChatIds()) {
            Chat chat = context.Chats.get(chatId);
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

    public int getReadCount(int chatId) {
        return context.Chats.get(chatId).getReadPmCount();
    }

    public int getIgnoredCount(int chatId) {
        return context.Chats.get(chatId).getIgnoredPmCount();
    }

    public ChatType getChatType(int chatId) {
        return context.Chats.get(chatId).getChatType();
    }

    public static LinkedList<SinglePm> convertToSinglePm(LinkedList<Pm> pms) {
        LinkedList<SinglePm> singlePms = new LinkedList<>();
        for (Pm pm : pms) {
            singlePms.add(convertToSinglePm(pm));
        }
        return singlePms;
    }

    public static LinkedList<SinglePm> convertToSinglePm(LinkedList<Pm> pms, int readCount, int ignoredCount) {
        LinkedList<SinglePm> singlePms = new LinkedList<>();
        for (int i = 0; i < pms.size(); i++) {
            if (i < readCount) {
                singlePms.add(convertToSinglePm(pms.get(i), PmVerdict.SEEN));
            } else if (i < ignoredCount) {
                singlePms.add(convertToSinglePm(pms.get(i), PmVerdict.NOTSEEN));
            } else {
                singlePms.add(convertToSinglePm(pms.get(i), PmVerdict.SENT));
            }
        }
        return singlePms;
    }

    public static SinglePm convertToSinglePm(Pm pm) {
        return new SinglePm(
                pm.getId(),
                pm.getUserId(),
                PmVerdict.SENT,
                pm.getText()
        );
    }

    public static SinglePm convertToSinglePm(Pm pm, PmVerdict pmVerdict) {
        return new SinglePm(
                pm.getId(),
                pm.getUserId(),
                pmVerdict,
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
