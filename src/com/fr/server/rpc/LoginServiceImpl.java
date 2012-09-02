package com.fr.server.rpc;

import com.fr.client.rpc.LoginService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1L;

	public String login(String userName, String password, boolean stayLoggedIn) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean requestActivation(String emailAdress) {
		// TODO Auto-generated method stub
		return false;
	}

}
