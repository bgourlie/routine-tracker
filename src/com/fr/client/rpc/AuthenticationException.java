package com.fr.client.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthenticationException extends RuntimeException implements
		IsSerializable {

	private static final long serialVersionUID = 1L;

	public AuthenticationException() {
	}

	public AuthenticationException(String s) {
		super(s, null);
	}
}
