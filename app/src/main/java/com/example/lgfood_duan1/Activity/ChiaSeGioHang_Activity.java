package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lgfood_duan1.Adapter.chiaSeGioHang_showDoc_adapter;
import com.example.lgfood_duan1.Model.model_Cart;
import com.example.lgfood_duan1.Model.model_viTri;
import com.example.lgfood_duan1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChiaSeGioHang_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment
            ChiaSeGioHang_google_map;
    private LinearLayout
            ChiaSeGioHang_llout_btn_ggmap_phatTinNhieu,
            ChiaSeGioHang_llout_btn_rscv_phatTinHieu,
            ChiaSeGioHang_llout_FormTop,
            ChiaSeGioHang_llout_btn_showFormTop,
            ChiaSeGioHang_item_llout_btn_chiaSeGioHang;
    private RecyclerView
            ChiaSeGioHang_rscv_showDanhSach;
    private ImageView
            ChiaSeGioHang_img_btn_back,
            ChiaSeGioHang_img_btn_showTheo,
            ChiaSeGioHang_img_phongTo,
            ChiaSeGioHang_img_thuNho,
            ChiaSeGioHang_img_btn_timKiemKey,
            ChiaSeGioHang_item_img_btn_xoaItem;

    private EditText
            ChiaSeGioHang_edt_timKiemKey;
    private CardView
            ChiaSeGioHang_crv_btn_showTheo,
            ChiaSeGioHang_crv_btn_viTri,
            ChiaSeGioHang_item_crview;


    private FrameLayout
            ChiaSeGioHang_frlout_showListGgMap,
            ChiaSeGioHang_frlout_showListRscv;
    private CircleImageView
            ChiaSeGioHang_item_crimg_avata;

    private TextView
            ChiaSeGioHang_item_tv_nameUser,
            ChiaSeGioHang_item_tv_diaChi,
            ChiaSeGioHang_item_tv_key;
    //    ggmap
    private Location currentLocation;
    private FusedLocationProviderClient client;
    private static final int REQUEST_CODE = 101;
    //    model
    private model_viTri arrViTri;
    private model_Cart arrCart, arrCartTam;
    private ArrayList<model_viTri> arrListViTri;
    private ArrayList<model_Cart> arrListCart, arrayListCartTam;

    private SharedPreferences shareAcout;
    //    mảng
    private ArrayList<LatLng> arrayList;


    // value
    boolean checkOnclick = true, checkShowForm = true;
    String idGioHang, idViTri,key;
    //Firebase
    private DatabaseReference dataRef, dataAccoutRef;
    private FirebaseDatabase database;
    // adapter
    chiaSeGioHang_showDoc_adapter seGioHang_showDoc_adapter;
    int lever = 20;

    @Override
    protected void onStart() {
        dataRef = database.getReference("location").child(idViTri);
        dataRef.child("tinhTrang").setValue(false);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chia_se_gio_hang);
        anhXa();
        getSharedPre();

        getDataFirebaseViTri();
        fetchLastLocation();
        addLocationMe();
        batSuKien();
//        chia sẻ giỏ hàng
        getDataGioHang();
        ChiaSeGioHang_img_btn_timKiemKey.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String valueKey = ChiaSeGioHang_edt_timKiemKey.getText().toString();
                if (valueKey.equals("")) {
                    getDataFirebaseViTri();
                }
                return false;
            }
        });

    }

    //thêm 1 user me
    private void addLocationMe() {
        dataRef = database.getReference("location").child(idViTri);
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrViTri = snapshot.getValue(model_viTri.class);
//                arrayList.add(new LatLng(arrViTri.getLatitude(), arrViTri.getLongitude()));
                key = arrViTri.getKey();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    //   Trung lưu vị trí lên mảng
    private void setArrViTri(ArrayList<model_viTri> arrListViTris) {
        arrayList.clear();
        for (int i = 0; i < arrListViTris.size(); i++) {
            arrayList.add(new LatLng(arrListViTris.get(i).getLatitude(), arrListViTris.get(i).getLongitude()));
        }


    }

    /******************** Trung Show thông tin ra kiểu dọc**********************/
    private void showListProduc_Vartical(ArrayList<model_viTri> arrListViTri) {
        ChiaSeGioHang_rscv_showDanhSach.setLayoutManager(new GridLayoutManager(this, 2));
        ChiaSeGioHang_rscv_showDanhSach.setItemAnimator(new DefaultItemAnimator());
        //        Initilize
        seGioHang_showDoc_adapter = new chiaSeGioHang_showDoc_adapter(arrListViTri, ChiaSeGioHang_Activity.this);

        ChiaSeGioHang_rscv_showDanhSach.setAdapter(seGioHang_showDoc_adapter);
    }

    //Trung: lấy dữ liệu sản phẩm trên firebase về
    private void getDataFirebaseViTri() {

        dataRef = database.getReference("location");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrListViTri.clear();
//                Chạy vòng lặp kiểm tra dữ liệu
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrViTri = child.getValue(model_viTri.class);
                    if (arrViTri.isTinhTrang() == true) {
                        arrListViTri.add(arrViTri);
                    }
                }
                setArrViTri(arrListViTri);

                showListProduc_Vartical(arrListViTri);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        dataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                fetchLastLocation();

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void batSuKien() {
//                    bắt sự kiện tắt item poup
        ChiaSeGioHang_item_img_btn_xoaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_bottom);
                ChiaSeGioHang_item_crview.setAnimation(animation);
                ChiaSeGioHang_item_img_btn_xoaItem.setVisibility(View.INVISIBLE);
                ChiaSeGioHang_item_crview.setVisibility(View.INVISIBLE);
            }
        });
//      Tìm kiếm vị trí user bằng key
        ChiaSeGioHang_img_btn_timKiemKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                check validate
                String valueKey = ChiaSeGioHang_edt_timKiemKey.getText().toString();
                if (!valueKey.equals("")) {

                    dataRef = database.getReference("location");
                    dataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            arrListViTri.clear();
//                Chạy vòng lặp kiểm tra dữ liệu
                            for (DataSnapshot child : snapshot.getChildren()) {
                                arrViTri = child.getValue(model_viTri.class);
                                if (arrViTri.getKey().contains(valueKey)) {
                                    arrListViTri.add(arrViTri);
                                }
                            }
                            showListProduc_Vartical(arrListViTri);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                } else {
                    getDataFirebaseViTri();
                }
            }
        });


// chuyển đến trang chia sẻ kết nối "Phát tín hiệu"
        ChiaSeGioHang_llout_btn_ggmap_phatTinNhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiaSeGioHang_Activity.this, chiaSeKetNoiGioHang_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("iT_key",key);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ChiaSeGioHang_llout_btn_rscv_phatTinHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiaSeGioHang_Activity.this, chiaSeKetNoiGioHang_Activity.class);

                Bundle bundle = new Bundle();
                bundle.putString("iT_key",key);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        show form tìm kiếm và chức năng
        ChiaSeGioHang_llout_btn_showFormTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams params = ChiaSeGioHang_llout_FormTop.getLayoutParams();

                if (checkShowForm == false) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_top);
                    ChiaSeGioHang_llout_FormTop.setAnimation(animation);

                    params.height = 0;
                    params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    ChiaSeGioHang_llout_FormTop.setLayoutParams(params);

                    checkShowForm = true;
                } else {
                    params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_top);
                    ChiaSeGioHang_llout_FormTop.setAnimation(animation);
                    ChiaSeGioHang_llout_FormTop.setLayoutParams(params);
                    checkShowForm = false;

                }
            }
        });
//        thoát trang
        ChiaSeGioHang_img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiaSeGioHang_Activity.this, gio_Hang_Activity.class);
                startActivity(intent);
            }
        });
//  show theo gg map/ list danh sach
        ChiaSeGioHang_crv_btn_showTheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkOnclick == true) {
                    ChiaSeGioHang_img_btn_showTheo.setImageResource(R.drawable.ic_col_sort_row);
                    ChiaSeGioHang_frlout_showListGgMap.setVisibility(View.VISIBLE);
                    ChiaSeGioHang_frlout_showListRscv.setVisibility(View.INVISIBLE);

                    checkOnclick = false;

                } else {
                    ChiaSeGioHang_img_btn_showTheo.setImageResource(R.drawable.ic_ggmap);
                    ChiaSeGioHang_frlout_showListGgMap.setVisibility(View.INVISIBLE);
                    ChiaSeGioHang_frlout_showListRscv.setVisibility(View.VISIBLE);
                    Animation animationout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_bottom);
                    ChiaSeGioHang_item_crview.setAnimation(animationout);
                    ChiaSeGioHang_item_img_btn_xoaItem.setAnimation(animationout);
                    ChiaSeGioHang_item_img_btn_xoaItem.setVisibility(View.INVISIBLE);
                    ChiaSeGioHang_item_crview.setVisibility(View.INVISIBLE);
                    checkOnclick = true;
                }
            }
        });

    }


    // harePre
    private void getSharedPre() {
        shareAcout = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        idViTri = shareAcout.getString("IDVITRI", "");
        idGioHang = shareAcout.getString("IDGIOHANG", "");


    }

    //Trung nhận dữ liệu từ trang Adapter
    public void nhanDuLieuAdapterItem(String idGioHangTamB) {

        setDataChiaSeGioHang(idGioHangTamB);
    }

    // lấy xong dữ liệu giỏ hàng a thì bắt đầu chuyển chia sẻ qua giỏ hàng tạm b
    private void setDataChiaSeGioHang(String idGioHangTamB) {
        dataRef = database.getReference("gioHangTam");
//        xóa dữ liệu trong giỏ hàng tạm trước khi add mưới vô
        dataRef.child(idGioHangTamB).removeValue();
// chạy vòng for để add tát cả sản phẩm vô mảng tạm
        for (int i = 0; i < arrListCart.size(); i++) {
            arrCartTam = new model_Cart(UUID.randomUUID().toString(), arrListCart.get(i).getIdSanPham(), arrListCart.get(i).getSoLuong());
//            nếu báo lỗi thì check lại cấp quyền gps
            dataRef.child(idGioHangTamB).child(arrCartTam.getIdGioHang()).setValue(arrCartTam);
        }
        Toast.makeText(this, "Đã gửi giỏ hàng", Toast.LENGTH_SHORT).show();
    }

    // lấy dữ liệu từ giỏ hàng
    private void getDataGioHang() {
        dataRef = database.getReference("GioHangs").child(idGioHang);
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrListCart.clear();
//                Chạy vòng lặp kiểm tra dữ liệu
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrCart = child.getValue(model_Cart.class);
                    arrListCart.add(arrCart);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }


    // Trung xin quyền gg map
    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(ChiaSeGioHang_Activity.this, currentLocation.getLatitude()
                            + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    ChiaSeGioHang_google_map.getMapAsync(ChiaSeGioHang_Activity.this);

                }
            }
        });
    }


    //Trung show gg map
    @Override
    public void onMapReady(GoogleMap googleMap) {

        getDataFirebaseViTri();

        ChiaSeGioHang_img_phongTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        ChiaSeGioHang_img_thuNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                googleMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        if (arrListViTri != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                int finalI = i;

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

//                    set giá trị cho item sử lý bắt sự kiện
                        try {
                            ChiaSeGioHang_item_tv_key.setText("key:#" + arrListViTri.get(finalI).getKey());
                            Glide.with(ChiaSeGioHang_Activity.this).load(arrListViTri.get(finalI).getAnhUser())
                                    .into(ChiaSeGioHang_item_crimg_avata);
                            ChiaSeGioHang_item_tv_nameUser.setText(arrListViTri.get(finalI).getNameUser());
                            ChiaSeGioHang_item_tv_diaChi.setText(arrListViTri.get(finalI).getVitri());

                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom);
                            ChiaSeGioHang_item_crview.setAnimation(animation);
                            ChiaSeGioHang_item_img_btn_xoaItem.setAnimation(animation);
                            ChiaSeGioHang_item_img_btn_xoaItem.setVisibility(View.VISIBLE);
                            ChiaSeGioHang_item_crview.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            Toast.makeText(ChiaSeGioHang_Activity.this, "User này đã dừng!!", Toast.LENGTH_SHORT).show();

                        }


                        return false;
                    }
                });

                try {
                    //                MarkerOptions markerOptions = new MarkerOptions().position(arrayList.get(i));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arrayList.get(i), 25));
                    try {

                        String nameUsser = arrListViTri.get(finalI).getNameUser().toUpperCase();
                        String key = arrListViTri.get(finalI).getKey();
                        MarkerOptions markerOptions = new MarkerOptions().position(arrayList.get(i))
                                .title(nameUsser + "/key:#" + key);
                        googleMap.addMarker(markerOptions);
                    }catch (Exception e ){

                    }

//                    bắt sự kiện chia sẻ giỏ hàng
                    ChiaSeGioHang_item_llout_btn_chiaSeGioHang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
//                                nếu như có user thì gửi idgiohaang
                                nhanDuLieuAdapterItem(arrListViTri.get(finalI).getIdGioHangTam());
                            } catch (Exception e) {
//                                nếu như không có bắt lỗi tắt item và thông báo
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_bottom);
                                ChiaSeGioHang_item_crview.setAnimation(animation);
                                ChiaSeGioHang_item_img_btn_xoaItem.setAnimation(animation);
                                ChiaSeGioHang_item_img_btn_xoaItem.setVisibility(View.INVISIBLE);
                                ChiaSeGioHang_item_crview.setVisibility(View.INVISIBLE);
                                Toast.makeText(ChiaSeGioHang_Activity.this, "User này đã dừng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
//                    Toast.makeText(ChiaSeGioHang_Activity.this, "User này đã dừng!!", Toast.LENGTH_SHORT).show();

                }

            }
        } else {
            arrayList.clear();
        }

    }


    // Trung bắt sự kiện xin quyền location
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }

    // Trung ánh  xạ activity
    private void anhXa() {
        client = LocationServices.getFusedLocationProviderClient(ChiaSeGioHang_Activity.this);
//        Model
        arrayList = new ArrayList<LatLng>();
        arrListViTri = new ArrayList<>();
        arrListCart = new ArrayList<>();
        arrayListCartTam = new ArrayList<>();
//        firebase
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");

//        GG map
        ChiaSeGioHang_google_map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.chiaSeGioHang_google_map);
//         LinearLayout
        ChiaSeGioHang_llout_btn_ggmap_phatTinNhieu = findViewById(R.id.chiaSeGioHang_llout_btn_ggmap_phatTinNhieu);
        ChiaSeGioHang_llout_btn_rscv_phatTinHieu = findViewById(R.id.chiaSeGioHang_llout_btn_rscv_phatTinHieu);
        ChiaSeGioHang_llout_btn_showFormTop = findViewById(R.id.chiaSeGioHang_llout_btn_showFormTop);
        ChiaSeGioHang_item_llout_btn_chiaSeGioHang = findViewById(R.id.chiaSeGioHang_item_llout_btn_chiaSeGioHang);
//         RecyclerView
        ChiaSeGioHang_rscv_showDanhSach = findViewById(R.id.chiaSeGioHang_rscv_showDanhSach);
//         ImageView
        ChiaSeGioHang_img_btn_back = findViewById(R.id.chiaSeGioHang_img_btn_back);
        ChiaSeGioHang_img_btn_showTheo = findViewById(R.id.chiaSeGioHang_img_btn_showTheo);
        ChiaSeGioHang_img_phongTo = findViewById(R.id.chiaSeGioHang_img_phongTo);
        ChiaSeGioHang_img_thuNho = findViewById(R.id.chiaSeGioHang_img_thuNho);
        ChiaSeGioHang_img_btn_timKiemKey = findViewById(R.id.chiaSeGioHang_img_btn_timKiemKey);
        ChiaSeGioHang_item_img_btn_xoaItem = findViewById(R.id.chiaSeGioHang_item_img_btn_xoaItem);
//         EditText
        ChiaSeGioHang_edt_timKiemKey = findViewById(R.id.chiaSeGioHang_edt_timKiemKey);
//         CardView
        ChiaSeGioHang_crv_btn_showTheo = findViewById(R.id.chiaSeGioHang_crv_btn_showTheo);
        ChiaSeGioHang_crv_btn_viTri = findViewById(R.id.chiaSeGioHang_crv_btn_viTri);
        ChiaSeGioHang_item_crview = findViewById(R.id.chiaSeGioHang_item_crview);
//         FrameLayout
        ChiaSeGioHang_frlout_showListGgMap = findViewById(R.id.chiaSeGioHang_frlout_showListGgMap);
        ChiaSeGioHang_frlout_showListRscv = findViewById(R.id.chiaSeGioHang_frlout_showListRscv);
        ChiaSeGioHang_llout_FormTop = findViewById(R.id.chiaSeGioHang_llout_FormTop);
//        CircleImageView
        ChiaSeGioHang_item_crimg_avata = findViewById(R.id.chiaSeGioHang_item_crimg_avata);
//                        Texview
        ChiaSeGioHang_item_tv_nameUser = findViewById(R.id.chiaSeGioHang_item_tv_nameUser);
        ChiaSeGioHang_item_tv_diaChi = findViewById(R.id.chiaSeGioHang_item_tv_diaChi);
        ChiaSeGioHang_item_tv_key = findViewById(R.id.chiaSeGioHang_item_tv_key);
    }

}