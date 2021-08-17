package client.utils;

import shared.model.SinglePm;
import shared.model.SingleTweet;

import java.util.LinkedList;

public class SingleTweetUtility {
    public static boolean isSingleTweetListChanged(
            LinkedList<SingleTweet> tweets1,
            LinkedList<SingleTweet> tweets2) {
        if (tweets1 == null)
            return true;
        if (tweets1.size() != tweets2.size())
            return true;
        for (int i = 0; i < tweets1.size(); i++) {
            SingleTweet tweet1 = tweets1.get(i);
            SingleTweet tweet2 = tweets2.get(i);
            if (isSingleTweetChane(tweet1, tweet2))
                return true;
        }
        return false;
    }

    public static boolean isSingleTweetChane(SingleTweet t1, SingleTweet t2) {
        if (!t1.getText().equals(t2.getText()) || t1.getTweetId() != t2.getTweetId() ||
        t1.getUserId() != t2.getUserId()) {
            return true;
        }
        return false;
    }
}
