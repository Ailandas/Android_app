package com.example.ailandas;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_USER_NAME= "username";
    static final int PREF_ACTIVITY_ID=0;
    static final String PREF_USER_EMAIL="email";
    static final String PREF_USER_IMAGE_URI="";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPrefUserImageUri(Context ctx, String uri){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_IMAGE_URI, uri);
        editor.commit();
    }
    public static String getPrefUserImageUri(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_IMAGE_URI,"");
    }
    public static void setPrefUserEmail(Context ctx, String email){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, email);
        editor.commit();
    }
    public static String getPrefUserEmail(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL,"");
    }
    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);

        editor.commit();
    }

    public static void setPrefActivityId(Context ctx, int id){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();

        editor.putInt("PREF_ACTIVITY_ID", id);

        editor.commit();
    }
    public static int getPrefActivityId(Context ctx){
        return getSharedPreferences(ctx).getInt("PREF_ACTIVITY_ID",0);
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
}
