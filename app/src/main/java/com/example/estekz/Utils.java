package com.example.estekz;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.Random;

public class Utils {


    private static final ColorDrawable[] colorsList =
            {
                    new ColorDrawable(Color.parseColor("#80d6ff")),
                    new ColorDrawable(Color.parseColor("#42a5f5")),
                    new ColorDrawable(Color.parseColor("#0077c2")),
                    new ColorDrawable(Color.parseColor("#81d4fa")),
                    new ColorDrawable(Color.parseColor("#b6ffff")),
                    new ColorDrawable(Color.parseColor("#4ba3c7")),
            };

    static ColorDrawable getRandomDrawableColor() {
        int idx = new Random().nextInt(colorsList.length);
        return colorsList[idx];
    }



//    static String DateToTimeFormat(String date){
//        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
//        String publicationTime = null;
//        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//                    "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
//            Date newDate = simpleDateFormat.parse(date);
//            publicationTime = prettyTime.format(newDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return publicationTime;
//    }
}

