package com.abc.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.abc.entity.*;
import com.abc.model.*;
import com.abc.repository.*;
import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.abc.dao.DAO;

@RestController
@CrossOrigin
public class DonhangController {
	@Autowired
	DonhangRepositoty repo;
	
	@Autowired
	CTDHRepository ctRepo;
	
	@Autowired
	GiohangRepository ghRepo;

	@Autowired
	NhanvienRepository nvRepo;
	
//	@GetMapping("/doanhthu")
//	public ArrayList<DoanhThuHangThang> getDoanhThu() {
//		return new DAO().getDoanhThuHangThang();
//	}

    @GetMapping("/topsp/{top}")
    public ArrayList<TopSanPham> getTopSP(@PathVariable("top") int top) {
        return new DAO().getTopSanPham(top);
    }

	@GetMapping("/topsp/{top}/{from}/{to}")
	public ArrayList<TopSanPham> getTopSP_Date(@PathVariable("top") int top, @PathVariable("from") String from, @PathVariable("to") String to) {
		return new DAO().getTopSanPham_Date(top, from, to);
	}

	@GetMapping("/topspdt/{top}")
	public ArrayList<TopSanPhamDoanhThu> getTopSPDT(@PathVariable("top") int top) {
		return new DAO().getTopSanPhamDoanhthu(top);
	}

	@GetMapping("/topspdt/{top}/{from}/{to}")
	public ArrayList<TopSanPhamDoanhThu> getTopSPDT_Date(@PathVariable("top") int top, @PathVariable("from") String from, @PathVariable("to") String to) {
		return new DAO().getTopSanPhamDoanhThu_Date(top, from, to);
	}

	@GetMapping("/topsp/nhap/{top}")
	public ArrayList<TopSanPham> getTopSPNhap(@PathVariable("top") int top) {
		return new DAO().getTopNhap(top);
	}

	@GetMapping("/topsp/nhap/{top}/{from}/{to}")
	public ArrayList<TopSanPham> getTopSPNhap_Date(@PathVariable("top") int top, @PathVariable("from") String from, @PathVariable("to") String to) {
		return new DAO().getTopNhap_Date(top, from, to);
	}

	@GetMapping("/topspdt/nhap/{top}")
	public ArrayList<TopSanPhamDoanhThu> getTopSPDTNhap(@PathVariable("top") int top) {
		return new DAO().getTopSanPhamPhiNhap(top);
	}

	@GetMapping("/topspdt/nhap/{top}/{from}/{to}")
	public ArrayList<TopSanPhamDoanhThu> getTopSPDTNhap_Date(@PathVariable("top") int top, @PathVariable("from") String from, @PathVariable("to") String to) {
		return new DAO().getTopSanPhamPhiNhap_Date(top, from, to);
	}

	//

	@GetMapping("/doanhthu/{nam}")
	public ArrayList<DoanhThuHangThang> getDoanhThu(@PathVariable("nam") int nam) {
		return new DAO().getDoanhThuHangThang(nam);
	}

	@GetMapping("/doanhthu/trangthai/{nam}")
	public ArrayList<DoanhThuTrangThai> getDoanhThuTrangThai(@PathVariable("nam") int nam) {
		return new DAO().getDoanhThuTrangThaiNam(nam);
	}

	@GetMapping("/doanhthu/trangthai/{from}/{to}")
	public ArrayList<DoanhThuTrangThai> getDoanhThuTrangThaiByDate(@PathVariable("from") String from, @PathVariable("to") String to) {
		return new DAO().getDoanhThuTrangThaiNgay(from, to);
	}

	@GetMapping("/doanhthu/{from}/{to}")
	public ArrayList<DoanhThuTheoNgay> getDoanhThuTheoNgay(@PathVariable("from") String from, @PathVariable("to") String to) {
		return new DAO().getDoanhThuTheoNgay(from, to);
	}
	
	@GetMapping("/donhang")
	public List<Donhang> getListDH(){
		return repo.findAll(Sort.by(Sort.Order.desc("ngaydat"),Sort.Order.asc("trangthai")));
	}
	
	@GetMapping("/donhang/manhathuoc/{madh}")// ?username làm biến ảo (không trùng với các method get khác) có thể nhập sai trường username :)
	public Optional<Donhang> getIdDonhangByMaNhaThuoc(@PathVariable("madh") String madh) {
		return repo.findById(madh);
	}
	
	@GetMapping("/donhang/manv/{madh}")
	public Optional<Donhang> getIdDonhangByManv(@PathVariable("madh") String madh) {
		return repo.findById(madh);
	}
	
	@GetMapping("/donhang/{manhathuoc}")
	public List<Donhang> getDonhangByManhathuoc(@PathVariable("manhathuoc") String mant){
		List<Donhang> list = repo.getDonhangByManhathuoc(mant);
		java.util.Collections.sort(list,new Comparator<Donhang>() {

			@Override
			public int compare(Donhang o1, Donhang o2) {
				int cpTT = o2.getNgaydat().compareTo(o1.getNgaydat());
				if(cpTT != 0) {
					return o2.getNgaydat().compareTo(o1.getNgaydat());
				}
				return o1.getTrangthai()>o2.getTrangthai()?1:-1;
			}
			
		});
		return list;
	}
	

	
	@PostMapping("/donhang/{manhathuoc}/{hinhthucthanhtoan}")
	public ResponseEntity<String> insertDonhang(@Validated @RequestBody List<Dathang> listDH, @PathVariable("manhathuoc") String manhathuoc, @PathVariable("hinhthucthanhtoan") int hinhthucthanhtoan, @RequestParam(required = false) String paymentCreated) {
		try {
			Donhang donhang = new Donhang();
			String madh = "DH" +  System.currentTimeMillis() % 100000000;
			float tongtien = 0;
			for(Dathang dh : listDH) {
				tongtien += dh.getDongia() * dh.getSoluong();
			}
			
			donhang.setMadh(madh);
			//Timestamp tm = new Timestamp(System.currentTimeMillis());
			
			//donhang.setNgaydat(tm);
			donhang.setHinhthucthanhtoan(hinhthucthanhtoan);
			donhang.setTrangthai(0);
			if(donhang.getHinhthucthanhtoan() == 2) donhang.setPaymentcreated(paymentCreated);
			else donhang.setPaymentcreated(null);

			Nhathuoc nhathuoc = new Nhathuoc();
			nhathuoc.setManhathuoc(manhathuoc);
			donhang.setNhathuoc(nhathuoc);
			donhang.setTongtien(tongtien);
			//save donhang
			try {
				repo.save(donhang);
				
				//save ctdh
				for(Dathang dh : listDH) {
					CTDH ctdh = new CTDH();
					CTDH_ID id = new CTDH_ID(madh,dh.getMasp());
					ctdh.setDonhang(donhang);
					ctdh.setId(id);
					Sanpham sanpham = new Sanpham();
					sanpham.setMasp(dh.getMasp());
					ctdh.setSanpham(sanpham);
					ctdh.setSoluong(dh.getSoluong());
					ctdh.setGiaban(dh.getDongia());
					ctRepo.save(ctdh);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return new ResponseEntity<String>("không thể thêm đơn hàng",HttpStatus.BAD_REQUEST);
			}
			try {
				for(Dathang dh : listDH) {
					ghRepo.deleteGiohangByMaNhaThuocAndMasp(manhathuoc, dh.getMasp());
				}
				return new ResponseEntity<String>(madh,HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
//				return new ResponseEntity<String>("Don hang chua duoc them vao gio",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
	}

//	@PostMapping("/donhang/{manv}/{hinhthucthanhtoan}")
//	public ResponseEntity<String> insertDonhangByNv(@Validated @RequestBody List<Dathang> listDH, @PathVariable("manv") String manv, @PathVariable("hinhthucthanhtoan") int hinhthucthanhtoan, @RequestParam(required = false) String paymentCreated) {
//		try {
//			Donhang donhang = new Donhang();
//			String madh = "DH" +  System.currentTimeMillis() % 100000000;
//			float tongtien = 0;
//			for(Dathang dh : listDH) {
//				tongtien += dh.getDongia() * dh.getSoluong();
//			}
//
//			donhang.setMadh(madh);
//			//Timestamp tm = new Timestamp(System.currentTimeMillis());
//
//			//donhang.setNgaydat(tm);
//			donhang.setHinhthucthanhtoan(hinhthucthanhtoan);
//			donhang.setTrangthai(0);
//			if(!paymentCreated.isEmpty()) donhang.setPaymentcreated(paymentCreated);
//			else donhang.setPaymentcreated(null);
//
////			Nhathuoc nhathuoc = new Nhathuoc();
////			nhathuoc.setManhathuoc(manhathuoc);
//
//			Nhanvien nhanvien = new Nhanvien();
//			nhanvien.setManv(manv);
//			donhang.setNhanvien(nhanvien);
//			donhang.setTongtien(tongtien);
//			//save donhang
//			try {
//				repo.save(donhang);
//
//				//save ctdh
//				for(Dathang dh : listDH) {
//					CTDH ctdh = new CTDH();
//					CTDH_ID id = new CTDH_ID(madh,dh.getMasp());
//					ctdh.setDonhang(donhang);
//					ctdh.setId(id);
//					Sanpham sanpham = new Sanpham();
//					sanpham.setMasp(dh.getMasp());
//					ctdh.setSanpham(sanpham);
//					ctdh.setSoluong(dh.getSoluong());
//					ctRepo.save(ctdh);
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				return new ResponseEntity<String>("không thể thêm đơn hàng",HttpStatus.BAD_REQUEST);
//			}
//			try {
//				for(Dathang dh : listDH) {
//					ghRepo.deleteGiohangByMaNhaThuocAndMasp(manhathuoc, dh.getMasp());
//				}
//				return new ResponseEntity<String>(madh,HttpStatus.OK);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
//	}
	
	@DeleteMapping("/donhang/{madh}")
	public ResponseEntity<String> deleteIdDonhang(@PathVariable("madh") String madh) {
		List<CTDH> listCT = ctRepo.findAll();
		try {
			
			for(CTDH ct : listCT) {
				if(ct.getId().getMadh().equalsIgnoreCase(madh)) {
					ctRepo.delete(ct);
				}
			}
			repo.deleteById(madh);
			return new ResponseEntity<String>("Successed !!!",HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/donhang/ctt/{madh}")
	public ResponseEntity<Boolean> chuyenDonhang(@PathVariable("madh") String madh, @RequestParam(name = "manv",required = false) String manv) {
		Optional<Donhang> dh = repo.findById(madh);
		if(dh.get().getTrangthai() < 3) {
			try {
				dh.get().setTrangthai(dh.get().getTrangthai()+1);

				if(manv!="" || manv!=null) {
					Optional<Nhanvien> listNV = nvRepo.findById(manv);
					dh.get().setNhanvien(listNV.get());
				}

				repo.save(dh.get());
				return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/donhang/{madh}")
	public ResponseEntity<Boolean> huyDonhang(@PathVariable("madh") String madh, @RequestParam(name = "manv",required = false) String manv) {
		Optional<Donhang> dh = repo.findById(madh);
		if(true) {
			try {
				dh.get().setTrangthai(4);

				if(manv!="" || manv!=null) {
					Optional<Nhanvien> listNV = nvRepo.findById(manv);
					dh.get().setNhanvien(listNV.get());
				}

				repo.save(dh.get());
				return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}


	@PutMapping("/donhang")
	public ResponseEntity<String> updateDonhang(@Validated @RequestBody Donhang donhang) {
		try {
			List<Donhang> listDH = repo.findAll();
			for(Donhang dh : listDH) {
				if (dh.getMadh().equalsIgnoreCase(donhang.getMadh())) {
					dh.setNhanvien(donhang.getNhanvien());
					repo.save(donhang);
					return new ResponseEntity<String>("Successed !!!",HttpStatus.OK);
				}
			}
			return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
	}
}

