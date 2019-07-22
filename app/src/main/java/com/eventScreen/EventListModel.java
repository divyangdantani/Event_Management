package com.eventScreen;

/**
 * Created by Divyang Dantani on 3/8/2018.
 */

public class EventListModel {



    private String mEvent,mType,mLocation,mDetails,mGuest,mStart_time,mDuration;
    private String mImage;

    public EventListModel(String mEvent, String mType, String mLocation, String mDetails, String mGuest, String mStart_time, String mDuration) {
        this.mEvent = mEvent;
        this.mType = mType;
        this.mLocation = mLocation;
        //this.mImage = mImage;
        this.mDetails = mDetails;
        this.mGuest = mGuest;
        this.mStart_time = mStart_time;
        this.mDuration = mDuration;
    }

    public String getmEvent() {
        return mEvent;
    }

    public String getmType() {
        return mType;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmDetails() {
        return mDetails;
    }

    public String getmGuest() {
        return mGuest;
    }

    public String getmDuration() {
        return mDuration;
    }
}
