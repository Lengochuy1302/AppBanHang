package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.lgfood_duan1.Adapter.showSlider_adaper;
import com.example.lgfood_duan1.Adapter.trangChu_showDoc_adapter;
import com.example.lgfood_duan1.Adapter.trangChu_showNgang_adapter;
import com.example.lgfood_duan1.Model.model_Account;
import com.example.lgfood_duan1.Model.model_Cart;
import com.example.lgfood_duan1.Model.model_SanPham;
import com.example.lgfood_duan1.Model.model_addToCart;
import com.example.lgfood_duan1.Model.model_yeuThich;
import com.example.lgfood_duan1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.lgfood_duan1.R.id.trangChuSanPham_img_logo;
import static com.example.lgfood_duan1.R.id.trangChuSanPham_rscV_showSanPhamDoc;

public class trangChu_SanPham_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout
            TrangChuSanPham_drawerllout_main;
    private NavigationView
            TrangChuSanPham_nav_drawer;
    private ImageView
            TrangChuSanPham_img_showMenu,
            TrangChuSanPham_img_btnGioHang,
            TrangChuSanPham_img_btn_kieuLoaiSanPham,
            TrangChuSanPham_img_showLoaiCoffee,
            TrangChuSanPham_img_showLoaiThaoDuoc,
            TrangChuSanPham_img_showLoaiHat,
            TrangChuSanPham_img_showLoaiMuc,
            TrangChuSanPham_img_showLoaiTra;

    private TextView
            TrangChuSanPham_tv_soLuongThongBao,
            TrangChuSanPham_tv_btn_showTatCaLoaiSanpham,
            TrangChuSanPham_tv_showLoai,
            TrangChuSanPham_tv_btn_timTen,
            TrangChuSanPham_tv_btn_timGia,
            TrangChuSanPham_tv_btn_timLoai,
            TrangChuSanPham_tv_loai;
    private LinearLayout
            TrangChuSanPham_llout_formChonLoai;
    private RecyclerView
            TrangChuSanPham_rscV_showSanPhamNgang,
            TrangChuSanPham_rscV_showSanPhamDoc;
    private FrameLayout
            DatNhanh_FmLt_showChiTietSanPham,
            SanPham_flou_search,
            TrangChuSanPham_Flout_btn_user,
            TrangChuSanPham_Flout_btn_donHang;
    private LinearLayout
            DatNhanh_btn_themSanPhamVaoGioHang;
    private CircleImageView TrangChuSanPham_img_logo;
    private EditText
            TrangChuSanPham_edt_timKiemSanPham;

    private FloatingActionButton
            TrangChuSanPham_FloatBtn_nemu;


    private SharedPreferences shareAcout;
    String idSharePre;
    String idGioHangShare;
    //    sp Slider\
    SliderView sliderView;
    int[] images_slider = {R.drawable.img_panner, R.drawable.banner1, R.drawable.baner2};
    Dialog dialogLoading;
    Dialog diaLog;
    //Firebase
    private DatabaseReference dataRef, dataAccoutRef;
    private FirebaseDatabase database;
    //    Model
    private ArrayList<model_Cart> model_cartArrayList;
    private ArrayList<model_SanPham> arrListSanPham;
    private ArrayList<model_Cart> arrListCart;
    ArrayList<model_yeuThich> arrListYeuThich;
    private model_yeuThich arrYeuThich;
    private model_SanPham arrSanPham;
    private model_Cart arrCart;
    // adapter
    trangChu_showDoc_adapter TrangChu_showDoc_adapter;
    trangChu_showNgang_adapter TrangChu_showNgang_adapter;

    int timkiem = 0;
    //biến số lượng và id giỏ hàng
    int i = 1;
    String idGioHang;
    boolean checkMenu = false;
    GoogleSignInClient mGoogleSignInClient;


    SharedPreferences sharedPreferences;
    public static boolean CHECK = false;
    String id;
    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage=50;
    private int currentpage=1;
    GridLayoutManager gridLayoutManager;
    int dem =-1;
    int khoangDem=10;
    ArrayList<model_SanPham> arrListSanPhamPhanTrang;


    @Override
    protected void onStart() {

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                diaLog.dismiss();
            }
        },1000);
        diaLog.show();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu_san_pham);

        anhXa();
        batSuKien();
        getDataFirebaseCart();
        NavigationDrawer();
        getDataRealtimeDatabase();

        timKiem();
        showSlider();
        processrequest();
        getDataFirebaseAccout();
//        checkItemYeuThich();
    }

    //    lấy thông tin user
    private void getDataFirebaseAccout() {

    }

    //    Trung thêm nhanh sản phẩm vào giỏ hàng
    public void themNhanhSanPhamVaoGioHang(model_SanPham sanPham) {
        anhXa();
        getDataFirebaseCart();
        String idProduct = sanPham.getIdSanPham();
        Log.d("eee", "idprduc" + idProduct);
        if (sharedPreferences.getString("IDDANHSACHYEUTHICH", "").isEmpty()) {
            ImageView item_dialog_chucNang_img_imgErro=dialogLoading.findViewById(R.id.item_dialog_chucNang_img_imgErro);
            TextView item_dialog_chucNang_txt_nameErro=dialogLoading.findViewById(R.id.item_dialog_chucNang_txt_nameErro);
            Button Okay = dialogLoading.findViewById(R.id.btn_okay);
            Button Cancel = dialogLoading.findViewById(R.id.btn_cancel);
            //setText Item
            Okay.setText("Đăng nhập");
            item_dialog_chucNang_img_imgErro.setImageResource(R.drawable.question);
            item_dialog_chucNang_txt_nameErro.setText("Bạn chưa đăng nhập vào ứng dụng này?");
            Okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                            Toast.makeText(context.getApplicationContext(), "ban chua login", Toast.LENGTH_SHORT).show();

                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                            startActivity(intent);
                            diaLog.dismiss();
                        }
                    },1000);
                    diaLog.show();
                    dialogLoading.dismiss();
                }
            });

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLoading.dismiss();
                }
            });
            dialogLoading.show();
        } else {
            dataRef = database.getReference("Accounts").child(idSharePre);
            dataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    final Dialog dialogOnStar = new Dialog(trangChu_SanPham_Activity.this);
                    dialogOnStar.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogOnStar.setContentView(R.layout.item_login);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialogAddNgang = new Dialog(trangChu_SanPham_Activity.this);
                            dialogAddNgang.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogAddNgang.setContentView(R.layout.item_login);
                            dialogAddNgang.setContentView(R.layout.activity_add_to_cart_anim);
                            dialogAddNgang.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
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
                                    dialogAddNgang.dismiss();
                                }
                            }, 2300);
                            dialogAddNgang.show();
                            dialogOnStar.dismiss();
                        }
                    },1000);
                    dialogOnStar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogOnStar.show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    //thai
    private void processrequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    //    BT: showSlider
    private void showSlider() {
        sliderView = findViewById(R.id.linearLayout2);

        showSlider_adaper showSliderAdaper = new showSlider_adaper(images_slider);

        sliderView.setSliderAdapter(showSliderAdaper);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


    }

    //Trung Tìm kiếm tên
    private void timKiemTen(ArrayList<model_SanPham> arrlSanPham, String value) {
        if (!value.equals("")) {
            ArrayList<model_SanPham> arrListSanPhamTimKiem = new ArrayList<>();
            for (int i = 0; i < arrlSanPham.size(); i = i + 1) {
                String valueArr = (arrlSanPham.get(i).getTenSanPham());
                String valueTimKiem = value;
                if (valueArr.contains(valueTimKiem)) {
                    arrListSanPhamTimKiem.add(arrlSanPham.get(i));
                }
            }

            TrangChuSanPham_tv_loai.setText("/" + arrListSanPhamTimKiem.size() + "SP/TÊN");
            showListProduc_Vartical(arrListSanPhamTimKiem);
        }

    }

    //Trung Tìm kiếm giâ
    private void timKiemGia(ArrayList<model_SanPham> arrlSanPham, String value) {

        if (!value.equals("")) {
            ArrayList<model_SanPham> arrListSanPhamTimKiem = new ArrayList<>();
            for (int i = 0; i < arrlSanPham.size(); i = i + 1) {
                String valueArr = String.valueOf(arrlSanPham.get(i).getGiaBanSanPham());
                String valueTimKiem = value.toUpperCase().toString();
                if (valueArr.contains(valueTimKiem)) {

                    arrListSanPhamTimKiem.add(arrlSanPham.get(i));
                }
            }

            TrangChuSanPham_tv_loai.setText("/" + arrListSanPhamTimKiem.size() + "SP/GIÁ");
            showListProduc_Vartical(arrListSanPhamTimKiem);
        }


    }

    //Trung Tìm kiếm loại
    private void timKiemLoai(ArrayList<model_SanPham> arrlSanPham, String value) {
        if (!value.equals("")) {
            ArrayList<model_SanPham> arrListSanPhamTimKiem = new ArrayList<>();
            for (int i = 0; i < arrlSanPham.size(); i = i + 1) {
                String valueArr = String.valueOf(arrlSanPham.get(i).getLoaiSanPham());
                String valueTimKiem = value.toUpperCase().toString();
                if (valueArr.contains(valueTimKiem)) {

                    arrListSanPhamTimKiem.add(arrlSanPham.get(i));
                }
            }
            TrangChuSanPham_tv_loai.setText("/" + arrListSanPhamTimKiem.size() + "SP/LOẠI");
            showListProduc_Vartical(arrListSanPhamTimKiem);
        }


    }

    //Trung Tìm kiếm sản phẩm
    private void timKiem() {

        //        Chon tìm loại tên
        if (timkiem == 0) {
            TrangChuSanPham_edt_timKiemSanPham.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    String giaTriTimKiem = TrangChuSanPham_edt_timKiemSanPham.getText().toString();
                    if (giaTriTimKiem.isEmpty()) {
                        TrangChuSanPham_edt_timKiemSanPham.setText("");
                        showListProduc_Vartical(arrListSanPham);
                        TrangChuSanPham_tv_loai.setText("/" + arrListSanPham.size() + "SP/TÊN");

                    } else {
                        timKiemTen(arrListSanPham, giaTriTimKiem);
                    }

                    return false;
                }
            });
        } else if (timkiem == 1) {
            TrangChuSanPham_edt_timKiemSanPham.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    String giaTriTimKiem = TrangChuSanPham_edt_timKiemSanPham.getText().toString();
                    if (giaTriTimKiem.isEmpty()) {
                        TrangChuSanPham_edt_timKiemSanPham.setText("");
                        showListProduc_Vartical(arrListSanPham);
                        Toast.makeText(trangChu_SanPham_Activity.this, arrListYeuThich + "", Toast.LENGTH_SHORT).show();

                        TrangChuSanPham_tv_loai.setText("/" + arrListSanPham.size() + "SP/GIÁ");

                    } else {
                        timKiemGia(arrListSanPham, giaTriTimKiem);
                    }

                    return false;
                }
            });
        } else if (timkiem == 2) {
            TrangChuSanPham_edt_timKiemSanPham.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    String giaTriTimKiem = TrangChuSanPham_edt_timKiemSanPham.getText().toString();
                    if (giaTriTimKiem.isEmpty()) {
                        TrangChuSanPham_edt_timKiemSanPham.setText("");
//                        showListProduc_Vartical(arrListSanPham);
                        Toast.makeText(trangChu_SanPham_Activity.this, arrListYeuThich + "", Toast.LENGTH_SHORT).show();

                        TrangChuSanPham_tv_loai.setText("/" + arrListSanPham.size() + "SP/LOẠI");

                    } else {
                        timKiemLoai(arrListSanPham, giaTriTimKiem);
                    }
                    return false;
                }
            });
        }
    }

    //    Bắt sự kiện thi thao tác
    private void batSuKien() {
// bắt sự kiện chuyển trang thông tin cá nhân
        TrangChuSanPham_Flout_btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shareAcout.getString("IDUSRE", "").isEmpty()) {
                    Toast.makeText(trangChu_SanPham_Activity.this, "ban chua login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(trangChu_SanPham_Activity.this, Login_Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(trangChu_SanPham_Activity.this,thongTinTaiKhoan_Activity.class);
                    startActivity(intent);}
            }
        });
//         bắt sự kiện chuyển trang đơn hàngủa tôi
        TrangChuSanPham_Flout_btn_donHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shareAcout.getString("IDUSRE", "").isEmpty()) {
                    Toast.makeText(trangChu_SanPham_Activity.this, "ban chua login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(trangChu_SanPham_Activity.this, Login_Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(trangChu_SanPham_Activity.this,donHangUser_Activity.class);
                    startActivity(intent);}
            }
        });

// show menu FloatBtn
        TrangChuSanPham_FloatBtn_nemu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkMenu == false) {
                    Animation animationout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom);

                    checkMenu = true;
                    TrangChuSanPham_Flout_btn_user.setVisibility(View.VISIBLE);
                    TrangChuSanPham_Flout_btn_donHang.setVisibility(View.VISIBLE);
                    TrangChuSanPham_Flout_btn_user.setAnimation(animationout);
                    TrangChuSanPham_Flout_btn_donHang.setAnimation(animationout);
                }else{
                    Animation animationout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_bottom);

                    checkMenu = false;
                    TrangChuSanPham_Flout_btn_user.setVisibility(View.INVISIBLE);
                    TrangChuSanPham_Flout_btn_donHang.setVisibility(View.INVISIBLE);
                    TrangChuSanPham_Flout_btn_user.setAnimation(animationout);
                    TrangChuSanPham_Flout_btn_donHang.setAnimation(animationout);
                }
            }
        });
//        mở giỏ hàng
        TrangChuSanPham_img_btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("IDDANHSACHYEUTHICH", "").isEmpty()) {
                    Toast.makeText(trangChu_SanPham_Activity.this, "Bạn chưa đăng nhập!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(trangChu_SanPham_Activity.this, Login_Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(trangChu_SanPham_Activity.this, gio_Hang_Activity.class);
                    startActivity(intent);
                }
            }
        });
//        bắt sự kiện tìm kiếm
        TrangChuSanPham_edt_timKiemSanPham.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                timKiem();
                return false;
            }
        });
        TrangChuSanPham_tv_btn_timTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timkiem = 0;
                TrangChuSanPham_tv_loai.setText("/TÊN");
                TrangChuSanPham_edt_timKiemSanPham.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT); //for decimal numbers

                TrangChuSanPham_tv_btn_timTen.setTextColor(Color.parseColor("#F0A23C"));
                TrangChuSanPham_tv_btn_timGia.setTextColor(Color.parseColor("#BCBCBC"));
                TrangChuSanPham_tv_btn_timLoai.setTextColor(Color.parseColor("#BCBCBC"));
                TrangChuSanPham_llout_formChonLoai.setVisibility(View.INVISIBLE);
                TrangChuSanPham_tv_loai.setVisibility(View.VISIBLE);
                timKiem();
            }
        });
//        Chọn tìm loại giá
        TrangChuSanPham_tv_btn_timGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TrangChuSanPham_edt_timKiemSanPham.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //for decimal numbers

                timkiem = 1;
                TrangChuSanPham_tv_loai.setText("/GIÁ");
                TrangChuSanPham_tv_btn_timTen.setTextColor(Color.parseColor("#BCBCBC"));
                TrangChuSanPham_tv_btn_timGia.setTextColor(Color.parseColor("#F0A23C"));
                TrangChuSanPham_tv_btn_timLoai.setTextColor(Color.parseColor("#BCBCBC"));
                TrangChuSanPham_llout_formChonLoai.setVisibility(View.INVISIBLE);
                TrangChuSanPham_tv_loai.setVisibility(View.VISIBLE);

                timKiem();
            }
        });
//        Chọn tìm loại Loại
        TrangChuSanPham_tv_btn_timLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrangChuSanPham_edt_timKiemSanPham.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT); //for decimal numbers

                timkiem = 2;
                TrangChuSanPham_tv_loai.setText("/LOẠI");
                TrangChuSanPham_tv_btn_timTen.setTextColor(Color.parseColor("#BCBCBC"));
                TrangChuSanPham_tv_btn_timGia.setTextColor(Color.parseColor("#BCBCBC"));
                TrangChuSanPham_tv_btn_timLoai.setTextColor(Color.parseColor("#F0A23C"));
                TrangChuSanPham_llout_formChonLoai.setVisibility(View.INVISIBLE);
                TrangChuSanPham_tv_loai.setVisibility(View.VISIBLE);
                timKiem();


            }
        });
//   show form chọn loại sản phẩm
        TrangChuSanPham_img_btn_kieuLoaiSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TrangChuSanPham_llout_formChonLoai.getVisibility() == View.VISIBLE) {
//                    khi tắt
                    TrangChuSanPham_llout_formChonLoai.setVisibility(View.INVISIBLE);
                    TrangChuSanPham_tv_loai.setVisibility(View.VISIBLE);
                } else {
//                    khi mở lên
                    TrangChuSanPham_llout_formChonLoai.setVisibility(View.VISIBLE);
                    TrangChuSanPham_tv_loai.setVisibility(View.INVISIBLE);
                }


            }
        });
//   Loại sản phẩm
//        loại coffe
        TrangChuSanPham_img_showLoaiCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setTextColor(Color.parseColor("#F9B864"));

                TrangChuSanPham_img_showLoaiCoffee.setBackgroundResource(R.drawable.broder_radius_cam2_thuonghieu);
                TrangChuSanPham_img_showLoaiCoffee.setImageResource(R.drawable.ic_coffe_white);

                TrangChuSanPham_img_showLoaiThaoDuoc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiThaoDuoc.setImageResource(R.drawable.ic_herb);

                TrangChuSanPham_img_showLoaiHat.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiHat.setImageResource(R.drawable.ic_seed);

                TrangChuSanPham_img_showLoaiMuc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiMuc.setImageResource(R.drawable.ic_jam);

                TrangChuSanPham_img_showLoaiTra.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiTra.setImageResource(R.drawable.ic_tea);
                showListProduc_Vartical(kiemTraLoaiSanPham("Cà phê", arrListSanPham));
                Toast.makeText(trangChu_SanPham_Activity.this, arrListYeuThich + "", Toast.LENGTH_SHORT).show();

                TrangChuSanPham_tv_showLoai.setText("Loại: COFFE");
            }
        });
//        loại thảo dược
        TrangChuSanPham_img_showLoaiThaoDuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();

                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setTextColor(Color.parseColor("#F9B864"));

                TrangChuSanPham_img_showLoaiCoffee.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiCoffee.setImageResource(R.drawable.ic_coffee);

                TrangChuSanPham_img_showLoaiThaoDuoc.setBackgroundResource(R.drawable.broder_radius_cam2_thuonghieu);
                TrangChuSanPham_img_showLoaiThaoDuoc.setImageResource(R.drawable.ic_herb_white);

                TrangChuSanPham_img_showLoaiHat.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiHat.setImageResource(R.drawable.ic_seed);

                TrangChuSanPham_img_showLoaiMuc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiMuc.setImageResource(R.drawable.ic_jam);

                TrangChuSanPham_img_showLoaiTra.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiTra.setImageResource(R.drawable.ic_tea);
                showListProduc_Vartical(kiemTraLoaiSanPham("Thảo mộc", arrListSanPham));
                Toast.makeText(trangChu_SanPham_Activity.this, arrListYeuThich + "", Toast.LENGTH_SHORT).show();
                TrangChuSanPham_tv_showLoai.setText("Loại: THẢO MỘC");

            }
        });
//        Loại hạt
        TrangChuSanPham_img_showLoaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();

                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setTextColor(Color.parseColor("#F9B864"));

                TrangChuSanPham_img_showLoaiCoffee.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiCoffee.setImageResource(R.drawable.ic_coffee);

                TrangChuSanPham_img_showLoaiThaoDuoc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiThaoDuoc.setImageResource(R.drawable.ic_herb);

                TrangChuSanPham_img_showLoaiHat.setBackgroundResource(R.drawable.broder_radius_cam2_thuonghieu);
                TrangChuSanPham_img_showLoaiHat.setImageResource(R.drawable.ic_seeds_white);

                TrangChuSanPham_img_showLoaiMuc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiMuc.setImageResource(R.drawable.ic_jam);

                TrangChuSanPham_img_showLoaiTra.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiTra.setImageResource(R.drawable.ic_tea);
                showListProduc_Vartical(kiemTraLoaiSanPham("Hạt đặc sản", arrListSanPham));
                Toast.makeText(trangChu_SanPham_Activity.this, arrListYeuThich + "", Toast.LENGTH_SHORT).show();

                TrangChuSanPham_tv_showLoai.setText("Loại: HẠT");
            }
        });
//        Loại mức
        TrangChuSanPham_img_showLoaiMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();

                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setTextColor(Color.parseColor("#F9B864"));

                TrangChuSanPham_img_showLoaiCoffee.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiCoffee.setImageResource(R.drawable.ic_coffee);

                TrangChuSanPham_img_showLoaiThaoDuoc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiThaoDuoc.setImageResource(R.drawable.ic_herb);

                TrangChuSanPham_img_showLoaiHat.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiHat.setImageResource(R.drawable.ic_seed);

                TrangChuSanPham_img_showLoaiMuc.setBackgroundResource(R.drawable.broder_radius_cam2_thuonghieu);
                TrangChuSanPham_img_showLoaiMuc.setImageResource(R.drawable.ic_jam_white);

                TrangChuSanPham_img_showLoaiTra.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiTra.setImageResource(R.drawable.ic_tea);
                showListProduc_Vartical(kiemTraLoaiSanPham("Trái cây xấy dẻo", arrListSanPham));
                Toast.makeText(trangChu_SanPham_Activity.this, arrListYeuThich + "", Toast.LENGTH_SHORT).show();


                TrangChuSanPham_tv_showLoai.setText("Loại: MỨC");

            }
        });
//        Loại trà
        TrangChuSanPham_img_showLoaiTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();

                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setTextColor(Color.parseColor("#F9B864"));

                TrangChuSanPham_img_showLoaiCoffee.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiCoffee.setImageResource(R.drawable.ic_coffee);

                TrangChuSanPham_img_showLoaiThaoDuoc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiThaoDuoc.setImageResource(R.drawable.ic_herb);

                TrangChuSanPham_img_showLoaiHat.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiHat.setImageResource(R.drawable.ic_seed);

                TrangChuSanPham_img_showLoaiMuc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiMuc.setImageResource(R.drawable.ic_jam);

                TrangChuSanPham_img_showLoaiTra.setBackgroundResource(R.drawable.broder_radius_cam2_thuonghieu);
                TrangChuSanPham_img_showLoaiTra.setImageResource(R.drawable.ic_tea_white);


                showListProduc_Vartical(kiemTraLoaiSanPham("Trà túi lọc", arrListSanPham));
                Toast.makeText(trangChu_SanPham_Activity.this, arrListYeuThich + "", Toast.LENGTH_SHORT).show();
                TrangChuSanPham_tv_showLoai.setText("Loại: TEA");
            }
        });
//        tất cả sản phẩm
        TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setBackgroundResource(R.drawable.broder_radius_cam2_thuonghieu);
                TrangChuSanPham_tv_btn_showTatCaLoaiSanpham.setTextColor(Color.parseColor("#EEF5FF"));

                TrangChuSanPham_img_showLoaiCoffee.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiCoffee.setImageResource(R.drawable.ic_coffee);

                TrangChuSanPham_img_showLoaiThaoDuoc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiThaoDuoc.setImageResource(R.drawable.ic_herb);

                TrangChuSanPham_img_showLoaiHat.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiHat.setImageResource(R.drawable.ic_seed);

                TrangChuSanPham_img_showLoaiMuc.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiMuc.setImageResource(R.drawable.ic_jam);

                TrangChuSanPham_img_showLoaiTra.setBackgroundResource(R.drawable.broder_radius_xanhduong_nhe);
                TrangChuSanPham_img_showLoaiTra.setImageResource(R.drawable.ic_tea);
                showListProduc_Vartical(arrListSanPham);
                Toast.makeText(trangChu_SanPham_Activity.this, arrListYeuThich + "", Toast.LENGTH_SHORT).show();

                TrangChuSanPham_tv_showLoai.setText("Sản phẩm: ALL");
            }
        });
    }

    //  Trung Kiểm tra loại sản phẩm
    private ArrayList<model_SanPham> kiemTraLoaiSanPham(String loaiSP, ArrayList<model_SanPham> arrListSP) {
        ArrayList<model_SanPham> arrListLoaiSanPham = new ArrayList<>();
        for (int i = 0; i < arrListSP.size(); i = i + 1) {
            if (loaiSP.equals(arrListSP.get(i).getLoaiSanPham())) {
                arrListLoaiSanPham.add(arrListSP.get(i));
            }
        }
        return arrListLoaiSanPham;
    }

//    Trung Show sản phẩm Adapter

    /********************Show thông tin ra kiểu ngang**********************/
    private void showListProduc_Horizoltal() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(trangChu_SanPham_Activity.this, LinearLayoutManager.HORIZONTAL, false);
        TrangChuSanPham_rscV_showSanPhamNgang.setLayoutManager(linearLayoutManager);
        TrangChuSanPham_rscV_showSanPhamNgang.setItemAnimator(new DefaultItemAnimator());
//        Initilize
        TrangChu_showNgang_adapter = new trangChu_showNgang_adapter(arrListSanPham, this);

        TrangChuSanPham_rscV_showSanPhamNgang.setAdapter(TrangChu_showNgang_adapter);
    }


    /********************Show thông tin ra kiểu dọc**********************/
    private void showListProduc_Vartical(ArrayList<model_SanPham> arrListSp) {
        TrangChuSanPham_rscV_showSanPhamDoc.setLayoutManager(gridLayoutManager);
        TrangChuSanPham_rscV_showSanPhamDoc.setItemAnimator(new DefaultItemAnimator());
        //        Initilize


        TrangChu_showDoc_adapter = new trangChu_showDoc_adapter(arrListSp, trangChu_SanPham_Activity.this, arrListYeuThich, new trangChu_showDoc_adapter.IClickListener() {
            @Override
            public void onClickShowItem(model_SanPham sanPham) {
                showItemChiTietSanPham(sanPham);
            }
        });


        TrangChuSanPham_rscV_showSanPhamDoc.setAdapter(TrangChu_showDoc_adapter);

    }


    public void setNotifyitem(int viTri) {
        showListProduc_Horizoltal();
        TrangChu_showNgang_adapter.notifyDataSetChanged();
        TrangChu_showDoc_adapter.notifyItemChanged(viTri);
    }

    // chọn hủy yêu thích
    public void onClicktHeartItemDelete(String idYeuThich, int viTri, boolean loai) {

        dataRef = database.getReference("danhSachSanPhamYeuThich").child(sharedPreferences.getString("IDDANHSACHYEUTHICH", "")).child(idYeuThich);
        dataRef.removeValue();

    }

    // chon yêu thích
    public void onClickHeartItem(String idSanPham, int viTri, boolean loai) {
        Toast.makeText(this, arrListYeuThich.size() + "", Toast.LENGTH_SHORT).show();
        String idYeuThich;
        UUID uuid = UUID.randomUUID();
        idYeuThich = String.valueOf(uuid);
        model_yeuThich yeuThich = new model_yeuThich(idSanPham, idYeuThich, true);
        dataRef = database.getReference("danhSachSanPhamYeuThich");
        dataRef.child(sharedPreferences.getString("IDDANHSACHYEUTHICH", "")).child(idYeuThich).setValue(yeuThich);

    }
    //thai phân trang
    private void getDataFirebase() {
        arrListSanPhamPhanTrang=getmListPost();
        showListProduc_Vartical(arrListSanPhamPhanTrang);
        Toast.makeText(this, arrListSanPham.size()+"", Toast.LENGTH_SHORT).show();
        if (currentpage<totalPage){
//            Adapter_SanPham_Kho.addFoodterLoading();
            TrangChu_showDoc_adapter.addFoodterLoading();
        }else {
            isLastPage=true;
        }
        loadData();


    }
    private void loadData() {
        TrangChuSanPham_rscV_showSanPhamDoc.addOnScrollListener(new paginationScrollListener(gridLayoutManager) {
            @Override
            public void loadMoreItem() {
                isLoading=true;
                currentpage+=1;
                loadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
    }
    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<model_SanPham>list=getmListPost();

                TrangChu_showDoc_adapter.removeFoodterLoading();
                arrListSanPhamPhanTrang.addAll(list);
                TrangChu_showDoc_adapter.notifyDataSetChanged();

                isLoading=false;
                if (khoangDem<totalPage){
                    TrangChu_showDoc_adapter.addFoodterLoading();
                    khoangDem=khoangDem+10;

                }else {
                    isLastPage=true;

                }
            }
        },2000);
    }
    private ArrayList<model_SanPham> getmListPost(){
        ArrayList<model_SanPham> list=new ArrayList<>();
        if (arrListSanPham.size()>=khoangDem){
            do {
                dem ++;
                if (dem<arrListSanPham.size()){

                    list.add(arrListSanPham.get(dem));
                }
            }while(dem<= khoangDem);

        }

        return list;
    }
    //Trung: lấy dữ liệu sản phẩm trên firebase về
    private void getDataRealtimeDatabase() {

        dataRef = database.getReference().child("khoHang");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrListSanPham.clear();
//                Chạy vòng lặp kiểm tra dữ liệu
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrSanPham = child.getValue(model_SanPham.class);
                    arrListSanPham.add(arrSanPham);
                }
//                showListProduc_Vartical(arrListSanPham);
                showListProduc_Horizoltal();
                getDataFirebase();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    //    BToàn: show menu Drawer
    private void NavigationDrawer() {
        /*           NavigationView Drawer Menu           */

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, TrangChuSanPham_drawerllout_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        TrangChuSanPham_drawerllout_main.addDrawerListener(toggle);
        toggle.syncState();

        TrangChuSanPham_nav_drawer.setNavigationItemSelectedListener(trangChu_SanPham_Activity.this);
        TrangChuSanPham_nav_drawer.bringToFront();
        TrangChuSanPham_nav_drawer.setCheckedItem(R.id.drawer_nav_home);
        /*           Phần quyền đăng nhập           */

        Menu menu = TrangChuSanPham_nav_drawer.getMenu();
//        menu.findItem(R.id.drawer_nav_profile).setVisible(false);
//        try {

        if (shareAcout.getString("IDUSRE", "").isEmpty()) {
            menu.findItem(R.id.drawer_nav_logout).setVisible(false);
        } else {
            menu.findItem(R.id.drawer_nav_login).setVisible(false);
        }
        if (!shareAcout.equals("")) {
            dataRef = database.getReference("Accounts").child(idSharePre);
            dataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    CircleImageView trangChuSanPham_img_logo = (CircleImageView) TrangChuSanPham_nav_drawer.getHeaderView(0).findViewById(R.id.trangChuSanPham_img_logo);
                    trangChuSanPham_img_logo.setImageResource(R.drawable.man);
                    model_Account account = snapshot.getValue(model_Account.class);
                    try {
                        if (!account.getAnhKhachHang().equals("")) {
                            Glide.with(trangChu_SanPham_Activity.this)
                                    .load(account.getAnhKhachHang())
                                    .into(trangChuSanPham_img_logo);
                        }
                    } catch (Exception e) {

                    }


                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
//        } catch (Exception e) {

//
//        }
        TrangChuSanPham_img_showMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TrangChuSanPham_drawerllout_main.isDrawerVisible(GravityCompat.START))
                    TrangChuSanPham_drawerllout_main.closeDrawer(GravityCompat.START);
                else TrangChuSanPham_drawerllout_main.openDrawer(GravityCompat.START);
            }
        });

    }


    //thai fix lai drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            //item chuc nang cua app

            case R.id.drawer_nav_home:
                startActivity(new Intent(getApplicationContext(), trangChu_SanPham_Activity.class));
                break;

            case R.id.drawer_nav_like:
                if (shareAcout.getString("IDUSRE", "").isEmpty()) {
                    Toast.makeText(this, "ban chua login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(trangChu_SanPham_Activity.this, Login_Activity.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getApplicationContext(), favorite_Activity.class));
                }
                break;

            case R.id.drawer_nav_cart:
                if (shareAcout.getString("IDUSRE", "").isEmpty()) {
                    Toast.makeText(this, "ban chua login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(trangChu_SanPham_Activity.this, Login_Activity.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getApplicationContext(), gio_Hang_Activity.class));
                }
                break;

            case R.id.drawer_nav_gioHang:
                if (shareAcout.getString("IDUSRE", "").isEmpty()) {
                    Toast.makeText(this, "ban chua login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(trangChu_SanPham_Activity.this, Login_Activity.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getApplicationContext(), donHangUser_Activity.class));
                }
                //item profile
                break;

            case R.id.drawer_nav_login:
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                break;
            case R.id.drawer_nav_profile:
                if (shareAcout.getString("IDUSRE", "").isEmpty()) {

                    ImageView item_dialog_chucNang_img_imgErro = dialogLoading.findViewById(R.id.item_dialog_chucNang_img_imgErro);
                    TextView item_dialog_chucNang_txt_nameErro = dialogLoading.findViewById(R.id.item_dialog_chucNang_txt_nameErro);
                    Button Okay = dialogLoading.findViewById(R.id.btn_okay);
                    Button Cancel = dialogLoading.findViewById(R.id.btn_cancel);
                    //setText Item
                    Okay.setText("Đăng nhập");
                    item_dialog_chucNang_img_imgErro.setImageResource(R.drawable.question);
                    item_dialog_chucNang_txt_nameErro.setText("Bạn chưa đăng nhập vào ứng dụng này?");
                    Okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(trangChu_SanPham_Activity.this, "ban chua login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(trangChu_SanPham_Activity.this, Login_Activity.class);
                            startActivity(intent);
                            dialogLoading.dismiss();
                        }
                    });

                    Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogLoading.dismiss();
                        }
                    });
                    dialogLoading.show();


                } else {
                    Intent intent3 = new Intent(trangChu_SanPham_Activity.this, thongTinTaiKhoan_Activity.class);
                    startActivity(intent3);
                }

                break;

            case R.id.drawer_nav_logout:
                ImageView item_dialog_chucNang_img_imgErro = dialogLoading.findViewById(R.id.item_dialog_chucNang_img_imgErro);
                TextView item_dialog_chucNang_txt_nameErro = dialogLoading.findViewById(R.id.item_dialog_chucNang_txt_nameErro);
                Button Okay = dialogLoading.findViewById(R.id.btn_okay);
                Button Cancel = dialogLoading.findViewById(R.id.btn_cancel);
                //setText Item
                Okay.setText("Đăng xuất");
                item_dialog_chucNang_img_imgErro.setImageResource(R.drawable.question);
                item_dialog_chucNang_txt_nameErro.setText("Bạn có muốn đăng xuất không?");
                Okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog1 = new Dialog(trangChu_SanPham_Activity.this);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setContentView(R.layout.item_login);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.clear();
                                editor.commit();
                                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), Sin_Up_Activity.class));
                                finish();
                                Intent intent2 = new Intent(trangChu_SanPham_Activity.this, Login_Activity.class);
                                startActivity(intent2);
                                dialog1.dismiss();


                            }
                        }, 3000);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.show();
                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLoading.dismiss();
                    }
                });
                dialogLoading.show();


                break;


            //chuc nang khac
            case R.id.drawer_nav_rate:

                break;

            case R.id.drawer_nav_share:

                break;
        }
        TrangChuSanPham_drawerllout_main.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (TrangChuSanPham_drawerllout_main.isDrawerVisible(GravityCompat.START)) {
            TrangChuSanPham_drawerllout_main.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    //thai:onClickItemSanPham
    public void showItemChiTietSanPham(model_SanPham sanPham) {
        anhXa();
        getDataFirebaseCart();
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dat_nhanh);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

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

        Glide.with(trangChu_SanPham_Activity.this)
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

        //giam so luong san pham

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
                if (sharedPreferences.getString("IDDANHSACHYEUTHICH", "").isEmpty()) {
                    Toast.makeText(trangChu_SanPham_Activity.this, "Bạn chưa đăng nhập!!!", Toast.LENGTH_SHORT).show();
                } else {
                    dataRef = database.getReference("Accounts").child(idSharePre);
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
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();

    }

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
                TrangChuSanPham_tv_soLuongThongBao.setText(arrListCart.size() + "");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    //     Ánh xạ
    private void anhXa() {

        gridLayoutManager=new GridLayoutManager(this,2);
        arrListSanPhamPhanTrang=new ArrayList<>();
//        SharedPreferences
        dialogLoading = new Dialog(trangChu_SanPham_Activity.this);
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLoading.setContentView(R.layout.item_dialog_chucnang_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogLoading.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialogLoading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogLoading.setCancelable(false); //Optional
        dialogLoading.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        diaLog = new Dialog(trangChu_SanPham_Activity.this);
        diaLog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        diaLog.setContentView(R.layout.item_login);
        diaLog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        shareAcout = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        idSharePre = shareAcout.getString("IDUSRE", "");
        idGioHangShare = shareAcout.getString("IDGIOHANG", "");

//        model
        arrListSanPham = new ArrayList<model_SanPham>();
        arrListCart = new ArrayList<model_Cart>();
        model_cartArrayList = new ArrayList<>();
        arrListYeuThich = new ArrayList<model_yeuThich>();
        //Firebase
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");
        //      ImageView
        TrangChuSanPham_img_showMenu = findViewById(R.id.trangChuSanPham_img_showMenu);
        TrangChuSanPham_img_btnGioHang = findViewById(R.id.trangChuSanPham_img_btnGioHang);
        TrangChuSanPham_img_btn_kieuLoaiSanPham = findViewById(R.id.trangChuSanPham_img_btn_kieuLoaiSanPham);
        TrangChuSanPham_img_showLoaiCoffee = findViewById(R.id.trangChuSanPham_img_showLoaiCoffee);
        TrangChuSanPham_img_showLoaiThaoDuoc = findViewById(R.id.trangChuSanPham_img_showLoaiThaoDuoc);
        TrangChuSanPham_img_showLoaiHat = findViewById(R.id.trangChuSanPham_img_showLoaiHat);
        TrangChuSanPham_img_showLoaiMuc = findViewById(R.id.trangChuSanPham_img_showLoaiMuc);
        TrangChuSanPham_img_showLoaiTra = findViewById(R.id.trangChuSanPham_img_showLoaiTra);
//FrameLayout
//        SanPham_flou_search = findViewById(R.id.sanPham_flou_search);
        //      TextView
        TrangChuSanPham_tv_soLuongThongBao = findViewById(R.id.trangChuSanPham_tv_soLuongThongBao);
        TrangChuSanPham_tv_btn_showTatCaLoaiSanpham = findViewById(R.id.trangChuSanPham_tv_btn_showTatCaLoaiSanpham);
        TrangChuSanPham_tv_showLoai = findViewById(R.id.trangChuSanPham_tv_showLoai);
        TrangChuSanPham_tv_btn_timTen = findViewById(R.id.trangChuSanPham_tv_btn_timTen);
        TrangChuSanPham_tv_btn_timGia = findViewById(R.id.trangChuSanPham_tv_btn_timGia);
        TrangChuSanPham_tv_btn_timLoai = findViewById(R.id.trangChuSanPham_tv_btn_timLoai);
        TrangChuSanPham_tv_loai = findViewById(R.id.trangChuSanPham_tv_loai);

        //      ecyclerView
        TrangChuSanPham_rscV_showSanPhamNgang = findViewById(R.id.trangChuSanPham_rscV_showSanPhamNgang);
        TrangChuSanPham_rscV_showSanPhamDoc = findViewById(R.id.trangChuSanPham_rscV_showSanPhamDoc);
        //        DrawerLayout
        TrangChuSanPham_drawerllout_main = findViewById(R.id.trangChuSanPham_drawerllout_main);
        //        LinearLayout
        TrangChuSanPham_llout_formChonLoai = findViewById(R.id.trangChuSanPham_llout_formChonLoai);
        //        NavigationView
        TrangChuSanPham_nav_drawer = findViewById(R.id.trangChuSanPham_nav_drawer);
        //  EditText
        TrangChuSanPham_edt_timKiemSanPham = findViewById(R.id.trangChuSanPham_edt_timKiemSanPham);

        DatNhanh_FmLt_showChiTietSanPham = findViewById(R.id.datNhanh_FmLt_showChiTietSanPham);
//        CircleImageView

        TrangChuSanPham_img_logo = findViewById(trangChuSanPham_img_logo);
        //        Button
//        FrameLayout

        TrangChuSanPham_Flout_btn_user = findViewById(R.id.trangChuSanPham_Flout_btn_user);
        TrangChuSanPham_Flout_btn_donHang = findViewById(R.id.trangChuSanPham_Flout_btn_donHang);
//                FloatingActionButton
        TrangChuSanPham_FloatBtn_nemu = findViewById(R.id.trangChuSanPham_FloatBtn_nemu);
    }

}

