package com.example.divyangdantani.eventmanagementfinal;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Divyang Dantani on 2/2/2018.
 */

public class SharedPrefManager {

        private static SharedPrefManager mInstance;
        private static Context mCtx;



        private static final String SHARED_PREF_NAME = "mysharedpref12";
        private static final String KEY_USERNAME = "username";
        private static final String KEY_USER_EMAIL = "useremail";
        private static final String KEY_USER_ID = "user_id";
        private static final String KEY_USER_NAME = "name";
        private static final String KEY_USER_MOBILE = "mobileno";
        private static final String KEY_USER_MEMBER = "memberNo";
        private static final String KEY_DATE_FROM = "dateFrom";
        private static final String KEY_TIME_FROM = "timeFrom";


        public SharedPrefManager(Context context)
        {
            mCtx=context;
        }



        public static synchronized SharedPrefManager getInstance(Context context){
            if (mInstance == null) {
                mInstance = new SharedPrefManager(context);
            }
            return mInstance;
        }

        public boolean dateTime(String dateFrom,String timeFrom)
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_DATE_FROM,dateFrom);
            editor.putString(KEY_TIME_FROM,timeFrom);

            editor.apply();
            return true;
        }

        public  boolean userLogin(String id,String username,String email,String name,String mobileno,String memberNo)
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_USER_ID,id);
            editor.putString(KEY_USERNAME,username);
            editor.putString(KEY_USER_EMAIL,email);
            editor.putString(KEY_USER_NAME,name);
            editor.putString(KEY_USER_MOBILE,mobileno);
            editor.putString(KEY_USER_MEMBER,memberNo);

            editor.apply();
            return true;
        }

        public boolean isLoggedIn()
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            if(sharedPreferences.getString(KEY_USERNAME,null) !=null) {
                return true;
            }
            return false;
        }

        public boolean logout()
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            return true;
        }

    public String getUserId()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }
        public String getUsername()
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USERNAME,null);
        }

        public String getname()
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USER_NAME,null);
        }

        public String getMobile()
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USER_MOBILE,null);
        }
        public String getEmail()
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USER_EMAIL,null);
        }
        public String getMemberNo()
         {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USER_MEMBER,null);
        }

        public String getDateFrom()
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_DATE_FROM,null);
        }

    public String getTimeFrom()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TIME_FROM,null);
    }

   /* public void setEventsIds(List<Event> eventList) {
        Set<String> idStrSet = new HashSet<>();
        for (Event ev: eventList) {
            idStrSet.add(Integer.toString(ev.getId()));
        }
        this.setStringSet(PREF_EVENTS_IDS, idStrSet);
    }

    public Set<Integer> getEventsIds() {
        Set<String> idsStrSet = mInstance.getStringSet(PREF_EVENTS_IDS, null);
        if (idsStrSet == null) {
            return null;
        }
        Set<Integer> idsSet = new HashSet<>();
        for (String idStr : idsStrSet) {
            idsSet.add(Integer.parseInt(idStr));
        }
        return idsSet;
    }

    private void setString(String key, String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void setStringSet(String key, Set<String> strSet) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putStringSet(key, strSet);
        editor.apply();
    }
*/
}

