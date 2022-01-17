package com.example.lgfood_duan1.Model;

public class model_admin {
    private String idAdmin;
    private String idUser;

    public model_admin() {
    }

    public model_admin(String idAdmin, String idUser) {
        this.idAdmin = idAdmin;
        this.idUser = idUser;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
