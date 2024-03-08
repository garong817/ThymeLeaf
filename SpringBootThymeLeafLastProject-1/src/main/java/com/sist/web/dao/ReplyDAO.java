package com.sist.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;
import com.sist.web.entity.*;

public interface ReplyDAO extends JpaRepository<Reply, Integer>{
	// 데이터 읽기
	@Query(value = "SELECT * FROM reply WHERE fno=:fno ORDER By fno DESC",
			nativeQuery = true)
	public List<Reply> replyListData(@Param("fno") int fno);
	
	public Reply findByNo(int no);
	//insert, update, delete
}
