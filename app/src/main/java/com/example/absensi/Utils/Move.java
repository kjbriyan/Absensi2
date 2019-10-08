package com.example.absensi.Utils;

import android.content.Context;
import android.content.Intent;

public class Move extends Intent {

    public static void move(Context context, Class<?> Activity) {
        Intent i = new Intent(context, Activity);
        context.startActivity(i);

    }

    public static void moveData(Context context, Class<?> Activity, String str1, String str2) {
        Intent i = new Intent(context, Activity);
        i.putExtra(str1,str2);
        context.startActivity(i);

    }

}
