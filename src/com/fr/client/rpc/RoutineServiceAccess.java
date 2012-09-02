package com.fr.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class RoutineServiceAccess extends ServiceAccess {

	private final static String ROUTINE_SERVICE = "RoutineService";

	private RoutineServiceAsync routineService = null;

	public RoutineServiceAsync getService() {

		if (routineService == null) {
			initService();
		}

		return routineService;
	}

	protected void initService() {
		routineService = (RoutineServiceAsync) GWT.create(RoutineService.class);

		ServiceDefTarget endpoint = (ServiceDefTarget) routineService;
		endpoint.setServiceEntryPoint(getServiceURL(ROUTINE_SERVICE));
	}

}
