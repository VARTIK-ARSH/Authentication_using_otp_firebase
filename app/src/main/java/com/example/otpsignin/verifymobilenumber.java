package com.example.otpsignin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifymobilenumber extends AppCompatActivity {

    EditText input_mobile_no;
    Button getotp,forward1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verifymobilenumber);


//        forward1=findViewById(R.id.forward1);
//
//
//        forward1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(verifymobilenumber.this, otpverification.class);
//                startActivity(intent);
//            }
//        });

        Intent i;
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            i=new Intent(verifymobilenumber.this, MainActivity.class);
            startActivity(i);
            finish();

        }
        else
        {
            ProgressBar progressBar=findViewById(R.id.progressbar_sending_otp);

            input_mobile_no = findViewById(R.id.input_moile_number);
            getotp = findViewById(R.id.getotp);

            getotp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!input_mobile_no.getText().toString().trim().isEmpty()){
                        if((input_mobile_no.getText().toString().trim()).length()==10){

                            progressBar.setVisibility(View.VISIBLE);
                            getotp.setVisibility(View.INVISIBLE);

                            PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + input_mobile_no.getText().toString(), 60, TimeUnit.SECONDS, verifymobilenumber.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {

                                    progressBar.setVisibility(View.GONE);
                                    getotp.setVisibility(View.VISIBLE);
                                    Toast.makeText(verifymobilenumber.this, "Verification failed", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    getotp.setVisibility(View.VISIBLE);
                                    Intent intent=new Intent(getApplicationContext(),otpverification.class);
                                    intent.putExtra("mobile",input_mobile_no.getText().toString());
                                    intent.putExtra("backendotp",backendotp);
                                    startActivity(intent);
                                }
                            });

                        }
                        else {
                            Toast.makeText(verifymobilenumber.this, "Please enter correct number ", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(verifymobilenumber.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        }





}