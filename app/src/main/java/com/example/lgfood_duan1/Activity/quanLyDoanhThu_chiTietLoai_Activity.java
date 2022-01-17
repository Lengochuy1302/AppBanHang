package com.example.lgfood_duan1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lgfood_duan1.Adapter.adapter_SanPham_Kho;
import com.example.lgfood_duan1.Adapter.adapter_chiTietThongKeSanPham;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_chiTietLoaiSanPham_thongKe;
import com.example.lgfood_duan1.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;


public class quanLyDoanhThu_chiTietLoai_Activity extends AppCompatActivity {
    ImageView QuanLyDoanThuChiTiet_img_btn_backItem;
    RecyclerView QuanLyDoanThuChiTiet_rscv_showList;
    TextView QuanLyDoanThuChiTiet_tv_ten;
    PieChart QuanLyDoanThuChiTiet_pieChart;
    // model ArrList
    ArrayList<model_chiTietLoaiSanPham_thongKe> arrListChiTietThongKeLoai;
    ArrayList<String> arrListTenLoaiSanPham;
    ArrayList<String> arrListGiaLoaiSanPham;
    ArrayList<String> arrListSoLuongLoaiSanPham;
    adapter_chiTietThongKeSanPham Adapter_chiTietThongKeSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_doanh_thu_chi_tiet_loai);
        anhXa();
        batSuKien();
        nhanIntent();

    }
    /********************Show thông tin ra kiểu dọc**********************/
    private void showListProduc_Vartical(ArrayList<model_chiTietLoaiSanPham_thongKe> arrListChiTietThongKeLoai) {
        Collections.sort(arrListChiTietThongKeLoai, model_chiTietLoaiSanPham_thongKe.sapXepTheoThapDenCao);

        QuanLyDoanThuChiTiet_rscv_showList.setLayoutManager(new GridLayoutManager(this, 1));
        QuanLyDoanThuChiTiet_rscv_showList.setItemAnimator(new DefaultItemAnimator());
        //        Initilize
        Adapter_chiTietThongKeSanPham = new adapter_chiTietThongKeSanPham(arrListChiTietThongKeLoai,quanLyDoanhThu_chiTietLoai_Activity.this);
        QuanLyDoanThuChiTiet_rscv_showList.setAdapter(Adapter_chiTietThongKeSanPham);
        Adapter_chiTietThongKeSanPham.notifyDataSetChanged();
    }
    /*********Set PieChart Loại sản phẩm*******/
    private void setBieuDoPiaChart() {
        ArrayList<Double> valuePiaChat = new ArrayList<>();
        ArrayList<PieEntry> piaEnTries = new ArrayList<>();
        for (int i = 0; i < arrListChiTietThongKeLoai.size(); i++) {
            valuePiaChat.add((double) arrListChiTietThongKeLoai.get(i).getGiaSanPham() * arrListChiTietThongKeLoai.get(i).getSoLuongSanPham());
        }
        for (int i = 0; i < valuePiaChat.size(); i++) {
            double value = (double) valuePiaChat.get(i);
            PieEntry piaEntry = new PieEntry((float) value, i);

            piaEnTries.add(piaEntry);

        }
        PieDataSet pieDataSet = new PieDataSet(piaEnTries, "Số thu nhập VNĐ / SP");
//set color
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(16f);

        QuanLyDoanThuChiTiet_pieChart.setData(new PieData(pieDataSet));

        QuanLyDoanThuChiTiet_pieChart.animateXY(3000, 3000);
        QuanLyDoanThuChiTiet_pieChart.getDescription().setEnabled(false);
    }

    //    Nhận dữ liệu Intent
    private void nhanIntent() {
        Intent intent = getIntent();
        arrListTenLoaiSanPham = intent.getStringArrayListExtra("it_listTenLoai");
        arrListGiaLoaiSanPham = intent.getStringArrayListExtra("it_listGiaLoai");
        arrListSoLuongLoaiSanPham = intent.getStringArrayListExtra("it_listSoLuongLoai");

        for (int i = 0; i < arrListTenLoaiSanPham.size(); i++) {
            arrListChiTietThongKeLoai.add(new model_chiTietLoaiSanPham_thongKe(arrListTenLoaiSanPham.get(i).toString(), Double.parseDouble(arrListGiaLoaiSanPham.get(i)), Integer.parseInt(arrListSoLuongLoaiSanPham.get(i))));
        }
        Collections.sort(arrListChiTietThongKeLoai, model_chiTietLoaiSanPham_thongKe.sapXepTheoGiaCaoxuongThap);
//        sortASC(arrListChiTietThongKeLoai);
        setBieuDoPiaChart();
        showListProduc_Vartical(arrListChiTietThongKeLoai);
    }


    private void batSuKien() {
//        thoát trang quay lại trang thống kê loại, tổng
        QuanLyDoanThuChiTiet_img_btn_backItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quanLyDoanhThu_chiTietLoai_Activity.this, quanLyDoanhThu_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
//        ArrLisy
        arrListChiTietThongKeLoai = new ArrayList<>();
        arrListTenLoaiSanPham = new ArrayList<>();
        arrListGiaLoaiSanPham = new ArrayList<>();
        arrListSoLuongLoaiSanPham = new ArrayList<>();
//        ImageView
        QuanLyDoanThuChiTiet_img_btn_backItem = findViewById(R.id.quanLyDoanThuChiTiet_img_btn_backItem);
//        RecyclerView
        QuanLyDoanThuChiTiet_rscv_showList = findViewById(R.id.quanLyDoanThuChiTiet_rscv_showList);
//        TextView
        QuanLyDoanThuChiTiet_tv_ten = findViewById(R.id.quanLyDoanThuChiTiet_tv_ten);
//        PieChart
        QuanLyDoanThuChiTiet_pieChart = findViewById(R.id.quanLyDoanThuChiTiet_pieChart);


    }


}