package server.controller;

import org.apache.logging.log4j.LogManager;
import server.config.socketConfig.SocketConfig;
import server.controller.network.SocketResponseSender;
import server.db.Context;
import server.dbcontroller.AuthController;
import server.dbcontroller.MessageController;
import server.dbcontroller.TweetController;
import shared.request.NewPmRequest;
import shared.request.NewPrivateChatRequest;
import shared.request.NewTweetRequest;
import shared.request.SignupRequest;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager extends Thread {
    @Override
    public void run() {
        SocketConfig socketConfig = new SocketConfig();
//        Context context = new Context();
//        context.clearDB();
//        TestData.testNewUser();
//        TestData.testNewTweet();
//        TestData.testNewPrivateChat();
        try {
            ServerSocket serverSocket = new ServerSocket(socketConfig.getPort());
            LogManager.getLogger(SocketManager.class).info("serversocket is up");
            while(true) {
                System.out.println("--Waiting for new doppio client--");
                Socket socket = serverSocket.accept();
                System.out.println("--Connected--");
                LogManager.getLogger(SocketManager.class).info("new Socket is created");

                ClientThread clientThread = new ClientThread(new SocketResponseSender(socket));
                clientThread.start();
                LogManager.getLogger(SocketManager.class).info("new ClientThread is created");
            }
        } catch (IOException e) {
            LogManager.getLogger(SocketManager.class).error("can not setup serversocket");
            e.printStackTrace();
        }
    }

    static class TestData {
        public static void testNewUser() {
            AuthController authController = new AuthController();
            MessageController messageController = new MessageController();
            authController.signupUser(new SignupRequest(
                    "a1",
                    "a1",
                    "a1",
                    "a1",
                    "a1@gmail.com",
                    "a1",
                    "a1"
            ));
            authController.signupUser(new SignupRequest(
                    "a2",
                    "a2",
                    "a2",
                    "a2",
                    "a2@gmail.com",
                    "a2",
                    "a2"
            ));
            authController.signupUser(new SignupRequest(
                    "a3",
                    "a3",
                    "a3",
                    "a3",
                    "a3@gmail.com",
                    "a3",
                    "a3"
            ));
            authController.signupUser(new SignupRequest(
                    "aa",
                    "aa",
                    "aa",
                    "aa",
                    "aa@gmail.com",
                    "aa",
                    "aa"
            ));
            authController.signupUser(new SignupRequest(
                    "mohammad",
                    "mb",
                    "1381",
                    "mb",
                    "mb@gmail.com",
                    "mb",
                    "mb"
            ));

        }

        public static void testNewTweet() {
            TweetController tweetController = new TweetController();
            AuthController authController = new AuthController();
            int id1 = authController.getUser("a1").getId();
            int id2 = authController.getUser("a2").getId();
            int tweetId = tweetController.newTweet(new NewTweetRequest(
                    -1,
                    "firstTweet",
                    null,
                    id1
            ));
            tweetController.newTweet(new NewTweetRequest(
                    -1,
                    "second!Tweet",
                    null,
                    id1
            ));
            tweetController.newTweet(new NewTweetRequest(
                    -1,
                    "avval Tweet",
                    null,
                    id2
            ));
            tweetController.newTweet(new NewTweetRequest(
                    -1,
                    "dovvom Tweet",
                    null,
                    id2
            ));
            tweetController.newTweet(new NewTweetRequest(
                    tweetId,
                    "a2 comments on a1",
                    null,
                    id2
            ));
        }
        public static void testNewPrivateChat() {
            MessageController messageController = new MessageController();
            AuthController authController = new AuthController();
            int id1 = messageController.newPrivateChat(new NewPrivateChatRequest(
                    authController.getUser("a1").getId(),
                    authController.getUser("a2").getId()
            ));
            messageController.sendNewPm(new NewPmRequest(
                    id1,
                    "salam, a2!",
                    null,
                    authController.getUser("a1").getId()
            ));
        }
    }
}
