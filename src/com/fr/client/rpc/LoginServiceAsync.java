package com.fr.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	public void login(String userName, String password, boolean stayLoggedIn,
			AsyncCallback callback);

	public void requestActivation(String emailAddress, AsyncCallback callback);
}
