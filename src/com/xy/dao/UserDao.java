package com.xy.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.xy.domain.User;
import com.xy.utils.Utils;

public class UserDao {

	public User findUser(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(Utils.getDatasource());
		return qr.query("select * from user where username=? and password=?", new BeanHandler<User>(User.class),username,password);
	}
}
