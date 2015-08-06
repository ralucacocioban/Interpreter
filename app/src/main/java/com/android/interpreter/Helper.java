package com.android.interpreter;

/**
 * Created by demouser on 8/6/15.
 */
public class Helper {

    public int getDropdownLanguagePosition(String currentUserLanguage, String [] languages) {
        //TODO: we can make it better, but array is not large
        for (int i = 0; i < languages.length; ++i) {
            if (languages[i] == currentUserLanguage) {
                return i;
            }
        }
        return 0;
    }
}
