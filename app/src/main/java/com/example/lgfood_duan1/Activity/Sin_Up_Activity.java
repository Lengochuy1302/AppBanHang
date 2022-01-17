package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Sin_Up_Activity extends AppCompatActivity {

    private TextView
            SignUp_tv_btn_Login,
            SignUp_tv_btn_loginText,

    Login_tv_Phone,
            Login_tv_google;
    private EditText
            signUp_edt_teDangNhapKhachHang,
            SignUp_edt_gmail,
            SignUp_edt_MatKhau,
            SignUp_edt_NhapLaiMatKhau,
            SignUp_edt_DiaChi,
            SignUp_edt_SDT,
            signUp_edt_TenKH;
    private LinearLayout
            SignUp_lv_btn_submid;
    private ImageView
            Login_tv_back,
            SingUp_Im_ViTri;
    //Login with google
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    //Login normal
    String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9])|(9[0-46-9]))(\\d)(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})?(\\d)$";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    DatabaseReference node;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    model_Account model_account;
    FusedLocationProviderClient fusedLocationProviderClient;

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_up);

        anhXa();
        processrequest();
        batSuKien();
    }

    //  Trung lấy vị trí
    private void getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(Sin_Up_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//             getLocation();
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {

                        try {
                            Geocoder geocoder = new Geocoder(
                                    Sin_Up_Activity.this, Locale.getDefault());

                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            SignUp_edt_DiaChi.setText(Html.fromHtml("" + addresses.get(0).getAddressLine(0)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(Sin_Up_Activity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        }


//                    GioHang_tv_diaChi
    }

    //khai báo client của google
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
    //thai: rememberUser
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
                                GoogleSignInAccount googleSignInAccount= GoogleSignIn.getLastSignedInAccount(Sin_Up_Activity.this);

                                String hoVaTen=googleSignInAccount.getDisplayName();
                                String gmail=googleSignInAccount.getEmail();
                                String anhKH=String.valueOf(googleSignInAccount.getPhotoUrl());
                                database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                //    FirebaseStorage
                                node = database.getReference("Accounts");

                                model_account = new model_Account(UUID.randomUUID().toString(), hoVaTen, gmail, "", "", gmail, "", UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString(), anhKH);
                                node.child(model_account.getId()).setValue(model_account);

                                rememberUser(model_account.getId(),model_account.getIdGioHang(),gmail,"",true,"",model_account.getIdViTri(),model_account.getIdGioHangTam(),hoVaTen,model_account.getAnhKhachHang(),model_account.getIdDanhSachYeuThich(),model_account.getIdDanhSachDonHang());

                                final Dialog dialog = new Dialog(Sin_Up_Activity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.item_login);
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(Sin_Up_Activity.this,trangChu_SanPham_Activity.class);
                                        startActivity(intent);
                                    }
                                },3000);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                startActivity(new Intent(getApplicationContext(), trangChu_SanPham_Activity.class));

                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Sin_Up_Activity.this, "fail", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void batSuKien() {
//        Lấy vị trí
        SingUp_Im_ViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
//        chuyển sang trang đăng kí tài khoản
        SignUp_tv_btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sin_Up_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
        SignUp_tv_btn_loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sin_Up_Activity.this, Login_Activity.class);

                startActivity(intent);
            }
        });
        Login_tv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLogin();
            }
        });
        Login_tv_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sin_Up_Activity.this, Nhap_SDT.class);
                startActivity(intent);
            }
        });


//        back về login
        Login_tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sin_Up_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        //        check tài khoản
        SignUp_lv_btn_submid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //      gọi hàm check tài khoản
                checkValidateSet();
            }
        });
    }

    private void checkValidateSet() {

        String tenKH = signUp_edt_TenKH.getText().toString().trim();
        String userName = signUp_edt_teDangNhapKhachHang.getText().toString().trim();
        String email = SignUp_edt_gmail.getText().toString().trim();
        String password = SignUp_edt_MatKhau.getText().toString().trim();
        String repeatPassword = SignUp_edt_NhapLaiMatKhau.getText().toString().trim();
        String address = SignUp_edt_DiaChi.getText().toString().trim();
        String soDienThoai = SignUp_edt_SDT.getText().toString().trim();
        if (tenKH.length() < 6 || tenKH.length() > 100) {
            signUp_edt_TenKH.setError("họ và tên khách hàng gồm 6 - 50 kí tự ");
        } else if (userName.length() < 6 || userName.length() > 50) {
            signUp_edt_teDangNhapKhachHang.setError("Tên đăng nhập gồm 6 - 50 kí tự ");
        }else if (signUp_edt_teDangNhapKhachHang.getText().toString().contains(" ")){
            signUp_edt_teDangNhapKhachHang.setError("Không được để khoảng trống!");
        }
        else if (!email.matches(emailPattern)) {
            SignUp_edt_gmail.setError("Sai định dạng Email");
        } else if (password.isEmpty() || password.length() < 6) {
            SignUp_edt_MatKhau.setError("Mật khẩu đang trống hoặc bé hơn 6 kí tự");
        } else if (!password.equals(repeatPassword)) {
            SignUp_edt_NhapLaiMatKhau.setError("Mật khẩu không khớp");
        } else if (!(soDienThoai.length() == 10 || !soDienThoai.matches(reg))) {
            SignUp_edt_SDT.setError("Nhập sai định dạng số điện thoại");

        } else if (address.length() < 6 || address.length() > 150) {
            SignUp_edt_DiaChi.setError("Địa Chỉ lớn hơn 6 và  không quá 100 kí tự");
        } else {
            database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
            //    FirebaseStorage
            node = database.getReference("Accounts");

            model_account = new model_Account(UUID.randomUUID().toString(), tenKH, userName, password, address, email, soDienThoai, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString(), "");
            node.child(model_account.getId()).setValue(model_account);
            Intent intent = new Intent(Sin_Up_Activity.this, Login_Activity.class);
            startActivity(intent);
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
    }


    private void anhXa() {


//        TextView
        SignUp_tv_btn_Login = findViewById(R.id.signUp_tv_btn_Login);
        SignUp_tv_btn_loginText = findViewById(R.id.signUp_tv_btn_loginText);

        Login_tv_google = findViewById(R.id.login_tv_google);
        Login_tv_Phone = findViewById(R.id.login_tv_Phone);


//        EditText
        signUp_edt_teDangNhapKhachHang = findViewById(R.id.signUp_edt_teDangNhapKhachHang);
        SignUp_edt_gmail = findViewById(R.id.signUp_edt_gmailKH);
        SignUp_edt_MatKhau = findViewById(R.id.signUp_edt_matKhauKH);
        SignUp_edt_NhapLaiMatKhau = findViewById(R.id.signUp_edt_repeatMatKhau);
        SignUp_edt_DiaChi = findViewById(R.id.signUp_edt_DiaChiKhachHang);
        SignUp_edt_SDT = findViewById(R.id.signUp_edt_phoneNumber);
        signUp_edt_TenKH = findViewById(R.id.signUp_edt_TenKH);
//        LinearLayout
        SignUp_lv_btn_submid = findViewById(R.id.signUp_lv_btn_submid);

//        Imgview
        Login_tv_back = findViewById(R.id.thongTinTaiKhoan_img_btn_thoat);
        SingUp_Im_ViTri = findViewById(R.id.singUp_Im_ViTri);
//        Firebase auth
        mAuth = FirebaseAuth.getInstance();
//        Firebase realtime
        database.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    }
}