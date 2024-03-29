package com.sist.web.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sist.web.entity.BoardEntity;
import java.util.*;
public interface BoardService extends JpaRepository<BoardEntity, Integer>{
    // 상세보기 
	public BoardEntity findByNo(int no);
	// SELECT * FROM jpaBoard WHERE no=1
	// insert , update , delete => 자동 
	// --------------- save  , delete
	// 목록 출력 
	@Query(value="SELECT * FROM jpaBoard ORDER BY no DESC LIMIT :start,10",nativeQuery = true)
	public List<BoardEntity> boardListData(@Param("start") Integer start);
	// 총페이지 
	@Query(value="SELECT CEIL(COUNT(*)/10.0) FROM jpaBoard",nativeQuery = true)
	public int boardTotalPage();
	
}