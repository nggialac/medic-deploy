package com.abc.controller;

import java.util.List;
import java.util.Optional;

import com.abc.entity.Danhgia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.abc.entity.Binhluan;
import com.abc.entity.Sanpham;
import com.abc.repository.BinhluanRepository;

@RestController
public class BinhluanController {
	@Autowired
	BinhluanRepository repo;
	
	@GetMapping("/binhluan")
	public List<Binhluan> getListBL(){
		return repo.findAll();
	}

	@GetMapping("/binhluan/{masp}")
	public List<Binhluan> getListBLByMasp(@Validated @PathVariable("masp") String masp){
		return repo.findBinhluanByMasp(masp);
	}

	@GetMapping("/binhluan/nt/{manhathuoc}")
	public List<Binhluan> getListBLByManhathuoc(@Validated @PathVariable("manhathuoc") String manhathuoc){
		return repo.findAllByNhathuoc_Manhathuoc(manhathuoc);
	}
	
	@PutMapping("/binhluan")
	public ResponseEntity<String> updateBinhluan(@Validated @RequestBody Binhluan binhluan) {

		try {
			List<Binhluan> listBl = repo.findAll();
			for(Binhluan bl : listBl) {
				if(bl.getId() == binhluan.getId()) {
					repo.save(binhluan);
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
	
	@PostMapping("/binhluan")
	public ResponseEntity<String> insertBinhluan(@Validated @RequestBody Binhluan binhluan) {
		try {
			repo.save(binhluan);
			return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);

//		List<Binhluan> listBL = repo.findAll();
//		for(Binhluan bl :listBL) {
//			if(bl.getNhathuoc().getManhathuoc().equalsIgnoreCase(binhluan.getNhathuoc().getManhathuoc()) && bl.getSanpham().getMasp().equalsIgnoreCase(binhluan.getSanpham().getMasp())){
//				try {
//					repo.updateBinhLuan(binhluan.getNhathuoc().getManhathuoc(), binhluan.getSanpham().getMasp(), binhluan.getNoidung());
//					return new ResponseEntity<Boolean>(true,HttpStatus.OK);
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//					return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
//				}
//			}
//		}
//		try {
//			repo.save(binhluan);
//			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
//		}
	}
	
	@DeleteMapping("/binhluan/{id}")
	public ResponseEntity<String> deleteIdBinhluan(@PathVariable("id") int id) {
		try {
			repo.deleteById(id);
			return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
	}
}
