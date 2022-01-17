package com.example.lgfood_duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lgfood_duan1.Activity.Xu_Li_Don_Hang_Activity;
import com.example.lgfood_duan1.Model.model_Cart;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_addToCart;
import com.example.lgfood_duan1.Model.model_chiTietSanPhamHoaDon;
import com.example.lgfood_duan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class xacNhanDonHangAdmin_Adapter extends RecyclerView.Adapter<xacNhanDonHangAdmin_Adapter.ViewHolder> {
    Xu_Li_Don_Hang_Activity mContext;
    ArrayList<model_SanPham> arrListSanPham;
    ArrayList<model_chiTietSanPhamHoaDon> arrListCTSPHoaDon;

    public xacNhanDonHangAdmin_Adapter(Xu_Li_Don_Hang_Activity mContext, ArrayList<model_SanPham> arrListSanPham, ArrayList<model_chiTietSanPhamHoaDon> arrListCTSPHoaDon) {
        this.mContext = mContext;
        this.arrListSanPham = arrListSanPham;
        this.arrListCTSPHoaDon = arrListCTSPHoaDon;
    }

    @NonNull
    @NotNull
    @Override
    public xacNhanDonHangAdmin_Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom_xacnhandathanguser, parent, false);
        return new xacNhanDonHangAdmin_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull xacNhanDonHangAdmin_Adapter.ViewHolder holder, int position) {
        model_SanPham arrSanPham = null;

        model_chiTietSanPhamHoaDon arrCTSPHoaDon = arrListCTSPHoaDon.get(position);

        for (int i = 0; i < arrListCTSPHoaDon.size(); i++) {
            if (arrCTSPHoaDon.getIdSanPham().equals(arrListSanPham.get(i).getIdSanPham())) {
                arrSanPham = arrListSanPham.get(i);
            }
        }
        holder.xuLi_txt_tenItem.setText(arrSanPham.getTenSanPham());
        holder.xuLi_txt_giaItem.setText(String.valueOf(arrCTSPHoaDon.getGiaSanPham()) + "00vnđ");
        holder.xuLi_txt_xuatXuItem.setText(arrSanPham.getXuatXuSanPham());
        Glide.with(mContext)
                .load(arrSanPham.getAnhSanPham())
                .into(holder.xuLi_img_anhItem);
        holder.xuLi_txt_soLuongSanPham.setText("XL:" + arrCTSPHoaDon.getSoLuongSanPham());

    }

    @Override
    public int getItemCount() {
        if (arrListCTSPHoaDon != null) {
            return arrListCTSPHoaDon.size();
        } else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView xuLi_img_anhItem;

        TextView xuLi_txt_tenItem, xuLi_txt_xuatXuItem, xuLi_txt_giaItem, xuLi_txt_soLuongSanPham;
        CardView xuLi_cardView_formItem1;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            xuLi_img_anhItem = itemView.findViewById(R.id.xuLi_img_anhItem);
            xuLi_txt_tenItem = itemView.findViewById(R.id.xuLi_txt_tenItem);
            xuLi_txt_xuatXuItem = itemView.findViewById(R.id.xuLi_txt_xuatXuItem);
            xuLi_txt_giaItem = itemView.findViewById(R.id.xuLi_txt_giaItem);
            xuLi_txt_soLuongSanPham = itemView.findViewById(R.id.xuLi_txt_soLuongSanPham);
            xuLi_cardView_formItem1 = itemView.findViewById(R.id.xuLi_cardView_formItem1);
        }
    }
}
