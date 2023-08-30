package com.peazh.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.peazh.web.dto.LoginDTO;
import com.peazh.web.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(LoginDTO dto, HttpSession session, HttpServletRequest request, @RequestParam("id") String id) {
		// System.out.println(dto.toString()); id=peazh, pw=0000
		LoginDTO result= loginService.login(dto);
		System.out.println(result.toString());//LoginDTO(count=1, id=admin, pw=null, m_name=관리자)
		System.out.println(id);
		System.out.println(request.getParameter("id"));
		if (result != null && result.getCount() == 1) {
			
			return "redirect:/board";
			
		} else {
			return "redirect:/login";
		}
	}
}
