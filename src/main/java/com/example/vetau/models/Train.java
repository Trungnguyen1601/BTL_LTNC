package com.example.vetau.models;

import javafx.scene.control.Button;

import java.time.LocalDate;
import java.util.Date;

public class Train {
    private int ID_train;
    private String Gadi;
    private String Gaden;
    private int Tau;
    private String Giodi;
    private Button deleteButton;
    private Button inforButton;
    private Date ngaydi;
    private Date ngayden;

    public Train(int ID_train, String gadi, String gaden, int tau, String giodi, Date ngaydi, Date ngayden) {
        this.ID_train = ID_train;
        Gadi = gadi;
        Gaden = gaden;
        Tau = tau;
        Giodi = giodi;
        this.deleteButton = new Button();
        this.inforButton = new Button();
        this.ngaydi = ngaydi;
        this.ngayden = ngayden;
    }

    public Train(int ID_train, String gadi, String gaden, int tau, String giodi) {
        this.ID_train = ID_train;
        Gadi = gadi;
        Gaden = gaden;
        Tau = tau;
        Giodi = giodi;
        this.deleteButton = new Button();
        this.inforButton = new Button();

    }

    public int getID_train() {
        return ID_train;
    }

    public void setID_train(int ID_train) {
        this.ID_train = ID_train;
    }

    public String getGadi() {
        return Gadi;
    }

    public void setGadi(String gadi) {
        Gadi = gadi;
    }

    public String getGaden() {
        return Gaden;
    }

    public void setGaden(String gaden) {
        Gaden = gaden;
    }

    public int getTau() {
        return Tau;
    }

    public void setTau(int tau) {
        Tau = tau;
    }

    public String getGiodi() {
        return Giodi;
    }

    public void setGiodi(String giodi) {
        Giodi = giodi;
    }
    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getInforButton() {
        return inforButton;
    }

    public Date getNgaydi() {
        return ngaydi;
    }

    public void setNgaydi(Date ngaydi) {
        this.ngaydi = ngaydi;
    }

    public Date getNgayden() {
        return ngayden;
    }

    public void setNgayden(Date ngayden) {
        this.ngayden = ngayden;
    }
}
