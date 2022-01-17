package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lgfood_duan1.Adapter.suLyDonHangAdmin_Adapter;
import com.example.lgfood_duan1.Adapter.xacNhanDonHangUser_Adapter;
import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_hoaDon;
import com.example.lgfood_duan1.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class xac_Nhan_Don_hang_Activity extends AppCompatActivity {

    private TextView
            XacNhanDonHang_tv_name,
            XacNhanDonHang_tv_btn_donChuaXacNhan,
            XacNhanDonHang_tv_donHang,
            XacNhanDonHang_tv_btn_donDaXacNhan;

    private ImageView
            XacNhanDonHang_img_back;
    private RecyclerView
            XacNhanDonHang_rsc_hienDanhSachDonHangAdmin;
    //    SharePre
    SharedPreferences sharePreAcout;
    //    value
    String idDanhSachDonHangUser, idUserSharePre;
    //    Firebase\
    private DatabaseReference dataHoaDonRef, dataCTSPHoaDonRef, dataAccountRef;
    private FirebaseDatabase database;
    // model
    private ArrayList<model_hoaDon> arrListHoaDon;
    private ArrayList<model_Account> arrListAccount, arrListAccountHoaDon;
    private model_hoaDon arrHoaDon;
    private model_Account arrAccount;
    // Adapter
    suLyDonHangAdmin_Adapter Adapter_suLyDonHangAdmin;
    boolean checkShowLoai = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang);
        anhXa();
        batSuKien();
        getValueSharePre();
        getDataFirebaseListAcount();


    }

    // lấy danh sách user
    private void getDataFirebaseListAcount() {
        arrListAccountHoaDon.clear();

        dataAccountRef = database.getReference("Accounts");
        dataAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrAccount = child.getValue(model_Account.class);
                    arrListAccountHoaDon.add(arrAccount);
                }
                getDataFirebaseDonHang(arrListAccountHoaDon);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    // lấy thông tin danh sách user có trong hóa đơn
    private void getDataFirebaseAccount(ArrayList<model_hoaDon> arrListHoaDon) {
        arrListAccountHoaDon.clear();
        for (int i = 0; i < arrListHoaDon.size(); i++) {
            dataAccountRef = database.getReference("Accounts").child(arrListHoaDon.get(i).getIdKhachHang());
            dataAccountRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    arrAccount = snapshot.getValue(model_Account.class);
                    arrListAccountHoaDon.add(arrAccount);
                    showListDonHang(arrListHoaDon, arrListAccountHoaDon);
                    Adapter_suLyDonHangAdmin.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }

    // show dánh sách hóa đơn
    private void showListDonHang(ArrayList<model_hoaDon> arrListHoaDon, ArrayList<model_Account> arrListAccountHoaDons) {
        XacNhanDonHang_rsc_hienDanhSachDonHangAdmin.setHasFixedSize(true);
        XacNhanDonHang_rsc_hienDanhSachDonHangAdmin.setLayoutManager(new LinearLayoutManager(this));

        Adapter_suLyDonHangAdmin = new suLyDonHangAdmin_Adapter(arrListHoaDon, arrListAccountHoaDons, this, checkShowLoai);
        XacNhanDonHang_rsc_hienDanhSachDonHangAdmin.setAdapter(Adapter_suLyDonHangAdmin);

    }

    //lấy id từ SharePre
    private void getValueSharePre() {
        sharePreAcout = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        idDanhSachDonHangUser = sharePreAcout.getString("IDDANHSACHDONHANG", "");
        idUserSharePre = sharePreAcout.getString("IDUSRE", "");
//
    }

    // lấy dữ liệu chi tiết sản phẩm hóa tại Firebase, khi user click thì mới chuy xuất tại id chi tiết hóa đơn mà hóa đơn cung cấp
    public void showChiTietDonHang(String idUser,String idDanhSachChiTietDonHang,String idHoaDon,String idChiTietSanPhamHoaDon,int tinhTrangDonHang,double tongTien) {
        Intent intent = new Intent(xac_Nhan_Don_hang_Activity.this,Xu_Li_Don_Hang_Activity.class);
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

    // lấy dữ liệu từ hóa đơn tại idDanhSachHoaDon User cung cấp
    private void getDataFirebaseDonHang(ArrayList<model_Account> arrListAccountHoaDon) {
        arrListHoaDon.clear();
        for (int i = 0; i < arrListAccountHoaDon.size(); i++) {
            dataHoaDonRef = database.getReference("HoaDon").child(arrListAccountHoaDon.get(i).getIdDanhSachDonHang());
            dataHoaDonRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        arrHoaDon = child.getValue(model_hoaDon.class);
                        if (checkShowLoai == true) {
                            if (arrHoaDon.getTinhTrangDonHang() == 1) {
                                arrListHoaDon.add(arrHoaDon);
                            }
                        }
                        if (checkShowLoai == false) {
                            if (arrHoaDon.getTinhTrangDonHang() == 0) {
                                arrListHoaDon.add(arrHoaDon);
                            }
                        }
                    }

                    getDataFirebaseAccount(arrListHoaDon);

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }


    //    Bắt sự kiện buttom
    private void batSuKien() {
//        Bắt sự kiện chuyển show loại đơn xác nhận và chưa xác nhận
        XacNhanDonHang_tv_btn_donChuaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkShowLoai = false;
                getDataFirebaseListAcount();

                Adapter_suLyDonHangAdmin.notifyDataSetChanged();

                XacNhanDonHang_tv_btn_donChuaXacNhan.setBackgroundResource(R.drawable.broder_radius_cam_thuonghieu);
                XacNhanDonHang_tv_btn_donChuaXacNhan.setTextColor(Color.parseColor("#FFFFFF"));
                XacNhanDonHang_tv_btn_donDaXacNhan.setBackgroundResource(R.color.lf_gray4);
                XacNhanDonHang_tv_btn_donDaXacNhan.setTextColor(Color.parseColor("#F0A23C"));

            }
        });
        XacNhanDonHang_tv_btn_donDaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkShowLoai = true;
                getDataFirebaseListAcount();
                Adapter_suLyDonHangAdmin.notifyDataSetChanged();

                XacNhanDonHang_tv_btn_donChuaXacNhan.setBackgroundResource(R.color.lf_gray4);
                XacNhanDonHang_tv_btn_donChuaXacNhan.setTextColor(Color.parseColor("#F0A23C"));
                XacNhanDonHang_tv_btn_donDaXacNhan.setBackgroundResource(R.drawable.broder_radius_cam_thuonghieu);
                XacNhanDonHang_tv_btn_donDaXacNhan.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

//        bắt sự kiện thoát trang
        XacNhanDonHang_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(xac_Nhan_Don_hang_Activity.this, thongTinTaiKhoan_Activity.class);
                startActivity(intent);
            }
        });
    }


    //     Ánh xạ đây nè :D
    private void anhXa() {
//        model arrlist
        arrListHoaDon = new ArrayList<>();
        arrListAccount = new ArrayList<>();
        arrListAccountHoaDon = new ArrayList<>();
        //    Firebase
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
//       TextView
        XacNhanDonHang_tv_name = findViewById(R.id.xacNhanDonHang_tv_name);
        XacNhanDonHang_tv_btn_donChuaXacNhan = findViewById(R.id.xacNhanDonHang_tv_btn_donChuaXacNhan);
        XacNhanDonHang_tv_btn_donDaXacNhan = findViewById(R.id.xacNhanDonHang_tv_btn_donDaXacNhan);

        XacNhanDonHang_tv_donHang = findViewById(R.id.xacNhanDonHang_tv_donHang);

//      ImageView
        XacNhanDonHang_img_back = findViewById(R.id.xacNhanDonHang_img_back);

//        Button
//RecyclerView
        XacNhanDonHang_rsc_hienDanhSachDonHangAdmin = findViewById(R.id.xacNhanDonHang_rsc_hienDanhSachDonHangAdmin);


    }
}