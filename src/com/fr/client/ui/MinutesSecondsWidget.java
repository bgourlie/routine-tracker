package com.fr.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

class MinutesSecondsWidget extends Composite {
	private final HorizontalPanel panel = new HorizontalPanel();

	private final TextBox txtMinutes = new TextBox();

	private final ListBox lstSeconds = new ListBox();

	public MinutesSecondsWidget() {
		String s;
		for (int i = 0; i <= 60; i++) {
			s = String.valueOf(i);
			lstSeconds.addItem(s);
		}
		txtMinutes.setWidth("50px");
		panel.add(txtMinutes);
		panel.add(new HTML("minutes"));
		panel.add(lstSeconds);
		panel.add(new HTML("seconds"));
		initWidget(panel);
	}

	public void reset() {
		txtMinutes.setText("0");
		lstSeconds.setSelectedIndex(0);
	}

	public int getSeconds() {
		try {
			return (Integer.parseInt(txtMinutes.getText().trim()) * 60)
					+ (Integer.parseInt(lstSeconds.getItemText(lstSeconds
							.getSelectedIndex())));
		} catch (Exception e) {
			return 0;
		}
	}

	public void setWidgetValues(int seconds) {

		txtMinutes.setText(String.valueOf(seconds / 60));
		lstSeconds.setSelectedIndex(listBoxIndexByItemString(lstSeconds, String
				.valueOf(seconds % 60)));
	}

	private int listBoxIndexByItemString(ListBox listBox, String itemString) {
		for (int i = 0; i < listBox.getItemCount(); i++) {
			if (listBox.getItemText(i).equals(itemString)) {
				return i;
			}
		}
		return -1;
	}

}
