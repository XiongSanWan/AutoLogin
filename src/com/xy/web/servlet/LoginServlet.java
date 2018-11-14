package com.xy.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xy.domain.User;
import com.xy.service.UserService;
import com.xy.utils.MD5Utils;

public class LoginServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		UserService us = new UserService();
		try {
			User user = us.findUser(username,password);
			if(user!=null){
				String autologin = request.getParameter("autologin");
				Cookie cookie = new Cookie("user", user.getUsername()+"&"+user.getPassword());	
				cookie.setPath("/");//使cookie在不同的应用中可以被取到，本机tomcat/webapp下面有两个应用：cas和webapp_b，
				if(autologin!=null){
					cookie.setMaxAge(60*60*36*7);
				}else{
					cookie.setMaxAge(0);
				}
				response.addCookie(cookie);//将cookie添加到response中，响应给客户端
				
				request.getSession().setAttribute("user", user);//将user对象保存在session中便于不同的request 取到user的属性
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}else{
				request.setAttribute("msg", "用户名或密码错误，请重新输入");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
