package com.fr.client.ui;

import java.util.Iterator;
import java.util.Vector;

import com.fr.client.data.types.SessionExercise;

public class SessionExerciseEditorListenerCollection extends Vector {
	static final long serialVersionUID = 2L;

	public void fireOnSessionExerciseAdded(SessionExercise sessionExercise) {
		for (Iterator it = iterator(); it.hasNext();) {
			SessionExerciseEditorListener listener = (SessionExerciseEditorListener) it
					.next();
			listener.onSessionExerciseAdded(sessionExercise);
		}
	}

	public void fireOnSessionExerciseChanged(SessionExercise sessionExercise) {
		for (Iterator it = iterator(); it.hasNext();) {
			SessionExerciseEditorListener listener = (SessionExerciseEditorListener) it
					.next();
			listener.onSessionExerciseChanged(sessionExercise);
		}
	}

	public void fireOnSessionExerciseDeleted(SessionExercise sessionExercise) {
		for (Iterator it = iterator(); it.hasNext();) {
			SessionExerciseEditorListener listener = (SessionExerciseEditorListener) it
					.next();
			listener.onSessionExerciseDeleted(sessionExercise);
		}
	}

}
