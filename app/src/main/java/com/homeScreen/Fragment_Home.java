package com.homeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.divyangdantani.eventmanagementfinal.R;

public class Fragment_Home extends Fragment {

     CardView _payment_card,_link_card,_add_card,_notification_card,_member_card;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_home,null);
         _payment_card = view.findViewById(R.id.payment_card);
         _link_card = view.findViewById(R.id.link_card);
         _add_card = view.findViewById(R.id.add_card);
         _notification_card = view.findViewById(R.id.notification_card);
         _member_card = view.findViewById(R.id.member_card);
         paymentDetails();
         linksDetails();
         addDetails();
         notificationDetails();
         memberDetails();
        return view;
    }

    public void memberDetails()
    {
        _member_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MemberDetails_Card.class));
            }
        });
    }

    public void paymentDetails()
    {
        _payment_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Payment_Card.class));
            }
        });
    }

    public void linksDetails()
    {
        _link_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Links_Card.class));
            }
        });
    }

    public void addDetails()
    {
        final View view;
        _add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Add_Card.class));

            }
        });
    }

    public void notificationDetails()
    {
        _notification_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Notification_Card.class));
            }
        });
    }

}

