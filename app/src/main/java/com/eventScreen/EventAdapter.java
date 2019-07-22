package com.eventScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.divyangdantani.eventmanagementfinal.R;

import java.util.ArrayList;

/**
 * Created by Divyang Dantani on 3/8/2018.
 */

public class EventAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    Context c;
    ArrayList<String> event=new ArrayList<>();
    ArrayList<String> type=new ArrayList<>();
    ArrayList<String> location=new ArrayList<>();
    ArrayList<String> details=new ArrayList<>();
    ArrayList<String> guest=new ArrayList<>();
    ArrayList<String> start_time=new ArrayList<>();
    ArrayList<String> duration=new ArrayList<>();

    public EventAdapter(Context c, ArrayList<String> event,ArrayList<String> type,ArrayList<String> location,ArrayList<String> details,ArrayList<String> guest,ArrayList<String> start_time,ArrayList<String> duration) {
        this.c = c;
        this.event = event;
        this.type = type;
        this.location = location;
        this.details = details;
        this.guest = guest;
        this.start_time = start_time;
        this.duration = duration;
    }

    private static class ViewHolder {
        TextView eventTitle,eventDateTime,eventGuest,eventType,eventDuration,eventLocation,eventDetils;
    }

    @Override
    public int getCount() {
        return event.size();
    }

    @Override
    public Object getItem(int position) {
        return event.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(c).inflate(R.layout.event_details, parent, false);
            viewHolder.eventTitle = convertView.findViewById(R.id.tvEventdetailsTitle);
            viewHolder.eventDateTime = convertView.findViewById(R.id.tvEventdetailsDate);
            viewHolder.eventType = convertView.findViewById(R.id.tvEventdetailsaType);
            viewHolder.eventGuest = convertView.findViewById(R.id.tvEventdetailsaguest);
            viewHolder.eventDuration = convertView.findViewById(R.id.tvEventdetailsDuration);
            viewHolder.eventLocation = convertView.findViewById(R.id.tvEventdetailsLocation);
            viewHolder.eventDetils = convertView.findViewById(R.id.tvEventdetailsBody);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.eventTitle.setText(event.get(position));
        viewHolder.eventDateTime.setText(start_time.get(position));
        viewHolder.eventType.setText(type.get(position));
        viewHolder.eventGuest.setText(guest.get(position));
        viewHolder.eventLocation.setText(location.get(position));
        viewHolder.eventDuration.setText(duration.get(position)+" Minutes");
        viewHolder.eventDetils.setText(details.get(position));
        return convertView;

    }

}

