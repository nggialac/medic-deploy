package com.abc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Binhluan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String noidung;
	Date time;
	
	@ManyToOne
	@JoinColumn(name = "masp")
	Sanpham sanpham;
	
	@ManyToOne
	@JoinColumn(name = "manhathuoc")
	Nhathuoc nhathuoc;

	@JsonIgnore
	@OneToMany(mappedBy = "binhluan")
	List<Phanhoibinhluan> phanhoibinhluan;

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

	public Sanpham getSanpham() {
		return sanpham;
	}

	public void setSanpham(Sanpham sanpham) {
		this.sanpham = sanpham;
	}

	public Nhathuoc getNhathuoc() {
		return nhathuoc;
	}

	public void setNhathuoc(Nhathuoc nhathuoc) {
		this.nhathuoc = nhathuoc;
	}

	public List<Phanhoibinhluan> getPhanhoibinhluan() {
		return phanhoibinhluan;
	}

	public void setPhanhoibinhluan(List<Phanhoibinhluan> phanhoibinhluan) {
		this.phanhoibinhluan = phanhoibinhluan;
	}
}
