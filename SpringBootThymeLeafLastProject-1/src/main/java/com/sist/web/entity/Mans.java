package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/*
JMNO int 
TITLE text 
SUBTITLE text 
POSTER text 
PRICE int 
OPTION1 text 
OPTION2 text 
JJIMCOUNT int 
HIT int
 */

@Entity(name="jmmshop") // 테이블에 있는 이름과 다르면 옆에 기재
@Data
public class Mans {
    @Id
   private int jmno;
   private String title;
   private String subtitle;
   private String poster;
   private int price;
   private String option1,option2;
   private int jjimcount;
   private int hit;
}