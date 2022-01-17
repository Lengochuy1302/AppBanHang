package com.example.lgfood_duan1.Model;

public class model_Cart {
    private String
    idGioHang,
    idSanPham,
    soLuong;

    public model_Cart(){

    }
    public model_Cart(String idGioHang, String idSanPham, String soLuong) {
        this.idGioHang = idGioHang;
        this.idSanPham = idSanPham;
        this.soLuong = soLuong;
    }

    public String getIdGioHang() {
        return idGioHang;
    }

    public void setIdGioHang(String idGioHang) {
        this.idGioHang = idGioHang;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
}
