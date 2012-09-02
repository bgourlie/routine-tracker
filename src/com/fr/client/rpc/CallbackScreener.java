package com.fr.client.rpc;

import com.fr.client.common.Dialogs;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CallbackScreener implements AsyncCallback {
	private ScreenedCallback callback = null;

	public CallbackScreener(ScreenedCallback callback) {
		this.callback = callback;
	}

	public void onFailure(Throwable caught) {
		// check for a AuthenticationFailed exception
		// and if found, take appropriate action.
		// otherwise pass the exception onto the
		// page level callback

		if (caught instanceof DatabaseException) {
			Dialogs.databaseError(caught.getMessage());
		} else if (caught instanceof AuthenticationException) {

			Dialogs.authenticationFailed();
		} else {
			System.out.println(caught.getMessage());
			caught.printStackTrace();
			Dialogs.unknownRPCError(caught.getMessage());
		}

	}

	public void onSuccess(Object result) {

		if (callback != null) {
			callback.onSuccess(result);
		}

	}

}
