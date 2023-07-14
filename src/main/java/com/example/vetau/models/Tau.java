package com.example.vetau.models;

public class Tau {
    private String IDTau;
    private int Soluongtoa;
    public Tau()
    {

    }

    public Tau(String ID_Tau, int soluongtoa) {
        IDTau = ID_Tau;
        Soluongtoa = soluongtoa;
    }

    public String getIDTau() {
        return IDTau;
    }

    public void setIDTau(String ID_Tau) {
        this.IDTau = ID_Tau;
    }

    public int getSoluongtoa() {
        return Soluongtoa;
    }

    public void setSoluongtoa(int soluongtoa) {
        Soluongtoa = soluongtoa;
    }
    @Override
    public String toString()
    {
       return IDTau;
    }



}
