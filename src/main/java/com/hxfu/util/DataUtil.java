package com.hxfu.util;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {
    public static Map<String, String> createItemStyle(Integer sale) {
        String color = "";
        if (sale < 60)
            color = "#96dee8";
        else if (sale >= 60 && sale < 160)
            color = "#c4ebad";
        else if (sale >= 160 && sale < 300)
            color = "#6be6c1";
        else if (sale >= 300 && sale < 500)
            color = "#3fb1e3";
        else if (sale >= 500)
            color = "#a0a7e6";
        Map<String, String> map = new HashMap<>();
        map.put("color", color);
        return map;
    }
}
