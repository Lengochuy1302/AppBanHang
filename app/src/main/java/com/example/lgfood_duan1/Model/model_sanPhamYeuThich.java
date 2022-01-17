package com.example.lgfood_duan1.Model;

public class model_sanPhamYeuThich {
    String idSanPham;
    String idYeuThich;

    public void model_sanPhamYeuThich() {

    }

    public model_sanPhamYeuThich(String idYeuThich, String idSanPham) {
        this.idYeuThich = idYeuThich;
        this.idSanPham = idSanPham;
    }

    public String getIdYeuThich() {
        return idYeuThich;
    }

    public void setIdYeuThich(String idYeuThich) {
        this.idYeuThich = idYeuThich;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }
}
