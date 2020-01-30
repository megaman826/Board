package com.lib.action;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lib.center.AuthCenter;
import com.main.system.HttpError;
import com.main.system.Session;

import javafx.util.Pair;

public class ActionServlet extends HttpServlet {
	private ServletConfig config = null;
	private String actionPackage = "";
	
	private Map<String, RequestProcessor> requestProcessorMap = new HashMap<String, RequestProcessor>(); // name, ..
	
	public void init(ServletConfig config, String actionPackage) throws ServletException {
		// TODO Auto-generated method stub
		this.config = config;
		this.actionPackage = actionPackage;
		
		try {
			requestProcessorMap = getActionClassList().stream()
					.collect(Collectors.toMap(e->e.getName(), e->new RequestProcessor(e)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub			
		String path = req.getPathInfo();
		
		RequestProcessor rp = requestProcessorMap.get(path);
		if(rp == null) {
			return;
		}		
		
		Session session = null;
		Pair<HttpError, Session> sessionProc = AuthCenter.checkSession(req, resp);
		if(sessionProc.getKey() != HttpError.NONE)
			return;
		
		session = sessionProc.getValue();
		System.out.println(session.getId() + "/" + session.getAuthKey());
		
		rp.process();
	}
	
	private List<ActionClassWrap> getActionClassList() throws Exception{
		Class<? extends HttpServlet> servletClass = this.getClass();
		ClassLoader servletClassLoader = servletClass.getClassLoader();
		String servletClassName = servletClass.getName();
		servletClassName = servletClassName.replace('.', '/') + ".class";
		URL url = servletClassLoader.getResource(servletClassName);
		if(url == null)
			throw new Exception("## ServletClass is null");
		
		String servletClassPath = URLDecoder.decode(url.getPath(), "UTF8");
		if(!servletClassPath.endsWith(servletClassName))
			throw new Exception("## ServletClassPath Decode is incorrect");
		
		String rootClassPath = servletClassPath.substring(0, servletClassPath.length() - servletClassName.length());
		File rootClassDir = new File(rootClassPath, actionPackage.replace('.', File.separatorChar));
		
		List<String> classNames = new LinkedList<String>();
		findAllClassFile("", rootClassDir, classNames);
		
		List<ActionClassWrap> actionClassWrapList = new LinkedList<ActionClassWrap>();
		for(String className : classNames) {
			String realClassName = actionPackage + className.replace('/', '.');
			Class<?> c;
			try {
				c = servletClassLoader.loadClass(realClassName);
			}catch(ClassNotFoundException e) {
				System.out.println("## " + realClassName + " not found");
				continue;
			}
			
			if(Action.class.isAssignableFrom(c)) {
				ActionClassWrap actionClassWrap = new ActionClassWrap();
				actionClassWrap.setActionClass(c.asSubclass(Action.class));
				actionClassWrap.setName(className);
				actionClassWrapList.add(actionClassWrap);
			}
		}
		
		return actionClassWrapList;
	}
	
	private void findAllClassFile(String prefix, File rootDir, List<String> classFiles) {
		File[] filelist = rootDir.listFiles();
		if (filelist == null) { return; }
		for (File file : filelist) {
			String name = file.getName();
			if (file.isDirectory()) {
				findAllClassFile(prefix + "/" + name, file, classFiles);
			} else {
				if (file.getName().endsWith(".class")) {
					classFiles.add(prefix + "/" + name.substring(0, name.length() - 6));
				}
			}
		}
	}
}
