package com.android.new_call_app.Utils;

/**
 * Created By Ayoub aitouna 08/26/2022
 */

import com.android.new_call_app.R;
import com.android.new_call_app.models.Characters;
import com.android.new_call_app.models.data;

import java.util.ArrayList;

public class Config {
    public static data appdata;
    public static final String Admob = "Admob";
    public static final String unityAd = "unityAd";
    public static final String Unity_Applovin = "Unity_Applovin";
    public static final String applovin = "Applovin";
    public static final String fb = "facebook";
    public static final String Yandex = "yandex";
    public static Characters selectedCharacter;
    public static final String PUT_ID = "0xff00784fea";
    public static final ArrayList<Characters> characters = new ArrayList<>();
    public static final ArrayList<String> comments = new ArrayList<>();

    public static void Init() {
        addCaracters();
        addcomments();
    }

    static void addCaracters() {
        characters.add(new Characters(R.drawable.photo, "User1", "+0678953289"));
        characters.add(new Characters(R.drawable.photo, "User2", "+0678953289"));
        characters.add(new Characters(R.drawable.photo, "User3", "+0678953289"));
        characters.add(new Characters(R.drawable.photo, "User5", "+0678953289"));
        characters.add(new Characters(R.drawable.photo, "User6", "+0678953289"));
        characters.add(new Characters(R.drawable.photo, "User7", "+0678953289"));
    }

    static void addcomments() {
        comments.add("Hey");
        comments.add("Hello");
        comments.add("Woow");
        comments.add("Yeaah");
        comments.add("Doooo");
        comments.add("hhhhhhhhhh hhhhhhhhhhhhhhhh");
        comments.add("love yaaa");
        comments.add("Greattt braaah!!");
    }

    public enum ConnectionType {
        Local, Online
    }

    public static Boolean ShowOpenAd = true;

}
