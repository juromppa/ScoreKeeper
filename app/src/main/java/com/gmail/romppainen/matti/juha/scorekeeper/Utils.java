package com.gmail.romppainen.matti.juha.scorekeeper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Juha on 4.1.2017.
 */

class Utils {

    static String ConvertDate(int epoch) {
        Date date = new Date(epoch * 1000L);
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
        return df2.format(date);
    }
}
