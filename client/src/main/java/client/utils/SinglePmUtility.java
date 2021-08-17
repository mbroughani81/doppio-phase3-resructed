package client.utils;

import shared.model.SinglePm;

public class SinglePmUtility {
    public static boolean isSinglePmChange(SinglePm pm1, SinglePm pm2) {
        if (pm1.getText().equals(pm2.getText()) && pm1.getPmVerdict() == pm2.getPmVerdict() &&
                pm1.getUserId() == pm2.getUserId())
            return true;
        return false;
    }
}
