package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lgfood_duan1.Adapter.addToGioHangAdapter;
import com.example.lgfood_duan1.Adapter.favoriteAdapter;
import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_Cart;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_addToCart;
import com.example.lgfood_duan1.Model.model_yeuThich;
import com.example.lgfood_duan1.Model.model_yeuThichShow;
import com.example.lgfood_duan1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class favorite_Activity extends AppCompatActivity {
    RecyclerView favorite_rev_showItem;
    ImageView favorite_img_btnThoat;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    SharedPreferences sharedPreferences;
    ArrayList<model_yeuThich> arrListYeuThich;
    favoriteAdapter favoriteAdapter;
    ArrayList<model_SanPham> arrListSanPham;
    ArrayList<model_Cart> arrListCart;
    model_Cart arrCart;
    String idGioHang;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        anhXa();
        batSuKien();
        getDataFirebaseYeuThich();
        loadData();

    }

    // show dialog chi tiết sản phẩm
    public void showDialogChiTietSanPham(model_SanPham sanPham) {
        anhXa();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dat_nhanh);

        LinearLayout datNhanh_linear_turnOffDiaLog = dialog.findViewById(R.id.datNhanh_linear_turnOffDiaLog);
        //thong tin san pham
        ImageView datNhanh_img_showAnhSanPham = dialog.findViewById(R.id.datNhanh_img_showAnhSanPham);
        TextView datNhanh_tv_xuatXuSanPham = dialog.findViewById(R.id.datNhanh_tv_xuatXuSanPham);
        TextView datNhanh_tv_showTenSanPham = dialog.findViewById(R.id.datNhanh_tv_showTenSanPham);
        TextView datNhanh_tv_giaSanPham = dialog.findViewById(R.id.datNhanh_tv_giaSanPham);
        TextView datNhanh_tv_giamGiaSanPham = dialog.findViewById(R.id.datNhanh_tv_giamGiaSanPham);
        TextView datNhanh_tv_ngaySanXuat = dialog.findViewById(R.id.datNhanh_tv_ngaySanXuat);
        TextView datNhanh_tv_soLuongSanPhamTrongKho = dialog.findViewById(R.id.datNhanh_tv_soLuongSanPhamTrongKho);
        TextView datNhanh_tv_soLuongSanPhamYeuThichDaMua = dialog.findViewById(R.id.datNhanh_tv_soLuongSanPhamYeuThichDaMua);
        TextView datNhanh_tv_moTaSanPham = dialog.findViewById(R.id.datNhanh_tv_moTaSanPham);
        //tang giam so luong sp
        ImageView datNhanh_img_btn_giamSoLuongSanPham = dialog.findViewById(R.id.datNhanh_img_btn_giamSoLuongSanPham);
        TextView datNhanh_tv_SoLuongSanpham = dialog.findViewById(R.id.datNhanh_tv_SoLuongSanpham);
        ImageView datNhanh_img_btn_tangSoLuongSanPham = dialog.findViewById(R.id.datNhanh_img_btn_tangSoLuongSanPham);

        Button datNhanh_btn_themSanPhamVaoGioHang = dialog.findViewById(R.id.datNhanh_btn_themSanPhamVaoGioHang);

        Glide.with(favorite_Activity.this)
                .load(sanPham.getAnhSanPham())
                .into(datNhanh_img_showAnhSanPham);
        datNhanh_tv_xuatXuSanPham.setText(sanPham.getXuatXuSanPham());
        datNhanh_tv_showTenSanPham.setText(sanPham.getTenSanPham());
        datNhanh_tv_giaSanPham.setText(sanPham.getGiaBanSanPham() + "00đ");
        datNhanh_tv_giamGiaSanPham.setText("   -" + sanPham.getGiamGiaSanPham());
        datNhanh_tv_ngaySanXuat.setText(sanPham.getNgaySanXuatSanPham());
        datNhanh_tv_soLuongSanPhamTrongKho.setText(String.valueOf(sanPham.getSoLuongSanPham()));
//        datNhanh_tv_soLuongSanPhamYeuThichDaMua.setText(Integer.valueOf(sanPham.getAnhSanPham()));
        datNhanh_tv_moTaSanPham.setText(sanPham.getMoTaSanPham());

        datNhanh_btn_themSanPhamVaoGioHang.setText("ADD TO CART " + sanPham.getGiaBanSanPham() + "00VNĐ");
        getDataFirebaseCart();
        //giam so luong san pham
        for (int j = 0; j < arrListCart.size(); j++) {
            if (sanPham.getIdSanPham().equals(arrListCart.get(j).getIdSanPham())) {
                i = Integer.parseInt(arrListCart.get(j).getSoLuong());

            }

        }

        datNhanh_tv_SoLuongSanpham.setText(String.valueOf(i));
        datNhanh_img_btn_giamSoLuongSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if (i <= 1) {
                    i = 1;
                    datNhanh_tv_SoLuongSanpham.setText(String.valueOf(i));
                    datNhanh_btn_themSanPhamVaoGioHang.setText("ADD TO CART " + String.valueOf(i * sanPham.getGiaBanSanPham()) + "00VNĐ");
                    return;
                } else {
                    datNhanh_tv_SoLuongSanpham.setText(String.valueOf(i));
                    datNhanh_btn_themSanPhamVaoGioHang.setText("ADD TO CART " + String.valueOf(i * sanPham.getGiaBanSanPham()) + "00VNĐ");

                }


            }
        });
        //tang so luong san pham
        datNhanh_img_btn_tangSoLuongSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                datNhanh_tv_SoLuongSanpham.setText(String.valueOf(i));
                datNhanh_btn_themSanPhamVaoGioHang.setText("ADD TO CART " + String.valueOf(i * sanPham.getGiaBanSanPham()) + "00VNĐ");

            }
        });
        //turn off dialog
        datNhanh_linear_turnOffDiaLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                i = 1;
            }
        });

        //add to cart
        datNhanh_btn_themSanPhamVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idProduct = sanPham.getIdSanPham();
                Log.d("eee", "idprduc" + idProduct);
                dataRef = database.getReference("Accounts").child(sharedPreferences.getString("IDUSRE", ""));
                dataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean check = false;
                        String idCart = "";
                        int viTri = 0;
                        dataRef = database.getReference("newCarts");
                        model_Account account = snapshot.getValue(model_Account.class);
                        idGioHang = String.valueOf(account.getIdGioHang());
                        UUID uuid = UUID.randomUUID();
                        String idProduct = sanPham.getIdSanPham();
                        String idChiTietSanPham = String.valueOf(uuid);
                        model_addToCart newCart = new model_addToCart(sanPham.getIdSanPham(), sanPham.getMoTaSanPham(), sanPham.getTenSanPham(), sanPham.getNgaySanXuatSanPham(), sanPham.getXuatXuSanPham(), sanPham.getLoaiSanPham(), sanPham.getTinhTrangSanPham(), sanPham.getAnhSanPham(), sanPham.getNgayNhapSanPham(), sanPham.getSoLuongSanPham(), sanPham.getGiamGiaSanPham(), sanPham.getGiaNhapSanPham(), sanPham.getGiaBanSanPham());
                        dataRef.child(idGioHang).child(sanPham.getIdSanPham()).setValue(newCart);

//                       Trung  kiem tra trong gioHangs đã có sản phẩm chưa nếu có chỉ tăng số lượng không tăng cart mới

                        dataRef = database.getReference("GioHangs").child(idGioHang);


                        for (int j = 0; j < arrListCart.size(); j++) {
                            if (arrListCart.get(j).getIdSanPham().equals(idProduct)) {
                                check = true;
                                idCart = arrListCart.get(j).getIdGioHang();
                                viTri = j;
                            }

                        }


                        if (check == true) {
                            int tong = i + Integer.parseInt(arrListCart.get(viTri).getSoLuong());
                            dataRef.child(idCart).child("soLuong").setValue(tong + "");

                        } else {
                            model_Cart cart = new model_Cart(UUID.randomUUID().toString(), idProduct, i + "");
                            dataRef.child(cart.getIdGioHang()).setValue(cart);
                        }
                        dialog.setContentView(R.layout.activity_add_to_cart_anim);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 2300);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    //     lấy danh sách giỏ hàng
    private void getDataFirebaseCart() {
        dataRef = database.getReference("GioHangs").child(sharedPreferences.getString("IDGIOHANG", ""));
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrListCart.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrCart = child.getValue(model_Cart.class);
                    arrListCart.add(arrCart);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    // bắt sự kiện thêm sản phẩm vào giỏ hàng
    public void themSanPhamVaoGioHang(model_SanPham arrSanPham) {
        anhXa();
        getDataFirebaseCart();
        String idProduct = arrSanPham.getIdSanPham();
        Log.d("eee", "idprduc" + idProduct);
        dataRef = database.getReference("Accounts").child(sharedPreferences.getString("IDUSRE", ""));
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean check = false;
                String idCart = "";
                int viTri = 0;
                dataRef = database.getReference("newCarts");
                model_Account account = snapshot.getValue(model_Account.class);
                idGioHang = String.valueOf(account.getIdGioHang());

                String idProduct = arrSanPham.getIdSanPham();

                model_addToCart newCart = new model_addToCart(arrSanPham.getIdSanPham(), arrSanPham.getMoTaSanPham(), arrSanPham.getTenSanPham(), arrSanPham.getNgaySanXuatSanPham(), arrSanPham.getXuatXuSanPham(), arrSanPham.getLoaiSanPham(), arrSanPham.getTinhTrangSanPham(), arrSanPham.getAnhSanPham(), arrSanPham.getNgayNhapSanPham(), arrSanPham.getSoLuongSanPham(), arrSanPham.getGiamGiaSanPham(), arrSanPham.getGiaNhapSanPham(), arrSanPham.getGiaBanSanPham());
                dataRef.child(idGioHang).child(arrSanPham.getIdSanPham()).setValue(newCart);

//                       Trung  kiem tra trong gioHangs đã có sản phẩm chưa nếu có chỉ tăng số lượng không tăng cart mới

                dataRef = database.getReference("GioHangs").child(idGioHang);
                for (int j = 0; j < arrListCart.size(); j++) {
                    if (arrListCart.get(j).getIdSanPham().equals(idProduct)) {
                        check = true;
                        idCart = arrListCart.get(j).getIdGioHang();
                        viTri = j;
                    }
                }
                if (check == true) {
                    int tong = i + Integer.parseInt(arrListCart.get(viTri).getSoLuong());
                    dataRef.child(idCart).child("soLuong").setValue(tong + "");

                } else {
                    model_Cart cart = new model_Cart(UUID.randomUUID().toString(), idProduct, i + "");
                    dataRef.child(cart.getIdGioHang()).setValue(cart);

                }
//                .setContentView(R.layout.activity_add_to_cart_anim);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        dialog.dismiss();
                    }
                }, 2300);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //    bắt sự kiện khi onlick xóa thích
    public void xoaItemThich(String idYeuThich, int viTri) {
        dataRef = database.getReference("danhSachSanPhamYeuThich").child(sharedPreferences.getString("IDDANHSACHYEUTHICH", "")).child(idYeuThich);
        dataRef.removeValue();
        favoriteAdapter.notifyItemChanged(viTri);
        Toast.makeText(this, "Đã xóa sản phẩm yêu thích!!!", Toast.LENGTH_SHORT).show();
    }

    //bắt cắc sự kiện
    private void batSuKien() {
        favorite_img_btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(favorite_Activity.this, trangChu_SanPham_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void getDataFirebaseYeuThich() {
        dataRef = database.getReference("danhSachSanPhamYeuThich").child(sharedPreferences.getString("IDDANHSACHYEUTHICH", ""));
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrListYeuThich.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    model_yeuThich arrYeuThich = child.getValue(model_yeuThich.class);
                    arrListYeuThich.add(arrYeuThich);

                }
                getDataFirebaseSanPham(arrListYeuThich);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getDataFirebaseSanPham(ArrayList<model_yeuThich> arrListYeuThich) {
        dataRef = database.getReference("khoHang");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrListSanPham.clear();
                for (DataSnapshot child : snapshot.getChildren()) {

                    model_SanPham arrSanPham = child.getValue(model_SanPham.class);
                    for (int i = 0; i < arrListYeuThich.size(); i++) {
                        if (arrSanPham.getIdSanPham().equals(arrListYeuThich.get(i).getIdSanPham())) {
                            arrListSanPham.add(arrSanPham);
                        }
                    }

                    favoriteAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void loadData() {
        favorite_rev_showItem.setHasFixedSize(true);
        favorite_rev_showItem.setLayoutManager(new LinearLayoutManager(this));
        favoriteAdapter = new favoriteAdapter(this, arrListYeuThich, arrListSanPham);
        favorite_rev_showItem.setAdapter(favoriteAdapter);
    }


    private void anhXa() {
        arrListCart = new ArrayList<>();
        favorite_rev_showItem = findViewById(R.id.favorite_rev_showItem);
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
        favorite_img_btnThoat = findViewById(R.id.favorite_img_btnThoat);
        sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);

        arrListSanPham = new ArrayList<>();
        arrListYeuThich = new ArrayList<>();
    }
}