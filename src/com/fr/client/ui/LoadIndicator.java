package com.fr.client.ui;

import com.fr.client.common.Bootstrap;
import com.fr.client.common.BootstrapListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoadIndicator extends Composite implements BootstrapListener {

	private final Image loadingImage = new Image("loading.gif");

	private final HTML percentageIndicator = new HTML("Loading... 0%");

	public LoadIndicator() {
		VerticalPanel panel = new VerticalPanel();
		panel.add(loadingImage);
		panel.add(percentageIndicator);
		initWidget(panel);
	}

	public void onBootstrapComplete() {
		// TODO Auto-generated method stub

	}

	public void onBootstrapStep(Bootstrap bootstrap, int step) {
		percentageIndicator.setHTML("Loading... "
				+ (int) (step / bootstrap.getBootstrapSteps() * 100) + "%");

	}

}
