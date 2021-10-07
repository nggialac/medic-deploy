package com.abc.repository;

import com.abc.entity.Binhluan;
import com.abc.entity.Phanhoibinhluan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface PhanhoibinhluanRepository  extends JpaRepository<Phanhoibinhluan, Integer> {

//    @Query(value="Select bl from Binhluan bl join Sanpham sp on bl.sanpham.masp = sp.masp where sp.masp=?1")
//    List<Binhluan> findBinhluanByMasp(String masp);
    List<Phanhoibinhluan> findAllByBinhluan_Id(int id);

    List<Phanhoibinhluan> findAllByBinhluan_Nhathuoc_Manhathuoc(String manhathuoc);

    @Query(value="Select ph from Binhluan bl, Phanhoibinhluan ph, Nhathuoc nt where bl.nhathuoc.manhathuoc = nt.manhathuoc and bl.id = ph.binhluan.id and nt.manhathuoc=?1")
    List<Phanhoibinhluan> findPHbyNT(String manhathuoc);
}
