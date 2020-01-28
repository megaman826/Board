package com.lib.action;

public class RequestProcessor {
	private Action action;
	public RequestProcessor(ActionClassWrap actionClassWrap) {
		Class<? extends Action> actionClass = actionClassWrap.getActionClass();
		try {
			action = actionClass.newInstance();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void process() {
		action.doAction();
	}
}
