package com.example.lgfood_duan1.Adapter;

import android.content.Context;
import android.util.Log;
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
import com.example.lgfood_duan1.R;

import java.util.ArrayList;

public class addToGioHangAdapter extends RecyclerView.Adapter<addToGioHangAdapter.myViewHolder> {
    Context mContext;
    ArrayList<model_addToCart> cartArrayList;
    ArrayList<model_Cart> arrListGioHangs;
    private IClickListener mIClickListener;
    double TongTien = 0;


    public addToGioHangAdapter() {

    }

    public interface IClickListener {
        void onCLickMinusItem(model_Cart cart, int viTri);

        void onClickPlusItem(model_Cart cart, int viTri);

        void onClickShowItem(model_addToCart cart, model_Cart arrGioHangs, int viTri);

        void onClickDelete(model_addToCart arrAddTocart,int viTri,model_Cart arrGioHangs);
    }

    public addToGioHangAdapter(Context mContext, ArrayList<model_addToCart> cartArrayList, ArrayList<model_Cart> arrListGioHangs, IClickListener mIClickListener) {
        this.mContext = mContext;
        this.cartArrayList = cartArrayList;
        this.arrListGioHangs = arrListGioHangs;
        this.mIClickListener = mIClickListener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom1, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addToGioHangAdapter.myViewHolder holder, int position) {
        model_Cart arrGioHangs = null;
        model_addToCart arrAddTocart = cartArrayList.get(position);
        for (int i = 0; i < arrListGioHangs.size(); i++) {
            if (arrListGioHangs.get(i).getIdSanPham().equals(cartArrayList.get(position).getIdSp())) {
                arrGioHangs = arrListGioHangs.get(i);
                holder.xuLi_txt_soLuong.setText(String.valueOf(arrGioHangs.getSoLuong()));


        holder.xuLi_txt_tenItem.setText(arrAddTocart.getTenSp());
        holder.xuLi_txt_giaItem.setText(String.valueOf(arrAddTocart.getGiaBanSp()) + "00vnđ");



        holder.xuLi_txt_xuatXuItem.setText(arrAddTocart.getXuatXuSp());
        Glide.with(mContext)
                .load(arrAddTocart.getAnhSp())
                .into(holder.xuLi_img_anhItem);
        model_Cart finalArrGioHangs = arrGioHangs;
        holder.xuLi_img_anhItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickShowItem(arrAddTocart, finalArrGioHangs, position);
            }
        });
        holder.xuLi_img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickDelete(arrAddTocart,position,finalArrGioHangs);
            }
        });
        model_Cart finalArrGioHangs1 = arrGioHangs;
        holder.xuLi_cardView_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.soLuong = Integer.parseInt(finalArrGioHangs1.getSoLuong());
                holder.soLuong = holder.soLuong - 1;
                if (holder.soLuong > 0) {

                    holder.xuLi_txt_soLuong.setText(String.valueOf(holder.soLuong));
                    mIClickListener.onCLickMinusItem(finalArrGioHangs1, position);
                } else {
                    holder.xuLi_cardView_minus.setAlpha((float) 0.2);
                    holder.xuLi_img_giaItem.setAlpha((float) 0.3);
                    holder.soLuong = 1;
                }

            }
        });
        holder.xuLi_cardView_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.xuLi_img_giaItem.setAlpha((float) 1);
                holder.xuLi_cardView_minus.setAlpha((float) 1);
                holder.soLuong = Integer.parseInt(finalArrGioHangs1.getSoLuong());
                holder.soLuong = holder.soLuong + 1;
                holder.xuLi_txt_soLuong.setText(String.valueOf(holder.soLuong));
                mIClickListener.onClickPlusItem(finalArrGioHangs1, position);

            }
        });
            }
        }

    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        int soLuong = 0;
        ImageView xuLi_img_anhItem, xuLi_img_giaItem,xuLi_img_delete;
        TextView xuLi_txt_tenItem, xuLi_txt_xuatXuItem, xuLi_txt_giaItem, xuLi_txt_soLuong;
        CardView xuLi_cardView_minus, xuLi_cardView_plus, xuLi_cardView_formItem1;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            xuLi_img_giaItem = itemView.findViewById(R.id.xuLi_img_giaItem);
            xuLi_img_anhItem = itemView.findViewById(R.id.xuLi_img_anhItem);
            xuLi_txt_tenItem = itemView.findViewById(R.id.xuLi_txt_tenItem);
            xuLi_txt_xuatXuItem = itemView.findViewById(R.id.xuLi_txt_xuatXuItem);
            xuLi_txt_giaItem = itemView.findViewById(R.id.xuLi_txt_giaItem);
            xuLi_txt_soLuong = itemView.findViewById(R.id.xuLi_txt_soLuong);
            xuLi_cardView_minus = itemView.findViewById(R.id.xuLi_cardView_minus);
            xuLi_cardView_plus = itemView.findViewById(R.id.xuLi_cardView_plus);
            xuLi_cardView_formItem1 = itemView.findViewById(R.id.xuLi_cardView_formItem1);
            xuLi_img_delete=itemView.findViewById(R.id.xuLi_img_delete);

        }
    }
}