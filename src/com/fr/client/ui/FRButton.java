package com.fr.client.ui;

import com.google.gwt.user.client.ui.Button;

public class FRButton extends Button {
	public FRButton() {
		super.setStyleName("FRButton");
	}

	public FRButton(String buttonText) {
		super(buttonText);
		super.setStyleName("FRButton");
	}
}
