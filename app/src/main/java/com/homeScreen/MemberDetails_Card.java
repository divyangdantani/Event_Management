package com.homeScreen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.divyangdantani.eventmanagementfinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemberDetails_Card extends AppCompatActivity {

    RecyclerView recyclerView;
    MemberAdepter adepter;

    List<MemberModel> memberList;

    private static final String MEMBER_URL = "http://192.168.43.46/android/v1/memberDetails.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details_card);

        memberList = new ArrayList<>();
        recyclerView = findViewById(R.id.member_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMemberDetails();

    }

    private void loadMemberDetails()
    {
        final ProgressDialog progressDialog = new ProgressDialog(MemberDetails_Card.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progressDialog.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MEMBER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray member = new JSONArray(response);
                            for(int i=0;i<member.length();i++)
                            {
                                JSONObject memberObject = member.getJSONObject(i);
                                String name = memberObject.getString("name");
                                String username = memberObject.getString("username");
                                String email = memberObject.getString("email");
                                String mobileno = memberObject.getString("mobileno");

                                MemberModel members = new MemberModel(name,email,username,mobileno);
                                memberList.add(members);
                            }
                            adepter = new MemberAdepter(getApplicationContext(),memberList);
                            recyclerView.setAdapter(adepter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
