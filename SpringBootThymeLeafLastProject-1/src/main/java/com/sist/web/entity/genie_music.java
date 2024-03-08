package com.sist.web.entity;
/*
NO int 
CNO int 
TITLE text 
SINGER text 
ALBUM text 
POSTER text 
STATE text 
IDCREMENT int
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class genie_music {
	@Id
	private int no;
	private int cno;
	private String title;
	private String singer;
	private String album;
	private String poster;
	private String state;
	private int idcrement;
}
