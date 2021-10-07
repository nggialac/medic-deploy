package com.abc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.entity.Binhluan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BinhluanRepository extends JpaRepository<Binhluan, Integer>{
    @Query(value="Select bl from Binhluan bl join Sanpham sp on bl.sanpham.masp = sp.masp where sp.masp=?1")
    List<Binhluan> findBinhluanByMasp(String masp);

    @Query(value="Select bl from Binhluan bl join Nhathuoc nt on bl.nhathuoc.manhathuoc = nt.manhathuoc where nt.manhathuoc=?1")
    List<Binhluan> findBinhluanByManhathuoc(String manhathuoc);

    List<Binhluan> findAllByNhathuoc_Manhathuoc(String manhathuoc);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update BINHLUAN set NOIDUNG=?3, TIME=GETDATE() where manhathuoc = ?1 and masp =?2 ")
    void updateBinhLuan(String manhathuoc,String masp,String noidung);
}
