package com.example.lgfood_duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lgfood_duan1.Activity.quanLyDoanhThu_chiTietLoai_Activity;
import com.example.lgfood_duan1.Model.model_chiTietLoaiSanPham_thongKe;
import com.example.lgfood_duan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class adapter_chiTietThongKeSanPham extends RecyclerView.Adapter<adapter_chiTietThongKeSanPham.ViewHolder> {
    ArrayList<model_chiTietLoaiSanPham_thongKe> arrListChiTietLoaiSanPhamThongKe;
    quanLyDoanhThu_chiTietLoai_Activity context;

    public adapter_chiTietThongKeSanPham(ArrayList<model_chiTietLoaiSanPham_thongKe> arrListChiTietLoaiSanPhamThongKe, quanLyDoanhThu_chiTietLoai_Activity context) {
        this.arrListChiTietLoaiSanPhamThongKe = arrListChiTietLoaiSanPhamThongKe;
        this.context = context;
    }

    @Override
    public adapter_chiTietThongKeSanPham.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_custom_showchitietthongkesanpham, parent, false);

        return new adapter_chiTietThongKeSanPham.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adapter_chiTietThongKeSanPham.ViewHolder holder, int position) {
        model_chiTietLoaiSanPham_thongKe arrChiTietLoai = arrListChiTietLoaiSanPhamThongKe.get(position);
        holder.Item_chiTietThongKeSanPham_tv_tenSanPham.setText(arrChiTietLoai.getTenSanPham());
        holder.Item_chiTietThongKeSanPham_tv_soLuongSanPham.setText("SL: "+arrChiTietLoai.getSoLuongSanPham());
        holder.Item_chiTietThongKeSanPham_tv_tongSoTienBanDuoc.setText(arrChiTietLoai.getSoLuongSanPham() * arrChiTietLoai.getGiaSanPham() + "00đ");
        holder.Item_chiTietThongKeSanPham_tv_xepHangSanPham.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        if (arrListChiTietLoaiSanPhamThongKe.size() != 0) {
            return arrListChiTietLoaiSanPhamThongKe.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout
                Item_chiTietThongKeSanPham_llout_xepHangSanPhamColor;
        TextView
                Item_chiTietThongKeSanPham_tv_xepHangSanPham,
                Item_chiTietThongKeSanPham_tv_tenSanPham,
                Item_chiTietThongKeSanPham_tv_soLuongSanPham,
                Item_chiTietThongKeSanPham_tv_tongSoTienBanDuoc;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            Item_chiTietThongKeSanPham_llout_xepHangSanPhamColor = itemView.findViewById(R.id.item_chiTietThongKeSanPham_llout_xepHangSanPhamColor);
            Item_chiTietThongKeSanPham_tv_xepHangSanPham = itemView.findViewById(R.id.item_chiTietThongKeSanPham_tv_xepHangSanPham);
            Item_chiTietThongKeSanPham_tv_tenSanPham = itemView.findViewById(R.id.item_chiTietThongKeSanPham_tv_tenSanPham);
            Item_chiTietThongKeSanPham_tv_soLuongSanPham = itemView.findViewById(R.id.item_chiTietThongKeSanPham_tv_soLuongSanPham);
            Item_chiTietThongKeSanPham_tv_tongSoTienBanDuoc = itemView.findViewById(R.id.item_chiTietThongKeSanPham_tv_tongSoTienBanDuoc);

        }
    }
}
