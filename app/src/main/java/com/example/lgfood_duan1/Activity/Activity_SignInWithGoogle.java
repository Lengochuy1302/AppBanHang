package com.example.lgfood_duan1.Activity;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Activity_SignInWithGoogle extends AppCompatActivity {
    private ImageView thongTinTaiKhoan_img_btn_thoat;
    private TextInputEditText
            google_edt_TenKH,
            google_edt_teDangNhapKhachHang,
            google_edt_gmailKH,
            google_edt_matKhauKH,
            google_edt_matKhauKHRepeat,
            google_edt_DiaChiKhachHang,
            google_edt_phoneNumber;
    private LinearLayout google_lv_btn_submid;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9])|(9[0-46-9]))(\\d)(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})?(\\d)$";
    FirebaseDatabase database;
    DatabaseReference dataRef;
    model_Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_with_google);

        anhXa();
        batSuKien();
        showThongTin();

    }

    private void addFirebase() {
        String tenKH = google_edt_TenKH.getText().toString().trim();
        String userName = google_edt_teDangNhapKhachHang.getText().toString().trim();
        String email = google_edt_gmailKH.getText().toString().trim();
        String password = google_edt_matKhauKH.getText().toString().trim();
        String repeatPassword = google_edt_matKhauKHRepeat.getText().toString().trim();
        String address = google_edt_DiaChiKhachHang.getText().toString().trim();
        String soDienThoai = google_edt_phoneNumber.getText().toString().trim();
        if (tenKH.length() < 6 || tenKH.length() > 100) {
            google_edt_TenKH.setError("họ và tên khách hàng gồm 6 - 50 kí tự ");
        } else if (userName.length() < 6 || userName.length() > 50) {
            google_edt_teDangNhapKhachHang.setError("Tên đăng nhập gồm 6 - 50 kí tự ");
        }else if (google_edt_teDangNhapKhachHang.getText().toString().contains(" ")){
            google_edt_teDangNhapKhachHang.setError("Không được để khoảng trống!");
        }
        else if (!email.matches(emailPattern)) {
            google_edt_gmailKH.setError("Sai định dạng Email");
        } else if (password.isEmpty() || password.length() < 6) {
            google_edt_matKhauKH.setError("Mật khẩu đang trống hoặc bé hơn 6 kí tự");
        } else if (!password.equals(repeatPassword)) {
            google_edt_matKhauKHRepeat.setError("Mật khẩu không khớp");
        } else if (!(soDienThoai.length() == 10 || !soDienThoai.matches(reg))) {
            google_edt_phoneNumber.setError("Nhập sai định dạng số điện thoại");

        } else if (address.length() < 6 || address.length() > 150) {
            google_edt_DiaChiKhachHang.setError("Địa Chỉ lớn hơn 6 và  không quá 100 kí tự");
        } else {
            database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
            //    FirebaseStorage
            dataRef = database.getReference("Accounts");

            account = new model_Account(UUID.randomUUID().toString(), tenKH, userName, password, address, email, soDienThoai, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString(), "");
            dataRef.child(account.getId()).setValue(account);
            Intent intent = new Intent(Activity_SignInWithGoogle.this, trangChu_SanPham_Activity.class);
            startActivity(intent);
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
    }

    private void showThongTin() {
        GoogleSignInAccount googleSignInAccount= GoogleSignIn.getLastSignedInAccount(this);
        google_edt_TenKH.setText(googleSignInAccount.getDisplayName());
        google_edt_teDangNhapKhachHang.setText(googleSignInAccount.getEmail());
        google_edt_gmailKH.setText(googleSignInAccount.getEmail());
        Toast.makeText(this, ""+google_edt_matKhauKH  , Toast.LENGTH_SHORT).show();
    }

    private void batSuKien() {
        thongTinTaiKhoan_img_btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        google_lv_btn_submid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFirebase();
            }
        });
    }

    private void anhXa() {

        thongTinTaiKhoan_img_btn_thoat=findViewById(R.id.thongTinTaiKhoan_img_btn_thoat);
        google_edt_TenKH=findViewById(R.id.google_edt_TenKH);
        google_edt_teDangNhapKhachHang=findViewById(R.id.google_edt_teDangNhapKhachHang);
        google_edt_gmailKH=findViewById(R.id.google_edt_gmailKH);
        google_edt_matKhauKH=findViewById(R.id.google_edt_matKhauKH);
        google_edt_matKhauKHRepeat=findViewById(R.id.google_edt_matKhauKHRepeat);
        google_edt_DiaChiKhachHang=findViewById(R.id.google_edt_DiaChiKhachHang);
        google_edt_phoneNumber=findViewById(R.id.google_edt_phoneNumber);
        google_lv_btn_submid=findViewById(R.id.google_lv_btn_submid);



    }

}