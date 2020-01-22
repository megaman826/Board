package com.exam.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.dao.MemberDao;
import com.exam.vo.MemberVo;

public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/signUp.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		
		MemberDao mDao = MemberDao.getInstance();
		MemberVo mDto = new MemberVo();
		mDto.setId(id);
		mDto.setPw(pw);
		mDto.setName(name);
		mDto.setEmail(email);
		int joinResult = mDao.join(mDto);
		
		if (joinResult == 1) {
			req.setAttribute("joinResult", joinResult);
			HttpSession session = req.getSession();
			session.setAttribute("sessionID", id);
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/index.jsp");
			rd.forward(req, resp);
		} else {
			req.setAttribute("joinResult", 0);
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/signUp.jsp");
			rd.forward(req, resp);
		}
	}
	
}