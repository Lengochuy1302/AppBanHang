package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_chiTietLoaiSanPham_thongKe;
import com.example.lgfood_duan1.Model.model_chiTietSanPhamHoaDon;
import com.example.lgfood_duan1.Model.model_hoaDon;
import com.example.lgfood_duan1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class quanLyDoanhThu_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView
            QuanLyDoanThu_tv_loaiTimKiem,
            QuanLyDoanThuTong_tv_tieuDe,
            QuanLyDoanThuTong_tv_doanThu,
            QuanLyDoanThuTong_tv_chiPhi,
            QuanLyDoanThuTong_tv_loiNhuan,
            QuanLyDoanThu_tv_vonTong,
            QuanLyDoanThu_tv_laiTong,
            QuanLyDoanThuTong_tv_loaiTien,

    QuanLyDoanThu_tv_phanTramLoaiCaPhe,
            QuanLyDoanThu_tv_giaTienLoaiCaPhe,
            QuanLyDoanThu_tv_soLuongLoaiCaPhe,

    QuanLyDoanThu_tv_phanTramLoaiTraTuiLoc,
            QuanLyDoanThu_tv_giaTienLoaiTraTuiLoc,
            QuanLyDoanThu_tv_soLuongLoaiTraTuiLoc,

    QuanLyDoanThu_tv_giaTienLoaiTraiCayXayDeo,
            QuanLyDoanThu_tv_soLuongLoaiTraiCayXayDeo,
            QuanLyDoanThu_tv_loaiPhamTramTraiCayXayDeo,

    QuanLyDoanThu_tv_phanTramLoaiHatDacSan,
            QuanLyDoanThu_tv_giaTienLoaiHatDacSan,
            QuanLyDoanThu_tv_soLuongLoaiHatDacSan,

    QuanLyDoanThu_tv_phamTramLoaiThaoMoc,
            QuanLyDoanThu_tv_giaTiemLoaiThaoMoc,
            QuanLyDoanThu_tv_soLuongThaoMoc,

    QuanLyDoanThu_tv_chonNgayTu,
            QuanLyDoanThu_tv_chonNgayDen;
    ImageView
            QuanLyDoanThu_img_btn_backItem,
            QuanLyDoanThu_img_btn_tuyChonFilter,
            QuanLyDoanThu_img_chiTietLoaiCaPhe,
            QuanLyDoanThu_img_chiTietLoaiTraTuiLoc,
            QuanLyDoanThu_img_chiTietLoaiTraiCayXayDeo,
            QuanLyDoanThu_img_chiTietLoaiHatDacSan,
            QuanLyDoanThu_img_chiTietLoaiThaoMoc,
            QuanLyDoanThu_img_btn_Loc;


    CardView
            QuanLyDoanThu_crv_colorLoaiCaPhe,
            QuanLyDoanThu_crv_colorTraTuiLoc,
            QuanLyDoanThu_crv_colorLoauTraiCayXayDeo,
            QuanLyDoanThu_crv_colorHatLoaiDacSan,
            QuanLyDoanThu_crv_colorLoaiThaoMoc;
    Button
            QuanLyDoanThu_btn_thuNhap,
            QuanLyDoanThu_btn_LoaiSp;
    LinearLayout
            QuanLyDoanThu_llout_ShowTongDoanhThu,
            QuanLyDoanThu_llout_ShowLoaiSanPham;
    CardView
            QuanLyDoanThu_crv_ShowChonLoaiTimKiem;
    ConstraintLayout
            QuanLyDoanThu_ctlout_locTheoTong,
            QuanLyDoanThu_ctlout_locTheoThang,
            QuanLyDoanThu_ctlout_locTheoKhoang,
            QuanLyDoanThu_ctlout_loaiTimKiemTuKhoang,
            QuanLyDoanThu_ctlout_xemChiTietLoaiCaPhe,
            QuanLyDoanThu_ctlout_xemChiTietLoaiTra,
            QuanLyDoanThu_ctlout_xemChiTietLoaiTCXDeo,
            QuanLyDoanThu_ctlout_xemChiTietLoaiHatDacSan,
            QuanLyDoanThu_ctlout_xemChiTietLoaiThaoMoc;
    ;
    BarChart
            QuanLyDoanThuTong_barChart;
    PieChart
            QuanLyDoanThu_pieChart;

    //    Firebase
    FirebaseDatabase database;
    DatabaseReference dataDonHangRef, dataSanPhamRef, dataAccountRef, dataChiTietHoaDonRef;

    //    ArrListModel
    ArrayList<model_hoaDon> arrListHoaDon;
    ArrayList<model_SanPham>
            arrListSanPham,
            arrListSanPhamLoaiCaPhe,
            arrListSanPhamLoaiTra,
            arrListSanPhamLoaiTraiCXDeo,
            arrListSanPhamLoaiHatDacSan,
            arrListSanPhamLoaiThaoMoc;
    ArrayList<model_Account> arrListAccount;
    ArrayList<model_chiTietSanPhamHoaDon> arrListChiTietHoaDon;

    ArrayList<model_chiTietLoaiSanPham_thongKe>
            arrListSanPhamChiTietLoaiCaPhe,
            arrListSanPhamChiTietLoaiTra,
            arrListSanPhamChiTietLoaiTraiCXDeo,
            arrListSanPhamChiTietLoaiHatDacSan,
            arrListSanPhamChiTietLoaiThaoMoc;

    ArrayList<String> arrListTenLoaiSanPham;
    ArrayList<String> arrListGiaLoaiSanPham;
    ArrayList<String> arrListSoLuongLoaiSanPham;
    //    Model
    model_hoaDon arrHoaDon;
    model_SanPham arrSanPham;
    model_Account arrAccout;
    model_chiTietSanPhamHoaDon arrChiTietHoaDon;
    //    Value
    double
            tongDoanhThu = 0,
            tongChiPhi = 0,
            tongLoiNhuan = 0,
            tongGiaLoaiCaPhe = 0,
            tongGiaLoaiTra = 0,
            tongGiaLoaiTCXDeo = 0,
            tongGiaLoaiHatDacSan = 0,
            tongGiaLoaiThaoMoc = 0,
            tongGiaNhapLoaiCaPhe = 0,
            tongGiaNhapLoaiTra = 0,
            tongGiaNhapLoaiTCXDeo = 0,
            tongGiaNhapLoaiHatDacSan = 0,
            tongGiaNhapLoaiThaoMoc = 0;
    int checkChonKieu = 0;
    boolean checkClickDate = true, checkClickShowKieu = true, checkClickClearSort = true, checkClickChonLoaiTim = true;
    // Date
    private Date date;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_doanh_thu);
        anhXa();
        batSuKien();
        getDataFirebaseAcountUser();
        getTongChiPhiDonHang();


    }

    private void batSuKien() {
//        xem chi tiết thông kê loại sản phẩm
//        chi tiết loại ca phê
        QuanLyDoanThu_ctlout_xemChiTietLoaiCaPhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quanLyDoanhThu_Activity.this, quanLyDoanhThu_chiTietLoai_Activity.class);
                arrListTenLoaiSanPham.clear();
                arrListGiaLoaiSanPham.clear();
                arrListSoLuongLoaiSanPham.clear();
                for (int i = 0; i < arrListSanPhamChiTietLoaiCaPhe.size(); i++) {
                    arrListTenLoaiSanPham.add(arrListSanPhamChiTietLoaiCaPhe.get(i).getTenSanPham());
                    arrListGiaLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiCaPhe.get(i).getGiaSanPham()));
                    arrListSoLuongLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiCaPhe.get(i).getSoLuongSanPham()));
                }
                intent.putStringArrayListExtra("it_listTenLoai", arrListTenLoaiSanPham);
                intent.putStringArrayListExtra("it_listGiaLoai", arrListGiaLoaiSanPham);
                intent.putStringArrayListExtra("it_listSoLuongLoai", arrListSoLuongLoaiSanPham);
                startActivity(intent);
            }
        });
//        chi tiết loại trà
        QuanLyDoanThu_ctlout_xemChiTietLoaiTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quanLyDoanhThu_Activity.this, quanLyDoanhThu_chiTietLoai_Activity.class);
                arrListTenLoaiSanPham.clear();
                arrListGiaLoaiSanPham.clear();
                arrListSoLuongLoaiSanPham.clear();
                for (int i = 0; i < arrListSanPhamChiTietLoaiTra.size(); i++) {
                    arrListTenLoaiSanPham.add(arrListSanPhamChiTietLoaiTra.get(i).getTenSanPham());
                    arrListGiaLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiTra.get(i).getGiaSanPham()));
                    arrListSoLuongLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiTra.get(i).getSoLuongSanPham()));
                }
                intent.putStringArrayListExtra("it_listTenLoai", arrListTenLoaiSanPham);
                intent.putStringArrayListExtra("it_listGiaLoai", arrListGiaLoaiSanPham);
                intent.putStringArrayListExtra("it_listSoLuongLoai", arrListSoLuongLoaiSanPham);
                startActivity(intent);
            }
        });
//        chi tiết loại trái cây xấy dẻo
        QuanLyDoanThu_ctlout_xemChiTietLoaiTCXDeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quanLyDoanhThu_Activity.this, quanLyDoanhThu_chiTietLoai_Activity.class);
                arrListTenLoaiSanPham.clear();
                arrListGiaLoaiSanPham.clear();
                arrListSoLuongLoaiSanPham.clear();
                for (int i = 0; i < arrListSanPhamChiTietLoaiTraiCXDeo.size(); i++) {
                    arrListTenLoaiSanPham.add(arrListSanPhamChiTietLoaiTraiCXDeo.get(i).getTenSanPham());
                    arrListGiaLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiTraiCXDeo.get(i).getGiaSanPham()));
                    arrListSoLuongLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiTraiCXDeo.get(i).getSoLuongSanPham()));
                }
                intent.putStringArrayListExtra("it_listTenLoai", arrListTenLoaiSanPham);
                intent.putStringArrayListExtra("it_listGiaLoai", arrListGiaLoaiSanPham);
                intent.putStringArrayListExtra("it_listSoLuongLoai", arrListSoLuongLoaiSanPham);
                startActivity(intent);
            }
        });
//        chi tiết loại hạt đạc sản
        QuanLyDoanThu_ctlout_xemChiTietLoaiHatDacSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quanLyDoanhThu_Activity.this, quanLyDoanhThu_chiTietLoai_Activity.class);
                arrListTenLoaiSanPham.clear();
                arrListGiaLoaiSanPham.clear();
                arrListSoLuongLoaiSanPham.clear();
                for (int i = 0; i < arrListSanPhamChiTietLoaiHatDacSan.size(); i++) {
                    arrListTenLoaiSanPham.add(arrListSanPhamChiTietLoaiHatDacSan.get(i).getTenSanPham());
                    arrListGiaLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiHatDacSan.get(i).getGiaSanPham()));
                    arrListSoLuongLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiHatDacSan.get(i).getSoLuongSanPham()));
                }
                intent.putStringArrayListExtra("it_listTenLoai", arrListTenLoaiSanPham);
                intent.putStringArrayListExtra("it_listGiaLoai", arrListGiaLoaiSanPham);
                intent.putStringArrayListExtra("it_listSoLuongLoai", arrListSoLuongLoaiSanPham);
                startActivity(intent);
            }
        });
        QuanLyDoanThu_ctlout_xemChiTietLoaiThaoMoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quanLyDoanhThu_Activity.this, quanLyDoanhThu_chiTietLoai_Activity.class);
                arrListTenLoaiSanPham.clear();
                arrListGiaLoaiSanPham.clear();
                arrListSoLuongLoaiSanPham.clear();
                for (int i = 0; i < arrListSanPhamChiTietLoaiThaoMoc.size(); i++) {
                    arrListTenLoaiSanPham.add(arrListSanPhamChiTietLoaiThaoMoc.get(i).getTenSanPham());
                    arrListGiaLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiThaoMoc.get(i).getGiaSanPham()));
                    arrListSoLuongLoaiSanPham.add(String.valueOf(arrListSanPhamChiTietLoaiThaoMoc.get(i).getSoLuongSanPham()));
                }
                intent.putStringArrayListExtra("it_listTenLoai", arrListTenLoaiSanPham);
                intent.putStringArrayListExtra("it_listGiaLoai", arrListGiaLoaiSanPham);
                intent.putStringArrayListExtra("it_listSoLuongLoai", arrListSoLuongLoaiSanPham);
                startActivity(intent);
            }
        });
//        chọn kiểu
        QuanLyDoanThu_ctlout_locTheoKhoang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkChonKieu = 0;
                checkClickChonLoaiTim = true;
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_top);
                QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setAnimation(animation);
                QuanLyDoanThu_img_btn_tuyChonFilter.setImageResource(R.drawable.ic_back_right);
                QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setVisibility(View.INVISIBLE);
                QuanLyDoanThu_tv_loaiTimKiem.setVisibility(View.INVISIBLE);
                QuanLyDoanThu_ctlout_loaiTimKiemTuKhoang.setVisibility(View.VISIBLE);
                QuanLyDoanThuTong_tv_tieuDe.setText("TỔNG DOANH THU THEO KHOẢNG");
            }
        });
        QuanLyDoanThu_ctlout_locTheoTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkChonKieu = 1;
                checkClickChonLoaiTim = true;
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_top);
                QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setAnimation(animation);
                QuanLyDoanThu_img_btn_tuyChonFilter.setImageResource(R.drawable.ic_back_right);
                QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setVisibility(View.INVISIBLE);
                QuanLyDoanThu_ctlout_loaiTimKiemTuKhoang.setVisibility(View.INVISIBLE);
                QuanLyDoanThu_tv_loaiTimKiem.setVisibility(View.VISIBLE);
                if (checkChonKieu == 1) {
                    QuanLyDoanThu_tv_loaiTimKiem.setText("Theo Tổng");
                    QuanLyDoanThuTong_tv_tieuDe.setText("TỔNG DOANH THU TỔNG");
                }
                setShowTongDoanhThu();
            }
        });
        QuanLyDoanThu_ctlout_locTheoThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date myDate = new Date();
                String reaTime = timeStampFormat.format(myDate);
                checkChonKieu = 2;
                checkClickChonLoaiTim = true;
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_top);
                QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setAnimation(animation);
                QuanLyDoanThu_img_btn_tuyChonFilter.setImageResource(R.drawable.ic_back_right);
                QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setVisibility(View.INVISIBLE);
                QuanLyDoanThu_ctlout_loaiTimKiemTuKhoang.setVisibility(View.INVISIBLE);
                QuanLyDoanThu_tv_loaiTimKiem.setVisibility(View.VISIBLE);
                if (checkChonKieu == 2) {
                    QuanLyDoanThu_tv_loaiTimKiem.setText("Theo Tháng");
                    QuanLyDoanThuTong_tv_tieuDe.setText("TỔNG DOANH THU THÁNG " + reaTime.substring(3, 5));

                }
                setShowDoanhThuTheoThang();
            }
        });
//        bắt sự kiện chọn kiểu loại
        QuanLyDoanThu_img_btn_tuyChonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkClickChonLoaiTim == true) {
                    checkClickChonLoaiTim = false;
                    QuanLyDoanThu_img_btn_tuyChonFilter.setImageResource(R.drawable.ic_back_down);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_top);
                    QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setAnimation(animation);
                    QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setVisibility(View.VISIBLE);
                    if (checkChonKieu == 0) {
                        QuanLyDoanThu_ctlout_locTheoKhoang.setAlpha((float) 0.5);
                        QuanLyDoanThu_ctlout_locTheoTong.setAlpha((float) 1);
                        QuanLyDoanThu_ctlout_locTheoThang.setAlpha((float) 1);
                    } else if (checkChonKieu == 1) {
                        QuanLyDoanThu_ctlout_locTheoTong.setAlpha((float) 0.5);
                        QuanLyDoanThu_ctlout_locTheoKhoang.setAlpha((float) 1);
                        QuanLyDoanThu_ctlout_locTheoThang.setAlpha((float) 1);
                    } else {
                        QuanLyDoanThu_ctlout_locTheoThang.setAlpha((float) 0.5);
                        QuanLyDoanThu_ctlout_locTheoTong.setAlpha((float) 1);
                        QuanLyDoanThu_ctlout_locTheoKhoang.setAlpha((float) 1);
                    }

                } else {
                    checkClickChonLoaiTim = true;
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_top);
                    QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setAnimation(animation);
                    QuanLyDoanThu_img_btn_tuyChonFilter.setImageResource(R.drawable.ic_back_right);
                    QuanLyDoanThu_crv_ShowChonLoaiTimKiem.setVisibility(View.INVISIBLE);
                }
            }
        });

//        bắt sự kiện lọc ngày tháng
        QuanLyDoanThu_img_btn_Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setShowDoanhThuTuKhoang() == true) {
                    //    Date hiện tại
                    SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date myDate = new Date();
                    String reaTime = timeStampFormat.format(myDate);
                    Toast.makeText(quanLyDoanhThu_Activity.this, reaTime + "", Toast.LENGTH_SHORT).show();

                    String ngayDen = null;
                    String ngayTu = null;
                    try {
                        ngayTu = QuanLyDoanThu_tv_chonNgayTu.getText().toString().substring(0, 10);
                    } catch (Exception e) {
                        Toast.makeText(quanLyDoanhThu_Activity.this, "Bạn chưa có ngày từ!!", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        ngayDen = QuanLyDoanThu_tv_chonNgayDen.getText().toString().substring(0, 10);
                    } catch (Exception e) {
                        Toast.makeText(quanLyDoanhThu_Activity.this, "Bạn chưa có ngày đến!!", Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<model_hoaDon> arrListHoaDonTam = new ArrayList<>();
                    arrListHoaDonTam.clear();
                    for (int i = 0; i < arrListHoaDon.size(); i++) {
                        arrHoaDon = arrListHoaDon.get(i);
                        String dateArrHoaDon = arrHoaDon.getNgayTaoChiTietHoaDon();
                        if (Integer.parseInt(dateArrHoaDon.substring(6, 10)) <= Integer.parseInt(ngayDen.substring(6, 10)) && Integer.parseInt(dateArrHoaDon.substring(6, 10)) >= Integer.parseInt(ngayTu.substring(6, 10))) {
                            if (Integer.parseInt(dateArrHoaDon.substring(3, 5)) <= Integer.parseInt(ngayDen.substring(3, 5)) && Integer.parseInt(dateArrHoaDon.substring(3, 5)) >= Integer.parseInt(ngayTu.substring(3, 5))) {
                                if (Integer.parseInt(dateArrHoaDon.substring(0, 2)) <= Integer.parseInt(ngayDen.substring(0, 2)) && Integer.parseInt(dateArrHoaDon.substring(0, 2)) >= Integer.parseInt(ngayTu.substring(0, 2))) {
                                    arrListHoaDonTam.add(arrHoaDon);
                                }
                            }
                        }
                    }
                    if (arrListHoaDonTam.size() != 0) {
                        tongDoanhThu = 0;
                        tongChiPhi = 0;
                        tongLoiNhuan = 0;
                        tongGiaLoaiCaPhe = 0;
                        tongGiaLoaiTra = 0;
                        tongGiaLoaiTCXDeo = 0;
                        tongGiaLoaiHatDacSan = 0;
                        tongGiaLoaiThaoMoc = 0;
                        tongGiaNhapLoaiCaPhe = 0;
                        tongGiaNhapLoaiTra = 0;
                        tongGiaNhapLoaiTCXDeo = 0;
                        tongGiaNhapLoaiHatDacSan = 0;
                        tongGiaNhapLoaiThaoMoc = 0;
                        getTongDoanhThu(arrListHoaDonTam);
                        getDataFirebaseChiTietHoaDon(arrListHoaDonTam);
                    } else {
                        Toast.makeText(quanLyDoanhThu_Activity.this, "Không có thu nhập vào thời điểm này!!", Toast.LENGTH_SHORT).show();
                        tongDoanhThu = 0;
                        tongChiPhi = 0;
                        tongLoiNhuan = 0;
                        tongGiaLoaiCaPhe = 0;
                        tongGiaLoaiTra = 0;
                        tongGiaLoaiTCXDeo = 0;
                        tongGiaLoaiHatDacSan = 0;
                        tongGiaLoaiThaoMoc = 0;
                        tongGiaNhapLoaiCaPhe = 0;
                        tongGiaNhapLoaiTra = 0;
                        tongGiaNhapLoaiTCXDeo = 0;
                        tongGiaNhapLoaiHatDacSan = 0;
                        tongGiaNhapLoaiThaoMoc = 0;
                        QuanLyDoanThuTong_tv_doanThu.setText("0đ");
                        QuanLyDoanThuTong_tv_chiPhi.setText("0đ");
                        setTongTienLoiNhuan();
                    }
                }


            }
        });
        //  chuyển về trang trước
        QuanLyDoanThu_img_btn_backItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quanLyDoanhThu_Activity.this, thongTinTaiKhoan_Activity.class);
                startActivity(intent);
            }
        });

        // bắt sự kiện mơ dialog chọn ngày
        QuanLyDoanThu_tv_chonNgayTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickDate = true;
                checkClickClearSort = true;
                QuanLyDoanThu_img_btn_Loc.setImageResource(R.drawable.ic_loc);
                showDatePickerDialog();
            }
        });
        QuanLyDoanThu_tv_chonNgayDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickDate = false;
                checkClickClearSort = true;
                QuanLyDoanThu_img_btn_Loc.setImageResource(R.drawable.ic_loc);
                showDatePickerDialog();
            }


        });

//        Bắt sự kiện chuyển show thông tin theo loại và tổng doanh thu
        QuanLyDoanThu_btn_thuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickShowKieu = true;
                //                set color
                QuanLyDoanThu_btn_thuNhap.setBackgroundResource(R.drawable.broder_radius_cam_thuonghieu);
                QuanLyDoanThu_btn_thuNhap.setTextColor(getApplication().getResources().getColor(R.color.white));
                QuanLyDoanThu_btn_LoaiSp.setBackgroundResource(R.drawable.broder_stroke_cam);
                QuanLyDoanThu_btn_LoaiSp.setTextColor(getApplication().getResources().getColor(R.color.lf_orange));
//                setVisibility
                QuanLyDoanThu_llout_ShowTongDoanhThu.setVisibility(View.VISIBLE);
                QuanLyDoanThu_llout_ShowLoaiSanPham.setVisibility(View.INVISIBLE);
            }
        });
        QuanLyDoanThu_btn_LoaiSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickShowKieu = false;
//                set color
                QuanLyDoanThu_btn_thuNhap.setBackgroundResource(R.drawable.broder_stroke_cam);
                QuanLyDoanThu_btn_thuNhap.setTextColor(getApplication().getResources().getColor(R.color.lf_orange));

                QuanLyDoanThu_btn_LoaiSp.setBackgroundResource(R.drawable.broder_radius_cam_thuonghieu);
                QuanLyDoanThu_btn_LoaiSp.setTextColor(getApplication().getResources().getColor(R.color.white));
//                setVisibility
                QuanLyDoanThu_llout_ShowTongDoanhThu.setVisibility(View.INVISIBLE);
                QuanLyDoanThu_llout_ShowLoaiSanPham.setVisibility(View.VISIBLE);

            }
        });
    }


    /********Xử lý hàm ngoài********/

    // Trung: hiện dialog date
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String date;
        int thang = month + 1;
        date = dayOfMonth + "/" + thang + "/" + year;
        if (date.length() < 10) {
            // Trung: set date cho editTextư
            if (checkClickDate == true) {
                QuanLyDoanThu_tv_chonNgayTu.setText("0" + date);
            } else {
                QuanLyDoanThu_tv_chonNgayDen.setText("0" + date);
            }

        } else {
            // Trung: set date cho editTextư
            if (checkClickDate == true) {
                QuanLyDoanThu_tv_chonNgayTu.setText(date);
            } else {
                QuanLyDoanThu_tv_chonNgayDen.setText(date);
            }
        }
    }

    /*********Set PieChart Loại sản phẩm*******/
    private void setBieuDoPiaChart() {
        ArrayList<Double> valuePiaChat = new ArrayList<>();
        ArrayList<PieEntry> piaEnTries = new ArrayList<>();

        valuePiaChat.add(tongGiaLoaiCaPhe);
        valuePiaChat.add(tongGiaLoaiTra);
        valuePiaChat.add(tongGiaLoaiTCXDeo);
        valuePiaChat.add(tongGiaLoaiHatDacSan);
        valuePiaChat.add(tongGiaLoaiThaoMoc);


        for (int i = 0; i < valuePiaChat.size(); i++) {
            float value = (float) Float.parseFloat(String.valueOf(valuePiaChat.get(i) * 10.0));

            PieEntry piaEntry = new PieEntry(value, i);

            piaEnTries.add(piaEntry);

        }
        PieDataSet pieDataSet = new PieDataSet(piaEnTries, "Phần trăm thu");
//set color
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(16f);

        QuanLyDoanThu_pieChart.setData(new PieData(pieDataSet));

        QuanLyDoanThu_pieChart.animateXY(3000, 3000);
        QuanLyDoanThu_pieChart.getDescription().setEnabled(false);
    }

    /*********Set giá trị loại sản phẩm*******/
//    tính ố lượng từng loại sản phẩm bán được
    private void setSoLuongLoaiSanPham() {


        int soLuongSPCaPhe = 0;
        int soLuongSPHatDacSan = 0;
        int soLuongSPTCXDeo = 0;
        int soLuongSPTra = 0;
        int soLuongSPThaoMoc = 0;
        for (int i = 0; i < arrListSanPhamChiTietLoaiCaPhe.size(); i++) {
            soLuongSPCaPhe = soLuongSPCaPhe + arrListSanPhamChiTietLoaiCaPhe.get(i).getSoLuongSanPham();
        }
        for (int i = 0; i < arrListSanPhamChiTietLoaiHatDacSan.size(); i++) {
            soLuongSPHatDacSan = soLuongSPHatDacSan + arrListSanPhamChiTietLoaiHatDacSan.get(i).getSoLuongSanPham();
        }
        for (int i = 0; i < arrListSanPhamChiTietLoaiTraiCXDeo.size(); i++) {
            soLuongSPTCXDeo = soLuongSPTCXDeo + arrListSanPhamChiTietLoaiTraiCXDeo.get(i).getSoLuongSanPham();
        }
        for (int i = 0; i < arrListSanPhamChiTietLoaiTra.size(); i++) {
            soLuongSPTra = soLuongSPTra + arrListSanPhamChiTietLoaiTra.get(i).getSoLuongSanPham();
        }
        for (int i = 0; i < arrListSanPhamChiTietLoaiThaoMoc.size(); i++) {
            soLuongSPThaoMoc = soLuongSPThaoMoc + arrListSanPhamChiTietLoaiThaoMoc.get(i).getSoLuongSanPham();
        }
        QuanLyDoanThu_tv_soLuongLoaiCaPhe.setText("SL: " + soLuongSPCaPhe);
        QuanLyDoanThu_tv_soLuongLoaiHatDacSan.setText("SL: " + soLuongSPHatDacSan);
        QuanLyDoanThu_tv_soLuongLoaiTraiCayXayDeo.setText("SL: " + soLuongSPTCXDeo);
        QuanLyDoanThu_tv_soLuongLoaiTraTuiLoc.setText("SL: " + soLuongSPTra);
        QuanLyDoanThu_tv_soLuongThaoMoc.setText("SL: " + soLuongSPThaoMoc);
    }

    //   tính phần trăm từng loại sản phẩm bán được
    private void setPhanTramLoaiSanPham() {
        String phanTramLoaiCaPhe = String.valueOf((tongGiaLoaiCaPhe / tongDoanhThu) * 100).substring(0, 3);
        String phanTramLoaiTra = String.valueOf((tongGiaLoaiTra / tongDoanhThu) * 100).substring(0, 3);
        String phanTramLoaiTCXDeo = String.valueOf((tongGiaLoaiTCXDeo / tongDoanhThu) * 100).substring(0, 3);
        String phanTramLoaiHatDacSan = String.valueOf((tongGiaLoaiHatDacSan / tongDoanhThu) * 100).substring(0, 3);
        String phanTramLoaiThaoMoc = String.valueOf((tongGiaLoaiThaoMoc / tongDoanhThu) * 100).substring(0, 3);
// set giá trị vô texview
        QuanLyDoanThu_tv_phanTramLoaiCaPhe.setText(phanTramLoaiCaPhe + "%");
        QuanLyDoanThu_tv_phanTramLoaiTraTuiLoc.setText(phanTramLoaiTra + "%");
        QuanLyDoanThu_tv_loaiPhamTramTraiCayXayDeo.setText(phanTramLoaiTCXDeo + "%");
        QuanLyDoanThu_tv_phanTramLoaiHatDacSan.setText(phanTramLoaiHatDacSan + "%");
        QuanLyDoanThu_tv_phamTramLoaiThaoMoc.setText(phanTramLoaiThaoMoc + "%");

    }

    //   tính tổng giá từng loại sản phẩm bán được
    private void setGiaLoaiSanPham() {
        QuanLyDoanThu_tv_giaTienLoaiCaPhe.setText(tongGiaLoaiCaPhe + "00đ");
        QuanLyDoanThu_tv_giaTienLoaiTraTuiLoc.setText(tongGiaLoaiTra + "00đ");
        QuanLyDoanThu_tv_giaTienLoaiTraiCayXayDeo.setText(tongGiaLoaiTCXDeo + "00đ");
        QuanLyDoanThu_tv_giaTienLoaiHatDacSan.setText(tongGiaLoaiHatDacSan + "00đ");
        QuanLyDoanThu_tv_giaTiemLoaiThaoMoc.setText(tongGiaLoaiThaoMoc + "00đ");
    }

    /*********Lọc doanh thu tổng"*******/
    private void setShowTongDoanhThu() {
        tongDoanhThu = 0;
        tongChiPhi = 0;
        tongLoiNhuan = 0;

        tongGiaLoaiCaPhe = 0;
        tongGiaLoaiTra = 0;
        tongGiaLoaiTCXDeo = 0;
        tongGiaLoaiHatDacSan = 0;
        tongGiaLoaiThaoMoc = 0;

        tongGiaNhapLoaiCaPhe = 0;
        tongGiaNhapLoaiTra = 0;
        tongGiaNhapLoaiTCXDeo = 0;
        tongGiaNhapLoaiHatDacSan = 0;
        tongGiaNhapLoaiThaoMoc = 0;
        getTongDoanhThu(arrListHoaDon);
        getDataFirebaseChiTietHoaDon(arrListHoaDon);
    }

    /*********Lọc doanh thu theo "Tháng"*******/
    private void setShowDoanhThuTheoThang() {
        //    Date hiện tại
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date myDate = new Date();
        String reaTime = timeStampFormat.format(myDate);

        ArrayList<model_hoaDon> arrListHoaDonTam = new ArrayList<>();
        arrListHoaDonTam.clear();
        for (int i = 0; i < arrListHoaDon.size(); i++) {
            arrHoaDon = arrListHoaDon.get(i);
            String dateArrHoaDon = arrHoaDon.getNgayTaoChiTietHoaDon();
            if (Integer.parseInt(dateArrHoaDon.substring(6, 10)) == Integer.parseInt(reaTime.substring(6, 10))) {
                if (Integer.parseInt(dateArrHoaDon.substring(3, 5)) == Integer.parseInt(reaTime.substring(3, 5))) {
                    arrListHoaDonTam.add(arrHoaDon);
                }
            }
        }
        Toast.makeText(this, arrListHoaDonTam.size() + "", Toast.LENGTH_SHORT).show();
        if (arrListHoaDonTam.size() != 0) {
            tongDoanhThu = 0;
            tongChiPhi = 0;
            tongLoiNhuan = 0;
            tongGiaLoaiCaPhe = 0;
            tongGiaLoaiTra = 0;
            tongGiaLoaiTCXDeo = 0;
            tongGiaLoaiHatDacSan = 0;
            tongGiaLoaiThaoMoc = 0;
            tongGiaNhapLoaiCaPhe = 0;
            tongGiaNhapLoaiTra = 0;
            tongGiaNhapLoaiTCXDeo = 0;
            tongGiaNhapLoaiHatDacSan = 0;
            tongGiaNhapLoaiThaoMoc = 0;
            getTongDoanhThu(arrListHoaDonTam);
            getDataFirebaseChiTietHoaDon(arrListHoaDonTam);
        } else {
            Toast.makeText(quanLyDoanhThu_Activity.this, "Không có thu nhập vào thời điểm này!!", Toast.LENGTH_SHORT).show();
            tongDoanhThu = 0;
            tongChiPhi = 0;
            tongLoiNhuan = 0;
            tongGiaLoaiCaPhe = 0;
            tongGiaLoaiTra = 0;
            tongGiaLoaiTCXDeo = 0;
            tongGiaLoaiHatDacSan = 0;
            tongGiaLoaiThaoMoc = 0;
            tongGiaNhapLoaiCaPhe = 0;
            tongGiaNhapLoaiTra = 0;
            tongGiaNhapLoaiTCXDeo = 0;
            tongGiaNhapLoaiHatDacSan = 0;
            tongGiaNhapLoaiThaoMoc = 0;
            QuanLyDoanThuTong_tv_doanThu.setText("0đ");
            QuanLyDoanThuTong_tv_chiPhi.setText("0đ");
            setTongTienLoiNhuan();
        }
    }

    /*********Lọc doanh thu theo "Từ"&"Đến"*******/
// checkValidate dữ liệu ngày "Từ" và ngày "Đến"
    private boolean setShowDoanhThuTuKhoang() {
        //    Date hiện tại

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date myDate = new Date();
        String reaTime = timeStampFormat.format(myDate);

        String ngayDen = null;
        String ngayTu = null;
        try {
            ngayTu = QuanLyDoanThu_tv_chonNgayTu.getText().toString().substring(0, 10);
        } catch (Exception e) {
            Toast.makeText(this, "Bạn chưa có ngày từ!!", Toast.LENGTH_SHORT).show();
        }
        try {
            ngayDen = QuanLyDoanThu_tv_chonNgayDen.getText().toString().substring(0, 10);
        } catch (Exception e) {
            Toast.makeText(this, "Bạn chưa có ngày đến!!", Toast.LENGTH_SHORT).show();
        }


//        check validate đầu vào
        if (!(ngayDen == null || ngayDen.equals(""))) {
            if (!(ngayTu == null || ngayDen.equals(""))) {

                if (checkClickClearSort == true) {
                    checkClickClearSort = false;
                    QuanLyDoanThu_img_btn_Loc.setImageResource(R.drawable.ic_clear_sort);

//        kiểm tra ngày đến
                    if (Integer.parseInt(ngayDen.substring(6, 10)) <= Integer.parseInt(reaTime.substring(6, 10))) {
                        if (Integer.parseInt(ngayDen.substring(3, 5)) <= Integer.parseInt(reaTime.substring(3, 5))) {
                            if (Integer.parseInt(ngayDen.substring(3, 5)) == Integer.parseInt(reaTime.substring(3, 5))) {

                                if (Integer.parseInt(ngayDen.substring(0, 2)) <= Integer.parseInt(reaTime.substring(0, 2))) {
//              kiểm tra ngày từ
                                    if (Integer.parseInt(ngayTu.substring(6, 10)) <= Integer.parseInt(ngayDen.substring(6, 10))) {
                                        if (Integer.parseInt(ngayTu.substring(3, 5)) <= Integer.parseInt(ngayDen.substring(3, 5))) {
                                            if (Integer.parseInt(ngayTu.substring(3, 5)) == Integer.parseInt(ngayDen.substring(3, 5))) {
                                                if (Integer.parseInt(ngayTu.substring(0, 2)) <= Integer.parseInt(ngayDen.substring(0, 2))) {
//                   kiểm tra mảng Hóa đơn
                                                    return true;
                                                } else {
                                                    Toast.makeText(this, "Ngày 'Từ' phải nhỏ hoặc bằng ngày 'Đến'!!", Toast.LENGTH_SHORT).show();
                                                    return false;
                                                }
                                            } else {
                                                return true;
                                            }
                                        } else {
                                            Toast.makeText(this, "Tháng 'Từ' phải nhỏ hoặc bằng tháng 'Đến'!!", Toast.LENGTH_SHORT).show();
                                            return false;
                                        }
                                    } else {
                                        Toast.makeText(this, "Năm 'Từ' phải nhỏ hơn hoặc bằng năm 'Đến!!", Toast.LENGTH_SHORT).show();
                                        return false;
                                    }


                                } else {
                                    Toast.makeText(this, "Ngày 'Đến' không được lớn hơn ngày hiện tại!!", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            } else {
                                if (Integer.parseInt(ngayTu.substring(6, 10)) <= Integer.parseInt(ngayDen.substring(6, 10))) {
                                    if (Integer.parseInt(ngayTu.substring(3, 5)) <= Integer.parseInt(ngayDen.substring(3, 5))) {
                                        if (Integer.parseInt(ngayTu.substring(3, 5)) == Integer.parseInt(ngayDen.substring(3, 5))) {
                                            if (Integer.parseInt(ngayTu.substring(0, 2)) <= Integer.parseInt(ngayDen.substring(0, 2))) {
//                   kiểm tra mảng Hóa đơn
                                                return true;
                                            } else {
                                                Toast.makeText(this, "Ngày 'Từ' phải nhỏ hoặc bằng ngày 'Đến'!!", Toast.LENGTH_SHORT).show();
                                                return false;
                                            }
                                        } else {

                                            return true;
                                        }
                                    } else {
                                        Toast.makeText(this, "Tháng 'Từ' phải nhỏ hoặc bằng tháng 'Đến'!!", Toast.LENGTH_SHORT).show();
                                        return false;
                                    }
                                } else {
                                    Toast.makeText(this, "Năm 'Từ' phải nhỏ hơn hoặc bằng năm 'Đến!!", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            }
                        } else {
                            Toast.makeText(this, "Tháng 'Đến' không được lớn hơn tháng hiện tại!!", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } else {
                        Toast.makeText(this, "Năm 'Đến' không được lớn hơn năm hiện tại!!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    checkClickClearSort = true;
                    QuanLyDoanThu_img_btn_Loc.setImageResource(R.drawable.ic_loc);
                    QuanLyDoanThu_tv_chonNgayTu.setText("");
                    QuanLyDoanThu_tv_chonNgayDen.setText("");
                    tongDoanhThu = 0;
                    tongChiPhi = 0;
                    tongLoiNhuan = 0;
                    tongGiaLoaiCaPhe = 0;
                    tongGiaLoaiTra = 0;
                    tongGiaLoaiTCXDeo = 0;
                    tongGiaLoaiHatDacSan = 0;
                    tongGiaLoaiThaoMoc = 0;
                    tongGiaNhapLoaiCaPhe = 0;
                    tongGiaNhapLoaiTra = 0;
                    tongGiaNhapLoaiTCXDeo = 0;
                    tongGiaNhapLoaiHatDacSan = 0;
                    tongGiaNhapLoaiThaoMoc = 0;
                    getTongDoanhThu(arrListHoaDon);
                    getDataFirebaseChiTietHoaDon(arrListHoaDon);


                }
            } else {
                Toast.makeText(this, "Bạn chưa có ngày từ!!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Bạn chưa có ngày đến!!", Toast.LENGTH_SHORT).show();
            return false;
        }
//        check validate mảng Hóa đơn


        return false;
    }


    /*********Set biểu dồ BarChart********/
    // tạo biều đồ BarChart Bên tổng doanh thu
    private void setBieuDoBarChart() {
        ArrayList<Double> valueBarChat = new ArrayList<>();
        ArrayList<BarEntry> barEnTries = new ArrayList<>();

        valueBarChat.add(tongDoanhThu);
        valueBarChat.add(tongChiPhi);
        valueBarChat.add(tongLoiNhuan);


        for (int i = 0; i < valueBarChat.size(); i++) {
            double value = (double) (Math.floor(valueBarChat.get(i) * 10) / 10);

            BarEntry barEntry = new BarEntry(i, (float) value);

            barEnTries.add(barEntry);

        }

//        setDataBar
        BarDataSet barDataSet = new BarDataSet(barEnTries, "");
//        setColor
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextSize(12f);
        barDataSet.setValueTextColor(Color.BLACK);
//        setGiaTri
        barDataSet.setDrawIcons(false);
        QuanLyDoanThuTong_barChart.setData(new BarData(barDataSet));
//        setAnima
        QuanLyDoanThuTong_barChart.animateY(3000);

        QuanLyDoanThuTong_barChart.getDescription().setText("Sơ đồ doanh thu");
        QuanLyDoanThuTong_barChart.getDescription().setTextColor(R.color.lf_orange);

    }

    /*********Tính tổng lợi nhuật đơn hàng********/
//   Tính tổng lợi nhuận
    private void setTongTienLoiNhuan() {
        tongLoiNhuan = tongDoanhThu - tongChiPhi;
        QuanLyDoanThuTong_tv_loiNhuan.setText(tongLoiNhuan + "00đ");
        QuanLyDoanThu_tv_laiTong.setText("T Lãi: " + tongLoiNhuan + "00đ");
        QuanLyDoanThu_tv_vonTong.setText("T Vốn: " + tongChiPhi + "00đ");

        setBieuDoBarChart();

    }


    /*********Tính tổng chi phí đơn hàng********/
    //    Tính tổng chi phí đơn hàng
    private void getTongChiPhiDonHang() {

        double giaNhapSanPham = tongGiaNhapLoaiCaPhe + tongGiaNhapLoaiTra + tongGiaNhapLoaiTCXDeo + tongGiaNhapLoaiHatDacSan + tongGiaNhapLoaiThaoMoc;
        tongChiPhi = giaNhapSanPham;


        QuanLyDoanThuTong_tv_chiPhi.setText(tongChiPhi + "00đ");

        setTongTienLoiNhuan();
    }

    //    lấy ArrListSanPham từ arrListChiTietDonHang tại idSanPham, Firebase
    private void getDataFirebaseSanPham() {
        arrListSanPham.clear();
        arrListSanPhamChiTietLoaiCaPhe.clear();
        arrListSanPhamChiTietLoaiTra.clear();
        arrListSanPhamChiTietLoaiTraiCXDeo.clear();
        arrListSanPhamChiTietLoaiHatDacSan.clear();
        arrListSanPhamChiTietLoaiThaoMoc.clear();
        for (int i = 0; i < arrListChiTietHoaDon.size(); i++) {
            String idSanPham = arrListChiTietHoaDon.get(i).getIdSanPham();

            dataSanPhamRef = database.getReference("khoHang").child(idSanPham);
            int finalI = i;
            int finalI1 = i;
            dataSanPhamRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    arrSanPham = snapshot.getValue(model_SanPham.class);

                    int soLuong = Integer.parseInt(arrListChiTietHoaDon.get(finalI).getSoLuongSanPham());
                    arrListSanPham.add(arrSanPham);
                    if (arrSanPham.getLoaiSanPham().equals("Cà phê")) {
                        arrListSanPhamLoaiCaPhe.add(arrSanPham);
                        tongGiaLoaiCaPhe = tongGiaLoaiCaPhe + (arrSanPham.getGiaBanSanPham() * soLuong);
                        tongGiaNhapLoaiCaPhe = tongGiaNhapLoaiCaPhe + (arrSanPham.getGiaNhapSanPham() * soLuong);
                        if (arrListSanPhamChiTietLoaiCaPhe != null) {
                            int check = 0;
                            for (int i = 0; i < arrListSanPhamChiTietLoaiCaPhe.size(); i++) {
                                if (arrListSanPhamChiTietLoaiCaPhe.get(i).getTenSanPham().equals(arrSanPham.getTenSanPham())) {
                                    arrListSanPhamChiTietLoaiCaPhe.get(i).setSoLuongSanPham(arrListSanPhamChiTietLoaiCaPhe.get(i).getSoLuongSanPham() + soLuong);
                                    check++;
                                }
                            }
                            if (check == 0) {
                                arrListSanPhamChiTietLoaiCaPhe.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                            }
                        } else {
                            arrListSanPhamChiTietLoaiCaPhe.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                        }
                    } else if (arrSanPham.getLoaiSanPham().equals("Trà túi lọc")) {
                        arrListSanPhamLoaiTra.add(arrSanPham);
                        tongGiaLoaiTra = tongGiaLoaiTra + (arrSanPham.getGiaBanSanPham() * soLuong);
                        tongGiaNhapLoaiTra = tongGiaNhapLoaiTra + (arrSanPham.getGiaNhapSanPham() * soLuong);
                        if (arrListSanPhamChiTietLoaiTra != null) {
                            int check = 0;
                            for (int i = 0; i < arrListSanPhamChiTietLoaiTra.size(); i++) {
                                if (arrListSanPhamChiTietLoaiTra.get(i).getTenSanPham().equals(arrSanPham.getTenSanPham())) {
                                    arrListSanPhamChiTietLoaiTra.get(i).setSoLuongSanPham(arrListSanPhamChiTietLoaiTra.get(i).getSoLuongSanPham() + soLuong);
                                    check++;
                                }
                            }
                            if (check == 0) {
                                arrListSanPhamChiTietLoaiTra.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                            }
                        } else {
                            arrListSanPhamChiTietLoaiTra.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                        }
                    } else if (arrSanPham.getLoaiSanPham().equals("Trái cây xấy dẻo")) {
                        arrListSanPhamLoaiTraiCXDeo.add(arrSanPham);
                        tongGiaLoaiTCXDeo = tongGiaLoaiTCXDeo + (arrSanPham.getGiaBanSanPham() * soLuong);
                        tongGiaNhapLoaiTCXDeo = tongGiaNhapLoaiTCXDeo + (arrSanPham.getGiaNhapSanPham() * soLuong);
                        if (arrListSanPhamChiTietLoaiTraiCXDeo != null) {
                            int check = 0;
                            for (int i = 0; i < arrListSanPhamChiTietLoaiTraiCXDeo.size(); i++) {
                                if (arrListSanPhamChiTietLoaiTraiCXDeo.get(i).getTenSanPham().equals(arrSanPham.getTenSanPham())) {
                                    arrListSanPhamChiTietLoaiTraiCXDeo.get(i).setSoLuongSanPham(arrListSanPhamChiTietLoaiTraiCXDeo.get(i).getSoLuongSanPham() + soLuong);
                                    check++;
                                }
                            }
                            if (check == 0) {
                                arrListSanPhamChiTietLoaiTraiCXDeo.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                            }
                        } else {
                            arrListSanPhamChiTietLoaiTraiCXDeo.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                        }
                    } else if (arrSanPham.getLoaiSanPham().equals("Hạt đặc sản")) {
                        arrListSanPhamLoaiHatDacSan.add(arrSanPham);
                        tongGiaLoaiHatDacSan = tongGiaLoaiHatDacSan + (arrSanPham.getGiaBanSanPham() * soLuong);
                        tongGiaNhapLoaiHatDacSan = tongGiaNhapLoaiHatDacSan + (arrSanPham.getGiaNhapSanPham() * soLuong);
                        if (arrListSanPhamChiTietLoaiHatDacSan != null) {
                            int check = 0;
                            for (int i = 0; i < arrListSanPhamChiTietLoaiHatDacSan.size(); i++) {
                                if (arrListSanPhamChiTietLoaiHatDacSan.get(i).getTenSanPham().equals(arrSanPham.getTenSanPham())) {
                                    arrListSanPhamChiTietLoaiHatDacSan.get(i).setSoLuongSanPham(arrListSanPhamChiTietLoaiHatDacSan.get(i).getSoLuongSanPham() + soLuong);
                                    check++;
                                }
                            }
                            if (check == 0) {
                                arrListSanPhamChiTietLoaiHatDacSan.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                            }
                        } else {
                            arrListSanPhamChiTietLoaiHatDacSan.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                        }
                    } else if (arrSanPham.getLoaiSanPham().equals("Thảo mộc")) {
                        arrListSanPhamLoaiThaoMoc.add(arrSanPham);
                        tongGiaLoaiThaoMoc = tongGiaLoaiThaoMoc + (arrSanPham.getGiaBanSanPham() * soLuong);
                        tongGiaNhapLoaiThaoMoc = tongGiaNhapLoaiThaoMoc + (arrSanPham.getGiaNhapSanPham() * soLuong);
                        if (arrListSanPhamChiTietLoaiThaoMoc != null) {
                            int check = 0;
                            for (int i = 0; i < arrListSanPhamChiTietLoaiThaoMoc.size(); i++) {
                                if (arrListSanPhamChiTietLoaiThaoMoc.get(i).getTenSanPham().equals(arrSanPham.getTenSanPham())) {
                                    arrListSanPhamChiTietLoaiThaoMoc.get(i).setSoLuongSanPham(arrListSanPhamChiTietLoaiThaoMoc.get(i).getSoLuongSanPham() + soLuong);
                                    check++;
                                }
                            }
                            if (check == 0) {
                                arrListSanPhamChiTietLoaiThaoMoc.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                            }
                        } else {
                            arrListSanPhamChiTietLoaiThaoMoc.add(new model_chiTietLoaiSanPham_thongKe(arrSanPham.getTenSanPham(), arrSanPham.getGiaBanSanPham(), soLuong));
                        }
                    }


                    if (arrListChiTietHoaDon.size() == finalI + 1) {
                        getTongChiPhiDonHang();
                        setGiaLoaiSanPham();
                        setSoLuongLoaiSanPham();
                        setPhanTramLoaiSanPham();
                        setBieuDoPiaChart();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });

        }

    }


    //    lấy danh sách chi tiết hóa đơn từ ArrListHoaDon tại idChiTietHoaDon
    private void getDataFirebaseChiTietHoaDon(ArrayList<model_hoaDon> arrListHoaDon) {
        arrListChiTietHoaDon.clear();
        for (int i = 0; i < arrListHoaDon.size(); i++) {
            String idChiTietHoaDon = arrListHoaDon.get(i).getIdChiTietDonHang();
            dataChiTietHoaDonRef = database.getReference("ChiTietHoaDon").child(idChiTietHoaDon);
            int finalI = i;
            dataChiTietHoaDonRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    for (DataSnapshot child : snapshot.getChildren()) {
                        arrChiTietHoaDon = child.getValue(model_chiTietSanPhamHoaDon.class);
                        arrListChiTietHoaDon.add(arrChiTietHoaDon);
                    }
                    if (arrListHoaDon.size() == finalI + 1) {
                        getDataFirebaseSanPham();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }

    /*********Tính tổng doanh thu đơn hàng********/
    //    tính tổng doanh thu gán giá trị
    private void getTongDoanhThu(ArrayList<model_hoaDon> arrListHoaDon) {
        for (int i = 0; i < arrListHoaDon.size(); i++) {
            tongDoanhThu = tongDoanhThu + (arrListHoaDon.get(i).getTongTien() - 30.0);
        }
        QuanLyDoanThuTong_tv_doanThu.setText(tongDoanhThu + "00đ");
        //        check loại tiền
        if (String.valueOf(tongDoanhThu).length() < 5) {
            QuanLyDoanThuTong_tv_loaiTien.setText("Ngàn đồng");
        } else if (String.valueOf(tongDoanhThu).length() > 5) {
            QuanLyDoanThuTong_tv_loaiTien.setText("Triệu đồng");

        }

    }

    // lấy danh sách user để lấy id Đơn hàng
    private void getDataFirebaseAcountUser() {
        dataAccountRef = database.getReference("Accounts");
        dataAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrListAccount.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrAccout = child.getValue(model_Account.class);
                    arrListAccount.add(arrAccout);
                }
                getDataFirebaseDonHang();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    //    lấy danh sách Đơn hàng đã xử lý "xong" từ firebase về
    private void getDataFirebaseDonHang() {
        arrListHoaDon.clear();
        for (int i = 0; i < arrListAccount.size(); i++) {
            String idDonHangUser = arrListAccount.get(i).getIdDanhSachDonHang();

            dataDonHangRef = database.getReference("HoaDon").child(idDonHangUser);
            int finalI = i;
            dataDonHangRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        arrHoaDon = child.getValue(model_hoaDon.class);
                        if (arrHoaDon.getTinhTrangDonHang() == 2) {
                            arrListHoaDon.add(arrHoaDon);
                        }

                    }
                    if (arrListAccount.size() == finalI + 1) {
                        getTongDoanhThu(arrListHoaDon);
                        getDataFirebaseChiTietHoaDon(arrListHoaDon);
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }

    private void anhXa() {


        // ArrListModel
        arrListHoaDon = new ArrayList<>();
        arrListSanPham = new ArrayList<>();
//        san phẩm loại
        arrListSanPhamLoaiCaPhe = new ArrayList<>();
        arrListSanPhamLoaiTra = new ArrayList<>();
        arrListSanPhamLoaiTraiCXDeo = new ArrayList<>();
        arrListSanPhamLoaiHatDacSan = new ArrayList<>();
        arrListSanPhamLoaiThaoMoc = new ArrayList<>();
//        chi tiết loại
        arrListSanPhamChiTietLoaiCaPhe = new ArrayList<>();
        arrListSanPhamChiTietLoaiTra = new ArrayList<>();
        arrListSanPhamChiTietLoaiTraiCXDeo = new ArrayList<>();
        arrListSanPhamChiTietLoaiHatDacSan = new ArrayList<>();
        arrListSanPhamChiTietLoaiThaoMoc = new ArrayList<>();
//      kiểu string
        arrListTenLoaiSanPham = new ArrayList<>();
        arrListGiaLoaiSanPham = new ArrayList<>();
        arrListSoLuongLoaiSanPham = new ArrayList<>();

        arrListAccount = new ArrayList<>();
        arrListChiTietHoaDon = new ArrayList<>();
        // Firebase
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");

        // TextView
        QuanLyDoanThu_tv_loaiTimKiem = findViewById(R.id.quanLyDoanThu_tv_loaiTimKiem);

        QuanLyDoanThuTong_tv_tieuDe = findViewById(R.id.quanLyDoanThuTong_tv_tieuDe);
        QuanLyDoanThuTong_tv_doanThu = findViewById(R.id.quanLyDoanThuTong_tv_doanThu);
        QuanLyDoanThuTong_tv_chiPhi = findViewById(R.id.quanLyDoanThuTong_tv_chiPhi);
        QuanLyDoanThuTong_tv_loiNhuan = findViewById(R.id.quanLyDoanThuTong_tv_loiNhuan);

        QuanLyDoanThu_tv_vonTong = findViewById(R.id.quanLyDoanThu_tv_vonTong);
        QuanLyDoanThu_tv_laiTong = findViewById(R.id.quanLyDoanThu_tv_laiTong);
        QuanLyDoanThuTong_tv_loaiTien = findViewById(R.id.quanLyDoanThuTong_tv_loaiTien);

        QuanLyDoanThu_tv_phanTramLoaiCaPhe = findViewById(R.id.quanLyDoanThu_tv_phanTramLoaiCaPhe);
        QuanLyDoanThu_tv_giaTienLoaiCaPhe = findViewById(R.id.quanLyDoanThu_tv_giaTienLoaiCaPhe);
        QuanLyDoanThu_tv_soLuongLoaiCaPhe = findViewById(R.id.quanLyDoanThu_tv_soLuongLoaiCaPhe);

        QuanLyDoanThu_tv_phanTramLoaiTraTuiLoc = findViewById(R.id.quanLyDoanThu_tv_phanTramLoaiTraTuiLoc);
        QuanLyDoanThu_tv_giaTienLoaiTraTuiLoc = findViewById(R.id.quanLyDoanThu_tv_giaTienLoaiTraTuiLoc);
        QuanLyDoanThu_tv_soLuongLoaiTraTuiLoc = findViewById(R.id.quanLyDoanThu_tv_soLuongLoaiTraTuiLoc);

        QuanLyDoanThu_tv_giaTienLoaiTraiCayXayDeo = findViewById(R.id.quanLyDoanThu_tv_giaTienLoaiTraiCayXayDeo);
        QuanLyDoanThu_tv_soLuongLoaiTraiCayXayDeo = findViewById(R.id.quanLyDoanThu_tv_soLuongLoaiTraiCayXayDeo);
        QuanLyDoanThu_tv_loaiPhamTramTraiCayXayDeo = findViewById(R.id.quanLyDoanThu_tv_loaiPhamTramTraiCayXayDeo);

        QuanLyDoanThu_tv_phanTramLoaiHatDacSan = findViewById(R.id.quanLyDoanThu_tv_phanTramLoaiHatDacSan);
        QuanLyDoanThu_tv_giaTienLoaiHatDacSan = findViewById(R.id.quanLyDoanThu_tv_giaTienLoaiHatDacSan);
        QuanLyDoanThu_tv_soLuongLoaiHatDacSan = findViewById(R.id.quanLyDoanThu_tv_soLuongLoaiHatDacSan);

        QuanLyDoanThu_tv_phamTramLoaiThaoMoc = findViewById(R.id.quanLyDoanThu_tv_phamTramLoaiThaoMoc);
        QuanLyDoanThu_tv_giaTiemLoaiThaoMoc = findViewById(R.id.quanLyDoanThu_tv_giaTiemLoaiThaoMoc);
        QuanLyDoanThu_tv_soLuongThaoMoc = findViewById(R.id.quanLyDoanThu_tv_soLuongThaoMoc);
//        ImageView
        QuanLyDoanThu_img_btn_backItem = findViewById(R.id.quanLyDoanThu_img_btn_backItem);
        QuanLyDoanThu_img_btn_tuyChonFilter = findViewById(R.id.quanLyDoanThu_img_btn_tuyChonFilter);
        QuanLyDoanThu_img_chiTietLoaiCaPhe = findViewById(R.id.quanLyDoanThu_img_chiTietLoaiCaPhe);
        QuanLyDoanThu_img_chiTietLoaiTraTuiLoc = findViewById(R.id.quanLyDoanThu_img_chiTietLoaiTraTuiLoc);
        QuanLyDoanThu_img_chiTietLoaiTraiCayXayDeo = findViewById(R.id.quanLyDoanThu_img_chiTietLoaiTraiCayXayDeo);
        QuanLyDoanThu_img_chiTietLoaiHatDacSan = findViewById(R.id.quanLyDoanThu_img_chiTietLoaiHatDacSan);
        QuanLyDoanThu_img_chiTietLoaiThaoMoc = findViewById(R.id.quanLyDoanThu_img_chiTietLoaiThaoMoc);
        QuanLyDoanThu_img_btn_Loc = findViewById(R.id.quanLyDoanThu_img_btn_Loc);
//        EditText
        QuanLyDoanThu_tv_chonNgayTu = findViewById(R.id.quanLyDoanThu_tv_chonNgayTu);
        QuanLyDoanThu_tv_chonNgayDen = findViewById(R.id.quanLyDoanThu_tv_chonNgayDen);
//        CardView
        QuanLyDoanThu_crv_colorLoaiCaPhe = findViewById(R.id.quanLyDoanThu_crv_colorLoaiCaPhe);
        QuanLyDoanThu_crv_colorTraTuiLoc = findViewById(R.id.quanLyDoanThu_crv_colorTraTuiLoc);
        QuanLyDoanThu_crv_colorLoauTraiCayXayDeo = findViewById(R.id.quanLyDoanThu_crv_colorLoauTraiCayXayDeo);
        QuanLyDoanThu_crv_colorHatLoaiDacSan = findViewById(R.id.quanLyDoanThu_crv_colorHatLoaiDacSan);
        QuanLyDoanThu_crv_colorLoaiThaoMoc = findViewById(R.id.quanLyDoanThu_crv_colorLoaiThaoMoc);
//        Button
        QuanLyDoanThu_btn_thuNhap = findViewById(R.id.quanLyDoanThu_btn_thuNhap);
        QuanLyDoanThu_btn_LoaiSp = findViewById(R.id.quanLyDoanThu_btn_LoaiSp);
//        LinearLayout
        QuanLyDoanThu_llout_ShowTongDoanhThu = findViewById(R.id.quanLyDoanThu_llout_ShowTongDoanhThu);
        QuanLyDoanThu_llout_ShowLoaiSanPham = findViewById(R.id.quanLyDoanThu_llout_ShowLoaiSanPham);
//        CardView
        QuanLyDoanThu_crv_ShowChonLoaiTimKiem = findViewById(R.id.quanLyDoanThu_crv_ShowChonLoaiTimKiem);
//        ConstraintLayout
        QuanLyDoanThu_ctlout_locTheoTong = findViewById(R.id.quanLyDoanThu_ctlout_locTheoTong);
        QuanLyDoanThu_ctlout_locTheoThang = findViewById(R.id.quanLyDoanThu_ctlout_locTheoThang);
        QuanLyDoanThu_ctlout_locTheoKhoang = findViewById(R.id.quanLyDoanThu_ctlout_locTheoKhoang);
        QuanLyDoanThu_ctlout_loaiTimKiemTuKhoang = findViewById(R.id.quanLyDoanThu_ctlout_loaiTimKiemTuKhoang);
        QuanLyDoanThu_ctlout_xemChiTietLoaiCaPhe = findViewById(R.id.quanLyDoanThu_ctlout_xemChiTietLoaiCaPhe);
        QuanLyDoanThu_ctlout_xemChiTietLoaiTra = findViewById(R.id.quanLyDoanThu_ctlout_xemChiTietLoaiTra);
        QuanLyDoanThu_ctlout_xemChiTietLoaiTCXDeo = findViewById(R.id.quanLyDoanThu_ctlout_xemChiTietLoaiTCXDeo);
        QuanLyDoanThu_ctlout_xemChiTietLoaiHatDacSan = findViewById(R.id.quanLyDoanThu_ctlout_xemChiTietLoaiHatDacSan);
        QuanLyDoanThu_ctlout_xemChiTietLoaiThaoMoc = findViewById(R.id.quanLyDoanThu_ctlout_xemChiTietLoaiThaoMoc);
//        BarChart
        QuanLyDoanThuTong_barChart = findViewById(R.id.quanLyDoanThuTong_barChart);
//        PieChart
        QuanLyDoanThu_pieChart = findViewById(R.id.quanLyDoanThu_pieChart);

    }

}