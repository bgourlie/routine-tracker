package com.fr.client;

import com.fr.client.common.User;
import com.fr.client.data.DataBroker;
import com.fr.client.data.DataCollection;
import com.fr.client.data.DataResponse;
import com.fr.client.data.types.UserExercise;
import com.fr.client.rpc.CallbackScreener;
import com.fr.client.rpc.ExerciseServiceAccess;
import com.fr.client.rpc.ScreenedCallback;
import com.fr.client.ui.UserExerciseEditor;
import com.fr.client.ui.UserExerciseEditorListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ExerciseManager implements EntryPoint, UserExerciseEditorListener {

	private final ExerciseServiceAccess exerciseService = new ExerciseServiceAccess();

	private final DataBroker userExerciseData = new DataBroker();

	private final UserExerciseEditor userExerciseEditor = new UserExerciseEditor();

	private ScreenedCallback callbackAddUserExercise() {
		return new ScreenedCallback() {
			public void onSuccess(Object result) {
				System.out.println("Great success");
				userExerciseData.dataInsertPermanent((DataResponse) result);
			}
		};
	}

	private ScreenedCallback callbackChangeUserExercise() {
		return new ScreenedCallback() {
			public void onSuccess(Object result) {
				System.out
						.println("Great success on change userExerciseCallback");
				userExerciseData.dataInsertPermanent((DataResponse) result);
			}
		};
	}

	private ScreenedCallback callbackDeleteUserExercise() {
		return new ScreenedCallback() {
			public void onSuccess(Object result) {
				System.out.println("Great success");
				userExerciseData.dataRemovePermanent((DataResponse) result);

			}
		};
	}

	private ScreenedCallback callbackGetUOMS() {
		return new ScreenedCallback() {
			public void onSuccess(Object result) {
				System.out.println("Great success");
				userExerciseEditor.setExerciseUOMs((DataCollection) result);
			}
		};
	}

	private ScreenedCallback callbackGetUserExercises() {
		return new ScreenedCallback() {
			public void onSuccess(Object result) {
				System.out.println("Great success");
				userExerciseData.setDataCollection((DataCollection) result);

			}
		};
	}

	public void onModuleLoad() {
		userExerciseData.addDataListener(userExerciseEditor);
		userExerciseEditor.addUserExerciseEditorListener(this);
		RootPanel.get().add(userExerciseEditor);
		exerciseService.getService().getUOMS(
				new CallbackScreener(callbackGetUOMS()));
		exerciseService.getService().getUserExercises(
				User.instance().getToken(),
				new CallbackScreener(callbackGetUserExercises()));
	}

	public void onUserExerciseAdded(UserExercise userExercise) {
		System.out.println("user exercise added");
		String requestID = userExerciseData.dataAddTemporary(userExercise);
		exerciseService.getService().addUserExercise(
				User.instance().getToken(), requestID, userExercise,
				new CallbackScreener(callbackAddUserExercise()));

	}

	public void onUserExerciseChanged(UserExercise userExercise) {
		System.out.println("user exercise changed");
		String requestID = userExerciseData.dataReplaceTemporary(userExercise,
				userExercise.getOrderIndex());

		exerciseService.getService().changeUserExercise(
				User.instance().getToken(), requestID, userExercise,
				new CallbackScreener(callbackChangeUserExercise()));

	}

	public void onUserExerciseDeleted(UserExercise userExercise) {
		System.out.println("user exercise deleted");
		String requestID = userExerciseData.dataRemoveTemporary(userExercise
				.getOrderIndex());

		exerciseService.getService().deleteUserExercise(
				User.instance().getToken(), requestID,
				userExercise.getUserExerciseID(),
				new CallbackScreener(callbackDeleteUserExercise()));

	}
}
