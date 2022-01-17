package com.example.lgfood_duan1.Adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lgfood_duan1.Activity.ChiaSeGioHang_Activity;
import com.example.lgfood_duan1.Model.model_addToCart;
import com.example.lgfood_duan1.Model.model_viTri;
import com.example.lgfood_duan1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class chiaSeGioHang_showDoc_adapter extends RecyclerView.Adapter<chiaSeGioHang_showDoc_adapter.ViewHolder> {
    private ArrayList<model_viTri> arrayListViTri;
    private ChiaSeGioHang_Activity context;
    String idGioHang;
    DatabaseReference mData, dataRef;
    FirebaseDatabase database;


    public chiaSeGioHang_showDoc_adapter(ArrayList<model_viTri> arrayListViTri, ChiaSeGioHang_Activity context) {
        this.arrayListViTri = arrayListViTri;
        this.context = context;
    }

    @Override
    public chiaSeGioHang_showDoc_adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_chia_se_giohang, parent, false);
        return new chiaSeGioHang_showDoc_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chiaSeGioHang_showDoc_adapter.ViewHolder holder, int position) {
        model_viTri arrViTri = arrayListViTri.get(position);

            holder.ItemChiaSeGioHang_tv_key.setText("key:#" + arrViTri.getKey());
            Glide.with(context).load(arrayListViTri.get(position).getAnhUser())
                    .into(holder.ItemChiaSeGioHang_crimg_avata);
            holder.ItemChiaSeGioHang_tv_nameUser.setText(arrViTri.getNameUser());
            holder.ItemChiaSeGioHang_tv_diaChi.setText(arrViTri.getVitri());
            holder.ItemChiaSeGioHang_llout_btn_chiaSeGioHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                1 lấy thông tin từ giỏ hàng người a

//                idGioHang

//                2 lấy id giỏ hàng tạm của người b
//                chuyển arrlist giỏ hàng a qua giỏ hàng tạm b
                    Toast.makeText(context, arrViTri.getIdGioHangTam() + "", Toast.LENGTH_SHORT).show();

                    context.nhanDuLieuAdapterItem(arrViTri.getIdGioHangTam());
                }
            });


    }

    @Override
    public int getItemCount() {
        if (arrayListViTri != null) {
            return arrayListViTri.size();
        }
        return 0;
    }


    //thai: lay du lieu tu firebase


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                ItemChiaSeGioHang_tv_key,
                ItemChiaSeGioHang_tv_nameUser,
                ItemChiaSeGioHang_tv_diaChi;
        CircleImageView
                ItemChiaSeGioHang_crimg_avata;
        LinearLayout
                ItemChiaSeGioHang_llout_btn_chiaSeGioHang;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

//            TextView
            ItemChiaSeGioHang_tv_key = itemView.findViewById(R.id.itemChiaSeGioHang_tv_key);
            ItemChiaSeGioHang_tv_nameUser = itemView.findViewById(R.id.itemChiaSeGioHang_tv_nameUser);
            ItemChiaSeGioHang_tv_diaChi = itemView.findViewById(R.id.itemChiaSeGioHang_tv_diaChi);
//            CircleImageView
            ItemChiaSeGioHang_crimg_avata = itemView.findViewById(R.id.itemChiaSeGioHang_crimg_avata);
//            LinearLayout
            ItemChiaSeGioHang_llout_btn_chiaSeGioHang = itemView.findViewById(R.id.itemChiaSeGioHang_llout_btn_chiaSeGioHang);
        }
    }
}
