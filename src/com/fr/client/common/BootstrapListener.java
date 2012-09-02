package com.fr.client.common;

public interface BootstrapListener {
	public void onBootstrapComplete();

	public void onBootstrapStep(Bootstrap bootstrap, int step);
}
