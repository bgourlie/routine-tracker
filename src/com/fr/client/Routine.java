package com.fr.client;

import java.util.Iterator;

import com.fr.client.common.User;
import com.fr.client.data.DataBroker;
import com.fr.client.data.DataCollection;
import com.fr.client.data.DataResponse;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.SessionExercise;
import com.fr.client.data.types.UserRoutine;
import com.fr.client.rpc.CallbackScreener;
import com.fr.client.rpc.ExerciseServiceAccess;
import com.fr.client.rpc.RoutineServiceAccess;
import com.fr.client.rpc.ScreenedCallback;
import com.fr.client.ui.RoutineSessionEditor;
import com.fr.client.ui.RoutineSessionEditorListener;
import com.fr.client.ui.SessionExerciseEditorListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public final class Routine implements EntryPoint, RoutineSessionEditorListener,
		SessionExerciseEditorListener, ChangeListener, KeyboardListener {

	private long currentRoutineID = -1;

	private ExerciseServiceAccess exerciseService = new ExerciseServiceAccess();

	private ListBox lstRoutineSelector = new ListBox();

	// part of a hack to manually trigger the onchange function
	// within the addUserRoutineCallback
	private Routine routineModule = this;

	private RoutineServiceAccess routineService = new RoutineServiceAccess();

	private DataBroker routineSessionData = new DataBroker();

	private RoutineSessionEditor routineSessionEditor = new RoutineSessionEditor();

	private DataBroker sessionExerciseData = new DataBroker();

	private TextBox txtNewRoutine = new TextBox();

	private DataBroker userRoutineData = new DataBroker();

	private ScreenedCallback callbackAddRoutineSession() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {

				routineSessionData.dataInsertPermanent((DataResponse) result);
			}
		};
	}

	private ScreenedCallback callbackAddSessionExercise() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				sessionExerciseData.dataInsertPermanent((DataResponse) result);
			}
		};
	}

	private ScreenedCallback callbackAddUserRoutine() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				DataResponse dataResponse = (DataResponse) result;

				userRoutineData.dataInsertPermanent(dataResponse);
				lstRoutineSelector.addItem(((UserRoutine) dataResponse
						.getDataObject()).getRoutineName());
				lstRoutineSelector.setSelectedIndex(lstRoutineSelector
						.getItemCount() - 1);
				routineModule.onChange(lstRoutineSelector);

			}
		};
	}

	private ScreenedCallback callbackChangeSessionExercise() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				sessionExerciseData.dataInsertPermanent((DataResponse) result);
			}
		};
	}

	private ScreenedCallback callbackDeleteRoutineSession() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				routineSessionData.dataRemovePermanent((DataResponse) result);
			}
		};
	}

	private ScreenedCallback callbackDeleteSessionExercise() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				sessionExerciseData.dataRemovePermanent((DataResponse) result);
			}
		};
	}

	private ScreenedCallback callbackGetExerciseUOMS() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				routineSessionEditor.getSessionExerciseEditor()
						.setExerciseUOMS((DataCollection) result);
			}
		};
	}

	private ScreenedCallback callbackGetRoutineSessions() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				routineSessionData.setDataCollection((DataCollection) result);
			}
		};
	}

	private ScreenedCallback callbackGetSessionExercises() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {
				sessionExerciseData.setDataCollection((DataCollection) result);
			}
		};
	}

	private ScreenedCallback callbackGetUserExercises() {
		return new ScreenedCallback() {
			public void onSuccess(Object result) {
				routineSessionEditor.getSessionExerciseEditor()
						.setUserExercises((DataCollection) result);
			}
		};
	}

	private ScreenedCallback callbackGetUserRoutines() {
		return new ScreenedCallback() {

			public void onSuccess(Object result) {

				DataCollection data = (DataCollection) result;
				userRoutineData.setDataCollection(data);
				lstRoutineSelector.clear();

				if (data.getDataCount() > 0) {
					lstRoutineSelector.addItem("Select One...");
					for (Iterator i = data.iterator(); i.hasNext();) {

						lstRoutineSelector.addItem(((UserRoutine) i.next())
								.getRoutineName());
					}
				} else {
					lstRoutineSelector.addItem("You have no Routines.");
				}
			}
		};
	}

	public void onChange(Widget sender) {
		if (sender == lstRoutineSelector) {
			if (lstRoutineSelector.getSelectedIndex() > 0) {
				long routineID = ((UserRoutine) userRoutineData
						.getDataObject(lstRoutineSelector.getSelectedIndex() - 1))
						.getUserRoutineID();

				if (routineID != currentRoutineID) {
					currentRoutineID = routineID;
					routineService.getService().getRoutineSessions(
							User.instance().getToken(), routineID,
							new CallbackScreener(callbackGetRoutineSessions()));
					if (!routineSessionEditor.isVisible()) {
						routineSessionEditor.setVisible(true);
					}
				}
			}
		}

	}

	public void onKeyDown(Widget sender, char keyCode, int modifiers) {

	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
		if (sender == txtNewRoutine && keyCode == KEY_ENTER
				&& txtNewRoutine.getText().length() > 0) {

			UserRoutine userRoutine = new UserRoutine();
			userRoutine.setRoutineName(txtNewRoutine.getText());

			String requestID = userRoutineData.dataAddTemporary(userRoutine);
			routineService.getService().addUserRoutine(
					User.instance().getToken(), requestID, userRoutine,
					new CallbackScreener(callbackAddUserRoutine()));
			txtNewRoutine.setText("");
		}
	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {

	}

	public void onModuleLoad() {

		/*
		 * Layout Stuff
		 */

		routineSessionEditor.setVisible(false);
		RootPanel.get("fr-widgetLstRoutineSelector").add(lstRoutineSelector);
		RootPanel.get("fr-widgetTxtNewRoutine").add(txtNewRoutine);
		RootPanel.get("fr-widgetRoutineEditor").add(routineSessionEditor);
		/*
		 * Register Listeners
		 */

		lstRoutineSelector.addChangeListener(this);
		txtNewRoutine.addKeyboardListener(this);
		routineSessionEditor.addRoutineEditorListener(this);
		routineSessionEditor.getSessionExerciseEditor()
				.addRoutineSessionEditorListener(this);

		routineSessionData.addDataListener(routineSessionEditor);

		sessionExerciseData.addDataListener(routineSessionEditor
				.getSessionExerciseEditor());

		/*
		 * RPC's
		 */

		exerciseService.getService().getUOMS(
				new CallbackScreener(callbackGetExerciseUOMS()));

		exerciseService.getService().getUserExercises(
				User.instance().getToken(),
				new CallbackScreener(callbackGetUserExercises()));

		routineService.getService().getUserRoutines(User.instance().getToken(),
				new CallbackScreener(callbackGetUserRoutines()));
	}

	public void onRoutineSessionAdded(String sessionName, int index) {

		int orderIndex;
		String requestID;

		if (index == -1) {
			orderIndex = routineSessionData.getDataCount();
		} else {
			orderIndex = index;
		}

		RoutineSession routineSession = new RoutineSession();
		routineSession.setUserRoutineID(currentRoutineID);
		routineSession.setRoutineSessionName(sessionName);
		routineSession.setOrderIndex(orderIndex);

		if (index == -1) {
			requestID = routineSessionData.dataAddTemporary(routineSession);
		} else {
			requestID = routineSessionData.dataAddTemporary(routineSession,
					index);
		}

		routineService.getService().addRoutineSession(
				User.instance().getToken(), requestID, routineSession,
				new CallbackScreener(callbackAddRoutineSession()));

	}

	public void onRoutineSessionDeleted(int index) {
		routineService.getService().deleteRoutineSession(
				User.instance().getToken(),
				routineSessionData.dataRemoveTemporary(index),
				((RoutineSession) routineSessionData.getDataObject(index))
						.getRoutineSessionID(),
				new CallbackScreener(callbackDeleteRoutineSession()));

	}

	public void onRoutineSessionSelected(int index) {

		routineService.getService().getSessionExercises(
				User.instance().getToken(),
				((RoutineSession) routineSessionData.getDataObject(index))
						.getRoutineSessionID(),
				new CallbackScreener(callbackGetSessionExercises()));

	}

	public void onSessionExerciseAdded(SessionExercise sessionExercise) {

		String requestID = sessionExerciseData
				.dataAddTemporary(sessionExercise);

		routineService.getService().addSessionExercise(
				User.instance().getToken(), requestID, sessionExercise,
				new CallbackScreener(callbackAddSessionExercise()));
	}

	public void onSessionExerciseChanged(SessionExercise sessionExercise) {

		String requestID = sessionExerciseData.dataReplaceTemporary(
				sessionExercise, sessionExercise.getOrderIndex());

		routineService.getService().changeSessionExercise(
				User.instance().getToken(), requestID, sessionExercise,
				new CallbackScreener(callbackChangeSessionExercise()));

	}

	public void onSessionExerciseDeleted(SessionExercise sessionExercise) {

		String requestID = sessionExerciseData
				.dataRemoveTemporary(sessionExercise.getOrderIndex());

		routineService.getService().deleteSessionExercise(
				User.instance().getToken(), requestID,
				sessionExercise.getSessionExerciseID(),
				new CallbackScreener(callbackDeleteSessionExercise()));

	}

}
