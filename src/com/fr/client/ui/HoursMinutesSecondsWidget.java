package com.fr.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

class HoursMinutesSecondsWidget extends Composite {
	private final HorizontalPanel panel = new HorizontalPanel();

	private final TextBox txtHours = new TextBox();

	private final ListBox lstMinutes = new ListBox();

	private final ListBox lstSeconds = new ListBox();

	public HoursMinutesSecondsWidget() {
		String s;
		for (int i = 0; i <= 60; i++) {
			s = String.valueOf(i);
			lstMinutes.addItem(s);
			lstSeconds.addItem(s);
		}
		txtHours.setWidth("50px");
		panel.add(txtHours);
		panel.add(new HTML("hours"));
		panel.add(lstMinutes);
		panel.add(new HTML("minutes"));
		panel.add(lstSeconds);
		panel.add(new HTML("seconds"));
		initWidget(panel);
	}

	public void reset() {
		txtHours.setText("0");
		lstMinutes.setSelectedIndex(0);
		lstSeconds.setSelectedIndex(0);
	}

	public int getSeconds() {
		try {
			return (Integer.parseInt(txtHours.getText().trim()) * 3600)
					+ (Integer.parseInt(lstMinutes.getItemText(lstMinutes
							.getSelectedIndex())) * 60)
					+ (Integer.parseInt(lstSeconds.getItemText(lstSeconds
							.getSelectedIndex())));
		} catch (Exception e) {
			return 0;
		}
	}

	public void setWidgetValues(int seconds) {
		txtHours.setText(String.valueOf(seconds / 3600));
		lstMinutes.setSelectedIndex(listBoxIndexByItemString(lstMinutes, String
				.valueOf((seconds % 3600) / 60)));
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
