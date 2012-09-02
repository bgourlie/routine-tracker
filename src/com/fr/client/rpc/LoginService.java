package com.fr.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;

public interface LoginService extends RemoteService {
	public String login(String userName, String password, boolean stayLoggedIn);

	public boolean requestActivation(String emailAdress);
}
