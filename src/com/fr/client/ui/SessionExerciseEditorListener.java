package com.fr.client.ui;

import com.fr.client.data.types.SessionExercise;

public interface SessionExerciseEditorListener extends java.util.EventListener {

	public void onSessionExerciseAdded(SessionExercise sessionExercise);

	public void onSessionExerciseChanged(SessionExercise sessionExercise);

	public void onSessionExerciseDeleted(SessionExercise sessionExercise);

}
