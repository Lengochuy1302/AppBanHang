package com.example.lgfood_duan1.Model;

import java.util.Comparator;

public class model_chiTietLoaiSanPham_thongKe {
    String tenSanPham;
    double giaSanPham;
    int soLuongSanPham;

    public model_chiTietLoaiSanPham_thongKe() {
    }

    public model_chiTietLoaiSanPham_thongKe(String tenSanPham, double giaSanPham, int soLuongSanPham) {
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.soLuongSanPham = soLuongSanPham;
    }
    public  static Comparator<model_chiTietLoaiSanPham_thongKe> sapXepTheoGiaCaoxuongThap = new Comparator<model_chiTietLoaiSanPham_thongKe>() {
        @Override
        public int compare(model_chiTietLoaiSanPham_thongKe P1, model_chiTietLoaiSanPham_thongKe P2) {
            return (int) (P1.getGiaSanPham()*P1.getSoLuongSanPham() - P2.getGiaSanPham()*P2.getSoLuongSanPham());
        }

    };
    public  static Comparator<model_chiTietLoaiSanPham_thongKe> sapXepTheoThapDenCao= new Comparator<model_chiTietLoaiSanPham_thongKe>() {
        @Override
        public int compare(model_chiTietLoaiSanPham_thongKe P1, model_chiTietLoaiSanPham_thongKe P2) {
            return (int) (P2.getGiaSanPham()*P2.getSoLuongSanPham() - P1.getGiaSanPham()*P1.getSoLuongSanPham());
        }

    };
    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(double giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public int getSoLuongSanPham() {
        return soLuongSanPham;
    }

    public void setSoLuongSanPham(int soLuongSanPham) {
        this.soLuongSanPham = soLuongSanPham;
    }
}
