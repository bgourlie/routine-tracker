package com.fr.client.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatabaseException extends RuntimeException implements
		IsSerializable {
	private static final long serialVersionUID = 1L;

	public DatabaseException() {
	}

	public DatabaseException(String s) {
		super(s, null);
	}

}
