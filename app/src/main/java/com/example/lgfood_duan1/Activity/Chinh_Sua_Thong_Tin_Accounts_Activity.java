package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class Chinh_Sua_Thong_Tin_Accounts_Activity extends AppCompatActivity {

    private EditText
            SuaThongTinNguoiDung_edt_tenNguoiDung,
            SuaThongTinNguoiDung_edt_tenDangNhap,
            SuaThongTinNguoiDung_edt_gmail,
            SuaThongTinNguoiDung_edt_soDienThoaiNguoiDung,
            SuaThongTinNguoiDung_edt_diaChi,
            SuaThongTinNguoiDung_edt_matKhauCu,
            SuaThongTinNguoiDung_edt_nhapMatKhauCu,
            SuaThongTinNguoiDung_edt_xacNhanMatKhauMoi;

    private LinearLayout
            SuaThongTinNguoiDung_llout_suaThongTin;
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

    private SharedPreferences shareAcout;

    String idSharePre;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private UploadTask uploadTask;
    private StorageReference mountainImagesRef, storageRef;
    private UUID uuid;
    private model_Account listAccount;
    private ArrayList<model_Account> arrListAccount;

    //    getdata intent
    private String itIdUser, itNameUser, itPassWordUser, itAddRessUser, itEmailUser, itMIdGioHang, itRealName, itPhoneNumberUser, itMAnhKhachHang;
//    getdata edittext

    private String realName, userName, email, password, repeatPassword, address, soDienThoai, newPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thong_tin_account);
        anhXa();

        getIt();

        setValue();
//        checkValidateSet();
        batSuKien();

    }


    //   BT : batSuKien


    private void batSuKien() {
//        back về 1 trang
        Login_tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chinh_Sua_Thong_Tin_Accounts_Activity.this, thongTinTaiKhoan_Activity.class);
                startActivity(intent);
            }
        });

        SuaThongTinNguoiDung_llout_suaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidateSet();
            }
        });
    }

    //        BT : getSharedPreFerences

    private void getSharedPre() {
        shareAcout = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        idSharePre = shareAcout.getString("IDUSRE", "");
    }


    //   BT : firebaseData


    private void firebaseData() {
        getSharedPre();
        //         Gán giá trị trong firebase

        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = database.getReference().child("Accounts").child(idSharePre);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listAccount = snapshot.getValue(model_Account.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
//  anh Bảo toàn getdata


    private void getData() {
        firebaseData();
        realName = SuaThongTinNguoiDung_edt_tenNguoiDung.getText().toString().trim();
        userName = SuaThongTinNguoiDung_edt_tenDangNhap.getText().toString().trim();
        email = SuaThongTinNguoiDung_edt_gmail.getText().toString().trim();
        password = SuaThongTinNguoiDung_edt_matKhauCu.getText().toString().trim();
        repeatPassword = SuaThongTinNguoiDung_edt_nhapMatKhauCu.getText().toString().trim();
        address = SuaThongTinNguoiDung_edt_diaChi.getText().toString().trim();
        soDienThoai = SuaThongTinNguoiDung_edt_soDienThoaiNguoiDung.getText().toString().trim();
        newPassword = SuaThongTinNguoiDung_edt_xacNhanMatKhauMoi.getText().toString().trim();
    }
    //   BT : checkValidateSet


    private void checkValidateSet() {
        getData();


        UUID uuid = UUID.randomUUID();
//            listAccount=new model_Account(uuid.toString(),userName,password,address,email,soDienThoai,uuid.toString(),"");
//            node.child(listAccount.getId()).setValue(listAccount);


        if (userName.length() < 6 || userName.length() > 50) {
            SuaThongTinNguoiDung_edt_tenDangNhap.setError("Tên đăng nhập gồm 6 - 50 kí tự");
        } else if (!email.matches(emailPattern)) {
            SuaThongTinNguoiDung_edt_gmail.setError("Sai định dạng Email");

        } else if (realName.length() < 2 || realName.length() > 50) {
            SuaThongTinNguoiDung_edt_tenNguoiDung.setError("Tên người dùng phải lớn hơn 2 kí tự");

        } else if (!(soDienThoai.length() == 10 || !soDienThoai.matches(reg))) {
            SuaThongTinNguoiDung_edt_soDienThoaiNguoiDung.setError("Nhập sai định dạng số điện thoại");

        } else if (!repeatPassword.equals(password)) {
            SuaThongTinNguoiDung_edt_nhapMatKhauCu.setError("Sai mật khẩu ! vui lòng nhập lại");

        } else if (newPassword.isEmpty() || password.length() < 6) {
            SuaThongTinNguoiDung_edt_xacNhanMatKhauMoi.setError("Mật khẩu đang trống hoặc bé hơn 6 kí tự");
        } else if (address.length() < 6 || address.length() > 150) {
            SuaThongTinNguoiDung_edt_diaChi.setError("Địa Chỉ lớn hơn 6 và  không quá 100 kí tự");
        } else {
            getData();
            database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
            node = database.getReference().child("Accounts");
            node.child(idSharePre).child("realName").setValue(realName);
            node.child(idSharePre).child("name").setValue(userName);
            node.child(idSharePre).child("email").setValue(email);
            node.child(idSharePre).child("phoneNumber").setValue(soDienThoai);
            node.child(idSharePre).child("address").setValue(address);
            node.child(idSharePre).child("password").setValue(newPassword);

            shareAcout = getSharedPreferences("USER_FILE", MODE_PRIVATE);
            SharedPreferences.Editor editor = shareAcout.edit();
            editor.putString("PASSWORD", newPassword);
            editor.putString("USERNAME", "");
            editor.putBoolean("REMEMBER", false);
            editor.apply();


            Intent intent = new Intent(Chinh_Sua_Thong_Tin_Accounts_Activity.this, thongTinTaiKhoan_Activity.class);
            startActivity(intent);
            Toast.makeText(this, "Sữa thành công", Toast.LENGTH_SHORT).show();
        }

    }


    //   BToàn:get Intent
    private void getIt() {
        Bundle bundle = getIntent().getExtras();
        itIdUser = bundle.getString("idUser", "");
        itRealName = bundle.getString("realNameUse", "");
        itNameUser = bundle.getString("nameUser", "");
        itPassWordUser = bundle.getString("passWordUser", "");
        itAddRessUser = bundle.getString("addRessUser", "");
        itEmailUser = bundle.getString("emailUser", "");
        itPhoneNumberUser = bundle.getString("phoneNumberUser", "");
        itMIdGioHang = bundle.getString("mIdGioHang", "");
        itMAnhKhachHang = bundle.getString("mAnhKhachHang", "");

    }


    //    BT : SetValues
    private void setValue() {

        SuaThongTinNguoiDung_edt_tenNguoiDung.setText(itRealName.toString());
        SuaThongTinNguoiDung_edt_tenDangNhap.setText(itNameUser.toString());
        SuaThongTinNguoiDung_edt_gmail.setText(itEmailUser.toString());
        SuaThongTinNguoiDung_edt_soDienThoaiNguoiDung.setText(itPhoneNumberUser.toString());
        SuaThongTinNguoiDung_edt_diaChi.setText(itAddRessUser.toString());
        SuaThongTinNguoiDung_edt_matKhauCu.setText(itPassWordUser.toString());


    }


    //    BT : ánh xạ


    private void anhXa() {


//        EditText
        SuaThongTinNguoiDung_edt_tenNguoiDung = findViewById(R.id.suaThongTinNguoiDung_edt_tenNguoiDung);
        SuaThongTinNguoiDung_edt_tenDangNhap = findViewById(R.id.suaThongTinNguoiDung_edt_tenDangNhap);
        SuaThongTinNguoiDung_edt_gmail = findViewById(R.id.suaThongTinNguoiDung_edt_gmail);
        SuaThongTinNguoiDung_edt_soDienThoaiNguoiDung = findViewById(R.id.suaThongTinNguoiDung_edt_soDienThoaiNguoiDung);
        SuaThongTinNguoiDung_edt_diaChi = findViewById(R.id.suaThongTinNguoiDung_edt_diaChi);
        SuaThongTinNguoiDung_edt_matKhauCu = findViewById(R.id.suaThongTinNguoiDung_edt_matKhauCu);
        SuaThongTinNguoiDung_edt_nhapMatKhauCu = findViewById(R.id.suaThongTinNguoiDung_edt_nhapMatKhauCu);
        SuaThongTinNguoiDung_edt_xacNhanMatKhauMoi = findViewById(R.id.suaThongTinNguoiDung_edt_xacNhanMatKhauMoi);

//        LinearLayout
        SuaThongTinNguoiDung_llout_suaThongTin = findViewById(R.id.suaThongTinNguoiDung_llout_suaThongTin);

//        Imgview
        Login_tv_back = findViewById(R.id.thongTinTaiKhoan_img_btn_thoat);
        SingUp_Im_ViTri = findViewById(R.id.singUp_Im_ViTri);
//        Firebase auth
        mAuth = FirebaseAuth.getInstance();
//        Firebase realtime
        database.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    }

}