package com.abc.controller;

import com.abc.entity.CTDH;
import com.abc.entity.CTPN;
import com.abc.entity.Donhang;
import com.abc.entity.Phieunhap;
import com.abc.repository.CTPNRepository;
import com.abc.repository.PhieunhapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CTPNController {

    @Autowired
    CTPNRepository repo;

    @Autowired
    PhieunhapRepository pnRepo;

    @GetMapping("/ctpn")
    public List<CTPN> getListCTPN() {
        return repo.findAll();
    }

    @GetMapping("/ctpn/{mapn}")
    public List<CTPN> getCTPNByMapn(@PathVariable("mapn") String mapn) {
        return repo.getCTPNByMapn(mapn);
    }

    @GetMapping("/ctpn/{manv}/{masp}")
    public ResponseEntity<Boolean> getCTDH(@PathVariable("manv") String manv, @PathVariable("masp") String masp) {
        List<Phieunhap> listDH = pnRepo.getPhieunhapByManv(manv);
        for(Phieunhap pn : listDH) {
            for(CTPN ct : pn.getListCTPN()) {
                if(ct.getSanpham().getMasp().equalsIgnoreCase(masp)) {
                    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("ctpn/{mapn}")
    public ResponseEntity<String> deleteCTDH(@PathVariable("mapn") String mapn) {
        try {
            repo.deleteCTPNByMapn(mapn);
            return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
    }
}
