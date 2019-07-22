package com.eventScreen;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.divyangdantani.eventmanagementfinal.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Filter_Dialog extends Dialog
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    private EventScreen mActivity;
    private CheckBox cbLimitDaysOfWeek, cbLimitTimeOfDay, cbLimitDate;
    private ViewGroup vgLimitDaysOfWeek, vgLimitTimeOfDay, vgLimitDate;
    private Button btnTimeOfDayFrom, btnTimeOfDayTo, btnDateFrom, btnDateTo;
    private Button btnDone;
    private TextView tvTimeFrom, tvTimeTo, tvDateFrom, tvDateTo;

    public String dateFromStr, dateToStr, timeFromStr, timeToStr;

    public Filter_Dialog(EventScreen context) {
        super(context);
        mActivity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_filter__dialog);


        cbLimitTimeOfDay = (CheckBox) findViewById(R.id.cbLimitTimeOfDay);
        cbLimitDate = (CheckBox) findViewById(R.id.cbLimitDate);

        cbLimitTimeOfDay.setOnCheckedChangeListener(this);
        cbLimitDate.setOnCheckedChangeListener(this);

        vgLimitTimeOfDay = (ViewGroup) findViewById(R.id.containerTimeOfDay);
        vgLimitDate = (ViewGroup) findViewById(R.id.containerLimitDate);

        btnTimeOfDayFrom = (Button) findViewById(R.id.btnTimeOfDayFrom);
        btnTimeOfDayTo = (Button) findViewById(R.id.btnTimeOfDayTo);
        btnDateFrom = (Button) findViewById(R.id.btnDateFrom);
        btnDateTo = (Button) findViewById(R.id.btnDateTo);

        btnDone = (Button) findViewById(R.id.btnDone);

        tvDateFrom = (TextView) findViewById(R.id.tvDateFrom);
        tvDateTo = (TextView) findViewById(R.id.tvDateTo);
        tvTimeFrom = (TextView) findViewById(R.id.tvTimeOfDayFrom);
        tvTimeTo = (TextView) findViewById(R.id.tvTimeOfDayTo);


        btnTimeOfDayFrom.setOnClickListener(this);
        btnTimeOfDayTo.setOnClickListener(this);
        btnDateFrom.setOnClickListener(this);
        btnDateTo.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());

        switch(v.getId()) {

            case R.id.btnTimeOfDayFrom:
                TimePickerDialog tpFrom = new TimePickerDialog(mActivity, new TimeFromListener(),
                        cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                tpFrom.show();
                break;
            case R.id.btnTimeOfDayTo:
                TimePickerDialog tpTo = new TimePickerDialog(mActivity, new TimeToListener(),
                        cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                tpTo.show();
                break;
            case R.id.btnDateFrom:
                DatePickerDialog dpFrom = new DatePickerDialog(mActivity, new DateFromListener(),
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dpFrom.show();
                break;
            case R.id.btnDateTo:
                DatePickerDialog dpTo = new DatePickerDialog(mActivity, new DateToListener(),
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dpTo.show();
                break;
            case R.id.btnDone:
                mActivity.timeFromStr = this.timeFromStr;
                mActivity.timeToStr = this.timeToStr;
                mActivity.dateFromStr = this.dateFromStr;
                mActivity.dateToStr = this.dateToStr;
                /*String date = tvDateFrom.getText().toString();
                String time = tvTimeFrom.getText().toString();

                Intent intent = new Intent(getContext(),EventScreen.class);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                mActivity.startActivity(intent);*/

                dismiss();
        }
    }


    public class TimeFromListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            DateManager dateManager = new DateManager();
            dateManager.setTime(hourOfDay, minute, 0);
            timeFromStr = dateManager.getTime();
            tvTimeFrom.setText(timeFromStr);
        }
    }

    private class TimeToListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            DateManager dateManager = new DateManager();
            dateManager.setTime(hourOfDay, minute, 0);
            timeToStr = dateManager.getTime();
            tvTimeTo.setText(timeToStr);
        }
    }

    private class DateFromListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            DateManager dateManager = new DateManager();
            dateManager.setDate(year, monthOfYear, dayOfMonth);
            dateFromStr = dateManager.getDate();
            tvDateFrom.setText(dateFromStr);
        }
    }

    private class DateToListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            DateManager dateManager = new DateManager();
            dateManager.setDate(year, monthOfYear, dayOfMonth);
            dateToStr = dateManager.getDate();
            tvDateTo.setText(dateToStr);
        }
    }





    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ViewGroup vg = null;
        switch (buttonView.getId()) {
            case R.id.cbLimitTimeOfDay:
                vg = vgLimitTimeOfDay;
                break;
            case R.id.cbLimitDate:
                vg = vgLimitDate;
                break;
        }
        if (vg != null) {
            if (isChecked)
                vg.setVisibility(View.VISIBLE);
            else
                vg.setVisibility(View.GONE);
        }

    }
}
