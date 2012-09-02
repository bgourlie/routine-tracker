package com.fr.client.ui;

import com.fr.client.data.types.ExerciseUOM;
import com.google.gwt.user.client.ui.Widget;

public abstract class UOMInput {

	private ExerciseUOM uom = null;

	Widget widget;

	public Widget getWidget() {
		return widget;
	}

	ExerciseUOM getUOM() {
		return uom;
	}

	public void setUOM(ExerciseUOM uom) {
		this.uom = uom;
		initWidget();
	}

	public abstract void resetWidget();

	public abstract void setValue(double value);

	public abstract double getValue();

	protected abstract void initWidget();

}
