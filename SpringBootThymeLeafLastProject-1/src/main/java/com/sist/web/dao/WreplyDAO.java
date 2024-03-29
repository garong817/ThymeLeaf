package com.sist.web.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;
import com.sist.web.entity.*;
public interface WreplyDAO extends JpaRepository<Wreply, Integer>{
	//데이터 읽기
	@Query(value="SELECT * FROM wreply WHERE jwno=:jwno ORDER BY jwno DESC",
			nativeQuery = true)
	public List<Wreply> replyListData(@Param("jwno") int jwno);
	
	//찾기
	public Wreply findByNo(int no);
	// insert , update , delete
}