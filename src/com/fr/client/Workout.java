/**
 * step 1 - determine which workout we are recording
 *
 */

package com.fr.client;

import com.fr.client.common.Bootstrap;
import com.fr.client.common.BootstrapListener;
import com.fr.client.common.User;
import com.fr.client.data.DataCollection;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.RoutineSessionData;
import com.fr.client.rpc.CallbackScreener;
import com.fr.client.rpc.ExerciseServiceAccess;
import com.fr.client.rpc.RoutineServiceAccess;
import com.fr.client.rpc.ScreenedCallback;
import com.fr.client.ui.WorkoutRecorder;
import com.fr.client.ui.WorkoutRecorderListener;
import com.fr.client.ui.WorkoutViewer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public final class Workout implements EntryPoint, BootstrapListener,
		WorkoutRecorderListener {

	private final ExerciseServiceAccess exerciseService = new ExerciseServiceAccess();

	private final RoutineServiceAccess routineService = new RoutineServiceAccess();

	private final WorkoutRecorder workoutRecorder = new WorkoutRecorder();

	private final WorkoutViewer workoutViewer = new WorkoutViewer();

	private DataCollection sessionExercises;

	private DataCollection exerciseUOMS;

	private DataCollection userExercises;

	private RoutineSession currentRoutineSession;

	private RoutineSessionData currentRoutineSessionData;

	private DataCollection currentSessionExerciseData;

	private ScreenedCallback callbackGetExerciseUOMS() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				exerciseUOMS = (DataCollection) result;
				workoutRecorder.setExerciseUOMS(exerciseUOMS);
				workoutViewer.setExerciseUOMS(exerciseUOMS);
			}
		};
	}

	private ScreenedCallback callbackGetNextRoutineSession() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				currentRoutineSession = (RoutineSession) result;
				workoutRecorder.setRoutineSession(currentRoutineSession);
				workoutViewer.setRoutineSession(currentRoutineSession);
			}
		};
	}

	private ScreenedCallback callbackGetSessionExercises() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				sessionExercises = (DataCollection) result;
				workoutRecorder.setSessionExercises(sessionExercises);

			}
		};
	}

	private ScreenedCallback callbackGetUserExercises() {
		return new ScreenedCallback() {
			public void onSuccess(Object result) {
				userExercises = (DataCollection) result;
				workoutRecorder.setUserExercises(userExercises);
				workoutViewer.setUserExercises(userExercises);
			}
		};
	}

	private ScreenedCallback callbackSaveRoutineSession() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				routineService.getService().getNextRoutineSession(
						User.instance().getToken(),
						new CallbackScreener(callbackGetNextRoutineSession()));
			}
		};
	}

	private ScreenedCallback callbackGetRoutineSessionData() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				currentRoutineSessionData = (RoutineSessionData) result;
			}
		};
	}

	private ScreenedCallback callbackGetSessionExerciseData() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				currentSessionExerciseData = (DataCollection) result;
			}
		};
	}

	public void onBootstrapComplete() {
		System.out.println("Bootstrap Complete");

		RootPanel.get("Workout-workoutViewer").add(workoutViewer);
		RootPanel.get("Workout-workoutRecorder").add(workoutRecorder);
	}

	public void onBootstrapStep(Bootstrap bootstrap, int step) {
		switch (step) {
		case 1:
			exerciseService.getService().getUOMS(bootstrap.getCallback());
			break;
		case 2:
			exerciseService.getService().getUserExercises(
					User.instance().getToken(), bootstrap.getCallback());
			break;
		case 3:
			routineService.getService().getNextRoutineSession(
					User.instance().getToken(), bootstrap.getCallback());
			break;
		case 4:
			routineService.getService().getSessionExercises(
					User.instance().getToken(),
					currentRoutineSession.getRoutineSessionID(),
					bootstrap.getCallback());
			break;
		case 5:

		}

	}

	public void onModuleLoad() {
		workoutRecorder.addWorkoutRecorderListener(this);

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.addBootstrapListener(this);
		bootstrap.addStep(callbackGetExerciseUOMS());
		bootstrap.addStep(callbackGetUserExercises());
		bootstrap.addStep(callbackGetNextRoutineSession());
		bootstrap.addStep(callbackGetSessionExercises());

		bootstrap.execute();

	}

	public void onSaveWorkout(RoutineSessionData routineSessionData,
			DataCollection sessionExerciseData) {
		routineService.getService().saveRoutineSession(
				User.instance().getToken(), routineSessionData,
				sessionExerciseData,
				new CallbackScreener(callbackSaveRoutineSession()));

	}
}
