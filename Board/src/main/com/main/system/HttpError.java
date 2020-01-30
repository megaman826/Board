package com.main.system;

public enum HttpError {
	NONE(0)
	;	

	private final int value;
    private HttpError(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }	
}