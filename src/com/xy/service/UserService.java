package com.xy.service;

import java.sql.SQLException;

import com.xy.dao.UserDao;

public class UserService {

	UserDao ud = new UserDao();
	public com.xy.domain.User findUser(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return ud.findUser(username,password);
		
	}
	
}
