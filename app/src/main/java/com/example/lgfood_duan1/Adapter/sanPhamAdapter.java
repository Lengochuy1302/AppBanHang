package com.example.lgfood_duan1.Adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lgfood_duan1.R;

import org.jetbrains.annotations.NotNull;

public class sanPhamAdapter extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView txtTen;
    TextView txtGia;
    TextView txtSoLuong;
    TextView txtXuatXu;
    ImageButton ItemkhoHang_imgBtn_xoaItem;
    CardView XuLi_relative_formItem2;
    public sanPhamAdapter(@NonNull @NotNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.xuLi_img_anhItem2) ;
        txtTen = (TextView) itemView.findViewById(R.id.xuLi_txt_tenItem2) ;
        txtGia = (TextView) itemView.findViewById(R.id.xuLi_txt_giaItem2) ;
        txtSoLuong = (TextView) itemView.findViewById(R.id.xuLi_txt_soLuongItem2) ;
        txtXuatXu = (TextView) itemView.findViewById(R.id.xuLi_txt_xuatXuItem2) ;
        XuLi_relative_formItem2 = itemView.findViewById(R.id.xuLi_cardView_formItem2);
        ItemkhoHang_imgBtn_xoaItem = itemView.findViewById(R.id.itemkhoHang_imgBtn_xoaItem);
    }

}
