package com.example.lgfood_duan1.Model;

public class model_addToCart {
    private String
            idSp,
            moTaSp,
            tenSp,
            ngaySanXuatSp,
            xuatXuSp,
            loaiSp,
            tinhTrangSp,
            anhSp,
            ngayNhapSp;

    private int
            soLuongTrongKho,
            soLuongSp,
            giamGiaSp;

    private double
            giaNhapSp,
            giaBanSp;

    public model_addToCart() {
    }

    public model_addToCart(String idSp, String moTaSp, String tenSp, String ngaySanXuatSp, String xuatXuSp, String loaiSp, String tinhTrangSp, String anhSp, String ngayNhapSp, int soLuongTrongKho, int soLuongSp, double giaNhapSp, double giaBanSp) {
        this.idSp = idSp;
        this.moTaSp = moTaSp;
        this.tenSp = tenSp;
        this.ngaySanXuatSp = ngaySanXuatSp;
        this.xuatXuSp = xuatXuSp;
        this.loaiSp = loaiSp;
        this.tinhTrangSp = tinhTrangSp;
        this.anhSp = anhSp;
        this.ngayNhapSp = ngayNhapSp;
        this.soLuongTrongKho = soLuongTrongKho;
        this.soLuongSp = soLuongSp;
        this.giamGiaSp = giamGiaSp;
        this.giaNhapSp = giaNhapSp;
        this.giaBanSp = giaBanSp;
    }

    public String getIdSp() {
        return idSp;
    }

    public void setIdSp(String idSp) {
        this.idSp = idSp;
    }

    public String getMoTaSp() {
        return moTaSp;
    }

    public void setMoTaSp(String moTaSp) {
        this.moTaSp = moTaSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getNgaySanXuatSp() {
        return ngaySanXuatSp;
    }

    public void setNgaySanXuatSp(String ngaySanXuatSp) {
        this.ngaySanXuatSp = ngaySanXuatSp;
    }

    public String getXuatXuSp() {
        return xuatXuSp;
    }

    public void setXuatXuSp(String xuatXuSp) {
        this.xuatXuSp = xuatXuSp;
    }

    public String getLoaiSp() {
        return loaiSp;
    }

    public void setLoaiSp(String loaiSp) {
        this.loaiSp = loaiSp;
    }

    public String getTinhTrangSp() {
        return tinhTrangSp;
    }

    public void setTinhTrangSp(String tinhTrangSp) {
        this.tinhTrangSp = tinhTrangSp;
    }

    public String getAnhSp() {
        return anhSp;
    }

    public void setAnhSp(String anhSp) {
        this.anhSp = anhSp;
    }

    public String getNgayNhapSp() {
        return ngayNhapSp;
    }

    public void setNgayNhapSp(String ngayNhapSp) {
        this.ngayNhapSp = ngayNhapSp;
    }

    public int getSoLuongTrongKho() {
        return soLuongTrongKho;
    }

    public void setSoLuongTrongKho(int soLuongTrongKho) {
        this.soLuongTrongKho = soLuongTrongKho;
    }

    public int getSoLuongSp() {
        return soLuongSp;
    }

    public void setSoLuongSp(int soLuongSp) {
        this.soLuongSp = soLuongSp;
    }

    public int getGiamGiaSp() {
        return giamGiaSp;
    }

    public void setGiamGiaSp(int giamGiaSp) {
        this.giamGiaSp = giamGiaSp;
    }

    public double getGiaNhapSp() {
        return giaNhapSp;
    }

    public void setGiaNhapSp(double giaNhapSp) {
        this.giaNhapSp = giaNhapSp;
    }

    public double getGiaBanSp() {
        return giaBanSp;
    }

    public void setGiaBanSp(double giaBanSp) {
        this.giaBanSp = giaBanSp;
    }
}
