package com.example.vetau.models;

import javafx.scene.control.Button;

public class ChitietTau {
    private Tau tau;
    private String ID_toa;
    private int Soluongghe;
    private String Loaitoa;

    private Button Delete_btn;
    private Button Detail_btn;
    private Button Update_btn;

    public ChitietTau()
    {

    }

    public ChitietTau(Tau tau, String ID_toa, int soluongghe, String loaitoa) {
        this.tau = tau;
        this.ID_toa = ID_toa;
        Soluongghe = soluongghe;
        Loaitoa = loaitoa;
    }

    public Tau getTau() {
        return tau;
    }

    public void setTau(Tau tau) {
        this.tau = tau;
    }

    public String getID_toa() {
        return ID_toa;
    }

    public void setID_toa(String ID_toa) {
        this.ID_toa = ID_toa;
    }

    public int getSoluongghe() {
        return Soluongghe;
    }

    public void setSoluongghe(int soluongghe) {
        Soluongghe = soluongghe;
    }

    public String getLoaitoa() {
        return Loaitoa;
    }

    public void setLoaitoa(String loaitoa) {
        Loaitoa = loaitoa;
    }
    public String getID_Tau()
    {
        return tau.getIDTau();
    }
    public int getSoluongtoa()
    {
        return tau.getSoluongtoa();
    }

    public Button getDelete_btn() {
        return Delete_btn;
    }

    public Button getDetail_btn() {
        return Detail_btn;
    }

    public Button getUpdate_btn() {
        return Update_btn;
    }
}
