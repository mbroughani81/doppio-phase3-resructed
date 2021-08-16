package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import shared.datatype.PmVerdict;
import shared.model.SingleUser;
import server.model.BlockList;
import server.model.Chat;
import server.model.MessageData;
import server.model.Pm;
import shared.datatype.ChatType;
import shared.request.NewGroupRequest;
import shared.request.NewPmRequest;
import shared.request.NewPrivateChatRequest;

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
        Pm pm = new Pm(newPmRequest.getUserId(), PmVerdict.SENT, newPmRequest.getText());
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

        LogManager.getLogger(MessageController.class).info("new pm created with id : " + id);
        return id;
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

    private int hasPrivateChat(int followerId, int followedId) {
        for (Chat chat : getPrivateChats(followerId)) {
            if (chat.getChatType() == ChatType.PRIVATE && chat.getMemberIds().contains(followedId))
                return chat.getId();
        }
        return -1;
    }
}
