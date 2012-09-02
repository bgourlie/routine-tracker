package com.fr.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class LoginServiceAccess extends ServiceAccess {

	private final static String LOGIN_SERVICE = "LoginService";

	private LoginServiceAsync loginService = null;

	public LoginServiceAsync getService() {
		if (loginService == null) {
			initService();
		}

		return loginService;
	}

	protected void initService() {
		loginService = (LoginServiceAsync) GWT.create(LoginService.class);

		ServiceDefTarget endpoint = (ServiceDefTarget) loginService;
		endpoint.setServiceEntryPoint(getServiceURL(LOGIN_SERVICE));
	}

}
