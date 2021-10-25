package com.abc.controller;

import com.abc.dao.DAO;
import com.abc.entity.*;
import com.abc.model.Dathang;
import com.abc.model.DoanhThuHangThang;
import com.abc.model.DoanhThuTheoNgay;
import com.abc.model.TopSanPham;
import com.abc.repository.CTPNRepository;
import com.abc.repository.NhanvienRepository;
import com.abc.repository.PhieunhapRepository;
import com.abc.repository.SanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PhieunhapController {

    @Autowired
    PhieunhapRepository repo;

    @Autowired
    CTPNRepository ctpnRepo;

    @Autowired
    SanphamRepository spRepo;

    @Autowired
    NhanvienRepository nvRepo;

    @GetMapping("/phinhap/{nam}")
    public ArrayList<DoanhThuHangThang> getPhiNhap(@PathVariable("nam") int nam) {
        return new DAO().getPhiNhapHangThang(nam);
    }

    @GetMapping("/phinhap/{from}/{to}")
    public ArrayList<DoanhThuTheoNgay> getPhiNhapTheoNgay(@PathVariable("from") String from, @PathVariable("to") String to) {
        return new DAO().getPhiNhapTheoNgay(from, to);
    }

    @GetMapping("/topsp/{top}")
    public ArrayList<TopSanPham> getTopSP(@PathVariable("top") int top) {
        return new DAO().getTop(top);
    }

    @GetMapping("/phieunhap")
    public List<Phieunhap> getListPN(){
        return repo.findAll(Sort.by(Sort.Order.desc("ngaylap")));
    }

    @GetMapping("/phieunhap/{mapn}")
    public List<Phieunhap> getPhieunhapByManv(@PathVariable("mapn") String mapn){
        List<Phieunhap> list = repo.getPhieunhapByMapn(mapn);
        java.util.Collections.sort(list,new Comparator<Phieunhap>() {

            @Override
            public int compare(Phieunhap o1, Phieunhap o2) {
                int cpTT = o2.getNgaylap().compareTo(o1.getNgaylap());
//                if(cpTT != 0) {
//                    return o2.getNgaylap().compareTo(o1.getNgaylap());
//                }
                return o2.getNgaylap().compareTo(o1.getNgaylap());
            }
        });
        return list;
    }

    @PostMapping("/phieunhap/{manv}")
    public ResponseEntity<String> insertPhieunhap(@Validated @RequestBody List<Dathang> listDH, @PathVariable("manv") String manv) {
        try {
            Phieunhap phieunhap = new Phieunhap();
            String mapn = "PN" +  System.currentTimeMillis() % 100000000;
            float tongtien = 0;
            for(Dathang dh : listDH) {
                tongtien += dh.getDongia() * dh.getSoluong();
            }

            phieunhap.setMapn(mapn);
            phieunhap.setTrangthai(0);
//              Timestamp tm = new Timestamp(System.currentTimeMillis());
//
//              donhang.setNgaydat(tm);
//            donhang.setHinhthucthanhtoan(hinhthucthanhtoan);
//            donhang.setTrangthai(0);
//            if(!paymentCreated.isEmpty()) donhang.setPaymentcreated(paymentCreated);
//            else donhang.setPaymentcreated(null);

            Nhanvien nhanvien = new Nhanvien();
            nhanvien.setManv(manv);
            phieunhap.setNhanvien(nhanvien);
            phieunhap.setTongtien(tongtien);
            //save donhang
            try {
                repo.save(phieunhap);

                //save ctdh
                for(Dathang dh : listDH) {
                    CTPN ctpn = new CTPN();
                    CTPN_ID id = new CTPN_ID(mapn,dh.getMasp());
                    ctpn.setPhieunhap(phieunhap);
                    ctpn.setId(id);
                    Sanpham sanpham = new Sanpham();
                    sanpham.setMasp(dh.getMasp());

//                    sanpham = spRepo.findByMasp(dh.getMasp());
//                    sanpham.setSoluong(sanpham.getSoluong() + dh.getSoluong());

                    ctpn.setSanpham(sanpham);
                    ctpn.setSoluong(dh.getSoluong());
                    ctpn.setDongia(dh.getDongia());
                    ctpnRepo.save(ctpn);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return new ResponseEntity<String>("Không thể thêm phiếu nhập!", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<String>("Succeeded !!!",HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/phieunhap/{mapn}")
    public ResponseEntity<String> deletePhieunhapById(@PathVariable("mapn") String mapn) {
        List<CTPN> listCT = ctpnRepo.findAll();
        try {
            for(CTPN ct : listCT) {
                if(ct.getId().getMapn().equalsIgnoreCase(mapn)) {
                    ctpnRepo.delete(ct);
                }
            }
            repo.deleteById(mapn);
            return new ResponseEntity<String>("Succeeded !!!",HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/phieunhap")
    public ResponseEntity<String> updatePhieunhap(@Validated @RequestBody Phieunhap phieunhap) {
        try {
            List<Phieunhap> listPN = repo.findAll();
            for(Phieunhap pn : listPN) {
                if (pn.getMapn().equalsIgnoreCase(phieunhap.getMapn())) {
					pn.setNhanvien(phieunhap.getNhanvien());
                    repo.save(phieunhap);
                    return new ResponseEntity<String>("Succeeded !!!",HttpStatus.OK);
                }
            }
            return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Failed !!!",HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/phieunhap/ctt/{mapn}")
    public ResponseEntity<Boolean> chuyenPhieunhap(@PathVariable("mapn") String mapn, @RequestParam(name = "manv",required = false) String manv) {
        Optional<Phieunhap> dh = repo.findById(mapn);
        if(dh.get().getTrangthai()==0) {
            try {
                dh.get().setTrangthai(1);

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

    @PutMapping("/phieunhap/{mapn}")
    public ResponseEntity<Boolean> huyPhieunhap(@PathVariable("mapn") String mapn, @RequestParam(name = "manv",required = false) String manv) {
        Optional<Phieunhap> dh = repo.findById(mapn);
        if(dh.get().getTrangthai()==0) {
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
}




