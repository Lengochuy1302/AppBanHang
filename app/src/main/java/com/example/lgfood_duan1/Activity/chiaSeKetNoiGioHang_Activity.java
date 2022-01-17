package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.lgfood_duan1.Model.model_Cart;
import com.example.lgfood_duan1.Model.model_viTri;
import com.example.lgfood_duan1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class chiaSeKetNoiGioHang_Activity extends AppCompatActivity {
    private LottieAnimationView
            ChiaSeKetNoi_anim_connecting;
    private LinearLayout
            ChiaSeKetNoi_llout_btn_dungPhatTinHieu;
    private ImageView
            ChiaSeKetNoi_img_btn_back;
    private TextView ChiaSeKetNoi_tv_key;

    //    firebase
    private DatabaseReference dataRef;
    private FirebaseDatabase database;
    //    value
    private String idGioHang, idGioHangTam, idViTri;
    //    Sharepre
    private SharedPreferences shareAcout;
    //model arayliss
    private model_viTri arrViTri;
    private model_Cart arrCart, arrCartTam;
    private ArrayList<model_viTri> arrListViTri;
    private ArrayList<model_Cart> arrListCart, arrayListCartTam;
    Dialog dialog2;
    @Override
    protected void onStop() {
        dataRef = database.getReference("location").child(idViTri);
        dataRef.child("tinhTrang").setValue(false);
        super.onStop();
    }

    @Override
    protected void onStart() {
        dataRef = database.getReference("location").child(idViTri);
        dataRef.child("tinhTrang").setValue(true);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chia_se_ket_noi_gio_hang);
        anhXa();
        getSharedPre();
        batSuKien();
        getDataOnChangeGioHangTam();

    }


    // bắt sự kiện nếu giỏ hàng tạm có thay đổi
// harePre
    private void getSharedPre() {
        shareAcout = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        idGioHang = shareAcout.getString("IDGIOHANG", "");
        idGioHangTam = shareAcout.getString("IDGIOHANGTAM", "");
        idViTri = shareAcout.getString("IDVITRI", "");

    }

    //Trung dialog sửa sản phẩm
    private void openDialogUpdate() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cuttom_capnhatsanpham);
//        Window window = dialog.getWindow();
//        if (window == null) {
//            return;
//        }
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = dialog.getWindow().getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(windowAttributes);
//        sử lý khi nhấn ra ngoài thì có thoát hay không
        dialog.setCancelable(false);
//        khai báo bắt sự kiện
        TextView Dialog_them_tvBtn_khong = dialog.findViewById(R.id.dialog_them_tvBtn_khong);
        TextView Dialog_them_tvBtn_dongY = dialog.findViewById(R.id.dialog_them_tvBtn_dongY);

//        tắt dialog đi
        Dialog_them_tvBtn_khong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataRef = database.getReference("gioHangTam").child(idGioHangTam);
                dataRef.removeValue();
                dialog.dismiss();


            }
        });
        Dialog_them_tvBtn_dongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog diaLog1 = new Dialog(chiaSeKetNoiGioHang_Activity.this);
                diaLog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                diaLog1.setContentView(R.layout.item_login);
                diaLog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        ImageView item_dialog_chucNang_img_imgErro=dialog2.findViewById(R.id.item_dialog_chucNang_img_imgErro);
                        TextView item_dialog_chucNang_txt_nameErro=dialog2.findViewById(R.id.item_dialog_chucNang_txt_nameErro);
                        Button Okay = dialog2.findViewById(R.id.btn_okay);
                        Button Cancel = dialog2.findViewById(R.id.btn_cancel);
                        Okay.setText("Đồng ý");
                        item_dialog_chucNang_img_imgErro.setImageResource(R.drawable.question);
                        item_dialog_chucNang_txt_nameErro.setText("Bạn có muốn giữ giỏ hàng cũ không?");
                        Okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(chiaSeKetNoiGioHang_Activity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                                //             thêm vào giỏ hàng chính
                                dataRef = database.getReference("GioHangs").child(idGioHang);
                                setDataChiaSeGioHang();
                                Intent intent = new Intent(chiaSeKetNoiGioHang_Activity.this, gio_Hang_Activity.class);
                                startActivity(intent);
                                dialog2.dismiss();
                                //                xóa giỏ hàng tạm
                                dataRef = database.getReference("gioHangTam").child(idGioHangTam);
                                dataRef.removeValue();
                            }
                        });

                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                                dataRef = database.getReference("GioHangs").child(idGioHang);
                                dataRef.removeValue();
                                setDataChiaSeGioHang();
                                dataRef=database.getReference("newCarts").child(idGioHang);
                                dataRef.removeValue();
                                Intent intent = new Intent(chiaSeKetNoiGioHang_Activity.this, gio_Hang_Activity.class);
                                startActivity(intent);
                                //                xóa giỏ hàng tạm
                                dataRef = database.getReference("gioHangTam").child(idGioHangTam);
                                dataRef.removeValue();
                            }
                        });

                        dialog2.show();
                        diaLog1.dismiss();



                    }
                },1000);
                diaLog1.show();

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // khi đồng ý thì bắt đầu add sản phẩm từ giỏ hàng tạm được chia sẻ qua giỏ hàng chính khi xong thì xóa giỏ hàng tạm đi
    private void setDataChiaSeGioHang() {
        dataRef = database.getReference("GioHangs");
        for (int i = 0; i < arrayListCartTam.size(); i++) {
            arrCart = new model_Cart(UUID.randomUUID().toString(), arrayListCartTam.get(i).getIdSanPham(), arrayListCartTam.get(i).getSoLuong());
            dataRef.child(idGioHang).child(arrCart.getIdGioHang()).setValue(arrCart);
        }
    }

    //bắt sự kiện nếu như giỏ hàng tạm của user có thay đổi thì bắt sự kiện có sản phẩm được chia sẻ
    private void getDataOnChangeGioHangTam() {
//        bắt sự kiện tại giỏ hàng của mình nếu như mà có sự thay đổi thì hiện thông báo hỏi nếu đồng ý thì add vô giỏ hàng không thì xóa và hủy

        dataRef = database.getReference("gioHangTam").child(idGioHangTam);
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayListCartTam.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    arrCartTam = child.getValue(model_Cart.class);
                    arrayListCartTam.add(arrCartTam);
                }
                if (arrayListCartTam.size() != 0) {
                    openDialogUpdate();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void batSuKien() {
//        DỪNG CHIA SẺ KẾT NỐI
        ChiaSeKetNoi_llout_btn_dungPhatTinHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiaSeKetNoiGioHang_Activity.this, ChiaSeGioHang_Activity.class);
                startActivity(intent);
            }
        });
//        THOÁT TRANG
        ChiaSeKetNoi_img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiaSeKetNoiGioHang_Activity.this, ChiaSeGioHang_Activity.class);
                startActivity(intent);
            }
        });
    }


    private void anhXa() {
        dialog2=new Dialog(chiaSeKetNoiGioHang_Activity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.item_dialog_chucnang_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false); //Optional
        dialog2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Bundle bundle = getIntent().getExtras();
        String key;
        key = bundle.getString("iT_key", "");
        //TextView
        ChiaSeKetNoi_tv_key = findViewById(R.id.chiaSeKetNoi_tv_key);
        ChiaSeKetNoi_tv_key.setText("#"+key);

        //        Model
        arrListCart = new ArrayList<>();
        arrayListCartTam = new ArrayList<>();

//        firebase
        database = FirebaseDatabase.getInstance("https://duan-lgfood1-default-rtdb.asia-southeast1.firebasedatabase.app/");

        ChiaSeKetNoi_anim_connecting = findViewById(R.id.chiaSeKetNoi_anim_connecting);
//        Llout
        ChiaSeKetNoi_llout_btn_dungPhatTinHieu = findViewById(R.id.chiaSeKetNoi_llout_btn_dungPhatTinHieu);
//        imgview
        ChiaSeKetNoi_img_btn_back = findViewById(R.id.chiaSeKetNoi_img_btn_back);
    }

}