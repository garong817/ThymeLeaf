package com.sist.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;
import com.sist.web.entity.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import com.sist.web.dao.*;
@Controller
public class MainController {
   @Autowired
   private WomenDAO dao;
   @GetMapping("/")
   public String main_page(Model model,HttpServletRequest request)
   {
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
				   String no=cookies[i].getValue();
				   Women w=dao.findByJwno(Integer.parseInt(no));
				   cList.add(w);
				   k++;
			   }
		   }
	   }
	   model.addAttribute("cList", cList);
	   
	   List<Women> list=dao.womenMainData();
	   model.addAttribute("list", list);
	   
	   model.addAttribute("main_html", "main/home");
	   return "main";
   }
}