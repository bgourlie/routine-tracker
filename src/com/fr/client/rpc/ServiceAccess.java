package com.fr.client.rpc;

import com.google.gwt.core.client.GWT;

abstract class ServiceAccess {
	private static final String SERVICE_ROOT = "services/";

	protected String getServiceURL(String serviceName) {

		return GWT.getModuleBaseURL() + SERVICE_ROOT + serviceName;

	}

	protected abstract void initService();
}
