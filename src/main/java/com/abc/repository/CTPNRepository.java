package com.abc.repository;

import com.abc.entity.CTDH;
import com.abc.entity.CTPN;
import com.abc.entity.CTPN_ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CTPNRepository extends JpaRepository<CTPN, CTPN_ID> {

    @Query(nativeQuery = true, value = "select * from ctpn where mapn =?1")
    List<CTPN> getCTPNByMapn(String mapn);

//    @Query(nativeQuery = true, value = "select * from ctpn where mapn =?1")
//    List<CTPN> postCTPNByMapn(int mapn);

    @Query(nativeQuery = true, value = "delete from ctpn where mapn=?1")
    List<CTPN> deleteCTPNByMapn(String mapn);

}
