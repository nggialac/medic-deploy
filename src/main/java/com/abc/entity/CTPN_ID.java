package com.abc.entity;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class CTPN_ID implements Serializable {
    String masp;
    String mapn;

    public CTPN_ID() {
        super();
    }

    public CTPN_ID(String masp, String mapn) {
        super();
        this.masp = masp;
        this.mapn = mapn;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getMapn() {
        return mapn;
    }

    public void setMapn(String mapn) {
        this.mapn = mapn;
    }
}
