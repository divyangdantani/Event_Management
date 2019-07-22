package com.eventScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.divyangdantani.eventmanagementfinal.Constants;
import com.example.divyangdantani.eventmanagementfinal.R;
import com.example.divyangdantani.eventmanagementfinal.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Event extends Fragment {
    FloatingActionButton add_fab;
    private Event mEvent;
    private TextView tvTitle, tvDate, tvTime,
            tvDuration, tvLocation, tvBody;
    ArrayList<String> event=new ArrayList<>();
    ArrayList<String> type=new ArrayList<>();
    ArrayList<String> location=new ArrayList<>();
    ArrayList<String> details=new ArrayList<>();
    ArrayList<String> guest=new ArrayList<>();
    ArrayList<String> start_time=new ArrayList<>();
    ArrayList<String> duration=new ArrayList<>();
    ListView eventListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_fragment_event, null);

        eventListView = view.findViewById(R.id.eventListView);
      /*
        String[] eventItem = new String[] {"event","type","location","details","guest","start_time","duration"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity() , R.layout.event_details , eventItem);
        eventListView.setAdapter(adapter);*/

        //eventListView.setAdapter(new EventAdapter(getActivity(),event,type,location,details,guest,start_time,duration));
       add_fab = view.findViewById(R.id.add_fab);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),EventScreen.class));
            }
        });
        getEvent();
        return view;
/*
        tvTitle =  view.findViewById(R.id.tvEventdetailsTitle);
        tvDate =  view.findViewById(R.id.tvEventdetailsDate);
        tvTime =  view.findViewById(R.id.tvEventdetailsTime);
        tvDuration =  view.findViewById(R.id.tvEventdetailsDuration);
        tvLocation = view.findViewById(R.id.tvEventdetailsLocation);
        tvBody =  view.findViewById(R.id.tvEventdetailsBody);

        Intent intent = getActivity().getIntent();
        mEvent = intent.getParcelableExtra("event");
        this.setEventDetails();

        add_fab = view.findViewById(R.id.add_fab);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),EventScreen.class));
            }
        });
        return view;*/
    }
   /* private void setEventDetails() {
        tvTitle.setText(this.mEvent.getTitle());
        tvDate.setText(this.mEvent.getDateString());
        tvTime.setText(this.mEvent.getTimeString());
        // TODO find better formatting for duration
        tvDuration.setText(Integer.toString(this.mEvent.getDuration()) + " Minutes");
        tvLocation.setText(this.mEvent.getLocation());
        tvBody.setText(this.mEvent.getDetails());
    }
    public Event getEvent() {
        return mEvent;

    }*/

   public void getEvent()
   {

       StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.EVENT_URL2,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONArray jsonArray = new JSONArray(response);
                           for (int i = 0; i < jsonArray.length(); i++) {
                               JSONObject jsonObject =jsonArray.getJSONObject(i);
                             final String event1 = jsonObject.getString("event");
                               String type1 = jsonObject.getString("type");
                               String location1 = jsonObject.getString("location");
                               String details1 = jsonObject.getString("details");
                               String guest1 = jsonObject.getString("guest");
                               String start_time1 = jsonObject.getString("start_time");
                               String duration1 = jsonObject.getString("duration");
                               event.add(event1);
                               type.add(type1);
                               location.add(location1);
                               details.add(details1);
                               guest.add(guest1);
                               start_time.add(start_time1);
                               duration.add(duration1);
                               EventAdapter es=new EventAdapter(getActivity(),event,type,location,details,guest,start_time,duration);
                               eventListView.setAdapter(es);
                               Log.d("120000",response);
                           }
                       } catch (JSONException e) {
                           Log.d("120000",response);
                           e.printStackTrace();
                       }

                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                   }
               }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();

               return params;
           }
       };

       stringRequest.setRetryPolicy(new DefaultRetryPolicy(
               15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
       ));
       RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
   }

}
