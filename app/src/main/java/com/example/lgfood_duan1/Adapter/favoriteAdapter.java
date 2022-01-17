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
import com.example.lgfood_duan1.Activity.favorite_Activity;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_addToCart;
import com.example.lgfood_duan1.Model.model_yeuThich;
import com.example.lgfood_duan1.Model.model_yeuThichShow;
import com.example.lgfood_duan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class favoriteAdapter extends RecyclerView.Adapter<favoriteAdapter.ViewHolder> {
    favorite_Activity mContext;
    ArrayList<model_yeuThich> arrListYeuThich;
    ArrayList<model_SanPham> arrListSanPham;

    public favoriteAdapter(favorite_Activity mContext, ArrayList<model_yeuThich> arrListYeuThich, ArrayList<model_SanPham> arrListSanPham) {
        this.mContext = mContext;
        this.arrListYeuThich = arrListYeuThich;
        this.arrListSanPham = arrListSanPham;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom_favorite, parent, false);
        return new favoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull favoriteAdapter.ViewHolder holder, int position) {

        model_SanPham arrSanPham = null;
        model_yeuThich arrYeuThich = arrListYeuThich.get(position);
        for (int i = 0; i < arrListSanPham.size(); i++) {
            if (arrYeuThich.getIdSanPham().equals(arrListSanPham.get(i).getIdSanPham())) {
                arrSanPham = arrListSanPham.get(i);
                holder.xuLi_txt_tenItem.setText(arrSanPham.getTenSanPham());
                holder.xuLi_txt_giaItem.setText(String.valueOf(arrSanPham.getGiaBanSanPham()) + "00vnđ");
//        holder.xuLi_txt_soLuong.setText(String.valueOf(yeuThichShow.getSoLuongTrongKho()));
                holder.xuLi_txt_xuatXuItem.setText(arrSanPham.getXuatXuSanPham());
                Glide.with(mContext)
                        .load(arrSanPham.getAnhSanPham())
                        .into(holder.xuLi_img_anhItem);
//                bắt sự kiện xóa thích
                holder.xuLi_img_Btn_yeuThich.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.xoaItemThich(arrYeuThich.getIdYeuThich(), position);
                    }
                });
//                bắt sự kiện thêm nhanh sản phẩm vào giò hàng
                model_SanPham finalArrSanPham = arrSanPham;
                holder.xuLi_img_btn_themSanPhamVaoGioHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.themSanPhamVaoGioHang(finalArrSanPham);
                    }
                });
//                bắt sự kiện show chi tiết sản phẩm
                holder.favorite_carview_showChitietSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.showDialogChiTietSanPham(finalArrSanPham);
                    }
                });
            }
        }


    }

    @Override
    public int getItemCount() {
        arrListYeuThich.size();
        return arrListYeuThich.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView xuLi_img_anhItem, xuLi_img_Btn_yeuThich, xuLi_img_btn_themSanPhamVaoGioHang;
        TextView xuLi_txt_tenItem, xuLi_txt_xuatXuItem, xuLi_txt_giaItem;
        CardView favorite_carview_showChitietSanPham;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            xuLi_img_anhItem = itemView.findViewById(R.id.xuLi_img_anhItem2);
            xuLi_txt_tenItem = itemView.findViewById(R.id.xuLi_txt_tenItem2);
            xuLi_txt_xuatXuItem = itemView.findViewById(R.id.xuLi_txt_xuatXuItem2);
            xuLi_txt_giaItem = itemView.findViewById(R.id.xuLi_txt_giaItem2);
            xuLi_img_Btn_yeuThich = itemView.findViewById(R.id.xuLi_img_Btn_yeuThich);
            xuLi_img_btn_themSanPhamVaoGioHang = itemView.findViewById(R.id.xuLi_img_btn_themSanPhamVaoGioHang);
            favorite_carview_showChitietSanPham = itemView.findViewById(R.id.favorite_carview_showChitietSanPham);
        }
    }
}
