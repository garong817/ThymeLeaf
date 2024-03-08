package com.sist.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sist.web.dao.*;
import com.sist.web.entity.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MusicController {
	@Autowired
	   private MusicDAO dao;
	
	@GetMapping("/music/list")
	   public String music_main(String page,Model model,HttpServletRequest request)
	   {
	      //쿠키
	      Cookie[] cookies=request.getCookies();
	      
	      List<genie_music> cList=new ArrayList<genie_music>();
	      int k=0;
	      if(cookies!=null)
	      {
	         for(int i=cookies.length-1;i>=0;i--)
	         {
	            if(cookies[i].getName().startsWith("music"))
	            {
	               if(k>8)break;
	               String cno=cookies[i].getValue();
	               genie_music m=dao.findBycno(Integer.parseInt(cno));
	               cList.add(m);
	            }
	         }
	      }

	      //Model => HttpServletRequest를 대체 => 전송 객체
	      if(page==null)
	         page="1";
	      int curpage=Integer.parseInt(page);
	      int rowSize=9;
	      int start=(rowSize*curpage)-rowSize;
	      List<genie_music> list=dao.musicListData(start);
	      int count=dao.musicRowCount();
	      int totalpage=(int)(Math.ceil(count/9.0));
	      
	      final int BLOCK=10;
	      int startPage=((curpage-1)/BLOCK*BLOCK)+1;
	      int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
	      
	      if(endPage>totalpage)
	         endPage=totalpage;
	      
	      model.addAttribute("curpage",curpage);
	      model.addAttribute("totalpage",totalpage);
	      model.addAttribute("startPage",startPage);
	      model.addAttribute("endPage",endPage);
	      model.addAttribute("count",count);
	      model.addAttribute("list",list);
	      model.addAttribute("cList",cList);
	      model.addAttribute("main_html","music/list");
	      return "main";
	   }
	   
	   // 쿠키
	   @GetMapping("/music/before_detail")
	   public String music_before(int cno,RedirectAttributes ra,HttpServletResponse response)
	   {
	      // 쿠키에 저장
	      Cookie cookie=new Cookie("music"+cno,String.valueOf(cno));
	      // cookie는 저장시에 문자열만 저장이 가능
	      cookie.setPath("/");
	      cookie.setMaxAge(60*60*24);
	      response.addCookie(cookie); //클라이언트 브라우저로 전송
	      ra.addAttribute("cno",cno);
	      return "redirect:../music/detail";
	      /*
	       *   RedirectAttributes sendRedirect을 이용해서 데이터 전송
	       *   Model : forward
	       */
	   }
	   
	}
