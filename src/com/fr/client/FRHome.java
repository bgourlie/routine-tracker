package com.fr.client;

import com.fr.client.common.Module;
import com.fr.client.common.User;
import com.fr.client.rpc.CallbackScreener;
import com.fr.client.rpc.LoginServiceAccess;
import com.fr.client.rpc.ScreenedCallback;
import com.fr.client.ui.FRButton;
import com.fr.client.ui.FRPasswordTextBox;
import com.fr.client.ui.FRTextBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FRHome implements EntryPoint, ClickListener {

	private final FRButton btnLogin = new FRButton("Login");

	private final FRButton btnSignup = new FRButton("Signup");

	private final CheckBox chkStayLoggedIn = new CheckBox(
			" Stay logged in at this computer");

	private LoginServiceAccess loginServiceAccess = null;

	private final FRTextBox txtEmailAddress = new FRTextBox("E-Mail Address");

	private final FRPasswordTextBox txtPassword = new FRPasswordTextBox();

	private final FRTextBox txtUserName = new FRTextBox("User Name");

	private ScreenedCallback loginCallback() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				System.out.println("Login RPC returned");
				String token = (String) result;
				if (token == null) {
					Window.alert("Login Failed");
				} else {
					User.instance().setToken((String) result);
					Module.redirect(Module.MODULE_ROUTINE);
				}
			}
		};
	}

	public void onClick(Widget sender) {

		if (sender == btnLogin) {

			loginServiceAccess.getService().login(txtUserName.getText(),
					txtPassword.getText(), (!chkStayLoggedIn.isChecked()),
					new CallbackScreener(loginCallback()));
			System.out.println("login rpc dispatched");
		} else if (sender == btnSignup) {

			System.out.println("Signup Clicked");

			loginServiceAccess.getService().requestActivation(
					txtEmailAddress.getText(),
					new CallbackScreener(requestActivationCallback()));
		}

	}

	public void onModuleLoad() {

		btnLogin.addClickListener(this);
		btnSignup.addClickListener(this);

		RootPanel.get("FRHome-phTxtUserName").add(txtUserName);
		RootPanel.get("FRHome-phTxtPassword").add(txtPassword);
		RootPanel.get("FRHome-phChkStayLoggedIn").add(chkStayLoggedIn);
		RootPanel.get("FRHome-phBtnLogin").add(btnLogin);
		RootPanel.get("FRHome-phTxtEmailAddress").add(txtEmailAddress);
		RootPanel.get("FRHome-phBtnSignup").add(btnSignup);
		loginServiceAccess = new LoginServiceAccess();
	}

	private ScreenedCallback requestActivationCallback() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				System.out.println("requestActivation() success");
			}
		};
	}
}
