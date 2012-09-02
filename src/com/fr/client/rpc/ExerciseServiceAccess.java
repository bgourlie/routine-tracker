package com.fr.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ExerciseServiceAccess extends ServiceAccess {

	private final static String EXERCISE_SERVICE = "ExerciseService";

	private ExerciseServiceAsync exerciseService = null;

	public ExerciseServiceAsync getService() {

		if (exerciseService == null) {
			initService();
		}

		return exerciseService;
	}

	protected void initService() {
		exerciseService = (ExerciseServiceAsync) GWT
				.create(ExerciseService.class);

		ServiceDefTarget endpoint = (ServiceDefTarget) exerciseService;
		endpoint.setServiceEntryPoint(getServiceURL(EXERCISE_SERVICE));
	}

}