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
public class MansController {
   @Autowired
   private MansDAO dao;
   

   @GetMapping("/mshop/main")
   public String mshop_main(String page,Model model,HttpServletRequest request)
   {
      //쿠키
      Cookie[] cookies=request.getCookies();
      
      List<Mans> cList=new ArrayList<Mans>(); // 쿠키 맨즈리스트 출력
      int k=0;
      if(cookies!=null)
      {
         for(int i=cookies.length-1;i>=0;i--)
         {
            if(cookies[i].getName().startsWith("mshop"))
            {
               if(k>8)break;
               String jmno=cookies[i].getValue();
               Mans r=dao.findByJmno(Integer.parseInt(jmno));
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
      List<Mans> list=dao.mansListData(start);
      int count=dao.mansRowCount();
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
      model.addAttribute("main_html","mshop/main");
      return "main";
   }
   
   // 쿠키
   @GetMapping("/mshop/before_detail")
   public String mshop_before(int jmno,RedirectAttributes ra,HttpServletResponse response)
   {
      // 쿠키에 저장
      Cookie cookie=new Cookie("mshop"+jmno,String.valueOf(jmno));
      // cookie는 저장시에 문자열만 저장이 가능
      cookie.setPath("/");
      cookie.setMaxAge(60*60*24);
      response.addCookie(cookie); //클라이언트 브라우저로 전송
      ra.addAttribute("jmno",jmno);
      return "redirect:../mshop/detail";
      /*
       *   RedirectAttributes sendRedirect을 이용해서 데이터 전송
       *   Model : forward
       */
   }
   
   //상세 페이지
   @GetMapping("/mshop/detail")
   public String mshop_detail(int jmno,Model model)
   {
      Mans vo=dao.findByJmno(jmno);

      model.addAttribute("vo",vo);
      model.addAttribute("main_html","mshop/detail");
      return "main";
   }
   
   // 상품 찾기
   @RequestMapping("/mshop/find")
   public String mshop_find(String page,String title,Model model)
   {
	   if(title==null)
		   title="머슬";
	   
	   if(page==null)
		   page="1";
	   int curpage=Integer.parseInt(page);
	   int rowSize=20;
	   int start=(rowSize*curpage)-rowSize;
	   List<Mans> list=dao.mansFindData(start, title);
	   int count=dao.mansRowCount();
	   int totalpage=dao.mansFindTotalPage(title);
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
       model.addAttribute("title", title);
	   model.addAttribute("main_html", "mshop/find");
	   return "main";
   }
}