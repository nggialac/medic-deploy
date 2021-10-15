package com.abc.repository;

import com.abc.entity.Donhang;
import com.abc.entity.Phieunhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhieunhapRepository extends JpaRepository<Phieunhap, String> {

    @Query(nativeQuery = true, value= "select * from phieunhap where mapn=?1")
    List<Phieunhap> getPhieunhapByMapn(String mapn);

    @Query(nativeQuery = true, value= "select * from phieunhap where manv=?1")
    List<Phieunhap> getPhieunhapByManv(String manv);

}
