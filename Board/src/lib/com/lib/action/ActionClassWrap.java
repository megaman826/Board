package com.lib.action;

public class ActionClassWrap {
	private Class<? extends Action> actionClass;
	private String name;
	
	public Class<? extends Action> getActionClass() {
		return actionClass;
	}
	public void setActionClass(Class<? extends Action> actionClass) {
		this.actionClass = actionClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
