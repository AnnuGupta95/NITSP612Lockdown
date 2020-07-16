package com.nt.manager;

import org.springframework.beans.BeanUtils;

import com.nt.bo.UserDetailsBO;
import com.nt.dao.AuthenticationDAO;
import com.nt.dto.UserDetailsDTO;

public class AuthenticationManager {
	private AuthenticationDAO dao;
	private ThreadLocal<UserDetailsDTO> threadLocal=new ThreadLocal();

	public AuthenticationManager(AuthenticationDAO dao) {
		this.dao = dao;
	}
	
	public void signIn(String username,String password) {
		UserDetailsDTO dto=null;
		//convert username,password into UserDetailsDTO class object
		dto=new UserDetailsDTO();
		dto.setUsername(username);
		dto.setPwd(password);
		threadLocal.set(dto);
	}
	
	public  void singnOut() {
		threadLocal.remove();
	}
	
	public  boolean isAuthenticated() {
		UserDetailsDTO dto=null;
		UserDetailsBO bo=null;
		int count=0;
		//get Current client's/thread's UserDetailsDTO class object
		dto=threadLocal.get();
		//convert dto to bo
		bo=new UserDetailsBO();
		BeanUtils.copyProperties(dto,bo);
		//use DAO
		count=dao.validate(bo);
		return count==0?false:true;
		
		
	}
	

}
