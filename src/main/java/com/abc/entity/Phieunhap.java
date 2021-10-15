package com.abc.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Phieunhap {
    @Id
    String mapn;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date ngaylap;

    float tongtien;

    int trangthai;

    @OneToMany(mappedBy = "phieunhap")
    List<CTPN> listCTPN;

    @ManyToOne
    @JoinColumn(name = "manv")
    Nhanvien nhanvien;

//    public Phieunhap(String mapn, Date ngaylap, Nhanvien nhanvien) {
//        this.mapn = mapn;
//        this.ngaylap = ngaylap;
//        this.nhanvien = nhanvien;
//    }

    public List<CTPN> getListCTPN() {
        return listCTPN;
    }

    public void setListCTPN(List<CTPN> listCTPN) {
        this.listCTPN = listCTPN;
    }

    public String getMapn() {
        return mapn;
    }

    public void setMapn(String mapn) {
        this.mapn = mapn;
    }

    public Date getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(Date ngaylap) {
        this.ngaylap = ngaylap;
    }

    public Nhanvien getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(Nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public float getTongtien() {
        return tongtien;
    }

    public void setTongtien(float tongtien) {
        this.tongtien = tongtien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
