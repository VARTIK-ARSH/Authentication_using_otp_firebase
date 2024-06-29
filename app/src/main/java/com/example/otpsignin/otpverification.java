package com.example.otpsignin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpverification extends AppCompatActivity {

    EditText N1,N2,N3,N4,N5,N6;
    Button verifyotp,forward;

    String getotpbackend;
    TextView resendotp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);

//        forward=findViewById(R.id.forward);
//
//
//        forward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(otpverification.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        verifyotp=findViewById(R.id.verifyOTP);
        resendotp=findViewById(R.id.resendotp);
        N1 = findViewById(R.id.input1);
        N2 = findViewById(R.id.input2);
        N3 = findViewById(R.id.input3);
        N4 = findViewById(R.id.input4);
        N5 = findViewById(R.id.input5);
        N6 = findViewById(R.id.input6);

        TextView textView=findViewById(R.id.textmobile);
        textView.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));

        getotpbackend=getIntent().getStringExtra("backendotp");
       final ProgressBar progressBarverify=findViewById(R.id.progressbar_verify_otp);


        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!N1.getText().toString().trim().isEmpty() && !N2.getText().toString().trim().isEmpty() && !N3.getText().toString().trim().isEmpty() && !N4.getText().toString().trim().isEmpty() && !N5.getText().toString().trim().isEmpty() && !N6.getText().toString().trim().isEmpty())
                {
                    String entercodeotp = N1.getText().toString()+
                            N2.getText().toString()+
                            N3.getText().toString()+
                            N4.getText().toString()+
                            N5.getText().toString()+
                            N6.getText().toString();

                    if(getotpbackend!=null)
                    {
                        progressBarverify.setVisibility(View.VISIBLE);
                        verifyotp.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                                getotpbackend,entercodeotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            progressBarverify.setVisibility(View.GONE);
                                            verifyotp.setVisibility(View.VISIBLE);
                                            Intent intent=new Intent(otpverification.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                        else {
                                            progressBarverify.setVisibility(View.GONE);
                                            verifyotp.setVisibility(View.VISIBLE);
                                            Toast.makeText(otpverification.this, "Enter correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                    else {
                        progressBarverify.setVisibility(View.GONE);
                        verifyotp.setVisibility(View.VISIBLE);
                        Toast.makeText(otpverification.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    progressBarverify.setVisibility(View.GONE);
                    verifyotp.setVisibility(View.VISIBLE);
                    Toast.makeText(otpverification.this, "Enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpmove();

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("mobile"), 60, TimeUnit.SECONDS, otpverification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        progressBarverify.setVisibility(View.GONE);
                        verifyotp.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        progressBarverify.setVisibility(View.GONE);
                        verifyotp.setVisibility(View.VISIBLE);
                        Toast.makeText(otpverification.this, "Verification failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        progressBarverify.setVisibility(View.VISIBLE);
                        verifyotp.setVisibility(View.INVISIBLE);

                        getotpbackend=newbackendotp;
                        Toast.makeText(otpverification.this, "OTP resend", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void numberotpmove() {

        N1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                {
                    N2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        N2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                {
                    N3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        N3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                {
                    N4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        N4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                {
                    N5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        N5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                {
                    N6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}