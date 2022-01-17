package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lgfood_duan1.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Nhap_SDT extends AppCompatActivity {
    private  String SDT;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private CountryCodePicker mCountryCode;
    private EditText mPhoneNumber;

    private Button mGenerateBtn;
//    private ProgressBar mLoginProgress;

    private TextView mLoginFeedbackText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_sdt);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mCountryCode = findViewById(R.id.edtSDk);
        mPhoneNumber = findViewById(R.id.edtSDTDK);
        mGenerateBtn = findViewById(R.id.btnOTP_DK);
//        mLoginProgress = findViewById(R.id.login_progress_bar);
        mLoginFeedbackText = findViewById(R.id.login_form_feedback);

        mGenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String country_code = mCountryCode.getText().toString();
                String phone_number = mPhoneNumber.getText().toString();



                String complete_phone_number = "+" + mCountryCode + phone_number;

                if(phone_number.isEmpty()){
                    mLoginFeedbackText.setText("Vui lòng điền vào biểu mẫu để tiếp tục.");
                    mLoginFeedbackText.setVisibility(View.VISIBLE);
                } else {

                    final Dialog dialog = new Dialog(Nhap_SDT.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.item_login);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            Intent intent=new Intent(Sin_Up_Activity.this,trangChu_SanPham_Activity.class);
//                            startActivity(intent);
                                dialog.dismiss();
                        }
                    },3000);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    mGenerateBtn.setEnabled(false);


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            complete_phone_number,
                            60,
                            TimeUnit.SECONDS,
                            Nhap_SDT.this,
                            mCallbacks

                    );


                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationFailed(FirebaseException e) {
                mLoginFeedbackText.setText("Mã xác nhận thất bại. Vui lòng thử lại.");
                mLoginFeedbackText.setVisibility(View.VISIBLE);
//                mLoginProgress.setVisibility(View.INVISIBLE);
                mGenerateBtn.setEnabled(true);
            }

            @Override
            public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mPhoneNumber = findViewById(R.id.edtSDTDK);
                String phone_number = mPhoneNumber.getText().toString();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {

                                Intent otpIntent = new Intent(Nhap_SDT.this, OTP.class);
                                otpIntent.putExtra("AuthCredentials", s);
                                otpIntent.putExtra("sdt",phone_number);
                                startActivity(otpIntent);

                            }
                        },
                        5000);

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }
        };
    }
}