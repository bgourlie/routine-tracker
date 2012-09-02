package com.fr.client.ui;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FRTextBox extends TextBox implements FocusListener {

	private String label = null;

	public FRTextBox() {
		super.setStyleName("FRTextBox-unlabeled");
	}

	public FRTextBox(String label) {
		super.setStyleName("FRTextBox-labeled");
		setLabel(label);
	}

	public void setLabel(String label) {
		this.label = label;
		super.removeFocusListener(this);
		super.addFocusListener(this);
		super.setStyleName("FRTextBox-labeled");
		super.setText(label);

	}

	public void onFocus(Widget sender) {
		if (super.getText().trim().equals(label)) {
			super.setStyleName("FRTextBox-unlabeled");
			super.setText("");
		}

	}

	public String getText() {
		if (super.getText().equals(label)) {
			return null;
		} else {
			return super.getText();
		}
	}

	public void onLostFocus(Widget sender) {
		if (super.getText().trim().equals("")) {
			super.setStyleName("FRTextBox-labeled");
			super.setText(label);
		}
	}
}
