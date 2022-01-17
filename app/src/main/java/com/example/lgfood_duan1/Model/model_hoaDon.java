package com.example.lgfood_duan1.Model;

public class model_hoaDon {
    String idHoaDon;
    String idChiTietDonHang;
    String idKhachHang;
    double tongTien;
    int TinhTrangDonHang;
    String ngayTaoChiTietHoaDon;

    public model_hoaDon() {
    }

    public model_hoaDon(String idHoaDon, String idChiTietDonHang, String idKhachHang, double tongTien, int tinhTrangDonHang, String ngayTaoChiTietHoaDon) {
        this.idHoaDon = idHoaDon;
        this.idChiTietDonHang = idChiTietDonHang;
        this.idKhachHang = idKhachHang;
        this.tongTien = tongTien;
        TinhTrangDonHang = tinhTrangDonHang;
        this.ngayTaoChiTietHoaDon = ngayTaoChiTietHoaDon;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdChiTietDonHang() {
        return idChiTietDonHang;
    }

    public void setIdChiTietDonHang(String idChiTietDonHang) {
        this.idChiTietDonHang = idChiTietDonHang;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getTinhTrangDonHang() {
        return TinhTrangDonHang;
    }

    public void setTinhTrangDonHang(int tinhTrangDonHang) {
        TinhTrangDonHang = tinhTrangDonHang;
    }

    public String getNgayTaoChiTietHoaDon() {
        return ngayTaoChiTietHoaDon;
    }

    public void setNgayTaoChiTietHoaDon(String ngayTaoChiTietHoaDon) {
        this.ngayTaoChiTietHoaDon = ngayTaoChiTietHoaDon;
    }
}
