package com.lib.center;

import java.time.Instant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.system.HttpError;
import com.main.system.Session;

import javafx.util.Pair;

public class AuthCenter {
	public static Pair<HttpError, Session> checkSession(HttpServletRequest req, HttpServletResponse res) {
		Instant now = Instant.now();
		
		Cookie[] cookies = req.getCookies();
		if (cookies == null) {
			return new Pair<HttpError, Session>(HttpError.NOT_EXIST_COOKIE, null);
		}
				
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			String value = cookie.getValue();
			System.out.println(now + "/" + name + "/" + value);
		}
		
		return new Pair<HttpError, Session>(HttpError.NONE, null);
	}
}
