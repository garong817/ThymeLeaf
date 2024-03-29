package com.sist.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sist.web.dao.*;
import com.sist.web.entity.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

@Controller
public class SportsController {
	@Autowired
	private SportsDAO dao;
	
	@GetMapping("/yp/main")
	   public String yp_main(String page,Model model,HttpServletRequest request)
	   {
	      //쿠키
	      Cookie[] cookies=request.getCookies();
	      
	      List<SportsReserve> cList=new ArrayList<SportsReserve>();
	      int k=0;
	      if(cookies!=null)
	      {
	         for(int i=cookies.length-1;i>=0;i--)
	         {
	            if(cookies[i].getName().startsWith("yp"))
	            {
	               if(k>8)break;
	               String no=cookies[i].getValue();
	               SportsReserve r=dao.findByNo(Integer.parseInt(no));
	               cList.add(r);
	            }
	         }
	      }
	          
	      //Model => HttpServletRequest를 대체 => 전송 객체
	      if(page==null)
	         page="1";
	      int curpage=Integer.parseInt(page);
	      int rowSize=9;
	      int start=(rowSize*curpage)-rowSize;
	      List<SportsReserve> list=dao.sportsListData(start);
	      int count=dao.sportsRowCount();
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
	      model.addAttribute("main_html","yp/main");
	      return "main";
	   }
	   
	   // 쿠키
	   @GetMapping("/yp/before_detail")
	   public String yp_before(int no,RedirectAttributes ra,HttpServletResponse response)
	   {
	      // 쿠키에 저장
	      Cookie cookie=new Cookie("yp"+no,String.valueOf(no));
	      // cookie는 저장시에 문자열만 저장이 가능
	      cookie.setPath("/");
	      cookie.setMaxAge(60*60*24);
	      response.addCookie(cookie); //클라이언트 브라우저로 전송
	      ra.addAttribute("no",no);
	      return "redirect:../yp/detail";
	      /*
	       *   RedirectAttributes sendRedirect을 이용해서 데이터 전송
	       *   Model : forward
	       */
	   }
	   
	   //상세 페이지
	   @GetMapping("/yp/detail")
	   public String yp_detail(int no,Model model)
	   {
		  SportsReserve vo=dao.findByNo(no);

	      model.addAttribute("vo",vo);
	      model.addAttribute("main_html","yp/detail");
	      return "main";
	   }
	   
	   // 상품 찾기
	   @RequestMapping("/yp/find")
	   public String yp_find(String page,String address,Model model)
	   {
		   if(address==null)
			   address="인천";
		   
		   if(page==null)
			   page="1";
		   int curpage=Integer.parseInt(page);
		   int rowSize=20;
		   int start=(rowSize*curpage)-rowSize;
		   List<SportsReserve> list=dao.sportsFindData(start, address);
		   int count=dao.sportsRowCount();
		   int totalpage=dao.sportsFindTotalPage(address);
		   final int BLOCK=10;
		   int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		   int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		   
		   if(endPage>totalpage)
			   endPage=totalpage;
		   
		   model.addAttribute("curpage", curpage);
		   model.addAttribute("totalpage", totalpage);
		   model.addAttribute("startPage", startPage);
		   model.addAttribute("endPage", endPage);
		   model.addAttribute("count",count);
		   model.addAttribute("list", list);
	       model.addAttribute("address", address);
		   model.addAttribute("main_html", "yp/find");
		   return "main";
	   }
}
