package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_admin;
import com.example.lgfood_duan1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Login_Activity extends AppCompatActivity {

    private TextView
            Login_tv_btn_SignUp,
            Login_tv_btn_SignUpText,
            Login_tv_username,
            Login_tv_password,
            login_tv_Phone,
            login_tv_google;
    private EditText
            Login_edt_username,
            Login_edt_password;
    private LinearLayout
            Login_llout_btn_submid;

    private SharedPreferences shareAcout;
private ImageView Login_tv_back;
    String idSharePre, passSharePre, userSharePre, idShareGioHang, idGioHangTam;
    boolean rememberSharePre;


    //thai: login
    DatabaseReference mData;
    FirebaseDatabase database;
    CheckBox checkBox;
    //thai login gg;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    model_admin arrAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        checkSavePass();
        batSuKien();
        processrequest();
//

    }


    private void batSuKien() {
//        chuyển trang ra trang chủ
        Login_tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, trangChu_SanPham_Activity.class);
                startActivity(intent);
            }
        });
//        chuyển sang trang đăng kí tài khoản
        Login_tv_btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Sin_Up_Activity.class);
                startActivity(intent);
            }
        });
        Login_tv_btn_SignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Sin_Up_Activity.class);

                startActivity(intent);
            }
        });
        login_tv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLogin();
            }
        });
        login_tv_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Nhap_SDT.class);
                startActivity(intent);
            }
        });


        //        check tài khoản
        Login_llout_btn_submid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //      gọi hàm check tài khoản
//                checkValidateSet();
                loginNormal();

            }
        });
    }


    private void checkSavePass() {
        shareAcout = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = shareAcout.edit();
        rememberSharePre = shareAcout.getBoolean("REMEMBER", false);
        if (rememberSharePre == true) {
            Intent intent = new Intent(Login_Activity.this, trangChu_SanPham_Activity.class);
            startActivity(intent);

        } else {
            editor.clear();
            editor.commit();

        }

    }


    //thai sharePreference

    private void rememberUser(String idUser, String idGioHang, String user, String password, boolean status, String viTri, String idViTri, String idGioHangTam, String nameUser, String anhUser, String idDanhSachYeuThich, String idDanhSachDonHang) {

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            editor.putString("USERNAME", user);
            editor.putString("PASSWORD", password);
            editor.putString("IDUSRE", idUser);
            editor.putString("IDGIOHANG", idGioHang);
            editor.putString("VITRI", viTri);
            editor.putString("IDVITRI", idViTri);
            editor.putString("IDGIOHANGTAM", idGioHangTam);
            editor.putString("NAMEUSER", nameUser);
            editor.putString("ANHUSER", anhUser);
            editor.putString("IDDANHSACHYEUTHICH", idDanhSachYeuThich);
            editor.putString("IDDANHSACHDONHANG", idDanhSachDonHang);
        } else {
            editor.putString("USERNAME", user);
            editor.putString("PASSWORD", password);
            editor.putBoolean("REMEMBER", status);
            editor.putString("IDUSRE", idUser);
            editor.putString("IDGIOHANG", idGioHang);
            editor.putString("VITRI", viTri);
            editor.putString("IDVITRI", idViTri);
            editor.putString("IDGIOHANGTAM", idGioHangTam);
            editor.putString("NAMEUSER", nameUser);
            editor.putString("ANHUSER", anhUser);
            editor.putString("IDDANHSACHYEUTHICH", idDanhSachYeuThich);
            editor.putString("IDDANHSACHDONHANG", idDanhSachDonHang);


        }
        editor.commit();
    }

    //thai: khai báo client của google
    private void processrequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    //khi ấn nút login sẽ chạy vào hàm này
    private void processLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //mở dialog tài khoản gg
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    //lay dữ liệu tài khoản
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            firebaseAuthWithGoogle(account.getIdToken());

        } catch (ApiException e) {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
    }

    //xử lí đăng nhập
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(Login_Activity.this);

                                String hoVaTen = googleSignInAccount.getDisplayName();
                                String gmail = googleSignInAccount.getEmail();
                                String anhKH = String.valueOf(googleSignInAccount.getPhotoUrl());
                                database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                //    FirebaseStorage
                                mData = database.getReference("Accounts");

                                model_Account model_account = new model_Account(UUID.randomUUID().toString(), hoVaTen, gmail, "", "", gmail, "", UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), anhKH);
                                mData.child(model_account.getId()).setValue(model_account);

                                rememberUser(model_account.getId(), model_account.getIdGioHang(), gmail, "", true, "", model_account.getIdViTri(), model_account.getIdGioHangTam(), hoVaTen, model_account.getAnhKhachHang(), model_account.getIdDanhSachYeuThich(), model_account.getIdDanhSachDonHang());

                                final Dialog dialog = new Dialog(Login_Activity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.item_login);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Login_Activity.this, trangChu_SanPham_Activity.class);
                                        startActivity(intent);

                                    }
                                }, 3000);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                startActivity(new Intent(getApplicationContext(), trangChu_SanPham_Activity.class));

                            }
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login_Activity.this, "fail", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    //thai login
    private void loginNormal() {
        String userName = Login_edt_username.getText().toString().trim();
        String password = Login_edt_password.getText().toString().trim();
        if (userName.isEmpty()) {
            Login_edt_username.setError("Tên đăng nhập trống!");
        } else if (password.isEmpty()) {
            Login_edt_password.setError("Mật khẩu đang trống!");
        } else {
            mData = database.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Accounts");
            mData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        model_Account account = ds.getValue(model_Account.class);

                        if (userName.equals(account.getName() + "") && password.equals(account.getPassword() + "")) {
                            Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            rememberUser(account.getId(), account.getIdGioHang(), userName, password, checkBox.isChecked(), account.getAddress(), account.getIdViTri(), account.getIdGioHangTam(), account.getRealName(), account.getAnhKhachHang(), account.getIdDanhSachYeuThich(), account.getIdDanhSachDonHang());

                            final Dialog dialog = new Dialog(Login_Activity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.item_login);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Login_Activity.this, trangChu_SanPham_Activity.class);
                                    startActivity(intent);
                                }
                            }, 3000);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();

                            return;
                        } else {
                            Toast.makeText(Login_Activity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    /********************Kiểm tra tài khoản
     * @return**********************/


    private void anhXa() {
//        TextView
        Login_tv_btn_SignUp = findViewById(R.id.login_tv_btn_SignUp);
        Login_tv_btn_SignUpText = findViewById(R.id.login_tv_btn_SignUpText);
        Login_tv_username = findViewById(R.id.login_tv_username);
        Login_tv_password = findViewById(R.id.login_tv_password);
        login_tv_google = findViewById(R.id.login_tv_google);
        login_tv_Phone = findViewById(R.id.login_tv_Phone);

        Login_tv_back = findViewById(R.id.login_tv_back);
//        EditText
        Login_edt_username = findViewById(R.id.login_edt_username);
        Login_edt_password = findViewById(R.id.login_edt_password);
        checkBox = findViewById(R.id.login_checkBox);
//        LinearLayout
        Login_llout_btn_submid = findViewById(R.id.login_llout_btn_submid);
        //sharedPreference
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        Login_edt_username.setText(pref.getString("USERNAME", ""));
        Login_edt_password.setText(pref.getString("PASSWORD", ""));
        checkBox.setChecked(pref.getBoolean("REMEMBER", false));

    }
}