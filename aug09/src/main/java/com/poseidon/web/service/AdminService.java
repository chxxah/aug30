package com.poseidon.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.web.dao.AdminDAO;

@Service
public class AdminService {
	
	@Autowired
	private AdminDAO adminDAO;

	public Map<String, Object> adminLogin(Map<String, Object> map) {
		return adminDAO.adminLogin(map);
	}

	public List<Map<String, Object>> list() {
		return adminDAO.list();
	}

	public void noticeWrite(Map<String, Object> map) {
		adminDAO.noticeWrite(map);
	}

	public String noticeDetail(int nno) {
		return adminDAO.noticeDetail(nno);
	}

	public int noticeHide(int nno) {
		return adminDAO.noticeHide(nno);
	}

	public List<Map<String, Object>> multiBoard() {
		return adminDAO.multiBoard();
	}

	public void multiBoardInsert(Map<String, Object> map) {
		adminDAO.multiBoardInsert(map);
	}

	public List<String> memberList() {
		return adminDAO.memberList();
	}

	public int gradeChange(Map<String, Object> map) {
		return adminDAO.gradeChange(map);
	}

	public List<Map<String, Object>> post(Map<String, Object> map) {
		return adminDAO.post(map);
	}

	public List<Map<String, Object>> boardlist() {
		return adminDAO.boardlist();
	}

	public String content(int mbno) {
		return adminDAO.content(mbno);
	}

	

	




	

	
		
}

	
	
	
	
	
	
	

