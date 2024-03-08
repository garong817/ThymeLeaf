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
public class WomenController {
   @Autowired
   private WomenDAO dao;
   

   @GetMapping("/wshop/main")
   public String wshop_main(String page,Model model,HttpServletRequest request)
   {
      //쿠키
      Cookie[] cookies=request.getCookies();
      
      List<Women> cList=new ArrayList<Women>();
      int k=0;
      if(cookies!=null)
      {
         for(int i=cookies.length-1;i>=0;i--)
         {
            if(cookies[i].getName().startsWith("wshop"))
            {
               if(k>8)break;
               String jwno=cookies[i].getValue();
               Women r=dao.findByJwno(Integer.parseInt(jwno));
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
      List<Women> list=dao.womenListData(start);
      int count=dao.womenRowCount();
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
      model.addAttribute("main_html","wshop/main");
      return "main";
   }
   
   // 쿠키
   @GetMapping("/wshop/before_detail")
   public String wshop_before(int jwno,RedirectAttributes ra,HttpServletResponse response)
   {
      // 쿠키에 저장
      Cookie cookie=new Cookie("wshop"+jwno,String.valueOf(jwno));
      // cookie는 저장시에 문자열만 저장이 가능
      cookie.setPath("/");
      cookie.setMaxAge(60*60*24);
      response.addCookie(cookie); //클라이언트 브라우저로 전송
      ra.addAttribute("jwno",jwno);
      return "redirect:../wshop/detail";
      /*
       *   RedirectAttributes sendRedirect을 이용해서 데이터 전송
       *   Model : forward
       */
   }
   
   //상세 페이지
   @GetMapping("/wshop/detail")
   public String wshop_detail(int jwno,Model model)
   {
      Women vo=dao.findByJwno(jwno);

      model.addAttribute("vo",vo);
      model.addAttribute("main_html","wshop/detail");
      return "main";
   }
   
   // 상품 찾기
   @RequestMapping("/wshop/find")
   public String wshop_find(String page,String title,Model model)
   {
	   if(title==null)
		   title="슬림";
	   
	   if(page==null)
		   page="1";
	   int curpage=Integer.parseInt(page);
	   int rowSize=20;
	   int start=(rowSize*curpage)-rowSize;
	   List<Women> list=dao.womenFindData(start, title);
	   int count=dao.womenRowCount();
	   int totalpage=dao.womenFindTotalPage(title);
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
	   model.addAttribute("main_html", "wshop/find");
	   return "main";
   }
}