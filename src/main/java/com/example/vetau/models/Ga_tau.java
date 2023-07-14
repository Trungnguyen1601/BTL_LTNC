package com.example.vetau.models;

public class Ga_tau {
    private String ID_Gatau ;
    private String Ten_Gatau ;
    private String Dia_diem;
    public Ga_tau()
    {

    }
    public Ga_tau(String ID_Gatau, String ten_Gatau, String dia_diem) {
        this.ID_Gatau = ID_Gatau;
        Ten_Gatau = ten_Gatau;
        Dia_diem = dia_diem;
    }

    public String getID_Gatau() {
        return ID_Gatau;
    }

    public void setID_Gatau(String ID_Gatau) {
        this.ID_Gatau = ID_Gatau;
    }

    public String getTen_Gatau() {
        return Ten_Gatau;
    }

    public void setTen_Gatau(String ten_Gatau) {
        Ten_Gatau = ten_Gatau;
    }

    public String getDia_diem() {
        return Dia_diem;
    }

    public void setDia_diem(String dia_diem) {
        Dia_diem = dia_diem;
    }
    @Override
    public String toString()
    {
        return Ten_Gatau;
    }

}
