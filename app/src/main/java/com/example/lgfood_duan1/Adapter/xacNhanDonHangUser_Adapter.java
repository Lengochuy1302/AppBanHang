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
import com.example.lgfood_duan1.Model.model_Cart;
import com.example.lgfood_duan1.Model.model_addToCart;
import com.example.lgfood_duan1.Model.model_yeuThichShow;
import com.example.lgfood_duan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class xacNhanDonHangUser_Adapter extends RecyclerView.Adapter<xacNhanDonHangUser_Adapter.ViewHolder> {
    Context mContext;
    ArrayList<model_addToCart> arrListNewCart;
    ArrayList<model_Cart> arrListGioHangs;

    public xacNhanDonHangUser_Adapter(Context mContext, ArrayList<model_addToCart> arrListNewCart, ArrayList<model_Cart> arrListGioHangs) {
        this.mContext = mContext;
        this.arrListNewCart = arrListNewCart;
        this.arrListGioHangs = arrListGioHangs;
    }


    @NonNull
    @NotNull
    @Override
    public xacNhanDonHangUser_Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom_xacnhandathanguser, parent, false);
        return new xacNhanDonHangUser_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull xacNhanDonHangUser_Adapter.ViewHolder holder, int position) {
        model_Cart arrGioHang = null;

        model_addToCart arrNewCart = arrListNewCart.get(position);

        for (int i = 0; i < arrListGioHangs.size(); i++) {
            if (arrNewCart.getIdSp().equals(arrListGioHangs.get(i).getIdSanPham())) {
                arrGioHang = arrListGioHangs.get(i);
            }
        }

        holder.xuLi_txt_tenItem.setText(arrNewCart.getTenSp());
        holder.xuLi_txt_giaItem.setText(String.valueOf(arrNewCart.getGiaBanSp())+"00vnđ");
//        holder.xuLi_txt_soLuong.setText(String.valueOf(yeuThichShow.getSoLuongTrongKho()));
        holder.xuLi_txt_xuatXuItem.setText(arrNewCart.getXuatXuSp());
        Glide.with(mContext)
                .load(arrNewCart.getAnhSp())
                .into(holder.xuLi_img_anhItem);
        holder.xuLi_txt_soLuongSanPham.setText("XL:"+arrGioHang.getSoLuong());

    }

    @Override
    public int getItemCount() {
        if (arrListNewCart != null) {
            return arrListNewCart.size();
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
