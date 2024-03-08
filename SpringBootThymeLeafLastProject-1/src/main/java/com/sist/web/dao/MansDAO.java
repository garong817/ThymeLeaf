package com.sist.web.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.sist.web.entity.*;
@Repository
public interface MansDAO extends JpaRepository<Mans, Integer>{
   // JPQL / 일반 SQL => nativeQuery = true
      // => 조인 / 서브쿼리
   /*
    * private int jwno,price;
   private String title,subtitle,poster,option1,option2;
    */
   @Query(value = "SELECT jmno,price,title,subtitle,poster,jjimcount,hit,option1,option2 "
            +"FROM jmmshop ORDER BY jmno ASC "
            +"LIMIT 0,8",nativeQuery = true)
   public List<Mans> mansMainData();
   
   
   // 상세페이지
   public Mans findByJmno(int jmno); // 메소드로 SQL문장 생성
   //findByNo , findByTitle .... => where no , where title...
   // findByNoAndTitle => where no and title 
   // findByNameLike => where name LIKE
      
   //목록 출력
   @Query(value = "SELECT * "
         +"FROM jmmshop ORDER BY jmno ASC "
         +"LIMIT :start,9",nativeQuery = true)   //nativeQuery 있는 그대로실행 변경하지말고
   public List<Mans> mansListData(@Param("start") int start);
      
   @Query(value = "SELECT COUNT(*) FROM jmmshop",nativeQuery = true)
   public int mansRowCount();
   
   // 상품 찾기
   @Query(value = "SELECT * "
	         +"FROM jmmshop WHERE title LIKE CONCAT('%',:title,'%') "
	         +"LIMIT :start,20",nativeQuery = true)
	   public List<Mans> mansFindData(@Param("start") Integer start,@Param("title") String title);
	   
	   @Query(value = "SELECT CEIL(COUNT(*)/20.0) FROM jmmshop "
	         +"WHERE title LIKE CONCAT('%',:title,'%')",nativeQuery = true)
	   public int mansFindTotalPage(@Param("title") String title);

}
