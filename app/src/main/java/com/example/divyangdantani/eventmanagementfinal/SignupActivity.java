package com.example.divyangdantani.eventmanagementfinal;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    String value1 = "";

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_address) EditText _username;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.memberNo) Spinner _spMember;
    @BindView(R.id.spRelation1) Spinner _spMemberRelation1;
    @BindView(R.id.spRelation2) Spinner _spMemberRelation2;
    @BindView(R.id.spRelation3) Spinner _spMemberRelation3;
    @BindView(R.id.edMember1) EditText _edMember1;
    @BindView(R.id.edMember2) EditText _edMember2;
    @BindView(R.id.edMember3) EditText _edMember3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _spMemberRelation1.setVisibility(View.GONE);
        _edMember1.setVisibility(View.GONE);

        cheackUserLogin();
        memberName();
        memberRelation();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == _signupButton)

                    signup();
                    //onSignupSuccess();

            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }


        _signupButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String name = _nameText.getText().toString();
        final String username = _username.getText().toString();
        final String email = _emailText.getText().toString();
        final String mobileno = _mobileText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String memberNo = _spMember.getSelectedItem().toString();
        //String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                           /* boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                SignupActivity.this.startActivity(intent);
                            }else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }*/
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            notification();
                            //onSignupSuccess();
                        } catch (JSONException e) {
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("username", username);
                params.put("email", email);
                params.put("mobileno", mobileno);
                params.put("password", password);
                params.put("memberNo",memberNo);
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        //finish();
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        notification();
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String username = _username.getText().toString();
        String email = _emailText.getText().toString();
        String mobileno = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
       // String memberNo = _spMember.getSelectedItem().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(value1);
        }

        if (username.isEmpty()) {
            _username.setError("Enter Valid FlatNo.");
            valid = false;
        } else {
            _username.setError(value1);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(value1);
        }

        if (mobileno.isEmpty() || mobileno.length() != 10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(value1);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(value1);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(value1);
        }

        return valid;

    }

    public void cheackUserLogin()
    {
        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(getApplicationContext(),BottomActivity.class));
            return;
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    public void notification() {
        //Define Notification Manager

        String name = _nameText.getText().toString();

        Context context = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

//Define sound URI
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.event)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Hello "+name+" your Registration is Successful")
                .setSound(soundUri); //This sets the sound to play

//Display notification
        notificationManager.notify(0, mBuilder.build());
    }


    public void memberName()
    {

        String[] member = {"No. of Member","1", "2", "3"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, member);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spMember.setAdapter(arrayAdapter);

        _spMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();
                if(item.equals("1"))
                {
                    _edMember1.setVisibility(View.VISIBLE);
                    _spMemberRelation1.setVisibility(View.VISIBLE);
                    _edMember2.setVisibility(View.GONE);
                    _spMemberRelation2.setVisibility(View.GONE);
                    _edMember3.setVisibility(View.GONE);
                    _spMemberRelation3.setVisibility(View.GONE);
                }
                else if(item.equals("2"))
                {
                    _edMember1.setVisibility(View.VISIBLE);
                    _spMemberRelation1.setVisibility(View.VISIBLE);
                    _edMember2.setVisibility(View.VISIBLE);
                    _spMemberRelation2.setVisibility(View.VISIBLE);
                    _edMember3.setVisibility(View.GONE);
                    _spMemberRelation3.setVisibility(View.GONE);
                }
                else if(item.equals("3"))
                {
                    _edMember1.setVisibility(View.VISIBLE);
                    _spMemberRelation1.setVisibility(View.VISIBLE);
                    _edMember2.setVisibility(View.VISIBLE);
                    _spMemberRelation2.setVisibility(View.VISIBLE);
                    _edMember3.setVisibility(View.VISIBLE);
                    _spMemberRelation3.setVisibility(View.VISIBLE);
                }
                else {
                    _edMember1.setVisibility(View.GONE);
                    _spMemberRelation1.setVisibility(View.GONE);
                    _edMember2.setVisibility(View.GONE);
                    _spMemberRelation2.setVisibility(View.GONE);
                    _edMember3.setVisibility(View.GONE);
                    _spMemberRelation3.setVisibility(View.GONE);
                    _edMember1.setVisibility(View.GONE);
                    _spMemberRelation1.setVisibility(View.GONE);
                    _edMember2.setVisibility(View.GONE);
                    _spMemberRelation2.setVisibility(View.GONE);
                    _edMember3.setVisibility(View.GONE);
                    _spMemberRelation3.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public  void memberRelation()
    {
        String[] member = {"Father","Mother", "Son", "Daughter","Wife","Brother"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, member);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spMemberRelation1.setAdapter(arrayAdapter);
        _spMemberRelation2.setAdapter(arrayAdapter);
        _spMemberRelation3.setAdapter(arrayAdapter);
    }

}












