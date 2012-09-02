package com.fr.client.ui;

import java.util.Iterator;
import java.util.Vector;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataRelation;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.RoutineSessionData;
import com.fr.client.data.types.SessionExercise;
import com.fr.client.data.types.SessionExerciseData;
import com.fr.client.data.types.UserExercise;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkoutRecorder extends Composite implements ClickListener {

	private static final int COLUMN_ACTUAL = 2;

	private static final int COLUMN_EXERCISE = 0;

	private static final int COLUMN_NOTE = 3;

	private static final int COLUMN_TARGET = 1;

	private final Button btnSave = new Button("Save Workout");

	private final Button btnSkipWorkout = new Button("Skip Workout");

	private final HorizontalPanel buttonPanel = new HorizontalPanel();

	private DataCollection exerciseUOMS = null;

	private final HTML htmlHeader = new HTML();

	private WorkoutRecorderListenerCollection listeners = null;

	private final VerticalPanel mainPanel = new VerticalPanel();

	private final RecordTable recordTable = new RecordTable();

	private RoutineSession routineSession = null;

	private DataCollection sessionExercises = null;

	private Vector txtNotes = null;

	private final TextBox txtRoutineSessionNote = new TextBox();

	private Vector uomInputs = null;

	private DataCollection userExercises = null;

	public WorkoutRecorder() {

		btnSave.addClickListener(this);
		btnSkipWorkout.addClickListener(this);
		initWidget(mainPanel);
		mainPanel.add(htmlHeader);
		mainPanel.add(recordTable);
		mainPanel.add(txtRoutineSessionNote);
		mainPanel.add(buttonPanel);
		buttonPanel.add(btnSave);
		buttonPanel.add(btnSkipWorkout);
		String[] columnHeaders = { "Exercise", "Target", "Actual",
				"Note (optional)" };
		String[] alternatingRowStyles = { "alternatingRow1", "alternatingRow2" };

		recordTable.setColumnHeaders(columnHeaders);
		recordTable.setAlternatingRowStyles(alternatingRowStyles);
		recordTable.init();
	}

	public void addWorkoutRecorderListener(WorkoutRecorderListener listener) {
		if (listeners == null) {
			listeners = new WorkoutRecorderListenerCollection();
		}
		listeners.add(listener);

	}

	public void onClick(Widget sender) {
		if (sender == btnSave && validateWorkout()) {
			saveWorkout();
		} else if (sender == btnSkipWorkout) {
			skipWorkout();
		}
	}

	private void saveWorkout() {
		RoutineSessionData routineSessionData = new RoutineSessionData();
		routineSessionData.setRoutineSessionID(routineSession
				.getRoutineSessionID());
		routineSessionData.setNote(txtRoutineSessionNote.getText());
		routineSessionData.setStatus("completed");

		txtRoutineSessionNote.setText("");
		DataCollection sessionExerciseData = new DataCollection();

		for (int i = 0; i < sessionExercises.getDataCount(); i++) {
			SessionExercise se = (SessionExercise) sessionExercises
					.getDataObject(i);

			UserExercise ue = DataRelation.userExerciseByID(userExercises, se
					.getUserExerciseID());

			int actual = UOMOracle.getBaseValue(DataRelation.exerciseUOMByName(
					exerciseUOMS, ue.getDefaultUOM()), ((UOMInput) uomInputs
					.get(i)).getValue());

			String note = ((TextBox) txtNotes.get(i)).getText();

			// TODO secure this: on the server side of things, we do not
			// check that the user owns the specified sessionExercise()
			// when the session exercise data is saved. This would take a
			// lot of
			// hacking on the part of the user, but it is there
			// and could lead to a user adding exercise data to a
			// routine session he doesnt own
			SessionExerciseData sed = new SessionExerciseData();
			sed.setSessionExerciseID(se.getSessionExerciseID());
			sed.setActual(actual);
			sed.setNote(note);
			sed.setStatus("completed");
			sessionExerciseData.dataAddPermanent(sed);
		}

		listeners.fireOnWorkoutSaved(routineSessionData, sessionExerciseData);
	}

	public void setExerciseUOMS(DataCollection exerciseUOMS) {
		this.exerciseUOMS = exerciseUOMS;
	}

	public void setRoutineSession(RoutineSession routineSession) {

		recordTable.clearRecords();
		this.routineSession = routineSession;
		htmlHeader.setHTML("Recording "
				+ routineSession.getRoutineSessionName());
	}

	public void setSessionExercises(DataCollection sessionExercises) {
		this.sessionExercises = sessionExercises;
		SessionExercise se;
		UserExercise ue;
		uomInputs = new Vector();
		txtNotes = new Vector();

		int curRow = 0;
		for (Iterator i = sessionExercises.iterator(); i.hasNext();) {
			se = (SessionExercise) i.next();
			ue = DataRelation.userExerciseByID(userExercises, se
					.getUserExerciseID());

			recordTable.insertRow(++curRow);
			recordTable.setText(curRow, COLUMN_EXERCISE, ue.getExerciseName());
			recordTable.setText(curRow, COLUMN_TARGET, UOMOracle
					.getFormattedValue(DataRelation.exerciseUOMByName(
							exerciseUOMS, ue.getDefaultUOM()), se.getTarget()));
			UOMInput uomInput = UOMInputFactory.getUOMInput(ue
					.getExerciseType());
			TextBox txtNote = new TextBox();
			uomInput.setUOM(DataRelation.exerciseUOMByName(exerciseUOMS, ue
					.getDefaultUOM()));

			recordTable.setWidget(curRow, COLUMN_ACTUAL, uomInput.getWidget());
			recordTable.setWidget(curRow, COLUMN_NOTE, txtNote);

			uomInputs.add(uomInput);
			txtNotes.add(txtNote);
		}

		recordTable.applyAlternatingRowStyles(0);
	}

	public void setUserExercises(DataCollection userExercises) {
		this.userExercises = userExercises;
	}

	private void skipWorkout() {

		RoutineSessionData routineSessionData = new RoutineSessionData();
		routineSessionData.setRoutineSessionID(routineSession
				.getRoutineSessionID());
		routineSessionData.setNote(txtRoutineSessionNote.getText());
		routineSessionData.setStatus("skipped");

		txtRoutineSessionNote.setText("");
		listeners.fireOnWorkoutSaved(routineSessionData, null);
	}

	private boolean validateWorkout() {
		return true;
	}

}
