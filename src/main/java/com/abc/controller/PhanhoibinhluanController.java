package com.abc.controller;


import java.util.List;
import java.util.Optional;

import com.abc.entity.Phanhoibinhluan;
import com.abc.repository.PhanhoibinhluanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.abc.entity.Sanpham;
import com.abc.repository.BinhluanRepository;

@RestController
public class PhanhoibinhluanController {
    @Autowired
    PhanhoibinhluanRepository repo;

    @GetMapping("/phanhoi")
    public List<Phanhoibinhluan> getListPH(){
        return repo.findAll();
    }

    @GetMapping("/phanhoi/{mabl}")
    public List<Phanhoibinhluan> getListBLByMasp(@Validated @PathVariable("mabl") int mabl){
        return repo.findAllByBinhluan_Id(mabl);
    }

    @GetMapping("/phanhoi/nt/{manhathuoc}")
    public List<Phanhoibinhluan> getListBLByMasp(@Validated @PathVariable("manhathuoc") String manhathuoc){
        return repo.findPHbyNT(manhathuoc);
    }

    @PutMapping("/phanhoi")
    public ResponseEntity<String> updatePhanhoi(@Validated @RequestBody Phanhoibinhluan phanhoi) {

        try {
            List<Phanhoibinhluan> listPH = repo.findAll();
            for(Phanhoibinhluan ph : listPH) {
                if(ph.getId() == phanhoi.getId()) {
                    repo.save(phanhoi);
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

    @PostMapping("/phanhoi")
    public ResponseEntity<String> insertPhanhoi(@Validated @RequestBody Phanhoibinhluan bl) {

//        List<Phanhoibinhluan> listPH = repo.findAll();
        try {
            repo.save(bl);
            return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/phanhoi/{id}")
    public ResponseEntity<String> deleteIdPhanHoi(@PathVariable("id") int id) {
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
