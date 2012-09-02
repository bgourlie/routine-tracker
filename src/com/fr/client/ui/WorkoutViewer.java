package com.fr.client.ui;

import java.util.Iterator;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataRelation;
import com.fr.client.data.types.ExerciseUOM;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.RoutineSessionData;
import com.fr.client.data.types.SessionExercise;
import com.fr.client.data.types.SessionExerciseData;
import com.fr.client.data.types.UserExercise;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public final class WorkoutViewer extends Composite implements ClickListener {

	private final RecordTable recordTable = new RecordTable();

	private final VerticalPanel mainPanel = new VerticalPanel();

	private final HorizontalPanel buttonPanel = new HorizontalPanel();

	private final Button btnPreviousWorkout = new Button("<< Previous Workout");

	private final Button btnNextWorkout = new Button("Next Workout >>");

	private final Button btnLastCompletedWorkout = new Button(
			"Last Completed Workout");

	private RoutineSession routineSession = null;

	private RoutineSessionData routineSessionData = null;

	private DataCollection userExercises = null;

	private DataCollection sessionExercises = null;

	private DataCollection exerciseUOMS = null;

	private static final int COLUMN_EXERCISE = 0;

	private static final int COLUMN_ACTUAL = 1;

	private static final int COLUMN_NOTE = 2;

	public WorkoutViewer() {
		buttonPanel.add(btnPreviousWorkout);
		buttonPanel.add(btnLastCompletedWorkout);
		buttonPanel.add(btnNextWorkout);

		mainPanel.add(buttonPanel);
		mainPanel.add(recordTable);

		initWidget(mainPanel);

		String[] columnHeaders = { "Exercise", "Actual", "Note" };

		String[] alternatingRowStyles = { "alternatingRow1", "alternatingRow2" };

		recordTable.setColumnHeaders(columnHeaders);
		recordTable.setAlternatingRowStyles(alternatingRowStyles);
		recordTable.init();
	}

	public void setRoutineSession(RoutineSession routineSession) {

		this.routineSession = routineSession;
		this.routineSessionData = null;
	}

	public void setRoutineSessionData(RoutineSessionData routineSessionData) {

		if (routineSession == null
				|| routineSessionData.getRoutineSessionID() != routineSession
						.getRoutineSessionID()) {
			throw new RuntimeException(
					"routineSessionData does not correspond to the current routineSession or routineSession is null");
		}

		this.routineSessionData = routineSessionData;
	}

	public void setExerciseUOMS(DataCollection exerciseUOMS) {
		this.exerciseUOMS = exerciseUOMS;
	}

	public void setUserExercises(DataCollection userExercises) {
		this.userExercises = userExercises;
	}

	public void setSessionExerciseData(DataCollection sessionExerciseData) {

		if (routineSession == null || routineSessionData == null
				|| userExercises == null || exerciseUOMS == null) {
			throw new RuntimeException(
					"Attempt to populate Workout Viewer without requisite information");
		}
		recordTable.clearRecords();

		int curRow = 0;
		SessionExerciseData sed;
		SessionExercise se;
		UserExercise ue;
		ExerciseUOM euom;

		for (Iterator i = sessionExerciseData.iterator(); i.hasNext();) {
			sed = (SessionExerciseData) i.next();
			se = DataRelation.sessionExerciseByID(sessionExercises, sed
					.getSessionExerciseID());
			ue = DataRelation.userExerciseByID(userExercises, se
					.getUserExerciseID());
			euom = DataRelation.exerciseUOMByName(exerciseUOMS, ue
					.getDefaultUOM());

			recordTable.insertRow(++curRow);

			recordTable.setText(curRow, COLUMN_EXERCISE, ue.getExerciseName());
			recordTable.setText(curRow, COLUMN_ACTUAL, UOMOracle
					.getFormattedValue(euom, sed.getActual()));
			recordTable.setText(curRow, COLUMN_NOTE, sed.getNote());
		}

	}

	public void onClick(Widget sender) {

		if (sender == btnPreviousWorkout) {

		} else if (sender == btnNextWorkout) {

		} else if (sender == btnLastCompletedWorkout) {

		}

	}

}
