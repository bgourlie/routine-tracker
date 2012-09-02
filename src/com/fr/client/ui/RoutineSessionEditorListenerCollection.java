package com.fr.client.ui;

import java.util.Iterator;
import java.util.Vector;

public class RoutineSessionEditorListenerCollection extends Vector {
	static final long serialVersionUID = 10L;

	public void fireOnSessionAdded(String sessionName, int displayIndex) {
		System.out.println("firing onSessionAdded()");
		for (Iterator it = iterator(); it.hasNext();) {
			RoutineSessionEditorListener listener = (RoutineSessionEditorListener) it
					.next();
			listener.onRoutineSessionAdded(sessionName, displayIndex);
		}
	}

	public void fireOnSessionDeleted(int index) {
		System.out.println("firing onSessionDeleted()");
		for (Iterator it = iterator(); it.hasNext();) {
			RoutineSessionEditorListener listener = (RoutineSessionEditorListener) it
					.next();
			listener.onRoutineSessionDeleted(index);
		}
	}

	public void fireOnSessionSelected(int index) {
		System.out.println("firing onSessionSelected()");
		for (Iterator it = iterator(); it.hasNext();) {
			RoutineSessionEditorListener listener = (RoutineSessionEditorListener) it
					.next();
			listener.onRoutineSessionSelected(index);
		}
	}
}
