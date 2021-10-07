package com.abc.repository;

import java.util.List;

import com.abc.entity.Danhmuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abc.entity.Sanpham;

public interface SanphamRepository extends JpaRepository<Sanpham, String>{
	@Query(value = "select * from sanpham where madm = ?1",nativeQuery = true)
	List<Sanpham> getSPByMadm(String madm);

	List<Sanpham> findByTenspContains(String tensp);

	Sanpham findByMasp(String masp);

	Sanpham findFirstByOrderByMaspDesc();
}
