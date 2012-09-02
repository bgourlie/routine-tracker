package com.fr.client.ui;

import java.util.Iterator;
import java.util.Vector;

import com.fr.client.data.types.UserExercise;

public class UserExerciseEditorListenerCollection extends Vector {
	static final long serialVersionUID = 2L;

	public void fireOnUserExerciseAdded(UserExercise userExercise) {
		for (Iterator it = iterator(); it.hasNext();) {
			UserExerciseEditorListener listener = (UserExerciseEditorListener) it
					.next();
			listener.onUserExerciseAdded(userExercise);
		}
	}

	public void fireOnUserExerciseChanged(UserExercise userExercise) {
		for (Iterator it = iterator(); it.hasNext();) {
			UserExerciseEditorListener listener = (UserExerciseEditorListener) it
					.next();
			listener.onUserExerciseChanged(userExercise);
		}
	}

	public void fireOnUserExerciseDeleted(UserExercise userExercise) {
		for (Iterator it = iterator(); it.hasNext();) {
			UserExerciseEditorListener listener = (UserExerciseEditorListener) it
					.next();
			listener.onUserExerciseDeleted(userExercise);
		}
	}

}
