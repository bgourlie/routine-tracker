package com.fr.client.ui;

import com.google.gwt.user.client.ui.TextBox;

public class DurationInput extends UOMInput {

	public void resetWidget() {
		if (super.getUOM().getUOM().equals("seconds")) {
			((TextBox) super.widget).setText("0");
		} else if (super.getUOM().getUOM().equals("minutes:seconds")) {
			((MinutesSecondsWidget) super.widget).reset();
		} else if (super.getUOM().getUOM().equals("hours:minutes:seconds")) {
			((HoursMinutesSecondsWidget) super.widget).reset();
		}

	}

	protected void initWidget() {
		if (super.getUOM().getUOM().equals("seconds")) {
			super.widget = new TextBox();
		} else if (super.getUOM().getUOM().equals("minutes:seconds")) {
			super.widget = new MinutesSecondsWidget();
		} else if (super.getUOM().getUOM().equals("hours:minutes:seconds")) {
			super.widget = new HoursMinutesSecondsWidget();
		}

	}

	public void setValue(double value) {
		if (super.getUOM().getUOM().equals("seconds")) {
			((TextBox) super.widget).setText(String.valueOf(value));
		} else if (super.getUOM().getUOM().equals("minutes:seconds")) {
			((MinutesSecondsWidget) super.widget).setWidgetValues((int) value);
		} else if (super.getUOM().getUOM().equals("hours:minutes:seconds")) {
			((HoursMinutesSecondsWidget) super.widget)
					.setWidgetValues((int) value);
		}

	}

	public double getValue() {
		if (super.getUOM().getUOM().equals("seconds")) {
			return Double.parseDouble(((TextBox) super.widget).getText());
		} else if (super.getUOM().getUOM().equals("minutes:seconds")) {
			return (double) ((MinutesSecondsWidget) super.widget).getSeconds();
		} else if (super.getUOM().getUOM().equals("hours:minutes:seconds")) {
			return (double) ((HoursMinutesSecondsWidget) super.widget)
					.getSeconds();
		} else {
			throw new RuntimeException(
					"Runtime exception in getValue (DurationInput)");
		}
	}
}
