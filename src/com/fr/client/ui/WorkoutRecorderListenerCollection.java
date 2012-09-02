package com.fr.client.ui;

import java.util.Iterator;
import java.util.Vector;

import com.fr.client.data.DataCollection;
import com.fr.client.data.types.RoutineSessionData;

public class WorkoutRecorderListenerCollection extends Vector {
	static final long serialVersionUID = 2L;

	public void fireOnWorkoutSaved(RoutineSessionData routineSessionData,
			DataCollection sessionExerciseData) {
		for (Iterator it = iterator(); it.hasNext();) {
			WorkoutRecorderListener listener = (WorkoutRecorderListener) it
					.next();
			listener.onSaveWorkout(routineSessionData, sessionExerciseData);
		}
	}

}