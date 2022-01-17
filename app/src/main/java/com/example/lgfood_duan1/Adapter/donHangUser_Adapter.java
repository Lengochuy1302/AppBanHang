package com.example.lgfood_duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lgfood_duan1.Activity.donHangUser_Activity;
import com.example.lgfood_duan1.Activity.xac_Nhan_Don_hang_Activity;
import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_hoaDon;
import com.example.lgfood_duan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class donHangUser_Adapter extends RecyclerView.Adapter<donHangUser_Adapter.ViewHolder> {
    ArrayList<model_hoaDon> arrListHoaDon;
    model_Account arrAcout;
    donHangUser_Activity context;

    public donHangUser_Adapter(ArrayList<model_hoaDon> arrListHoaDon, model_Account arrAcout, donHangUser_Activity context) {
        this.arrListHoaDon = arrListHoaDon;
        this.arrAcout = arrAcout;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_xacnhandonhang, parent, false);
        return new donHangUser_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull donHangUser_Adapter.ViewHolder holder, int position) {
        model_hoaDon arrHoaDon = arrListHoaDon.get(position);
        model_Account arrAccount = arrAcout;
                holder.XacNhan_txt_tenKhachHang_item.setText(arrAccount.getRealName());
                holder.XacNhan_txt_dateKhachHang_item.setText(arrHoaDon.getNgayTaoChiTietHoaDon().substring(0, 10));
                holder.XacNhan_txt_sdtKhachHang_item.setText(arrAccount.getPhoneNumber());
                holder.XacNhan_diaChiKhachHang_item.setText(arrAccount.getAddress());
                holder.XacNhan_gia_item.setText(arrHoaDon.getTongTien() + "00vnđ");
                if (arrHoaDon.getTinhTrangDonHang() == 0) {
                    holder.XacNhan_txt_tinhTrang_itemChuaXacNhan.setVisibility(View.VISIBLE);
                    holder.XacNhan_txt_tinhTrang_itemDangXuLy.setVisibility(View.INVISIBLE);
                    holder.XacNhan_txt_tinhTrang_itemDaXuLy.setVisibility(View.INVISIBLE);
                } else if (arrHoaDon.getTinhTrangDonHang() == 1) {
                    holder.XacNhan_txt_tinhTrang_itemDangXuLy.setVisibility(View.VISIBLE);
                    holder.XacNhan_txt_tinhTrang_itemChuaXacNhan.setVisibility(View.INVISIBLE);
                    holder.XacNhan_txt_tinhTrang_itemDaXuLy.setVisibility(View.INVISIBLE);
                } else {
                    holder.XacNhan_txt_tinhTrang_itemDaXuLy.setVisibility(View.VISIBLE);
                    holder.XacNhan_txt_tinhTrang_itemChuaXacNhan.setVisibility(View.INVISIBLE);
                    holder.XacNhan_txt_tinhTrang_itemDangXuLy.setVisibility(View.INVISIBLE);
                }

                model_Account finalArrAccount = arrAccount;
                holder.XacNhan_cv_layoutItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.showChiTietDonHang(finalArrAccount.getId(),finalArrAccount.getIdDanhSachDonHang(), arrHoaDon.getIdHoaDon(), arrHoaDon.getIdChiTietDonHang(), arrHoaDon.getTinhTrangDonHang(), arrHoaDon.getTongTien());
                    }
                });


    }

    @Override
    public int getItemCount() {
        if (arrListHoaDon != null) {
            return arrListHoaDon.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                XacNhan_txt_tenKhachHang_item,
                XacNhan_txt_dateKhachHang_item,
                XacNhan_txt_sdtKhachHang_item,
                XacNhan_diaChiKhachHang_item,
                XacNhan_gia_item,
                XacNhan_txt_tinhTrang_itemDaXuLy,
                XacNhan_txt_tinhTrang_itemChuaXacNhan,
                XacNhan_txt_tinhTrang_itemDangXuLy;
        CardView XacNhan_cv_layoutItem;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            XacNhan_cv_layoutItem = itemView.findViewById(R.id.xacNhan_cv_layoutItem);
            XacNhan_txt_tenKhachHang_item = itemView.findViewById(R.id.xacNhan_txt_tenKhachHang_item);
            XacNhan_txt_dateKhachHang_item = itemView.findViewById(R.id.xacNhan_txt_dateKhachHang_item);
            XacNhan_txt_sdtKhachHang_item = itemView.findViewById(R.id.xacNhan_txt_sdtKhachHang_item);
            XacNhan_diaChiKhachHang_item = itemView.findViewById(R.id.xacNhan_diaChiKhachHang_item);
            XacNhan_gia_item = itemView.findViewById(R.id.xacNhan_gia_item);
            XacNhan_txt_tinhTrang_itemDaXuLy = itemView.findViewById(R.id.xacNhan_txt_tinhTrang_itemDaXuLy);
            XacNhan_txt_tinhTrang_itemChuaXacNhan = itemView.findViewById(R.id.xacNhan_txt_tinhTrang_itemChuaXacNhan);
            XacNhan_txt_tinhTrang_itemDangXuLy = itemView.findViewById(R.id.xacNhan_txt_tinhTrang_itemDangXuLy);
        }
    }
}
