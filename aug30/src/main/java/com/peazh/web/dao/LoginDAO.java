package com.peazh.web.dao;

import org.apache.ibatis.annotations.Mapper;

import com.peazh.web.dto.LoginDTO;

@Mapper
public interface LoginDAO {

	LoginDTO login(LoginDTO dto);
	
}
