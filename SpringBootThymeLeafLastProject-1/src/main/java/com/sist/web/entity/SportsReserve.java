package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
/*
NO int 
TYPE int 
TITLE text 
ADDRESS text 
TIME text 
POSTER text 
PHONE text 
HIT int 
JJIMCOUNT int
 */
@Entity(name = "sportsreserve")
@Data
public class SportsReserve {
	@Id
	private int no;
	private int type;
	private String title;
	private String address;
	private String time;
	private String poster;
	private String phone;
	private int hit;
	private int jjimcount;
}
