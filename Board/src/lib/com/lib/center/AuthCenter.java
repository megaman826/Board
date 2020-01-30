package com.lib.center;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;

import com.main.system.HttpError;
import com.main.system.Session;

import javafx.util.Pair;

public class AuthCenter {

	private static Map<String, Session> sessionMap = new HashMap<String, Session>(); // id, ..
	
	private final static String idKey = "idKey";
	private final static String sessionKey = "sessionKey";	
	
	public static Pair<HttpError, Session> checkSession(HttpServletRequest req, HttpServletResponse resp) {		
		Cookie[] cookies = req.getCookies();
		
		String id = null;
		String authKey = null;
		
		for(int i = 0; i < cookies.length; i++) {
			String name = cookies[i].getName();
			String value = cookies[i].getValue();
			
			switch(name) {
			case idKey:
				id = value;
				break;
			case sessionKey:
				authKey = value;				
				break;
			}			
		}
		
		if(id == null) {
			System.out.println("## idCookie�� �������� �ʽ��ϴ�.");
			
			id = "cookie";
			
			Cookie idCookie = new Cookie(idKey, id);
			idCookie.setMaxAge(10); // 10��
			idCookie.setPath("/");
			resp.addCookie(idCookie);
		}
		
		if(authKey == null) {
			System.out.println("## sessionCookie�� �������� �ʽ��ϴ�.");
			
			authKey = RandomStringUtils.randomAscii(10);
			System.out.println(authKey);
			
			Cookie sessionCookie = new Cookie(sessionKey, authKey);
			sessionCookie.setMaxAge(10); // 10��
			sessionCookie.setPath("/");
			resp.addCookie(sessionCookie);
		}

		Session session = null;
		if(sessionMap.containsKey(id)) {
			session = sessionMap.get(id);
			authKey = "default";
		}else {
			session = new Session(id, authKey);
			sessionMap.put(id, session);
		}
		
		if(!session.getAuthKey().equals(authKey)) {
			System.out.println("## sessionKey�� ��ġ���� �ʽ��ϴ�.");
			session.setAuthKey(authKey);
		}
		
		return new Pair<HttpError, Session>(HttpError.NONE, session);
	}
}
