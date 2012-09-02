package com.fr.client.ui;

import com.fr.client.data.types.UserExercise;

public interface UserExerciseEditorListener {
	public void onUserExerciseAdded(UserExercise userExercise);

	public void onUserExerciseChanged(UserExercise userExercise);

	public void onUserExerciseDeleted(UserExercise userExercise);
}
