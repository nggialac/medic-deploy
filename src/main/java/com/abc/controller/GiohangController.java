package com.abc.controller;

import java.util.List;

import com.abc.repository.NhathuocRepository;
import com.abc.repository.SanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.abc.entity.Danhmuc;
import com.abc.entity.Giohang;
import com.abc.entity.Giohang_ID;
import com.abc.entity.Nhathuoc;
import com.abc.entity.Sanpham;
import com.abc.entity.Taikhoan;
import com.abc.repository.GiohangRepository;

@RestController
@CrossOrigin
public class GiohangController {
	@Autowired
	GiohangRepository repo;

	@Autowired
	SanphamRepository repoSp;
	
	@GetMapping("/giohang/{manhathuoc}")
	public List<Giohang> getListGH(@PathVariable("manhathuoc") String manhathuoc){
		return repo.getGiohangByMaNhaThuoc(manhathuoc);
	}

//	@GetMapping("/giohang/{manhathuoc}")
//	public int getQuantityCartByMasp(@PathVariable("manhathuoc") String manhathuoc,@PathVariable("masp") String masp)

	@PutMapping("/giohang/{manhathuoc}/{masp}")
	public ResponseEntity<Boolean> changeNum(@PathVariable("manhathuoc") String manhathuoc,@PathVariable("masp") String masp,@RequestParam("soluong") int soluong) {
		Sanpham sp = repoSp.findByMasp(masp);
		int soluongton = sp.getSoluong();
		if(soluong > soluongton) return new ResponseEntity<Boolean>(false,HttpStatus.BAD_GATEWAY);

		List<Giohang> listGH = repo.getGiohangByMaNhaThuoc(manhathuoc);

		for(Giohang gh:listGH) {
			if(gh.getSanpham().getMasp().equalsIgnoreCase(masp)) {
				Giohang giohang = gh;
				giohang.setSoluong(soluong);
				//System.out.println(gh.getKhachhang().getTen());
				try {
					repo.save(giohang);
					return new ResponseEntity<Boolean>(true,HttpStatus.OK);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			}
		}
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_GATEWAY);
	}
	
	
	@PostMapping("/giohang/{manhathuoc}/{masp}")
	public ResponseEntity<Boolean> addCart(@PathVariable("manhathuoc") String manhathuoc, @PathVariable("masp") String masp,@RequestParam("soluong") int soluong) {
//		System.out.println("MA NHA THUOC: " + manhathuoc);
//		manhathuoc ="NT6948045";
		Giohang_ID id = new Giohang_ID(manhathuoc, masp);
		Giohang gh = new Giohang();
		gh.setId(id);
		gh.setSoluong(soluong);
		Sanpham sp = new Sanpham();
		sp.setMasp(masp);
		Nhathuoc nt = new Nhathuoc();
		nt.setManhathuoc(manhathuoc);
		gh.setSanpham(sp);
		gh.setNhathuoc(nt);


		Sanpham spTemp = repoSp.findByMasp(masp);
		int soluongton = spTemp.getSoluong();
//		if(soluong > soluongton) return new ResponseEntity<Boolean>(false,HttpStatus.BAD_GATEWAY);
		
		List<Giohang> listGH = repo.findAll();
		for(Giohang i:listGH) {
			if(i.getId().getMasp().equalsIgnoreCase(masp) && i.getId().getManhathuoc().equalsIgnoreCase(manhathuoc)) {
				if(i.getSoluong()+soluong <= soluongton) gh.setSoluong(soluong +i.getSoluong());
				else return new ResponseEntity<Boolean>(false,HttpStatus.BAD_GATEWAY);
			}
		}
		try {
			repo.save(gh);
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_GATEWAY);
	}
	
	@DeleteMapping("/giohang/{manhathuoc}")
	public ResponseEntity<String> deleteGiohangOfNhaThuoc(@PathVariable("manhathuoc") String manhathuoc) {
		try {
			repo.deleteGiohangByMaNhaThuoc(manhathuoc);
			return new ResponseEntity<String>("Successed !!!",HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/giohang/{manhathuoc}/{masp}")
	public ResponseEntity<String> deleteGiohangById(@PathVariable("manhathuoc") String manhathuoc, @PathVariable("masp") String masp) {
		try {
			repo.deleteGiohangByMaNhaThuocAndMasp(manhathuoc, masp);
			return new ResponseEntity<String>("Successed !!!",HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
	}
	@GetMapping("/numcart/{manhathuoc}")
	public int getSLC(@PathVariable("manhathuoc") String manhathuoc) {
		List<Giohang> list = repo.getGiohangByMaNhaThuoc(manhathuoc);
		int soluong = 0 ;
		for(Giohang gh:list) {
			soluong +=gh.getSoluong();
		}
		return soluong;
	}
	
}
