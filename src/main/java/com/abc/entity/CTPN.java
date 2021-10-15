package com.abc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Entity;

@Entity
public class CTPN {
    @EmbeddedId
    CTPN_ID id;

    int soluong;

    float dongia;

    @JsonIgnore
    @ManyToOne
    @MapsId("mapn")
    @JoinColumn(name = "mapn")
    Phieunhap phieunhap;

    @ManyToOne
    @MapsId("masp")
    @JoinColumn(name="masp")
    Sanpham sanpham;

    public CTPN() {

    }

    public Phieunhap getPhieunhap() {
        return phieunhap;
    }

    public void setPhieunhap(Phieunhap phieunhap) {
        this.phieunhap = phieunhap;
    }

    public Sanpham getSanpham() {
        return sanpham;
    }

    public void setSanpham(Sanpham sanpham) {
        this.sanpham = sanpham;
    }

    public CTPN_ID getId() {
        return id;
    }

    public void setId(CTPN_ID id) {
        this.id = id;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }
}
