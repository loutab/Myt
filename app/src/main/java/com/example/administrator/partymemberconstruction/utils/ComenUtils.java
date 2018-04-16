package com.example.administrator.partymemberconstruction.utils;

import com.example.administrator.partymemberconstruction.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/29/029.
 */

public class ComenUtils {
    public static String ChangeTime(String time) {
        try {


            String[] split = time.split(" ");
            String[] split1 = split[0].split("-");
            String[] split2 = split[1].split(":");


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
            Date date = new Date(System.currentTimeMillis());
            String format = simpleDateFormat.format(date);
            String[] split3 = format.split(" ");
            String[] split4 = split3[0].split("-");
            String[] split5 = split3[1].split(":");

            if (split[0].equals(split3[0])) {
                return "今天 " + split[1];
            } else if (split1[0].equals(split4[0]) && split1[1].equals(split4[1])) {
                Integer integer = Integer.valueOf(split1[2]);
                Integer integer1 = Integer.valueOf(split4[2]);
                int i = integer1 - integer;
                if (i == 1) {
                    return "昨天" + split[1];
                } else {
                    return split1[1] + "-" + split1[2] + " " + split[1];
                }
            } else if (split1[0].equals(split4[0])) {
                return split1[1] + "-" + split1[2] + " " + split[1];
            } else
                return time;
        } catch (Exception e) {
            return "";
        }
    }
}
