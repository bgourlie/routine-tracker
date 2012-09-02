package com.fr.client.ui;

import com.google.gwt.user.client.ui.TextBox;

public class RepetitionInput extends UOMInput {

	public void resetWidget() {
		((TextBox) super.widget).setText("0");

	}

	protected void initWidget() {
		TextBox t = new TextBox();
		t.setWidth("50px");
		super.widget = t;
	}

	public void setValue(double value) {
		((TextBox) super.widget).setText(String.valueOf(value));

	}

	public double getValue() {
		return Double.parseDouble(((TextBox) super.widget).getText());
	}
}
