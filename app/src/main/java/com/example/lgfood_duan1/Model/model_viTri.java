package com.example.lgfood_duan1.Model;

public class model_viTri {

    private String idVitri;
private String idGioHangTam;
    private String Vitri;
    private double Latitude;
    private double Longitude;
    private boolean tinhTrang;
    private String NgayTao;
    private String key;
    private String anhUser;
    private String nameUser;

    public model_viTri() {
    }

    public model_viTri(String idVitri, String idGioHangTam, String vitri, double latitude, double longitude, boolean tinhTrang, String ngayTao, String key, String anhUser, String nameUser) {
        this.idVitri = idVitri;
        this.idGioHangTam = idGioHangTam;
        Vitri = vitri;
        Latitude = latitude;
        Longitude = longitude;
        this.tinhTrang = tinhTrang;
        NgayTao = ngayTao;
        this.key = key;
        this.anhUser = anhUser;
        this.nameUser = nameUser;
    }

    public String getIdVitri() {
        return idVitri;
    }

    public void setIdVitri(String idVitri) {
        this.idVitri = idVitri;
    }

    public String getIdGioHangTam() {
        return idGioHangTam;
    }

    public void setIdGioHangTam(String idGioHangTam) {
        this.idGioHangTam = idGioHangTam;
    }

    public String getVitri() {
        return Vitri;
    }

    public void setVitri(String vitri) {
        Vitri = vitri;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAnhUser() {
        return anhUser;
    }

    public void setAnhUser(String anhUser) {
        this.anhUser = anhUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}