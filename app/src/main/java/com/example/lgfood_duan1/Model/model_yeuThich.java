package com.example.lgfood_duan1.Model;

public class model_yeuThich {
    private String
    idSanPham,
    idYeuThich;
    private boolean check=true;
    public model_yeuThich(){

    }

    public model_yeuThich(String idSanPham, String idYeuThich, boolean check) {
        this.idSanPham = idSanPham;
        this.idYeuThich = idYeuThich;
        this.check = check;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdYeuThich() {
        return idYeuThich;
    }

    public void setIdYeuThich(String idYeuThich) {
        this.idYeuThich = idYeuThich;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
