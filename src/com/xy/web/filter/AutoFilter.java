package com.xy.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xy.domain.User;
import com.xy.service.UserService;

public class AutoFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();// /autoLogin/login.jsp
		String path = req.getContextPath();// /autoLogin
		path = uri.substring(path.length());
		
		if(!(("/login.jsp".equals(path))||("/servlet/loginServlet".equals(path)))){//��������Щ·����ʱ�������ִ��
			User user = (User) req.getSession().getAttribute("user");
			if(user==null){	//��ֹ�ظ���¼����home��jsp��ת��������jspʱ�����Ѿ���¼�ظ���¼��Դ���˷ѡ�
		Cookie[] cookies = req.getCookies();
		String username="";
		String password="";
		for (int i = 0; cookies !=null &&i < cookies.length; i++) {
			if("user".equals(cookies[i].getName())){
				System.out.println(cookies[i].getName());
				String value = cookies[i].getValue();
				String[] split = value.split("&");
					username = split[0];
					password = split[1];
					System.out.println(username);
					System.out.println(password);
			}
		}
		UserService us = new UserService();
		try {
			User u = us.findUser(username, password);
			if(u!=null){
			req.getSession().setAttribute("user", u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
			chain.doFilter(request, response);
		}else{
			System.out.println("����");
		chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
