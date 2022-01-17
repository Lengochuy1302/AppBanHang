package com.example.lgfood_duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.R;
import com.example.lgfood_duan1.Activity.khoHang_Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adapter_SanPham_Kho extends RecyclerView.Adapter<adapter_SanPham_Kho.ViewHolder> {
    private List<model_SanPham> arrListSanPham;

    private FirebaseDatabase dataSanPham;
    private DatabaseReference dataSanPhamRef;
    private khoHang_Activity context;
    private IClickLinstenr iClickLinstenr;
    private static final int TYPE_ITEM=1;
    private static final int TYPE_LOADING =2;
    private boolean isLoadingAdd;
    public void setData(List<model_SanPham> list){
        this.arrListSanPham=list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if (arrListSanPham!=null && position==arrListSanPham.size()-1 && isLoadingAdd){
            return TYPE_LOADING;
        }

        return TYPE_ITEM;
    }

    public interface IClickLinstenr{
       void onClickCHinhSuaItem (model_SanPham sanPham);

   }


//    public adapter_SanPham_Kho(List<model_SanPham> arrListSanPham, khoHang_Activity context) {
//        this.arrListSanPham = arrListSanPham;
//        this.context = context;
//    }

    public adapter_SanPham_Kho(khoHang_Activity khoHang_activity, int item_custom2, ArrayList<model_SanPham> arrayList) {

    }

    public adapter_SanPham_Kho(List<model_SanPham> arrListSanPham, khoHang_Activity context, IClickLinstenr iClickLinstenr) {
        this.arrListSanPham = arrListSanPham;
        this.context = context;
        this.iClickLinstenr = iClickLinstenr;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        if (TYPE_ITEM==viewType){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_custom2, parent, false);

            return new ViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_loading, parent, false);
            return new ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull  adapter_SanPham_Kho.ViewHolder holder, int position) {

        if (holder.getItemViewType()==TYPE_ITEM){
            model_SanPham sanPham=arrListSanPham.get(position);
            Glide.with(context)
                    .load(arrListSanPham.get(position).getAnhSanPham())
                    .into(holder.imageView);
            holder.txtTen.setText(arrListSanPham.get(position).getTenSanPham());
            holder.txtXuatXu.setText(arrListSanPham.get(position).getXuatXuSanPham());
            holder.txtGia.setText(arrListSanPham.get(position).getGiaBanSanPham()+"00đ");
            holder.txtSoLuong.setText(arrListSanPham.get(position).getSoLuongSanPham()+"");

            holder.ItemkhoHang_imgBtn_xoaItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //                bắt sự kiện khi nhấn vào nút xóa
                    context.setXoaItem_gioHang(arrListSanPham.get(position).getIdSanPham(), position);
//                gửi id sản phẩm
                    return false;
                }
            });




            holder.XuLi_relative_formItem2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//             chuyển dữ liệu quá trang kho hàng add vô bule

                    iClickLinstenr.onClickCHinhSuaItem(sanPham);

//                context.setShowItem_gioHang(idSanPham, anhSanPham, tenSanPham, giaSanPham,
//                        giamGiaSanPham , soLuongSanPham, giaNhapSanPham ,xuatXu, ngaySanXuat, loaiSanPham);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (arrListSanPham  != null ){
            return arrListSanPham.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTen;
        TextView txtGia;
        TextView txtSoLuong;
        TextView txtXuatXu;
        ImageButton ItemkhoHang_imgBtn_xoaItem;
        CardView XuLi_relative_formItem2;
        public ViewHolder(@NonNull View itemView) {

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

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        public LoadingViewHolder(@NonNull  View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.process_bar);
        }
    }

    public void addFoodterLoading(){
        isLoadingAdd=true;
//        model_SanPham sanPham=new model_SanPham();

//        arrListSanPham.add(new model_SanPham(sanPham.getIdSanPham(),sanPham.getMoTaSanPham(),sanPham.getTenSanPham(),sanPham.getNgaySanXuatSanPham(),sanPham.getXuatXuSanPham(), sanPham.getLoaiSanPham(), sanPham.getTinhTrangSanPham(),sanPham.getAnhSanPham(),sanPham.getNgayNhapSanPham(),sanPham.getSoLuongSanPham(), sanPham.getGiamGiaSanPham(),sanPham.getGiaNhapSanPham(),sanPham.getGiaBanSanPham()));
        arrListSanPham.add(new model_SanPham("","","","","","","","","",2,2,1,1)) ;

    }

    public void removeFoodterLoading(){
        isLoadingAdd=false;

        int position=arrListSanPham.size()-1;
        model_SanPham Pos=arrListSanPham.get(position);
        if (Pos!=null){
            arrListSanPham.remove(position);
            notifyDataSetChanged();
        }
    }
}
