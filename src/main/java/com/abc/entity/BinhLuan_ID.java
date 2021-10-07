package com.abc.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class BinhLuan_ID implements Serializable{
    String manhathuoc;
    String masp;

    public String getManhathuoc() {
        return manhathuoc;
    }
    public void setManhathuoc(String manhathuoc) {
        this.manhathuoc = manhathuoc;
    }
    public String getMasp() {
        return masp;
    }
    public void setMasp(String masp) {
        this.masp = masp;
    }


}
