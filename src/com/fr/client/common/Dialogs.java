package com.fr.client.common;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public final class Dialogs {

	private static DialogBox currentDialog = null;

	public static void authenticationFailed() {

		DialogBox dialog = new DialogBox();
		VerticalPanel body = new VerticalPanel();
		Button btnOkay = new Button("Okay", goHome());

		dialog.setText("Authentication Error");

		String bodyText = "You have been logged out.  This could have happened for the following reasons:"
				+ "<ul><li>A long period of inactivity</li>"
				+ "<li>Your username has logged in at a different location</li></ul>";

		body.add(new HTML(bodyText));
		body.add(btnOkay);

		dialog.setWidget(body);
		currentDialog = dialog;
		dialog.show();
	}

	// TODO eventually pass the DatabaseException to the dialog to display
	// relevent info that
	// we could use as bug feedback once we do alpha testing
	public static void databaseError(String errorMessage) {
		DialogBox dialog = new DialogBox();
		VerticalPanel body = new VerticalPanel();
		Button btnOkay = new Button("Okay", goHome());

		dialog.setText("Database Error");

		String bodyText = "A database error occured:<br><br>" + errorMessage;

		body.add(new HTML(bodyText));
		body.add(btnOkay);
		dialog.setWidget(body);
		currentDialog = dialog;
		dialog.show();
	}

	public static void unknownRPCError(String errorText) {
		DialogBox dialog = new DialogBox();
		VerticalPanel body = new VerticalPanel();
		Button btnOkay = new Button("Okay", goHome());

		dialog.setText("Unknown RPC Error");

		String bodyText = "An unknown error occured.  Please file a bug report with the information below:<br /><br />"
				+ errorText;

		body.add(new HTML(bodyText));
		body.add(btnOkay);
		dialog.setWidget(body);
		currentDialog = dialog;
		dialog.show();
	}

	private static ClickListener goHome() {

		return new ClickListener() {
			public void onClick(Widget sender) {
				currentDialog.hide();
				Module.redirect(Module.MODULE_FRHOME);
			}
		};
	}

}
