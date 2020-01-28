package com.main;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.lib.action.ActionServlet;

public class Main extends ActionServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config, "com.action");
		
		System.out.println("## SERVER START!!");
	}
}
