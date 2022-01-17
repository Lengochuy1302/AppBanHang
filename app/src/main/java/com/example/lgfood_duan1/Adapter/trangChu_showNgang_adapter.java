package com.example.lgfood_duan1.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lgfood_duan1.Activity.Login_Activity;
import com.example.lgfood_duan1.Activity.trangChu_SanPham_Activity;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_yeuThich;
import com.example.lgfood_duan1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//Trung
public class trangChu_showNgang_adapter extends RecyclerView.Adapter<trangChu_showNgang_adapter.ViewHolder> {
    private ArrayList<model_SanPham> arrListSanPham;
    private ArrayList<model_yeuThich> arrListYeuThich;
    private trangChu_SanPham_Activity context;
    //Firebase
    private DatabaseReference dataRef;
    private FirebaseDatabase database;


    SharedPreferences sharedPreferences;

    public trangChu_showNgang_adapter(ArrayList<model_SanPham> arrListSanPham, trangChu_SanPham_Activity context) {
        this.arrListSanPham = arrListSanPham;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuttom_sanpham_trangchu_ngang, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(trangChu_showNgang_adapter.ViewHolder holder, int position) {

        model_SanPham sanPham = arrListSanPham.get(position);
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
        sharedPreferences = context.getSharedPreferences("USER_FILE", context.MODE_PRIVATE);

        arrListYeuThich = new ArrayList<>();
        dataRef = database.getReference("danhSachSanPhamYeuThich").child(sharedPreferences.getString("IDDANHSACHYEUTHICH", ""));

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrListYeuThich.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    model_yeuThich arrYeuThich = ds.getValue(model_yeuThich.class);
                    arrListYeuThich.add(arrYeuThich);
                }
                try {
                    model_yeuThich arrYeuThich = null;
                    if (arrListYeuThich != null || arrListYeuThich.size() != 0) {
                        for (int i = 0; i < arrListYeuThich.size(); i++) {
                            if (arrListYeuThich.get(i).getIdSanPham().equals(sanPham.getIdSanPham())) {
                                arrYeuThich = arrListYeuThich.get(i);
                                Log.d("ddd", arrYeuThich.getIdYeuThich() + "");
                                holder.ItemCuttomTrangChu_ngang_img_btn_chonYeuThich.setImageResource(R.drawable.ic_love_red);

                            }
                        }

                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        holder.ItemCuttomTrangChu_ngang_img_btn_themSanPhamVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.themNhanhSanPhamVaoGioHang(sanPham);

            }
        });

        holder.ItemCuttomTrangChu_ngang_tv_soLuongSanPhamMuaYeuThich.setText("0");
        holder.ItemCuttomTrangChu_ngang_tv_xuatXuSanPham.setText(sanPham.getXuatXuSanPham());
        holder.ItemCuttomTrangChu_ngang_tv_tenSanPham.setText(sanPham.getTenSanPham());
        holder.ItemCuttomTrangChu_ngang_tv_giaSanPham.setText(Double.parseDouble(sanPham.getGiaBanSanPham() + "") + "00đ");

        Glide.with(context).load(arrListSanPham.get(position).getAnhSanPham())
                .into(holder.ItemCuttomTrangChu_ngang_img_showAnhSanPham);


//      Show chi tiết sản phẩm
        holder.ItemCuttomTrangChu_ngang_carview_showItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showItemChiTietSanPham(sanPham);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (arrListSanPham != null) {
            return arrListSanPham.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                ItemCuttomTrangChu_ngang_tv_soLuongSanPhamMuaYeuThich,
                ItemCuttomTrangChu_ngang_tv_xuatXuSanPham,
                ItemCuttomTrangChu_ngang_tv_tenSanPham,
                ItemCuttomTrangChu_ngang_tv_giaSanPham;

        ImageView
                ItemCuttomTrangChu_ngang_img_showAnhSanPham,
                ItemCuttomTrangChu_ngang_img_btn_chonYeuThich,
                ItemCuttomTrangChu_ngang_img_btn_themSanPhamVaoGioHang;
        CardView
                ItemCuttomTrangChu_ngang_carview_showItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ItemCuttomTrangChu_ngang_carview_showItem = itemView.findViewById(R.id.itemCuttomTrangChu_ngang_carview_showItem);
            ItemCuttomTrangChu_ngang_tv_soLuongSanPhamMuaYeuThich = itemView.findViewById(R.id.itemCuttomTrangChu_ngang_tv_soLuongSanPhamMuaYeuThich);
            ItemCuttomTrangChu_ngang_tv_xuatXuSanPham = itemView.findViewById(R.id.itemCuttomTrangChu_ngang_tv_xuatXuSanPham);
            ItemCuttomTrangChu_ngang_tv_tenSanPham = itemView.findViewById(R.id.itemCuttomTrangChu_ngang_tv_tenSanPham);
            ItemCuttomTrangChu_ngang_tv_giaSanPham = itemView.findViewById(R.id.itemCuttomTrangChu_ngang_tv_giaSanPham);

            ItemCuttomTrangChu_ngang_img_showAnhSanPham = itemView.findViewById(R.id.itemCuttomTrangChu_ngang_img_showAnhSanPham);
            ItemCuttomTrangChu_ngang_img_btn_chonYeuThich = itemView.findViewById(R.id.itemCuttomTrangChu_ngang_img_btn_chonYeuThich);
            ItemCuttomTrangChu_ngang_img_btn_themSanPhamVaoGioHang = itemView.findViewById(R.id.itemCuttomTrangChu_ngang_img_btn_themSanPhamVaoGioHang);
        }

    }
}
