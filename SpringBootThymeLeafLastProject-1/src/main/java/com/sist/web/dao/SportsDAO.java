package com.sist.web.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sist.web.entity.*;
import java.util.*;

@Repository
public interface SportsDAO extends JpaRepository<SportsReserve, Integer>{
	@Query(value = "SELECT no,type,title,address,time,poster,phone,hit,jjimcount "
            +"FROM SportsReserve ORDER BY no ASC "
            +"LIMIT 0,8",nativeQuery = true) // 원래는 JPQL 구문으로 써야하는데 SQL문으로 사용하려고 true를 준다
	
	// JPA의 규칙
   public List<SportsReserve> sportsMainData();
	
	 //상세페이지
	   public SportsReserve findByNo(int no); // 메소드로 SQL문장 생성
	   
	 //목록 출력
	   @Query(value = "SELECT * "
	         +"FROM SportsReserve ORDER BY no ASC "
	         +"LIMIT :start,9",nativeQuery = true)   //nativeQuery 있는 그대로실행 변경하지말고
	   public List<SportsReserve> sportsListData(@Param("start") int start);
	      
	   @Query(value = "SELECT COUNT(*) FROM SportsReserve",nativeQuery = true)
	   public int sportsRowCount();
	   
	 // 업체 찾기
	   @Query(value = "SELECT * "
		         +"FROM SportsReserve WHERE address LIKE CONCAT('%',:address,'%') "
		         +"LIMIT :start,20",nativeQuery = true)
		   public List<SportsReserve> sportsFindData(@Param("start") Integer start,@Param("address") String address);
		   
		   @Query(value = "SELECT CEIL(COUNT(*)/20.0) FROM SportsReserve "
		         +"WHERE address LIKE CONCAT('%',:address,'%')",nativeQuery = true)
		   public int sportsFindTotalPage(@Param("address") String address);
	   
	   

}
