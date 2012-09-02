package com.fr.client.ui;

import com.fr.client.data.DataCollection;
import com.fr.client.data.types.RoutineSessionData;

public interface WorkoutRecorderListener {
	public void onSaveWorkout(RoutineSessionData routineSessionData,
			DataCollection sessionExerciseData);
}
