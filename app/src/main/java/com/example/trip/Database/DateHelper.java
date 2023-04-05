package com.example.trip.Database;

import java.util.ArrayList;
import java.util.List;

public class DateHelper {

    public static String getStringFromDmy(int d, int m, int y) {
        return d + "/" + m + "/" + y;
    }

    public static List<Integer> getDmyFromString(String date) {
        List<Integer> list = new ArrayList<>();
        int index1 = date.indexOf("/");
        int d = Integer.parseInt(date.substring(0, index1));
        int index2 = date.indexOf("/", 3);
        int m = Integer.parseInt(date.substring(index1 + 1, index2));
        int y = Integer.parseInt(date.substring(index2 + 1));
        list.add(d);
        list.add(m);
        list.add(y);
        return list;
    }

}
