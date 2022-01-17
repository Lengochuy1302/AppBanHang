package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class OTP extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String mAuthVerificationId;

    private EditText mOtpText,RealName,TenTK, MatKhau,NhapLaiMK,DiaChi,Gmail;
    private Button mVerifyBtn;

    private ProgressBar mOtpProgress;

    private TextView mOtpFeedback;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference node = db.getReference("TaiKhoan");
    private String SDT;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Intent intent = getIntent();
        SDT = intent.getStringExtra("sdt");


        TenTK = findViewById(R.id.edtTenTK);
        MatKhau = findViewById(R.id.edtMatKhau);
        RealName = findViewById(R.id.edtRealName);
        NhapLaiMK = findViewById(R.id.edtNhapLaiMatKhau);
        DiaChi = findViewById(R.id.edtDiaChi);
        Gmail = findViewById(R.id.edtGmail);


        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        mOtpFeedback = findViewById(R.id.otp_form_feedback1);
        mOtpProgress = findViewById(R.id.otp_progress_bar1);
        mOtpText = findViewById(R.id.edtOTPOTP1);

        mVerifyBtn = findViewById(R.id.btnOTPOTP1);

        mVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = mOtpText.getText().toString();

                if(otp.isEmpty()){

                    mOtpFeedback.setVisibility(View.VISIBLE);
                    mOtpFeedback.setText("Vui lòng điền vào biểu mẫu và thử lại.");

                } else {

                    mOtpProgress.setVisibility(View.VISIBLE);
                    mVerifyBtn.setEnabled(false);

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, otp);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });

    }//thai: rememberUser
    private void rememberUser(String idUser,String idGioHang,String user,String password,boolean status,String viTri,String idViTri,String idGioHangTam,String nameUser,String anhUser,String idDanhSachYeuThich,String idDanhSachDonHang){

        SharedPreferences pref=getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        if (!status){
            editor.putString("USERNAME",user);
            editor.putString("PASSWORD",password);
            editor.putString("IDUSRE",idUser);
            editor.putString("IDGIOHANG",idGioHang);
            editor.putString("VITRI",viTri);
            editor.putString("IDVITRI",idViTri);
            editor.putString("IDGIOHANGTAM",idGioHangTam);
            editor.putString("NAMEUSER",nameUser);
            editor.putString("ANHUSER",anhUser);
            editor.putString("IDDANHSACHYEUTHICH",idDanhSachYeuThich);
            editor.putString("IDDANHSACHDONHANG",idDanhSachDonHang);
        }else {
            editor.putString("USERNAME",user);
            editor.putString("PASSWORD",password);
            editor.putBoolean("REMEMBER",status);
            editor.putString("IDUSRE",idUser);
            editor.putString("IDGIOHANG",idGioHang);
            editor.putString("VITRI",viTri);
            editor.putString("IDVITRI",idViTri);
            editor.putString("IDGIOHANGTAM",idGioHangTam);
            editor.putString("NAMEUSER",nameUser);
            editor.putString("ANHUSER",anhUser);
            editor.putString("IDDANHSACHYEUTHICH",idDanhSachYeuThich);
            editor.putString("IDDANHSACHDONHANG",idDanhSachDonHang);


        }
        editor.commit();
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {






                            String getTenTK = TenTK.getText().toString().trim();
                            String getMatKhau = MatKhau.getText().toString().trim();
                            String getRepeatMK=NhapLaiMK.getText().toString().trim();
                            String getRealName = RealName.getText().toString().trim();
                            String getDiaChi = DiaChi.getText().toString().trim();
                            String getGmail = Gmail.getText().toString().trim();
                            if (getRealName.length()<6 || getRealName.length()>100)
                            {
                                RealName.setError("họ và tên khách hàng gồm 6 - 50 kí tự ");
                            }
                            else if (getTenTK.length()<6 || getTenTK.length()>50)
                            {
                                TenTK.setError("Tên đăng nhập gồm 6 - 50 kí tự ");
                            }else if (TenTK.getText().toString().contains(" ")){
                                TenTK.setError("Không được có khoảng trống");
                            } else if (!getGmail.matches(emailPattern))
                            {
                                Gmail.setError("Sai định dạng Email");
                            }else if (getMatKhau.isEmpty() || getMatKhau.length()<6)
                            {
                                MatKhau.setError("Mật khẩu đang trống hoặc bé hơn 6 kí tự");
                            }else if (!getMatKhau.equals(getRepeatMK)) {
                                NhapLaiMK.setError("Mật khẩu không khớp");
                            }else if (getDiaChi.length()<6 || getDiaChi.length()>150){
                                DiaChi.setError("Địa Chỉ lớn hơn 6 và  không quá 100 kí tự");
                            }
                            else{
                                //    FirebaseStorage
//                                if (task.isSuccessful()) {

                                    sendUserToHome();

                                model_Account account = new model_Account(UUID.randomUUID().toString(),getRealName,getTenTK,getMatKhau,getDiaChi,getGmail,SDT,UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString(),"");
                                node.child(account.getId()).setValue(account);
                                rememberUser(account.getId(),account.getIdGioHang(),getGmail,getMatKhau,true,getDiaChi,account.getIdViTri(),account.getIdGioHangTam(),getRealName,account.getAnhKhachHang(),account.getIdDanhSachYeuThich(),account.getIdDanhSachDonHang());


                                Intent homeIntent = new Intent(OTP.this, MainActivity.class);

                                startActivity(homeIntent);
//                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    mOtpFeedback.setVisibility(View.VISIBLE);
                                    mOtpFeedback.setText("There was an error verifying OTP");
//                                }
                            }
                            }
                            // add lên retimedatabase




                        mOtpProgress.setVisibility(View.INVISIBLE);
                        mVerifyBtn.setEnabled(true);
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser != null){
            sendUserToHome();
        }
    }

    public void sendUserToHome() {
    }
}