package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lgfood_duan1.Adapter.donHangUser_Adapter;
import com.example.lgfood_duan1.Adapter.suLyDonHangAdmin_Adapter;
import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_hoaDon;
import com.example.lgfood_duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class donHangUser_Activity extends AppCompatActivity {
    ImageView
            DonHang_img_btnThoat;
    RecyclerView
            DonHang_rscv_showDanhSachDonHang;
    FloatingActionButton
            DonHang_floatBtn_moGioHang;
    SharedPreferences sharedPreferences;
    // model
    FirebaseDatabase database;
    DatabaseReference dataRef;
    private ArrayList<model_hoaDon> arrListHoaDon;
    private model_hoaDon arrHoaDon;
    private model_Account arrAcout;
    private donHangUser_Adapter adapter_donHangUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_user);

        anhXa();
        batSuKien();
        getDataFirebaseDonHang();
    }

    // lấy thông tin user
    private void getDataFirebaseUsser(ArrayList<model_hoaDon> arrListHoaDon) {
        dataRef = database.getReference("Accounts").child(sharedPreferences.getString("IDUSRE", ""));
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrAcout = snapshot.getValue(model_Account.class);
                showListDonHang(arrListHoaDon,arrAcout);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    // show dánh sách hóa đơn
    private void showListDonHang(ArrayList<model_hoaDon>arrListHoaDon,model_Account arrAcout) {
        DonHang_rscv_showDanhSachDonHang.setHasFixedSize(true);
        DonHang_rscv_showDanhSachDonHang.setLayoutManager(new LinearLayoutManager(this));
        adapter_donHangUser = new donHangUser_Adapter(arrListHoaDon, arrAcout, this);
        DonHang_rscv_showDanhSachDonHang.setAdapter(adapter_donHangUser);
        adapter_donHangUser.notifyDataSetChanged();

    }

    //    lấy Liss ĐƠn hàng
    private void getDataFirebaseDonHang() {
        String idDanhSachDonHang = sharedPreferences.getString("IDDANHSACHDONHANG", "");
        dataRef = database.getReference("HoaDon").child(idDanhSachDonHang);
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrHoaDon = child.getValue(model_hoaDon.class);
                    arrListHoaDon.add(arrHoaDon);
                }
                getDataFirebaseUsser(arrListHoaDon);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void batSuKien() {
        DonHang_img_btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(donHangUser_Activity.this, trangChu_SanPham_Activity.class);
                startActivity(intent);
            }
        });
        DonHang_floatBtn_moGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(donHangUser_Activity.this, gio_Hang_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        arrListHoaDon = new ArrayList<>();

        sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");

//        ImageView
        DonHang_img_btnThoat = findViewById(R.id.donHang_img_btnThoat);
//        RecyclerView
        DonHang_rscv_showDanhSachDonHang = findViewById(R.id.donHang_rscv_showDanhSachDonHang);
//        FloatingActionButton
        DonHang_floatBtn_moGioHang = findViewById(R.id.donHang_floatBtn_moGioHang);

    }

    // lấy dữ liệu chi tiết sản phẩm hóa tại Firebase, khi user click thì mới chuy xuất tại id chi tiết hóa đơn mà hóa đơn cung cấp
    public void showChiTietDonHang(String idUser,String idDanhSachChiTietDonHang,String idHoaDon,String idChiTietSanPhamHoaDon,int tinhTrangDonHang,double tongTien) {
        Intent intent = new Intent(donHangUser_Activity.this,chiTietDonhangUser_Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("iT_idUser",idUser);
        bundle.putString("iT_idHoaDon",idHoaDon);
        bundle.putString("iT_idDanhSachChiTietDoHang",idDanhSachChiTietDonHang);
        bundle.putString("iT_idChiTietSanPhamHoaDon",idChiTietSanPhamHoaDon);
        bundle.putInt("iT_tinhTrang",tinhTrangDonHang);
        bundle.putDouble("iT_tongGia",tongTien);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}