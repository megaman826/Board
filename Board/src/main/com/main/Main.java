package com.main;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		System.out.println("## SERVER START!!");
		
		
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		
		System.out.println("Hello World!!");
		
		String uri = req.getRequestURI();
		String servletPath = req.getServletPath();
		System.out.println(uri);
		System.out.println(servletPath);
		
		// uri를 분석 = main/action/test/TestAction
		// 해당 action 페이지를 호출  = src/action/com.action.test/TestAction.java 내 함수를 uri를 통해 호출할수있어야한다.
	}

}
