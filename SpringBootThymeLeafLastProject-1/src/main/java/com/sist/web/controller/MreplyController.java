package com.sist.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import com.sist.web.dao.*;
import com.sist.web.entity.*;

import jakarta.servlet.http.HttpSession;
@Controller
public class MreplyController {
	@Autowired
	private MreplyDAO dao;
	
	@PostMapping("/mreply/insert")
	public String replyInsert(Mreply vo,HttpSession session,RedirectAttributes ra)
	{
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		vo.setId(id);
		vo.setName(name);
		dao.save(vo); // insert
		ra.addAttribute("jmno",vo.getJmno());
		return "redirect:/mshop/detail";
	}
	@GetMapping("/mreply/delete")
	public String replyDelete(int jmno,int no,RedirectAttributes ra)
	{
		Mreply vo=dao.findByNo(no);
		dao.delete(vo); //vo를 가져오라고함 
		ra.addAttribute("jmno",jmno);
		return "redirect:/mshop/detail";
	}
	@PostMapping("/mreply/update")
	public String replyUpdate(Mreply vo,RedirectAttributes ra)
	{
		dao.save(vo);
		ra.addAttribute("jmno",vo.getJmno());
		return "redirect:/mshop/detail";
	}
}
