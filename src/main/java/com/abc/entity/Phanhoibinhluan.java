package com.abc.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Phanhoibinhluan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String noidung;
    Date time;

    @ManyToOne
    @JoinColumn(name = "mabl")
    Binhluan binhluan;

    @ManyToOne
    @JoinColumn(name = "manv")
    Nhanvien nhanvien;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public Binhluan getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(Binhluan binhluan) {
        this.binhluan = binhluan;
    }

    public Nhanvien getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(Nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }
}