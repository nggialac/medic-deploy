package com.abc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.entity.Danhmuc;
import org.springframework.data.jpa.repository.Query;

public interface DanhmucRepository extends JpaRepository<Danhmuc, String>{

//    @Query(nativeQuery = true, value = "select dm from danhmuc dm order by dm.  desc")
    Danhmuc findFirstByOrderByMadmDesc();

    Danhmuc findByTendm(String tendm);

    Danhmuc existsDanhmucByTendm(String tendm);
}
