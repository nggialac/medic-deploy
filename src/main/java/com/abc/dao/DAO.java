package com.abc.dao;

import java.sql.*;
import java.util.ArrayList;

import com.abc.model.DoanhThuHangThang;
import com.abc.model.DoanhThuTheoNgay;
import com.abc.model.DoanhThuTrangThai;
import com.abc.model.Matkhau;

public class DAO {
	
	Connection con = null;

	public DAO() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager
					.getConnection("jdbc:sqlserver://MEDICALECOMMERCE2022.mssql.somee.com;databaseName=MEDICALECOMMERCE2022;username=nguyengialac076_SQLLogin_1;password=cwwajeix8r");
//					.getConnection("jdbc:sqlserver://MEDICALECOMMERCE2021.mssql.somee.com;databaseName=MEDICALECOMMERCE2021;username=nguyengialac99_SQLLogin_1;password=17e85eedo6");
//			.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MEDICALECOMMERCE2021;username=sa;password=123");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public ArrayList<DoanhThuHangThang> getDoanhThuHangThang(int nam) {
		ArrayList<DoanhThuHangThang> list = new ArrayList<DoanhThuHangThang>();
		try {
			PreparedStatement st;
			st = con.prepareStatement("SELECT MONTH(DONHANG.NGAYDAT),SUM(TONGTIEN) FROM DONHANG WHERE (DONHANG.TRANGTHAI = '3') AND (YEAR(DONHANG.NGAYDAT) = ?)  GROUP BY MONTH(DONHANG.NGAYDAT)");
//			Statement st = con.createStatement();
//			"SELECT MONTH(DONHANG.NGAYDAT),SUM(TONGTIEN) FROM DONHANG WHERE (DONHANG.TRANGTHAI = '3') AND (YEAR(DONHANG.NGAYDAT) = YEAR(GETDATE()))  GROUP BY MONTH(DONHANG.NGAYDAT)"
			st.setInt(1, nam);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				DoanhThuHangThang ds = new DoanhThuHangThang();
				ds.setThang(rs.getString(1));
				ds.setTien(rs.getInt(2));
//				ds.setNam(rs.getString(3));
				list.add(ds);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}



	public ArrayList<DoanhThuTheoNgay> getDoanhThuTheoNgay(String from, String to) {
		ArrayList<DoanhThuTheoNgay> list = new ArrayList<DoanhThuTheoNgay>();
		try {
//			Statement st = con.createStatement();
			PreparedStatement st;
			st = con.prepareStatement(
					"SELECT FORMAT(DONHANG.NGAYDAT, 'yyyy-MM-dd'),SUM(TONGTIEN) " +
					"FROM DONHANG WHERE (DONHANG.TRANGTHAI = '3') " +
					"AND DONHANG.NGAYDAT between CONVERT(datetime, ?) and CONVERT(datetime, ? +' 23:59:59:998') GROUP BY (DONHANG.NGAYDAT)");
			st.setString(1, from);
			st.setString(2, to);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				DoanhThuTheoNgay ds = new DoanhThuTheoNgay();
				ds.setNgay(rs.getString(1));
				ds.setTien(rs.getDouble(2));
//				ds.setNam(rs.getString(3));
				list.add(ds);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<DoanhThuTrangThai> getDoanhThuTrangThaiNam(int nam) {
		ArrayList<DoanhThuTrangThai> list = new ArrayList<DoanhThuTrangThai>();
		try {
//			Statement st = con.createStatement();
			PreparedStatement st;
			st = con.prepareStatement("select DONHANG.TRANGTHAI, COUNT(*) from DONHANG WHERE YEAR(DONHANG.NGAYDAT) = ?  GROUP BY(DONHANG.TRANGTHAI)");
			st.setInt(1, nam);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				DoanhThuTrangThai ds = new DoanhThuTrangThai();
				ds.setTrangthai(rs.getInt(1));
				ds.setSodon(rs.getInt(2));
//				ds.setNam(rs.getString(3));
				list.add(ds);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<DoanhThuTrangThai> getDoanhThuTrangThaiNgay(String from, String to) {
		ArrayList<DoanhThuTrangThai> list = new ArrayList<DoanhThuTrangThai>();
		try {
//			Statement st = con.createStatement();
			PreparedStatement st;
			st = con.prepareStatement("select DONHANG.TRANGTHAI, COUNT(*) from DONHANG WHERE DONHANG.NGAYDAT >= ? AND DONHANG.NGAYDAT <= ?  GROUP BY(DONHANG.TRANGTHAI)");
			st.setString(1, from);
			st.setString(2, to);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				DoanhThuTrangThai ds = new DoanhThuTrangThai();
				ds.setTrangthai(rs.getInt(1));
				ds.setSodon(rs.getInt(2));
//				ds.setNam(rs.getString(3));
				list.add(ds);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Matkhau> getMatkhau() {
		ArrayList<Matkhau> list = new ArrayList<Matkhau>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select NHATHUOC.TENNHATHUOC, NHATHUOC.Email , TAIKHOAN.PASSWORD from NHATHUOC, TAIKHOAN where NHATHUOC.MATK = TAIKHOAN.MATK");
			while (rs.next()) {
				Matkhau mk = new Matkhau();
				mk.setTennhahthuoc(rs.getString(1));
				mk.setEmail(rs.getString(2));
				mk.setPassword(rs.getString(3));
				list.add(mk);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
}
