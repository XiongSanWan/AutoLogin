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
				cookie.setPath("/");//ʹcookie�ڲ�ͬ��Ӧ���п��Ա�ȡ��������tomcat/webapp����������Ӧ�ã�cas��webapp_b��
				if(autologin!=null){
					cookie.setMaxAge(60*60*36*7);
				}else{
					cookie.setMaxAge(0);
				}
				response.addCookie(cookie);//��cookie��ӵ�response�У���Ӧ���ͻ���
				
				request.getSession().setAttribute("user", user);//��user���󱣴���session�б��ڲ�ͬ��request ȡ��user������
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}else{
				request.setAttribute("msg", "�û����������������������");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
