package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lgfood_duan1.Adapter.xacNhanDonHangAdmin_Adapter;
import com.example.lgfood_duan1.Adapter.xacNhanDonHangUser_Adapter;
import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_chiTietSanPhamHoaDon;
import com.example.lgfood_duan1.Model.model_hoaDon;
import com.example.lgfood_duan1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Xu_Li_Don_Hang_Activity extends AppCompatActivity {
    private ImageView
            XuLi_img_btn_backItem;
    private TextView
            XuLi_txt_tenKhachHang,
            XuLi_txt_gmailKhachHang,
            XuLi_txt_diaChiKhachHang,
            XuLi_txt_sdtKhachHang_xacNhan,
            XuLi_txt_tongTienFomXacNhan;
    private Button
            XuLi_btn_btnXacNhan;
    private LinearLayout
            XuLi_llout_btn_chat;
    private Spinner
            XuLI_spn_xuLiDonHang;
    private RecyclerView
            XuLi_rscv_showDanSach;
    Dialog dialogLoading;
    Dialog diaLog;
    //Value
    String idUserIt, idHoaDonIt, idChiTietSanPhamHoaDonIt, idDanhSachChiTietDonHang;
    double tongGiaIt;
    int tinhTrangHoaDonIt;
    // model
    private model_Account arrAccount;
    private model_hoaDon arrHoaDon;
    private model_chiTietSanPhamHoaDon arrCTSPHoaDon;
    private ArrayList<model_chiTietSanPhamHoaDon> arrListCTSPHoaDon;
    private ArrayList<model_hoaDon> arrListHoaDon;

    private model_SanPham arrSanPham;
    private ArrayList<model_SanPham> arrListSanPham;
    //    Firebase\
    private DatabaseReference dataCTSPHoaDonRef, dataAccountRef, dataSanPhamRef;
    private FirebaseDatabase database;
    //Adapter
    xacNhanDonHangAdmin_Adapter adapter_XNDonHangAdmin;
    private ArrayList<String> listLSP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_li_don_hang);

        anhXa();
        batSuKien();
        getDataIntent();
        getdataAcout();
        getDataHoaDon();
        getDataDTSPHoaDon();

    }

    //     lấy giá trị từ Danh Sách chi tiết hóa đơn Firebase
    private void getDataHoaDon() {
        dataCTSPHoaDonRef = database.getReference("HoaDon").child(idDanhSachChiTietDonHang).child(idHoaDonIt);
        dataCTSPHoaDonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrHoaDon = snapshot.getValue(model_hoaDon.class);
                setSpiner(arrHoaDon);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void setSpiner(model_hoaDon arrHoaDon) {
        if (arrHoaDon.getTinhTrangDonHang() == 0) {
            listLSP = new ArrayList<>();
            listLSP.add("Chưa xác nhận");
            listLSP.add("Đang xử lý");
            listLSP.add("Đã xử lý");
        }
        if (arrHoaDon.getTinhTrangDonHang() == 1) {
            listLSP = new ArrayList<>();
            listLSP.add("Đang xử lý");
            listLSP.add("Đã xử lý");
        }
        if (arrHoaDon.getTinhTrangDonHang() == 2) {
            listLSP = new ArrayList<>();
            listLSP.add("Đã xử lý");
        }


        ArrayAdapter loaiSanPhamSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listLSP);
        XuLI_spn_xuLiDonHang.setAdapter(loaiSanPhamSpinnerAdapter);

    }
// set tình trạng hóa đơn

    // show ra danh sách chi tiết sản phẩm hóa đơn
    private void showListDanhSach(ArrayList<model_chiTietSanPhamHoaDon> arrListCTSPHoaDon, ArrayList<model_SanPham> arrListSanPham) {
        XuLi_rscv_showDanSach.setHasFixedSize(true);
        XuLi_rscv_showDanSach.setLayoutManager(new LinearLayoutManager(this));
        adapter_XNDonHangAdmin = new xacNhanDonHangAdmin_Adapter(this, arrListSanPham, arrListCTSPHoaDon);
        XuLi_rscv_showDanSach.setAdapter(adapter_XNDonHangAdmin);
        adapter_XNDonHangAdmin.notifyDataSetChanged();
    }


    //    lấy thoogn tin chi tiết từng sản phẩm
    private void getDataThongTinSanPhamTrongKho(ArrayList<model_chiTietSanPhamHoaDon> arrListCTSPHoaDons) {
        for (int i = 0; i < arrListCTSPHoaDons.size(); i++) {
            dataSanPhamRef = database.getReference("khoHang").child(arrListCTSPHoaDons.get(i).getIdSanPham());
            int finalI = i;
            dataSanPhamRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    arrSanPham = snapshot.getValue(model_SanPham.class);
                    arrListSanPham.add(arrSanPham);
                    if (arrListCTSPHoaDons.size() == arrListSanPham.size()) {
                        showListDanhSach(arrListCTSPHoaDons, arrListSanPham);
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }


    }

    //     lấy giá trị từ Danh Sách chi tiết hóa đơn Firebase
    private void getDataDTSPHoaDon() {
        dataCTSPHoaDonRef = database.getReference("ChiTietHoaDon").child(idChiTietSanPhamHoaDonIt);
        dataCTSPHoaDonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrCTSPHoaDon = child.getValue(model_chiTietSanPhamHoaDon.class);
                    arrListCTSPHoaDon.add(arrCTSPHoaDon);
                }
                getDataThongTinSanPhamTrongKho(arrListCTSPHoaDon);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


//   setThoong tin nguoi dung

    // lấy thông tin user tại item
    private void getdataAcout() {
        dataAccountRef = database.getReference("Accounts").child(idUserIt);
        dataAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrAccount = snapshot.getValue(model_Account.class);
                setDataAcout(arrAccount);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    // gans giá trị
    private void setDataAcout(model_Account arrAccount) {
        XuLi_txt_tenKhachHang.setText(arrAccount.getRealName());
        XuLi_txt_gmailKhachHang.setText(arrAccount.getEmail());
        XuLi_txt_diaChiKhachHang.setText(arrAccount.getAddress());
        XuLi_txt_sdtKhachHang_xacNhan.setText(arrAccount.getPhoneNumber() + "");
        XuLi_txt_tongTienFomXacNhan.setText(tongGiaIt + "00vnđ");
    }

    // lấy dữ liệu intent
    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        idUserIt = bundle.getString("iT_idUser", "");
        idHoaDonIt = bundle.getString("iT_idHoaDon", "");
        idDanhSachChiTietDonHang = bundle.getString("iT_idDanhSachChiTietDoHang", "");

        idChiTietSanPhamHoaDonIt = bundle.getString("iT_idChiTietSanPhamHoaDon", "");
        tongGiaIt = bundle.getDouble("iT_tongGia", 0);
        tinhTrangHoaDonIt = bundle.getInt("iT_tinhTrang", 0);
//        set giá trị
    }

    private void batSuKien() {
//        bắt sự kiện xác nhận thay đổi
        XuLi_btn_btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ImageView item_dialog_chucNang_img_imgErro = dialogLoading.findViewById(R.id.item_dialog_chucNang_img_imgErro);
                TextView item_dialog_chucNang_txt_nameErro = dialogLoading.findViewById(R.id.item_dialog_chucNang_txt_nameErro);
                Button Okay = dialogLoading.findViewById(R.id.btn_okay);
                Button Cancel = dialogLoading.findViewById(R.id.btn_cancel);
                //setText Item
                Okay.setText("Xác nhận");
                item_dialog_chucNang_img_imgErro.setImageResource(R.drawable.question);
                item_dialog_chucNang_txt_nameErro.setText("Bạn có muốn xác nhận không?");
                Okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog1 = new Dialog(Xu_Li_Don_Hang_Activity.this);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setContentView(R.layout.item_login);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String tinhTrangDon = XuLI_spn_xuLiDonHang.getSelectedItem().toString();
                                dataCTSPHoaDonRef = database.getReference("HoaDon").child(idDanhSachChiTietDonHang).child(idHoaDonIt).child("tinhTrangDonHang");

                                if (tinhTrangDon.equals("Chưa xác nhận")) {
                                    dataCTSPHoaDonRef.setValue(0);
                                } else if (tinhTrangDon.equals("Đang xử lý")) {
                                    dataCTSPHoaDonRef.setValue(1);
                                } else if (tinhTrangDon.equals("Đã xử lý")) {
                                    dataCTSPHoaDonRef.setValue(2);
                                }
                                Intent intent = new Intent(Xu_Li_Don_Hang_Activity.this, xac_Nhan_Don_hang_Activity.class);
                                startActivity(intent);
                                dialog1.dismiss();


                            }
                        }, 3000);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.show();
                    }
                });
                Cancel.setText("Hủy");
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLoading.dismiss();
                    }
                });
                dialogLoading.show();


            }
        });
//        thoát trang
        XuLi_img_btn_backItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Xu_Li_Don_Hang_Activity.this, xac_Nhan_Don_hang_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        //        SharedPreferences
        dialogLoading = new Dialog(Xu_Li_Don_Hang_Activity.this);
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLoading.setContentView(R.layout.item_dialog_chucnang_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogLoading.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialogLoading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogLoading.setCancelable(false); //Optional
        dialogLoading.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        diaLog = new Dialog(Xu_Li_Don_Hang_Activity.this);
        diaLog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        diaLog.setContentView(R.layout.item_login);
        diaLog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        arrListCTSPHoaDon = new ArrayList<>();
        arrListSanPham = new ArrayList<>();

        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");

//        ImageView
        XuLi_img_btn_backItem = findViewById(R.id.xuLi_img_btn_backItem);
//        TextView
        XuLi_txt_tenKhachHang = findViewById(R.id.xuLi_txt_tenKhachHang);
        XuLi_txt_gmailKhachHang = findViewById(R.id.xuLi_txt_gmailKhachHang);
        XuLi_txt_diaChiKhachHang = findViewById(R.id.xuLi_txt_diaChiKhachHang);
        XuLi_txt_sdtKhachHang_xacNhan = findViewById(R.id.xuLi_txt_sdtKhachHang_xacNhan);
        XuLi_txt_tongTienFomXacNhan = findViewById(R.id.xuLi_txt_tongTienFomXacNhan);
//        Button
        XuLi_btn_btnXacNhan = findViewById(R.id.xuLi_btn_btnXacNhan);
//        LinearLayout
        XuLi_llout_btn_chat = findViewById(R.id.xuLi_llout_btn_chat);
//         Spinner
        XuLI_spn_xuLiDonHang = findViewById(R.id.xuLI_spn_xuLiDonHang);
//         RecyclerView
        XuLi_rscv_showDanSach = findViewById(R.id.xuLi_rscv_showDanSach);

    }
}