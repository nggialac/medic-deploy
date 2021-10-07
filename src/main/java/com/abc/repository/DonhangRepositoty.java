package com.abc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abc.entity.Donhang;

public interface DonhangRepositoty extends JpaRepository<Donhang, String>{
	
	@Query(nativeQuery = true, value= "select * from donhang where manhathuoc=?1")
	List<Donhang> getDonhangByManhathuoc(String mant);
	
	@Query(nativeQuery = true, value= "select * from donhang where manv=?1")
	List<Donhang> getDonhangByManv(String manv);

}
