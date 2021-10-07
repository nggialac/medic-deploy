package com.abc.controller;

import java.util.List;
import java.util.Optional;

import com.abc.entity.Danhmuc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.abc.entity.Sanpham;
import com.abc.repository.SanphamRepository;

@RestController
@CrossOrigin
public class SanphamController {
	
	@Autowired
	SanphamRepository repo;
	
	@GetMapping("/sanpham")
	public List<Sanpham> getListSP(){
		return repo.findAll();
	}

//	@GetMapping("/sanpham")
//	public List<Sanpham> getNumberSP(){
//		return repo.findAll();
//	}

	@GetMapping("/sanpham/search")
	public List<Sanpham> getListByName(@Validated @RequestParam("tensp") String tensp) {return repo.findByTenspContains(tensp);}

	@GetMapping("/sanpham/last")
	public Sanpham getLastSp() {
		return repo.findFirstByOrderByMaspDesc();
	}

	@PostMapping("/sanpham")
	public ResponseEntity<String> insertSanpham(@Validated @RequestBody Sanpham sanpham){
//		String temp = sanpham.getDanhmuc().getMadm().substring(0,2);
//		String masp = temp + System.currentTimeMillis() % 100000000;
//		sanpham.setMasp(masp);
		try {
			repo.save(sanpham);
			return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("/sanpham/{masp}")
	public ResponseEntity<String> deleteSanpham(@PathVariable("masp") String masp){
		try {
			repo.deleteById(masp);
			return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/sanpham")
	public ResponseEntity<String> updateSanpham(@Validated @RequestBody Sanpham sanpham){
		try {
			List<Sanpham> listSP = repo.findAll();
			for(Sanpham sp:listSP) {
				if(sp.getMasp().equalsIgnoreCase(sanpham.getMasp())) {
					repo.save(sanpham);
					return new ResponseEntity<String>("Successed" , HttpStatus.OK);
				}
			}
			return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
	}
	@GetMapping("/sanpham/{masp}")
	public Optional<Sanpham> getSPByID(@PathVariable("masp") String masp) {
		return repo.findById(masp);
	}
	@GetMapping("/sanpham/danhmuc/{madm}")
	public List<Sanpham> getListSPByMadm(@PathVariable("madm") String madm){
		return repo.getSPByMadm(madm);
	}
}
