package com.android.interpreter;

import java.util.HashMap;

/**
 * Created by demouser on 8/6/15.
 */
public class Config {
    public static final String mainFireBaseRef = "https://flickering-heat-70.firebaseio.com/interpreter/";
    public static final String usersFirebasePath = "https://flickering-heat-70.firebaseio.com/interpreter/user/";
    public static final String convsPath = "https://flickering-heat-70.firebaseio.com/interpreter/conversations/";

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String BOSS_API_KEY = "AIzaSyCXQPEmG2qw5C5iPCDWi3KieBzM7WtyIQY";

    public static final String[] sendLanguageArray = new String[]{"English", "Russian", "Ukrainian", "Polish", "Dutch", "Romanian"};
    public static final String[] receiveLanguageArray = new String[]{"English", "Russian", "Ukrainian", "Polish", "Dutch", "Romanian"};
    private static final HashMap<String,String> langCodes = new HashMap<String, String>()
    {{     put("English",   "en");
                put("Russian",  "rus");
                put("Ukrainian","uk");
                put("Polish",   "pl");
                put("Dutch",    "nl");
                put("Romanian", "ro");
            }};

    public static String getLangCode(String lang){
        return langCodes.get(lang);
    }
}
