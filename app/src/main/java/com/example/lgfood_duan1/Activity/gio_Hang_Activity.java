package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
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
import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_Cart;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_addToCart;
import com.example.lgfood_duan1.Model.model_viTri;
import com.example.lgfood_duan1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class gio_Hang_Activity extends AppCompatActivity {
    private ImageView
            GioHang_img_back;
    private TextView
            GioHang_tv_diaChi,
            GioHang_tv_name,
            GioHang_tv_tongTien;
    private Button
            GioHang_btn_datHang;
    private LinearLayout
            GioHang_llout_btn_shareCart;
    private RecyclerView
            GioHang_rv_showGioHang;
    //thai

    DatabaseReference mData, dataRef;
    FirebaseDatabase database;
    //model\
    private ArrayList<model_Cart> modelCartArrayList, arrListGioHangSCapNhat;
    private ArrayList<model_addToCart> cartArrayList;
    private model_addToCart modelAddToCart;
    private model_viTri arrViTri;
    List<Address> addresses;
    // Adapter
    addToGioHangAdapter cartAdapter;

    //    ggmap
    FusedLocationProviderClient fusedLocationProviderClient;

    //    sharePre
    private SharedPreferences shareAcout;
    SharedPreferences sharedPreferences;
    //    Value
    int i, tien;
    double TongTien = 0;
    private static final int REQUEST_CODE = 101;
    //    user
    String idUser, viTri, idViTri, idGioHangTam, nameUser, anhUser;

    //random
    UUID uuid;

    @Override
    protected void onStart() {
//        dataRef = database.getReference("location").child(idViTri);
//        dataRef.child("tinhTrang").setValue(false);

        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        anhXa();
        getLocation();
        //thai
        itemAddToCart();
        loadItemAddToCart();
        layTuFirebase();

        getSharedPre();
        batSuKien();

    }


    // harePre
    private void getSharedPre() {
        shareAcout = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        idUser = shareAcout.getString("IDUSRE", "");
        viTri = shareAcout.getString("VITRI", "");
        idViTri = shareAcout.getString("IDVITRI", "");
        idGioHangTam = shareAcout.getString("IDGIOHANGTAM", "");
        nameUser = shareAcout.getString("NAMEUSER", "");
        anhUser = shareAcout.getString("ANHUSER", "");

        if (GioHang_tv_diaChi.getText().equals("")) {
            GioHang_tv_diaChi.setText(viTri);
        }
    }


    //  Trung lấy vị trí lưu vị trí lên firebase location
    private void getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(gio_Hang_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//             getLocation();
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {

                        try {
                            Geocoder geocoder = new Geocoder(
                                    gio_Hang_Activity.this, Locale.getDefault());


                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            GioHang_tv_diaChi.setText(Html.fromHtml("" + addresses.get(0).getAddressLine(0)));


                            String viTri = GioHang_tv_diaChi.getText().toString();
                            dataRef = database.getReference().child("location").child(idViTri);

                            // datetime hiện tại
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'lúc' HH:mm:ss z");
                            Date reaDate = new Date(System.currentTimeMillis());
//                            add lên firebase
//                            random
                            uuid = UUID.randomUUID();

                            arrViTri = new model_viTri(uuid.toString(), idGioHangTam, viTri, addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), false, formatter.format(reaDate), uuid.toString().substring(0, 6), anhUser, nameUser);
                            dataRef.setValue(arrViTri);

                        } catch (IOException e) {
                            Log.d("ddd", e + "");
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(gio_Hang_Activity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        }

    }

    //  Trung xin quyền loacition
    private void location_chiaSeGioHang() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
    }

    // Trung bắt sự kiện xin quyền location
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location_chiaSeGioHang();
                }
                break;
        }
    }

    //thai
    private void itemAddToCart() {

        dataRef = database.getReference("GioHangs").child(sharedPreferences.getString("IDGIOHANG", ""));
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (modelCartArrayList != null) {
                    modelCartArrayList.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    model_Cart cart = ds.getValue(model_Cart.class);
                    modelCartArrayList.add(cart);
                }
                checkKhoHang();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    //thai
    private void checkKhoHang() {
        mData = database.getReference("khoHang");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    model_SanPham sanPham = ds.getValue(model_SanPham.class);

                    for (int i = 0; i < modelCartArrayList.size(); i++) {

                        if (modelCartArrayList.get(i).getIdSanPham().equals(sanPham.getIdSanPham())) {
                            mData = database.getReference("newCarts");
                            String soLuong = modelCartArrayList.get(i).getSoLuong();
                            modelAddToCart = new model_addToCart(sanPham.getIdSanPham(), sanPham.getMoTaSanPham(), sanPham.getTenSanPham(), sanPham.getNgaySanXuatSanPham(), sanPham.getXuatXuSanPham(), sanPham.getLoaiSanPham(), sanPham.getTinhTrangSanPham(), sanPham.getAnhSanPham(), sanPham.getNgayNhapSanPham(), sanPham.getSoLuongSanPham(), Integer.valueOf(soLuong), sanPham.getGiaNhapSanPham(), sanPham.getGiaBanSanPham());
                            mData.child(sharedPreferences.getString("IDGIOHANG", "")).child(sanPham.getIdSanPham()).setValue(modelAddToCart);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }


    //thai
    private void loadItemAddToCart() {
        GioHang_rv_showGioHang.setHasFixedSize(true);
        GioHang_rv_showGioHang.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new addToGioHangAdapter(this, cartArrayList, modelCartArrayList, new addToGioHangAdapter.IClickListener() {


            @Override
            public void onCLickMinusItem(model_Cart cart, int viTri) {
                onClickMinusItemAddToCart(cart, viTri);
            }

            @Override
            public void onClickPlusItem(model_Cart cart, int viTri) {
                onClickPlusItemAddToCart(cart, viTri);
            }

            @Override
            public void onClickDelete(model_addToCart cart, int viTri, model_Cart arrGioHangs) {
                onClickDeleteItem(cart, viTri, arrGioHangs);
            }

            @Override
            public void onClickShowItem(model_addToCart cart, model_Cart arrGioHangs, int viTri) {
                onClickShowItemChiTiet(cart, arrGioHangs, viTri);
            }


        });


        GioHang_rv_showGioHang.setAdapter(cartAdapter);
    }

    private void onClickShowItemChiTiet(model_addToCart cart, model_Cart arrGioHang, int viTri) {
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

        Glide.with(gio_Hang_Activity.this)
                .load(cart.getAnhSp())
                .into(datNhanh_img_showAnhSanPham);
        datNhanh_tv_xuatXuSanPham.setText(cart.getXuatXuSp());
        datNhanh_tv_showTenSanPham.setText(cart.getTenSp());
        datNhanh_tv_giaSanPham.setText(cart.getGiaBanSp() + "00đ");
        datNhanh_tv_giamGiaSanPham.setText("   -" + cart.getGiamGiaSp());
        datNhanh_tv_ngaySanXuat.setText(cart.getNgaySanXuatSp());
        datNhanh_tv_soLuongSanPhamTrongKho.setText(String.valueOf(cart.getSoLuongSp()));
//        datNhanh_tv_soLuongSanPhamYeuThichDaMua.setText(Integer.valueOf(sanPham.getAnhSanPham()));
        datNhanh_tv_moTaSanPham.setText(cart.getMoTaSp());

        datNhanh_tv_SoLuongSanpham.setText(String.valueOf(arrGioHang.getSoLuong()));
        i = Integer.parseInt(arrGioHang.getSoLuong());
        double gia = Double.parseDouble(String.valueOf(cart.getGiaBanSp()));
        double tong = 0;
        tong = i * gia;
        datNhanh_btn_themSanPhamVaoGioHang.setText("ADD TO CART " + tong + "00VNĐ");

        datNhanh_btn_themSanPhamVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrGioHang.setSoLuong(i + "");
                mData.child(sharedPreferences.getString("IDGIOHANG", "")).child(arrGioHang.getIdGioHang()).child("soLuong").setValue(arrGioHang.getSoLuong());

                dialog.setContentView(R.layout.activity_add_to_cart_anim);
                loadItemAddToCart();
                cartAdapter.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 2300);
            }
        });
        //giam so luong san pham
        datNhanh_img_btn_giamSoLuongSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = database.getReference("GioHangs");

                i--;
                if (i >= 1) {

//                    arrGioHang.setSoLuong(i + "");
                    datNhanh_tv_SoLuongSanpham.setText(i + "");


                    tinhTongGiaTienSanPham(cartArrayList);

                } else {
                    i = 1;
                    datNhanh_tv_SoLuongSanpham.setText(i + "");

//                    arrGioHang.setSoLuong(i + "");
                }
                double gia = Double.parseDouble(String.valueOf(cart.getGiaBanSp()));
                double tong = 0;
                tong = i * gia;
                datNhanh_btn_themSanPhamVaoGioHang.setText("ADD TO CART " + tong + "00VNĐ");
                datNhanh_tv_SoLuongSanpham.setText(i + "");


            }
        });
        //tang so luong san pham
        datNhanh_img_btn_tangSoLuongSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = database.getReference("GioHangs");


                i++;

//        tien= (int) (i*cart.getGiaBanSp());

//                arrGioHang.setSoLuong(i + "");
                tinhTongGiaTienSanPham(cartArrayList);
                double gia = Double.parseDouble(String.valueOf(cart.getGiaBanSp()));
                double tong = 0;
                tong = i * gia;
                datNhanh_btn_themSanPhamVaoGioHang.setText("ADD TO CART " + tong + "00VNĐ");
                datNhanh_tv_SoLuongSanpham.setText(i + "");
            }
        });
        //turn off dialog
        datNhanh_linear_turnOffDiaLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartAdapter.notifyItemChanged(viTri);
                dialog.dismiss();
                i = 1;
            }
        });


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();


    }

    private void onClickDeleteItem(model_addToCart cart, int viTri, model_Cart arrGioHangs) {

//        new AlertDialog.Builder(gio_Hang_Activity.this)
//                .setTitle(getString(R.string.app_name))
//                .setMessage("Bạn chắc chắn muốn xóa item này không?")
//                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {

        final Dialog dialogXoaItem = new Dialog(gio_Hang_Activity.this);
        dialogXoaItem.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogXoaItem.setContentView(R.layout.item_dialog_chucnang_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogXoaItem.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialogXoaItem.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogXoaItem.setCancelable(false); //Optional
        dialogXoaItem.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        ImageView item_dialog_chucNang_img_imgErro = dialogXoaItem.findViewById(R.id.item_dialog_chucNang_img_imgErro);
        TextView item_dialog_chucNang_txt_nameErro = dialogXoaItem.findViewById(R.id.item_dialog_chucNang_txt_nameErro);
        Button Okay = dialogXoaItem.findViewById(R.id.btn_okay);
        Button Cancel = dialogXoaItem.findViewById(R.id.btn_cancel);
        Okay.setText("Delete");
        item_dialog_chucNang_img_imgErro.setImageResource(R.drawable.question);
        item_dialog_chucNang_txt_nameErro.setText("Would you want to delete this product?");
        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = database.getReference("newCarts");

                mData.child(sharedPreferences.getString("IDGIOHANG", "")).child(cart.getIdSp()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {

                        Toast.makeText(gio_Hang_Activity.this, "Delete item success", Toast.LENGTH_SHORT).show();

                    }
                });

                mData = database.getReference("GioHangs");
                mData.child(sharedPreferences.getString("IDGIOHANG", "")).child(arrGioHangs.getIdGioHang()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {

                    }
                });

                cartAdapter.notifyItemChanged(viTri);
                dialogXoaItem.dismiss();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(gio_Hang_Activity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialogXoaItem.dismiss();
            }
        });
        dialogXoaItem.show();


    }

    //    tinh tong gia tien san pham


    private void tinhTongGiaTienSanPham(ArrayList<model_addToCart> arrListNewCart) {

        Log.d("ddd", "size" + arrListNewCart.size());


        dataRef = database.getReference("GioHangs").child(sharedPreferences.getString("IDGIOHANG", ""));
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                arrListGioHangSCapNhat.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    model_Cart cart = ds.getValue(model_Cart.class);
                    arrListGioHangSCapNhat.add(cart);
                }
                TongTien = 0;
                for (int i = 0; i < arrListGioHangSCapNhat.size(); i++) {
                    for (int j = 0; j < arrListNewCart.size(); j++) {
                        if (arrListGioHangSCapNhat.get(i).getIdSanPham().equals(arrListNewCart.get(j).getIdSp())) {
                            //        lấy được giá item và số lương

//            số lượng
                            int soLuongSanPham = Integer.parseInt(arrListGioHangSCapNhat.get(i).getSoLuong());
//            giá tiền
                            double giaSanPham = arrListNewCart.get(j).getGiaBanSp();
//            tính tổng giả tiền
                            TongTien = TongTien + (giaSanPham * soLuongSanPham);
                        }
                    }
                }
                GioHang_tv_tongTien.setText(TongTien + "00vnđ");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    //giam so luong san pham: thai
    private void onClickMinusItemAddToCart(model_Cart modelCartArrayList, int viTri) {

        mData = database.getReference("GioHangs");
        i = Integer.parseInt(modelCartArrayList.getSoLuong());
        int a;
        i--;
        if (i <= 1) {
            i = 1;

            modelCartArrayList.setSoLuong(i + "");
            mData.child(sharedPreferences.getString("IDGIOHANG", "")).child(modelCartArrayList.getIdGioHang()).setValue(modelCartArrayList);
            tinhTongGiaTienSanPham(cartArrayList);
            return;
        } else {
            modelCartArrayList.setSoLuong(i + "");
            mData.child(sharedPreferences.getString("IDGIOHANG", "")).child(modelCartArrayList.getIdGioHang()).setValue(modelCartArrayList);
            tinhTongGiaTienSanPham(cartArrayList);
        }

    }

    //tang so luong san pham:thai
    private void onClickPlusItemAddToCart(model_Cart modelCartArrayList, int viTri) {

        mData = database.getReference("GioHangs");
        i = Integer.parseInt(modelCartArrayList.getSoLuong());
        i++;

        modelCartArrayList.setSoLuong(i + "");
        mData.child(sharedPreferences.getString("IDGIOHANG", "")).child(modelCartArrayList.getIdGioHang()).setValue(modelCartArrayList);
        tinhTongGiaTienSanPham(cartArrayList);


    }

    //thai: lay du lieu tu firebase
    private void layTuFirebase() {
//        lấy sản phẩm từ giỏ hàng
        mData = database.getReference("newCarts").child(sharedPreferences.getString("IDGIOHANG", ""));
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                model_addToCart cart = snapshot.getValue(model_addToCart.class);

                if (cart != null) {
                    cartArrayList
                            .add(new model_addToCart(cart.getIdSp(),
                                    cart.getMoTaSp(),
                                    cart.getTenSp(),
                                    cart.getNgaySanXuatSp(),
                                    cart.getXuatXuSp(),
                                    cart.getLoaiSp(),
                                    cart.getTinhTrangSp(),
                                    cart.getAnhSp(),
                                    cart.getNgayNhapSp(),
                                    cart.getSoLuongSp(),
                                    cart.getGiamGiaSp(),
                                    cart.getGiaNhapSp(),
                                    cart.getGiaBanSp()));
                    cartAdapter.notifyDataSetChanged();
                    tinhTongGiaTienSanPham(cartArrayList);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                model_addToCart cart = snapshot.getValue(model_addToCart.class);
                if (cart == null || cartArrayList == null || cartArrayList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < cartArrayList.size(); i++) {
                    if (cart.getIdSp() == cartArrayList.get(i).getIdSp()) {
                        cartArrayList.set(i, cart);
                    }
                }

                cartAdapter.notifyDataSetChanged();
//                tinhTongGiaTienSanPham(modelCartArrayList, cartArrayList);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                model_addToCart cart = snapshot.getValue(model_addToCart.class);
                if (cart == null || cartArrayList == null || cartArrayList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < cartArrayList.size(); i++) {
                    if (cart.getIdSp() == cartArrayList.get(i).getIdSp()) {
                        cartArrayList.remove(cartArrayList.get(i));
                        break;
                    }
                }
                cartAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void batSuKien() {
//        chuyển trang đến xác nhận đặt hàng
        GioHang_btn_datHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(gio_Hang_Activity.this, Xac_Nhan_DH_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("iT_tongGiaTien", TongTien);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        bắt sự kiện mở trang chia sẻ giỏ hàng
        GioHang_llout_btn_shareCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();

//                mở xin quyền chia sẻ vị trí
                Intent intent = new Intent(gio_Hang_Activity.this, ChiaSeGioHang_Activity.class);
                startActivity(intent);
            }
        });
//        bắt sự kiện thoát trang giỏ hảng
        GioHang_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(gio_Hang_Activity.this, trangChu_SanPham_Activity.class);
                startActivity(intent);
            }
        });
    }


    //    Bắt sự kiện buttom

    // set dữ liệu
    //     Ánh xạ đây nè :D
    private void anhXa() {
        arrListGioHangSCapNhat = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");

//        TextView
        GioHang_tv_diaChi = findViewById(R.id.gioHang_tv_diaChi);
        GioHang_tv_name = findViewById(R.id.gioHang_tv_name);
        GioHang_tv_tongTien = findViewById(R.id.gioHang_tv_tongTien);


//      ImageView

        GioHang_img_back = findViewById(R.id.gioHang_img_back);

//      Button

        GioHang_btn_datHang = findViewById(R.id.gioHang_btn_datHang);

//      LinearLayout

        GioHang_llout_btn_shareCart = findViewById(R.id.gioHang_llout_btn_shareCart);

//        ListView

        GioHang_rv_showGioHang = findViewById(R.id.gioHang_rv_showGioHang);


        //thai
        sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        cartArrayList = new ArrayList<>();
        modelCartArrayList = new ArrayList<>();
    }

}