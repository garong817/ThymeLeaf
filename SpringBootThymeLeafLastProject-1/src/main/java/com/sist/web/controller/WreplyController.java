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
public class WreplyController {
	@Autowired
	private WreplyDAO dao;
	
	@PostMapping("/wreply/insert")
	public String replyInsert(Wreply vo,HttpSession session,RedirectAttributes ra)
	{
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		vo.setId(id);
		vo.setName(name);
		dao.save(vo); // insert
		ra.addAttribute("jwno",vo.getJwno());
		return "redirect:/wshop/detail";
	}
	@GetMapping("/wreply/delete")
	public String replyDelete(int jwno,int no,RedirectAttributes ra)
	{
		Wreply vo=dao.findByNo(no);
		dao.delete(vo); //vo를 가져오라고함 
		ra.addAttribute("jwno",jwno);
		return "redirect:/wshop/detail";
	}
	@PostMapping("/wreply/update")
	public String replyUpdate(Wreply vo,RedirectAttributes ra)
	{
		dao.save(vo);
		ra.addAttribute("jwno",vo.getJwno());
		return "redirect:/wshop/detail";
	}
}















