package com.sist.web.entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name="jpaboard")
@DynamicUpdate
// update시에 제외
@Data
// 추가적인 변수가 존재하면 안된다 => insert,update
public class BoardEntity {
   @Id
   private int no;
   private String name,subject,content;
   
   @Column(insertable = true,updatable = false)
   private String pwd;
   
   @Column(insertable = true,updatable = false)
   private int hit;
   
   @Column(insertable = true,updatable = false)
   private String regdate;
   
   @PrePersist
   public void regdate() {
	   this.regdate=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   }
}