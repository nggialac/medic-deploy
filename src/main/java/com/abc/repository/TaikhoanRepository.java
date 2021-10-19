package com.abc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.entity.Taikhoan;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.abc.entity.Taikhoan;

public interface TaikhoanRepository extends JpaRepository<Taikhoan, Integer> {
    @Query(value = "select tk from Taikhoan tk where tk.username = ?1")
    Taikhoan findByUsername(String username);

    Taikhoan findTaikhoanByUsername(String username);

    @Query(value = "select tk from Taikhoan tk join Nhathuoc nt on tk.matk=nt.taikhoan.matk where nt.email = ?1")
    Taikhoan findTaikhoanByEmail(String email);

    //
    @Query(value = "select tk from Taikhoan tk join Nhanvien nv on tk.matk=nv.taikhoan.matk where nv.email = ?1")
    Taikhoan findTaikhoanNVByEmail(String email);
}
