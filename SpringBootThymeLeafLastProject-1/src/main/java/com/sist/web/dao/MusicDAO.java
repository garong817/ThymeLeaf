package com.sist.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.web.entity.*;

public interface MusicDAO extends JpaRepository<genie_music, Integer>{

	@Query(value = "SELECT no,cno,title,singer,album,poster,state,idcrement "
            +"FROM genie_music ORDER BY jwno ASC "
            +"LIMIT 0,8",nativeQuery = true)
   public List<genie_music> musicMainData();
   
   //찾기
   public genie_music findBycno(int cno);
   
 //목록 출력
   @Query(value = "SELECT * "
         +"FROM genie_music ORDER BY cno ASC "
         +"LIMIT :start,9",nativeQuery = true)   //nativeQuery 있는 그대로실행 변경하지말고
   public List<genie_music> musicListData(@Param("start") int start);
   
   @Query(value = "SELECT COUNT(*) FROM genie_music",nativeQuery = true)
   public int musicRowCount();
}
