package com.example.divyangdantani.eventmanagementfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Profile extends Fragment{
    TextView _profileName,_profileUsername,_profileMobile,_profileEmail,_profileMemberNo;
    Button _btnLogout;
    ImageView _profileImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_profile, container, false);
        _profileName = view.findViewById(R.id.profileName);
        _profileUsername = view.findViewById(R.id.profileUsername);
        _profileMobile = view.findViewById(R.id.profileMobile);
        _profileEmail = view.findViewById(R.id.profileEmail);
        _btnLogout = view.findViewById(R.id.btn_logout);
        _profileImage = view.findViewById(R.id.profileImage);
        _profileMemberNo = view.findViewById(R.id.memberno);
        setProfile();
        return view;
    }

 public void setProfile()
    {
      _profileName.setText(SharedPrefManager.getInstance(getActivity()).getname());
      _profileUsername.setText(SharedPrefManager.getInstance(getActivity()).getUsername());
      _profileEmail.setText(SharedPrefManager.getInstance(getActivity()).getEmail());
      _profileMobile.setText(SharedPrefManager.getInstance(getActivity()).getMobile());
      _profileMemberNo.setText(SharedPrefManager.getInstance(getActivity()).getMemberNo());

       _btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getActivity()).logout();
                startActivity(new Intent(getContext(),LoginActivity.class));
                Toast.makeText(getActivity(),"You Logout Successfully",Toast.LENGTH_LONG).show();
            }
        });
        _profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Upload_Image.class));
            }
        });
    }

}
