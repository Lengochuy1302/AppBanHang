package com.example.lgfood_duan1.Model;

public class model_chiTietSanPhamHoaDon {
    String idChiTietHoaDon;
    String idSanPham;
    String soLuongSanPham;
    double giaSanPham;

    public model_chiTietSanPhamHoaDon() {
    }

    public model_chiTietSanPhamHoaDon(String idChiTietHoaDon, String idSanPham, String soLuongSanPham, double giaSanPham) {
        this.idChiTietHoaDon = idChiTietHoaDon;
        this.idSanPham = idSanPham;
        this.soLuongSanPham = soLuongSanPham;
        this.giaSanPham = giaSanPham;
    }

    public String getIdChiTietHoaDon() {
        return idChiTietHoaDon;
    }

    public void setIdChiTietHoaDon(String idChiTietHoaDon) {
        this.idChiTietHoaDon = idChiTietHoaDon;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getSoLuongSanPham() {
        return soLuongSanPham;
    }

    public void setSoLuongSanPham(String soLuongSanPham) {
        this.soLuongSanPham = soLuongSanPham;
    }

    public double getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(double giaSanPham) {
        this.giaSanPham = giaSanPham;
    }
}
