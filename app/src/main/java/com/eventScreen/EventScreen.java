package com.eventScreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.divyangdantani.eventmanagementfinal.Constants;
import com.example.divyangdantani.eventmanagementfinal.PaypalPaymentActivity;
import com.example.divyangdantani.eventmanagementfinal.R;
import com.example.divyangdantani.eventmanagementfinal.RequestHandler;
import com.example.divyangdantani.eventmanagementfinal.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventScreen extends AppCompatActivity {

    private static final String TAG = "EventScreen";

    public String timeFromStr, timeToStr, dateFromStr, dateToStr,dateMessage,timeMessage,final_Time,final_Date;

    private Button btnFilterOptions, btnCreate, btnCancel, btnRefreshSuggestions,btnAddDate,btnAddTime;;
    private EditText etEventTitle, etEventDetails,
            etDurationHours, etDurationMinutes,etGuest;
    private ViewGroup containerMembers;
    private Spinner spinTime, etEventLocation, etEventType;
    private List<Member> mMembers;

    private List<Member> mSelectedMembers;
    //    private Long mSelectedTimeStamp = null;
    private Long mSelectedUnixTimeStamp;
    private String eventTitle, eventLocation, eventDetails,eventType,finalDate,finalTime;
    private int durationHours, durationMinutes, durationTotalMinutes;
    private TextView txtDate,txtTime,txtDateTime;

   /* public List<Filter_Dialog.TimeFromListener> settime;
    Context mctx;

    public EventScreen(Context mctx, List<Filter_Dialog.TimeFromListener> settime){
        this.mctx = mctx;
        this.settime = settime;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);

        /*btnFilterOptions = (Button) findViewById(R.id.btnFilterOptions);
        btnFilterOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Dialog Appear here!!!", Toast.LENGTH_LONG);
                 Filter_Dialog filterDialog = new Filter_Dialog(EventScreen.this);
                filterDialog.show();
            }
        });*/

        btnCreate = findViewById(R.id.btnEventCreate);
        btnCancel = findViewById(R.id.btnEventCancel);
        btnAddDate = findViewById(R.id.btnAddDate);
        btnAddTime = findViewById(R.id.btnAddTime);

        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        btnAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });


        // btnRefreshSuggestions = findViewById(R.id.btnRefreshSuggestions);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    createEvent();
                   /* CreateEventTask createEventTask = new CreateEventTask();
                    createEventTask.execute();*/
                   startActivity(new Intent(getApplication(), PaypalPaymentActivity.class));
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* btnRefreshSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshSuggestions();
            }
        });*/

        etEventTitle = findViewById(R.id.etEventTitle);
        etEventType = findViewById(R.id.eventType);
        etEventLocation = findViewById(R.id.etEventLocation);
        etEventDetails = findViewById(R.id.etEventDetails);
        etDurationHours = findViewById(R.id.etDurationHours);
        etDurationMinutes = findViewById(R.id.etDurationMinutes);
        etGuest = findViewById(R.id.eventGuest);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);



        /* Filter_Dialog.TimeFromListener timeFromListener = settime.;
        txtTime.setText();
*/

        /*txtDate.setText(getIntent().getStringExtra("date"));
        txtTime.setText(getIntent().getStringExtra("time"));*/

        //spinTime = findViewById(R.id.spinTime);
        // TODO Remove this
//        this.tempFillSpinner();

        containerMembers = findViewById(R.id.containerMembers);
        containerMembers.setVisibility(View.GONE);

        mSelectedMembers = new ArrayList<>();
       /* GetPrivilegesTask getPrivilegesTask = new GetPrivilegesTask();
        getPrivilegesTask.execute();*/

        EventLocation();
        EventType();
    }

    /*public void getDateTime()
    {
        Filter_Dialog filter_dialog = new Filter_Dialog();
    }*/

    public void EventLocation() {
        String[] location = {" Select Location ", "Garden", "Club House", "other location"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, location);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etEventLocation.setAdapter(arrayAdapter);

        etEventLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String area = parent.getSelectedItem().toString();

                if (area.equalsIgnoreCase("Garden")) {
                    Toast.makeText(getApplicationContext(), "Garden is Selected", Toast.LENGTH_SHORT).show();
                } else if (area.equalsIgnoreCase("Club House")) {
                    Toast.makeText(getApplicationContext(), "Club House is Selected", Toast.LENGTH_SHORT).show();
                } else if (area.equalsIgnoreCase("other location")) {
                    Toast.makeText(getApplicationContext(), "other location is Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Location", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Toast.makeText(getApplicationContext(), "Please Select Location for Your Event!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void EventType() {
        String[] location = {" Select Type ", "Personal", "Public", "Other Event"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, location);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etEventType.setAdapter(arrayAdapter);

        etEventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getSelectedItem().toString();

                if (type.equalsIgnoreCase("Personal")) {
                    Toast.makeText(getApplicationContext(), "Personal Event is Selected", Toast.LENGTH_SHORT).show();
                } else if (type.equalsIgnoreCase("Public")) {
                    Toast.makeText(getApplicationContext(), "Public Event is Selected", Toast.LENGTH_SHORT).show();
                } else if (type.equalsIgnoreCase("Other Event")) {
                    Toast.makeText(getApplicationContext(), "Other Event is Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Type of Event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Toast.makeText(getApplicationContext(), "Please Select Type of Event!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createEvent()
    {
       Log.d(TAG,"Event");

       if(!validateFields())
       {
           onCreateEventFailed();
           return;
       }
        btnCreate.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(EventScreen.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Event...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String userId = SharedPrefManager.getInstance(getApplicationContext()).getUserId();
        final String eventTitle = etEventTitle.getText().toString();
        final String eventType = etEventType.getSelectedItem().toString();
        final String eventLocation = etEventLocation.getSelectedItem().toString();
        final String eventDescription = etEventDetails.getText().toString();
        final String eventGuest = etGuest.getText().toString();
        final String start_time = txtDate.getText().toString()+" "+txtTime.getText().toString();
        final String eventDuration = Integer.toString(durationTotalMinutes);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.EVENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                /*HashMap<String, Set<Integer>> header = new HashMap<>();
                header.put("Authorization",
                        new PreferencesManager(EventScreen.this, null)
                                .getEventsIds());*/

                Map<String,String> params = new HashMap<>();
                params.put("user_id",userId);
                params.put("event",eventTitle);
                params.put("type",eventType);
                params.put("location",eventLocation);
                params.put("details",eventDescription);
                params.put("guest",eventGuest);
                params.put("start_time",start_time);
                params.put("duration",eventDuration);
               /*Log.d("user_id",userId);
               Log.d("event",eventTitle);
               Log.d("type",eventType);
               Log.d("location",eventLocation);
               Log.d("details",eventDescription);
               Log.d("guest",eventGuest);
               Log.d("start_time",start_time);
               Log.d("duration",eventDuration);*/
               return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public void onCreateEventFailed() {
        Toast.makeText(getBaseContext(), "Event Registration failed!!!", Toast.LENGTH_LONG).show();
        btnCreate.setEnabled(true);
    }


   /* public void refreshSuggestions() {
        GregorianCalendar cal = new GregorianCalendar();
        long currentTimeMillis = System.currentTimeMillis();
        cal.setTimeInMillis(currentTimeMillis);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dateFrom, dateTo;
        if (dateFromStr != null && !dateFromStr.isEmpty()) {
            try {
                dateFrom = formatter.parse(dateFromStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            dateFrom = cal.getTime();
            dateFromStr = formatter.format(dateFrom);
        }

        if (dateToStr != null && !dateToStr.isEmpty()) {
            try {
                dateTo = formatter.parse(dateToStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 7);
            dateTo = cal.getTime();
            dateToStr = formatter.format(dateTo);
        }

        if (timeFromStr == null || timeFromStr.isEmpty()) {
            timeFromStr = "00:00:00";
        }

        if (timeToStr == null || timeToStr.isEmpty()) {
            timeToStr = "23:59:00";
        }

        calcDuration();
        if (durationTotalMinutes <= 0) {
            Toast.makeText(this, "Please Choose a suitable duration", Toast.LENGTH_SHORT).show();
            return;
        }


      *//* RefreshTimesTask refreshTimesTask = new RefreshTimesTask();
            refreshTimesTask.execute();*//*

    }*/

    private boolean validateFields() {
        eventTitle = etEventTitle.getText().toString();
        if (eventTitle.isEmpty()) {
            Toast.makeText(this, "Please Fill in Title", Toast.LENGTH_SHORT).show();
            return false;
        }

        eventLocation = etEventLocation.getSelectedItem().toString();
        if (eventLocation.isEmpty()) {
            Toast.makeText(this, "Please Fill in Location", Toast.LENGTH_SHORT).show();
            return false;
        }

        eventType = etEventType.getSelectedItem().toString();
        if(eventType.isEmpty()){
            Toast.makeText(this,"Please Select Type of Event",Toast.LENGTH_SHORT).show();
            return false;
        }

        eventDetails = etEventDetails.getText().toString();
        if (eventDetails.isEmpty()) {
            Toast.makeText(this, "Please Fill in Event Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        calcDuration();

        if (durationTotalMinutes <= 0) {
            Toast.makeText(this, "Please Choose a suitable duration", Toast.LENGTH_SHORT).show();
            return false;
        }

       /* if (mSelectedUnixTimeStamp == null) {
            Toast.makeText(this, "Please Choose a suitable Time", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        // TODO fix this after dynamic fill
//        mSelectedUnixTimeStamp = mSelectedTimeStamp;
        return true;
    }

    private void calcDuration() {
        String durationHoursStr = etDurationHours.getText().toString();
        durationHours = durationHoursStr.isEmpty() ? 0 : Integer.parseInt(durationHoursStr);

        String durationMinutesStr = etDurationMinutes.getText().toString();
        durationMinutes = durationMinutesStr.isEmpty() ? 0 : Integer.parseInt(durationMinutesStr);

        durationTotalMinutes = durationMinutes + durationHours * 60;
    }

    //DatePicker

    public void showDatePickerDialog(View view)
    {
        android.app.DialogFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(),getString(R.string.date_picker));
    }

    public void showTimePickerDialog(View view)
    {
        android.app.DialogFragment fragment = new TimePickerFragment();
        fragment.show(getFragmentManager(),getString(R.string.time_picker));
    }

    public void processDatePickerResult(int year,int month,int day)
    {
        String sMonth = Integer.toString(month+1);
        String sDay = Integer.toString(day);
        String sYear = Integer.toString(year);

        dateMessage = (sDay+"-"+sMonth+"-"+sYear);
        txtDate.setText(dateMessage);
        txtDate.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(),dateMessage+" is selected",Toast.LENGTH_LONG).show();

    }

    public void processTimePickerResult(int hourOfDay,int minute)
    {
        String sHour = Integer.toString(hourOfDay);
        String sMinute = Integer.toString(minute);

        timeMessage = (sHour+":"+sMinute);
        txtTime.setText(timeMessage);
        txtTime.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(),timeMessage+" is selected",Toast.LENGTH_LONG).show();

    }

    /*public String finalDateTime()
    {
        finalMessage = dateMessage + timeMessage;
        return finalMessage;
    }*/
}



