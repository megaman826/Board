package com.main.system;

public enum HttpError {
	SESSION_PROCESSING(7),
	INVALID_SESSION_TOKEN(6),
	EXPIRED_SESSION(5),
	NOT_EXIST_AUTH_TOKEN(4),
	NOT_EXIST_INNER_ID(3),
	NOT_EXIST_COOKIE(2),
	NOT_FOUND(1),
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